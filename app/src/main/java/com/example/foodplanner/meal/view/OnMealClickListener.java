package com.example.foodplanner.meal.view;


import com.example.foodplanner.Model.Meal;

public interface OnMealClickListener {
    void onFavMealClick(Meal meal);

    void onFavMealUnClick(Meal meal);
}