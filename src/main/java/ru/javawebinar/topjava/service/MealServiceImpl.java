package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.model.MealsData;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.List;

public class MealServiceImpl implements MealService{
    private static final int CALORIES_PER_DAY = 2000;

    @Override
    public List<MealTo> getAllMealsTo() {
        return MealsUtil.filteredByStreams(MealsData.getAllMeals(), null, null, CALORIES_PER_DAY);
    }

    @Override
    //public void saveMeal(Meal meal)
    public void saveMeal(Integer id, LocalDateTime dateTime, String description, int calories){
        Meal meal = new Meal(dateTime, description, calories);
        if (id != null) meal.setId(id);
        MealsData.saveMeal(meal);
    }

    @Override
    public Meal getMealFromId(Integer id) {
        return MealsData.getMealFromId(id);
    }

    @Override
    public void deleteMealFromId(Integer id) {
        MealsData.deleteMealFromId(id);
    }
}
