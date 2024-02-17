package com.example.foodplanner.countries.presenter;

import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.countries.View.CountryFragment;
import com.example.foodplanner.Model.MealsRepositoryImpl;
import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CountryPresenterImpl implements CountryPresenter {

    MealsRepositoryImpl repository;
    CountryFragment view;
    List<Meal> list;
    public CountryPresenterImpl(CountryFragment countryFragment, MealsRepositoryImpl productsRepository) {
        this.repository = productsRepository;
        this.view = countryFragment;
    }



    @Override
    public void getCountries() {
        Observable<List<Meal>> observable = repository.getAllCountries();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(countryList -> {
                    view.showData(countryList);
                });
    }

    @Override
    public void getAllCountryMeals(String countryName) {
        Observable<List<Meal>> observable = repository.getAllCountryMeals(countryName);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealList -> {
                    view.showPopularMeals(mealList);
                });
    }
}
