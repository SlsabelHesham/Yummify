package com.example.foodplanner.meal.view;

import com.example.foodplanner.Model.Meal;

import java.util.List;

public interface MealsView {
    public void showData(List<Meal> meal);
    public void showErrorMsg(String error);
    public void addMeal(Meal meal);
    public void removeMeal(Meal meal);
}