package com.example.foodplanner.plan.presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.FavouriteMeals.View.FavMealsView;
import com.example.foodplanner.FavouriteMeals.View.FavouritesFragment;
import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.Model.MealPlan;
import com.example.foodplanner.Model.MealsRepositoryImpl;
import com.example.foodplanner.plan.view.PlanFragment;
import com.example.foodplanner.plan.view.PlanView;

import java.util.List;

public class PlanPresenterImpl implements PlanPresenter {

    MealsRepositoryImpl repository;
    PlanView view;

    public PlanPresenterImpl(PlanFragment planFragment, MealsRepositoryImpl productsRepository) {
        this.repository = productsRepository;
        this.view = planFragment;
    }
/*
    @Override
    public LiveData<List<Meal>> getStoredMeals(String email) {
        return repository.getStoredMeals(email);
    }

    @Override
    public void removeFromFav(Meal meal) {
        repository.deleteMeal(meal);
    }*/

    @Override
    public LiveData<List<MealPlan>> getWeekPlan(String email , String day) {
        return repository.getPlan(email , day);
    }

    @Override
    public void removeFromPlan(MealPlan mealPlan) {
        repository.deletePlanMeal(mealPlan);
    }

    @Override
    public void removeWeekPlan(String email) {
        repository.deletePlan(email);
    }

    @Override
    public void addMealToPlan(MealPlan mealPlan) {
        repository.addMealToPlan(mealPlan);
    }
}
