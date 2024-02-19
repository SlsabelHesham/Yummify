package com.example.foodplanner.plan.presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.Model.MealPlan;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface PlanPresenter {
    Flowable<List<MealPlan>> getWeekPlan(String email , String day);

    Flowable<List<MealPlan>> getAllWeekPlan(String email);

    public void removeFromPlan(MealPlan mealPlan);
    public void removeWeekPlan(String email);
    public void addMealToPlan(MealPlan mealPlan);
}