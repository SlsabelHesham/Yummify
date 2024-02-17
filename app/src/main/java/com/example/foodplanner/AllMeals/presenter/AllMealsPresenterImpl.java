package com.example.foodplanner.AllMeals.presenter;

import com.example.foodplanner.search.view.AllCountryMealsFragment;
import com.example.foodplanner.search.view.AllIngredientMealsFragment;
import com.example.foodplanner.AllMeals.View.AllMealsFragment;
import com.example.foodplanner.AllMeals.View.MealsView;
import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.Model.MealsRepositoryImpl;
import com.example.foodplanner.countries.View.CountryFragment;
import com.example.foodplanner.countries.View.CountryView;
import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AllMealsPresenterImpl implements AllMealsPresenter {

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
        Observable<List<Meal>> observable = repository.getAllMeals(categoryName);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealList -> {
                    view.showData(mealList);
                });
    }

    @Override
    public void getAllCountryMeals(String countryName) {
        Observable<List<Meal>> observable = repository.getAllCountryMeals(countryName);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealList -> {
                    view.showData(mealList);
                });
    }

    @Override
    public void getAllIngredientMeals(String ingredientName) {
        Observable<List<Meal>> observable = repository.getAllIngredientMeals(ingredientName);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealList -> {
                    view.showData(mealList);
                });
    }

    @Override
    public void addToFav(Meal meal) {
        Completable completable = repository.insertMeal(meal);
        completable.subscribeOn(Schedulers.io())
                .subscribe();
    }


}
