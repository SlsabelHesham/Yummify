package com.example.foodplanner.AllMeals.presenter;

import com.example.foodplanner.search.view.AllCountryMealsFragment;
import com.example.foodplanner.search.view.AllIngredientMealsFragment;
import com.example.foodplanner.AllMeals.View.AllMealsFragment;
import com.example.foodplanner.AllMeals.View.MealsView;
import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.Model.MealsRepositoryImpl;
import com.example.foodplanner.Network.NetworkCallback;
import com.example.foodplanner.countries.View.CountryFragment;
import com.example.foodplanner.countries.View.CountryView;

import java.util.List;

public class AllMealsPresenterImpl implements NetworkCallback, AllMealsPresenter {

    MealsRepositoryImpl repository;
    MealsView view;

    CountryView countryView;



    public AllMealsPresenterImpl(AllMealsFragment allMealsFragment, MealsRepositoryImpl productsRepository) {
        this.repository = productsRepository;
        this.view = (MealsView) allMealsFragment;
    }

    public AllMealsPresenterImpl(CountryFragment countryFragment, MealsRepositoryImpl instance) {
        this.repository = instance;
        this.countryView = countryFragment;
    }

    public AllMealsPresenterImpl(AllCountryMealsFragment allCountryMealsFragment, MealsRepositoryImpl instance) {
        this.repository = instance;
        this.view = allCountryMealsFragment;
    }

    public AllMealsPresenterImpl(AllIngredientMealsFragment allIngredientMealsFragment, MealsRepositoryImpl instance) {
        this.repository = instance;
        this.view = allIngredientMealsFragment;
    }


    @Override
    public void getAllMeals(String categoryName) {
        repository.getAllMeals(this , categoryName);
    }

    @Override
    public void getAllCountryMeals(String countryName) {
        repository.getAllCountryMeals(this , countryName);
    }

    @Override
    public void getAllIngredientMeals(String ingredientName) {
        repository.getAllIngredientMeals(this , ingredientName);
    }

    @Override
    public void addToFav(Meal meal) {
        repository.insertMeal(meal);
    }

    @Override
    public void onSuccessResult(List list) {
        if(countryView != null){
            countryView.showPopularMeals(list);
        }
        else{
            view.showData(list);
        }
    }

    @Override
    public void onFailureResult(String errorMsg) {
        view.showErrorMsg(errorMsg);
    }
}
