package com.example.foodplanner.db;

import androidx.lifecycle.LiveData;


import com.example.foodplanner.Model.Meal;

import java.util.List;

public interface MealsLocalDataSource {
    void insertMeal(Meal meal);

    void deleteMeal(Meal meal);

    LiveData<List<Meal>> getAllStoredMeals(String email);

    public boolean checkMealExist(String mealId);
}