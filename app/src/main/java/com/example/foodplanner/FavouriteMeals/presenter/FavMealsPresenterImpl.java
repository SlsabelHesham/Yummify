package com.example.foodplanner.FavouriteMeals.presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.FavouriteMeals.View.FavMealsView;
import com.example.foodplanner.FavouriteMeals.View.FavouritesFragment;
import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.Model.MealsRepositoryImpl;

import java.util.List;

public class FavMealsPresenterImpl implements FavMealsPresenter {

    MealsRepositoryImpl repository;
    FavMealsView view;

    public FavMealsPresenterImpl(FavouritesFragment favouritesFragment, MealsRepositoryImpl productsRepository) {
        this.repository = productsRepository;
        this.view = favouritesFragment;
    }

    @Override
    public LiveData<List<Meal>> getStoredMeals() {
        return repository.getStoredMeals();
    }

    @Override
    public void removeFromFav(Meal meal) {
        repository.deleteMeal(meal);
    }
}
