package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealService mealService;

    public MealServlet() {
        super();
        mealService = new MealServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            log.debug("show all meals");
            req.setAttribute("mealsTo", mealService.getAllMealsTo());
            req.getRequestDispatcher("/meals.jsp").forward(req, resp);
        }else if (action.equalsIgnoreCase("insert") || action.equalsIgnoreCase("edit")){
            log.debug("forward to meal info");
            String mealIdParam = req.getParameter("id");
            Integer mealId = null;
            if (mealIdParam != null) {
                mealId = Integer.parseInt(req.getParameter("id"));
            }
            req.setAttribute("meal", mealService.getMealFromId(mealId));
            req.getRequestDispatcher("/meal-info.jsp").forward(req, resp);
        }else if (action.equalsIgnoreCase("delete")) {
            log.debug("delete meal");
            String mealIdParam = req.getParameter("id");
            if (mealIdParam != null) {
                try {
                    mealService.deleteMealFromId(Integer.parseInt(mealIdParam));
                    log.debug("success delete");
                } catch (NumberFormatException e) {
                    log.error("error delete from id " + mealIdParam);
                }

            }
            req.setAttribute("mealsTo", mealService.getAllMealsTo());
            req.getRequestDispatcher("/meals.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mealIdParam = req.getParameter("id");
        String description = req.getParameter("description");
        String calories = req.getParameter("calories");
        String dateTime = req.getParameter("dateTime");

        try {
            mealService.saveMeal(mealIdParam.isEmpty()?null:Integer.parseInt(mealIdParam),
                    LocalDateTime.parse(dateTime),
                    description,
                    Integer.parseInt(calories));
            log.debug("success add or update meal " + mealIdParam);
        } catch (NumberFormatException e) {
            log.error("error add or update meal from id " + mealIdParam + " wrong data calories " + calories);
        } catch (DateTimeParseException e){
            log.error("error add or update meal from id " + mealIdParam + " wrong DATE " + dateTime);
        }

        req.setAttribute("mealsTo", mealService.getAllMealsTo());
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }
}
