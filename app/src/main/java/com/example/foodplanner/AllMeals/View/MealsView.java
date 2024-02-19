package com.example.foodplanner.AllMeals.View;

import com.example.foodplanner.Model.Meal;

import java.util.List;

public interface MealsView {
    public void showData(List<Meal> products);
    public void showErrorMsg(String error);
}