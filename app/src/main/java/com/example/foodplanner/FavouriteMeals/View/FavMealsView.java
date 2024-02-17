package com.example.foodplanner.FavouriteMeals.View;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.Model.MealPlan;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.flow.Flow;


public interface FavMealsView {
    void showData(Flowable<List<Meal>> meals);
    public void removeMeal(Meal meal);

    public void addToPlan(MealPlan mealPlan);
}