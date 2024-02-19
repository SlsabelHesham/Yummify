package com.example.foodplanner.meal.presenter;


import com.example.foodplanner.Model.Meal;

import io.reactivex.rxjava3.core.Single;

public interface MealPresenter {
    public void getMealDetails(String mealName);
    public void addToFav(Meal meal);
    public void removeFromFav(Meal meal);
    public Single<Boolean> checkMealExist(String mealId);
}