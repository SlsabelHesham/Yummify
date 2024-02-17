package com.example.foodplanner.db;

import androidx.lifecycle.LiveData;


import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.Model.MealPlan;

import java.util.List;

public interface MealsLocalDataSource {
    void insertMeal(Meal meal);

    void deleteMeal(Meal meal);
    void deletePlanMeal(MealPlan mealPlan);

    void deletePlan(String email);
    LiveData<List<Meal>> getAllStoredMeals(String email);
    LiveData<List<MealPlan>> getPlan(String email , String day);

    public boolean checkMealExist(String mealId);

    void addMealToPlan(MealPlan mealPlan);
}