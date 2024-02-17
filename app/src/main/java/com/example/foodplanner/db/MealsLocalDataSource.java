package com.example.foodplanner.db;

import androidx.lifecycle.LiveData;


import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.Model.MealPlan;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface MealsLocalDataSource {
    Flowable<List<Meal>> getAllStoredMeals(String email);
    Completable insertMeal(Meal meal);
    Completable deleteMeal(Meal meal);
    Single<Integer> checkMealExist(String mealId);



    Flowable<List<MealPlan>> getPlan(String email , String day);
    Completable deletePlanMeal(MealPlan mealPlan);
    Completable deletePlan(String email);
    Completable addMealToPlan(MealPlan mealPlan);
}