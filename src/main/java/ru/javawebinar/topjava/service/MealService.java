package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDateTime;
import java.util.List;

public interface MealService {
    List<MealTo> getAllMealsTo();
    void saveMeal(Integer id, LocalDateTime dateTime, String description, int calories);
    Meal getMealFromId(Integer id);
    void deleteMealFromId(Integer id);
}
