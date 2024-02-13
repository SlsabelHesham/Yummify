package com.example.foodplanner.meal.presenter;


import com.example.foodplanner.Model.Meal;

public interface MealPresenter {
    public void getMealDetails(String mealName);

    public void addToFav(Meal meal);

    public void removeFromFav(Meal meal);

    public boolean checkMealExist(String mealId);
}