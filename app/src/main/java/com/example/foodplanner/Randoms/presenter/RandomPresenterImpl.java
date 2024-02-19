package com.example.foodplanner.Randoms.presenter;

import android.annotation.SuppressLint;
import com.example.foodplanner.Randoms.View.RandomView;
import com.example.foodplanner.Randoms.View.HomeFragment;
import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.Model.MealsRepositoryImpl;
import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RandomPresenterImpl implements RandomPresenter {

    MealsRepositoryImpl repository;
    RandomView view;



    public RandomPresenterImpl(HomeFragment homeFragment, MealsRepositoryImpl productsRepository) {
        this.repository = productsRepository;
        this.view = homeFragment;
    }
/*
    @SuppressLint("CheckResult")
    @Override
    public void getProducts() {
        Observable<List<Products>> observable = repository.getAllProducts();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(productsList -> {
                    view.showData(productsList);
                });
    }*/

    @SuppressLint("CheckResult")
    @Override
    public void getRandoms() {
        Observable<List<Meal>> observable = repository.getAllRandoms();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(productsList -> {
                    view.showData(productsList);
                });
    }

    @Override
    public void addToFav(Meal meal) {
        Completable completable = repository.insertMeal(meal);
        completable.subscribeOn(Schedulers.io())
                .subscribe();
    }
}
