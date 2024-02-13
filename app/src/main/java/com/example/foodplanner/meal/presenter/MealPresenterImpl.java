package com.example.foodplanner.meal.presenter;


import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.Model.MealsRepositoryImpl;
import com.example.foodplanner.Network.NetworkCallback;
import com.example.foodplanner.meal.view.MealFragment;
import com.example.foodplanner.meal.view.MealsView;

import java.util.List;

public class MealPresenterImpl implements NetworkCallback, MealPresenter {

    MealsRepositoryImpl repository;
    MealsView view;



    public MealPresenterImpl(MealFragment mealFragment, MealsRepositoryImpl productsRepository) {
        this.repository = productsRepository;
        this.view = mealFragment;
    }

    @Override
    public void onSuccessResult(List list) {
        view.showData((Meal) list.get(0));
    }

    @Override
    public void onFailureResult(String errorMsg) {
        view.showErrorMsg(errorMsg);
    }


    @Override
    public void getMealDetails(String mealName) {
        repository.getMeal(this , mealName);
    }

    @Override
    public void addToFav(Meal meal) {
        repository.insertMeal(meal);
    }

    @Override
    public void removeFromFav(Meal meal) {
        repository.deleteMeal(meal);
    }

    @Override
    public boolean checkMealExist(String mealId) {
         return repository.checkMealExist(mealId);
    }
}
