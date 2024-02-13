package com.example.foodplanner.AllMeals.presenter;


import com.example.foodplanner.Model.Meal;

public interface AllMealsPresenter {
    public void getAllMeals(String categoryName);

    public void getAllCountryMeals(String countryName);
    public void addToFav(Meal meal);
}