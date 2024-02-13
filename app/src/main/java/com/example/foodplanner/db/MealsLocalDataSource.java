package com.example.foodplanner.db;

import androidx.lifecycle.LiveData;


import com.example.foodplanner.Model.Meal;

import java.util.List;

public interface MealsLocalDataSource {
    void insertMeal(Meal product);

    void deleteMeal(Meal product);

    LiveData<List<Meal>> getAllStoredMeals();

    public boolean checkMealExist(String mealId);
}