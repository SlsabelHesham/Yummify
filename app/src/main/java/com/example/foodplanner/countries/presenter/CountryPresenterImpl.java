package com.example.foodplanner.countries.presenter;


import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.countries.View.CountryFragment;
import com.example.foodplanner.Model.MealsRepositoryImpl;
import com.example.foodplanner.Network.NetworkCallback;

import java.util.List;

public class CountryPresenterImpl implements NetworkCallback, CountryPresenter {

    MealsRepositoryImpl repository;
    CountryFragment view;

List<Meal> list;
    public CountryPresenterImpl(CountryFragment countryFragment, MealsRepositoryImpl productsRepository) {
        this.repository = productsRepository;
        this.view = countryFragment;
    }

    @Override
    public void onSuccessResult(List countries) {
        list = countries;
        if(list.get(0).getStrArea() == null){
            view.showPopularMeals(countries);
        }else{
            view.showData(countries);
        }
    }

    @Override
    public void onFailureResult(String errorMsg) {
        view.showErrorMsg(errorMsg);
    }


    @Override
    public void getCountries() {
        repository.getAllCountries(this);
    }
}
