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

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlanPresenterImpl implements PlanPresenter {

    MealsRepositoryImpl repository;
    PlanView view;
    FavMealsView mealsView;


    public PlanPresenterImpl(PlanFragment planFragment, MealsRepositoryImpl productsRepository) {
        this.repository = productsRepository;
        this.view = planFragment;
    }

    public PlanPresenterImpl(FavouritesFragment favouritesFragment, MealsRepositoryImpl instance) {
        this.repository = instance;
        this.mealsView = favouritesFragment;
    }

    @Override
    public Flowable<List<MealPlan>> getWeekPlan(String email , String day) {
        return repository.getPlan(email , day);
    }

    @Override
    public void removeFromPlan(MealPlan mealPlan) {
        Completable completable = repository.deletePlanMeal(mealPlan);
        completable.subscribeOn(Schedulers.io())
                .subscribe();
    }
    @Override
    public void removeWeekPlan(String email) {
        Completable completable = repository.deletePlan(email);
        completable.subscribeOn(Schedulers.io())
                .subscribe();
    }
    @Override
    public void addMealToPlan(MealPlan mealPlan) {
        Completable completable = repository.addMealToPlan(mealPlan);
        completable.subscribeOn(Schedulers.io())
                .subscribe();
    }
}
