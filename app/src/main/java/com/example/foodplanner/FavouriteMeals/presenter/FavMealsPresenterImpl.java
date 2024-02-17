package com.example.foodplanner.FavouriteMeals.presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.FavouriteMeals.View.FavMealsView;
import com.example.foodplanner.FavouriteMeals.View.FavouritesFragment;
import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.Model.MealPlan;
import com.example.foodplanner.Model.MealsRepositoryImpl;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavMealsPresenterImpl implements FavMealsPresenter {

    MealsRepositoryImpl repository;
    FavMealsView view;

    public FavMealsPresenterImpl(FavouritesFragment favouritesFragment, MealsRepositoryImpl productsRepository) {
        this.repository = productsRepository;
        this.view = favouritesFragment;
    }

    @Override
    public Flowable<List<Meal>> getStoredMeals(String email) {
        return repository.getStoredMeals(email);
    }

    @Override
    public void removeFromFav(Meal meal) {
        Completable completable = repository.deleteMeal(meal);
        completable.subscribeOn(Schedulers.io())
                .subscribe();
    }

    @Override
    public void addMealToPlan(MealPlan mealPlan) {
        repository.addMealToPlan(mealPlan);
    }
}
