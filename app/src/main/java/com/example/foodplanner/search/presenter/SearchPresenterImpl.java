package com.example.foodplanner.search.presenter;

import android.util.Log;

import com.example.foodplanner.Model.Category;
import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.Model.MealsRepositoryImpl;
import com.example.foodplanner.search.view.SearchFragment;
import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenterImpl implements SearchPresenter {

    MealsRepositoryImpl repository;
    SearchFragment view;
    List<Category> list;
    public SearchPresenterImpl(SearchFragment searchFragment, MealsRepositoryImpl productsRepository) {
        this.repository = productsRepository;
        this.view = searchFragment;
    }

    @Override
    public void getCountries() {
        Observable<List<Meal>> observable = repository.getAllCountries();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(countryList -> {
                    view.showCountryData(countryList);
                });
    }

    @Override
    public void getCategories() {
        Observable<List<Category>> observable = repository.getAllCategories();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categoryList -> {
                    view.showCategoryData(categoryList);
                });
    }

    @Override
    public void getIngredients() {
        Observable<List<Meal>> observable = repository.getAllIngredients();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ingredientList -> {
                    view.showIngredientsData(ingredientList);
                });
    }

    @Override
    public void getMealsByName(String mealName) {
        Observable<List<Meal>> observable = repository.getMeal(mealName);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealList -> {
                    view.showMealsData(mealList);
                });

    }
}
