package com.example.foodplanner.categories.presenter;


import com.example.foodplanner.Model.MealsRepositoryImpl;
import com.example.foodplanner.Network.NetworkCallback;
import com.example.foodplanner.categories.View.CategoryFragment;
import com.example.foodplanner.categories.View.CategoryView;

import java.util.List;

public class CategoryPresenterImpl implements NetworkCallback, CategoryPresenter {

    MealsRepositoryImpl repository;
    CategoryView view;


    public CategoryPresenterImpl(CategoryFragment categoryFragment, MealsRepositoryImpl productsRepository) {
        this.repository = productsRepository;
        this.view = categoryFragment;
    }

    @Override
    public void onSuccessResult(List categories) {
        view.showData(categories);
    }

    @Override
    public void onFailureResult(String errorMsg) {
        view.showErrorMsg(errorMsg);
    }

    @Override
    public void getCategories() {
        repository.getAllCategories(this);
    }
}
