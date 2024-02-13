package com.example.foodplanner.Randoms.View;

import com.example.foodplanner.Model.Meal;

import java.util.List;

public interface RandomView {
    public void showData(List<Meal> meals);
    public void showErrorMsg(String error);
    public void addMeal(Meal meal);
}