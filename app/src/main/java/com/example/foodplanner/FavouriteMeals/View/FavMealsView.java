package com.example.foodplanner.FavouriteMeals.View;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.Model.MealPlan;

import java.util.List;


public interface FavMealsView {
    public void showData(LiveData<List<Meal>> meals);
    public void removeMeal(Meal meal);

    public void addToPlan(MealPlan mealPlan);
}