package com.example.foodplanner.FavouriteMeals.presenter;

import androidx.lifecycle.LiveData;


import com.example.foodplanner.Model.Meal;

import java.util.List;

public interface FavMealsPresenter {
    public LiveData<List<Meal>> getStoredMeals();
    public void removeFromFav(Meal meal);
}