package com.example.foodplanner.plan.presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.Model.MealPlan;

import java.util.List;

public interface PlanPresenter {
    public LiveData<List<MealPlan>> getWeekPlan(String email , String day);

    public void removeFromPlan(MealPlan mealPlan);
    public void removeWeekPlan(String email);
    public void addMealToPlan(MealPlan mealPlan);
}