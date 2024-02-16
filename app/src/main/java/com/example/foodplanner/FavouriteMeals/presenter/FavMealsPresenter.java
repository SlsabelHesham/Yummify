package com.example.foodplanner.FavouriteMeals.presenter;

import androidx.lifecycle.LiveData;


import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.Model.MealPlan;

import java.util.List;

public interface FavMealsPresenter {
    public LiveData<List<Meal>> getStoredMeals(String email);
    public void removeFromFav(Meal meal);

    public void addMealToPlan(MealPlan mealPlan);
}