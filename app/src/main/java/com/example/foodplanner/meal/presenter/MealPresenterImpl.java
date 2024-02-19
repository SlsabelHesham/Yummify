package com.example.foodplanner.meal.presenter;

import android.annotation.SuppressLint;

import com.example.foodplanner.AccountFragment;
import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.Model.MealsRepositoryImpl;
import com.example.foodplanner.meal.view.MealFragment;
import com.example.foodplanner.meal.view.MealsView;
import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealPresenterImpl implements  MealPresenter {

    MealsRepositoryImpl repository;
    MealsView view;



    public MealPresenterImpl(MealFragment mealFragment, MealsRepositoryImpl productsRepository) {
        this.repository = productsRepository;
        this.view = mealFragment;
    }

    public MealPresenterImpl(AccountFragment accountFragment, MealsRepositoryImpl instance) {
        this.repository = instance;
        this.view = accountFragment;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getMealDetails(String mealName) {
        Observable<List<Meal>> observable = repository.getMeal(mealName);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealDetails -> {
                    view.showData(mealDetails);
                });
    }

    @Override
    public void addToFav(Meal meal) {
        Completable completable = repository.insertMeal(meal);
        completable.subscribeOn(Schedulers.io())
                .subscribe();
    }

    @Override
    public void removeFromFav(Meal meal) {
        Completable completable = repository.deleteMeal(meal);
        completable.subscribeOn(Schedulers.io())
                .subscribe();
    }

    @SuppressLint("CheckResult")
    @Override
    public Single<Boolean> checkMealExist(String mealId) {
         return repository.checkMealExist(mealId);
    }
}
