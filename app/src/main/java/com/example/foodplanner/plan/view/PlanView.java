package com.example.foodplanner.plan.view;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.Model.MealPlan;

import java.util.List;


public interface PlanView {
    void showSaturdayData(LiveData<List<MealPlan>> mealPlan);

    void showSundayData(LiveData<List<MealPlan>> mealPlan);

    void showMondayData(LiveData<List<MealPlan>> mealPlan);

    void showTuesdayData(LiveData<List<MealPlan>> mealPlan);

    void showWednesdayData(LiveData<List<MealPlan>> mealPlan);

    void showThursdayData(LiveData<List<MealPlan>> mealPlan);

    void showFridayData(LiveData<List<MealPlan>> mealPlan);

    public void removeMeal(MealPlan mealPlan);

    public void removeWeekPlan(String email);
    public void addToPlan(MealPlan mealPlan);
}