package com.example.foodplanner.plan.view;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.Model.MealPlan;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.flow.Flow;


public interface PlanView {
    void showSaturdayData(Flowable<List<MealPlan>> mealPlan);

    void showSundayData(Flowable<List<MealPlan>> mealPlan);

    void showMondayData(Flowable<List<MealPlan>> mealPlan);

    void showTuesdayData(Flowable<List<MealPlan>> mealPlan);

    void showWednesdayData(Flowable<List<MealPlan>> mealPlan);

    void showThursdayData(Flowable<List<MealPlan>> mealPlan);

    void showFridayData(Flowable<List<MealPlan>> mealPlan);

    public void removeMeal(MealPlan mealPlan);

    public void removeWeekPlan(String email);
    public void addToPlan(MealPlan mealPlan);
}