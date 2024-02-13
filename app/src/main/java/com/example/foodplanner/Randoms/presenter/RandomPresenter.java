package com.example.foodplanner.Randoms.presenter;


import com.example.foodplanner.Model.Meal;

public interface RandomPresenter {
    public void getRandoms();
    public void addToFav(Meal meal);
}