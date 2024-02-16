package com.example.foodplanner.FavouriteMeals.View;


import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.Model.MealPlan;

public interface OnFavoriteClickListener {
    void onDeleteFavMealClick(Meal meal);

    void addToPlan(MealPlan mealPlan);
}