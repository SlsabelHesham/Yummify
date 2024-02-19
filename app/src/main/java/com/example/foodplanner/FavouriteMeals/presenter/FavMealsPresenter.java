package com.example.foodplanner.FavouriteMeals.presenter;

import androidx.lifecycle.LiveData;


import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.Model.MealPlan;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface FavMealsPresenter {
    Flowable<List<Meal>> getStoredMeals(String email);
    public void removeFromFav(Meal meal);

    public void addMealToPlan(MealPlan mealPlan);
}