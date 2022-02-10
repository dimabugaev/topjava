package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MealsData {
    private final static Map<Integer, Meal> meals = new ConcurrentHashMap<>();
    private volatile static int nextId = 0;

    public static List<Meal> getAllMeals(){
        return new ArrayList<>(meals.values());
    }

    public static void saveMeal(Meal meal){
        if (meal.getId() == null) {
            synchronized (meal) {
                meal.setId(nextId);
                nextId++;
            }
        }

        meals.put(meal.getId(), meal);
    }

    public static Meal getMealFromId(Integer id){
        if (id == null)
            return null;
        return meals.get(id);
    }

    public static void deleteMealFromId(Integer id){
        meals.remove(id);
    }
}
