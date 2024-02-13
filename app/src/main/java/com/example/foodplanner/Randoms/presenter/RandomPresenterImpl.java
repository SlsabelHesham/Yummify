package com.example.foodplanner.Randoms.presenter;


import com.example.foodplanner.Randoms.View.RandomView;
import com.example.foodplanner.Randoms.View.HomeFragment;
import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.Model.MealsRepositoryImpl;
import com.example.foodplanner.Network.NetworkCallback;

import java.util.List;

public class RandomPresenterImpl implements NetworkCallback, RandomPresenter {

    MealsRepositoryImpl repository;
    RandomView view;



    public RandomPresenterImpl(HomeFragment homeFragment, MealsRepositoryImpl productsRepository) {
        this.repository = productsRepository;
        this.view = homeFragment;
    }

    @Override
    public void getRandoms() {
        repository.getAllRandoms(this);
    }

    @Override
    public void addToFav(Meal meal) {
        repository.insertMeal(meal);
    }

    @Override
    public void onSuccessResult(List list) {
        view.showData(list);
    }

    @Override
    public void onFailureResult(String errorMsg) {
        view.showErrorMsg(errorMsg);
    }
}
