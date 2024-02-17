package com.example.foodplanner.Model;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.Network.MealsRemoteDataSource;
import com.example.foodplanner.db.MealsLocalDataSource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class MealsRepositoryImpl implements MealRepository {
    private static MealsRepositoryImpl repository = null;
    MealsRemoteDataSource mealsRemoteDataSource;
    MealsLocalDataSource mealsLocalDataSource;

    private MealsRepositoryImpl(MealsRemoteDataSource mealsRemoteDataSource, MealsLocalDataSource mealsLocalDataSource) {
        this.mealsRemoteDataSource = mealsRemoteDataSource;
        this.mealsLocalDataSource = mealsLocalDataSource;
    }

    public static MealsRepositoryImpl getInstance(MealsRemoteDataSource mealsRemoteDataSource, MealsLocalDataSource mealsLocalDataSource) {
        if (repository == null) {
            repository = new MealsRepositoryImpl(mealsRemoteDataSource, mealsLocalDataSource);
        }
        return repository;
    }

    ////////////RX/////////////////////
    @Override
    public Observable getAllRandoms() {
        List<Observable<List<Meal>>> observables = new ArrayList<>();
        for(int i= 0; i<20;i++){
            observables.add(mealsRemoteDataSource.makeNetworkCallback());
        }
        return Observable.concat(observables);
    }

    @Override
    public Observable getAllCategories() {
        return mealsRemoteDataSource.makeNetworkCallbackForCategory();
    }
    @Override
    public Observable getAllCountries() {
        return  mealsRemoteDataSource.makeNetworkCallbackForCountry();
    }
    @Override
    public Observable getMeal(String mealName) {
        return mealsRemoteDataSource.makeNetworkCallbackForMealName(mealName);
    }
    @Override
    public Observable getAllMeals(String categoryName) {
        return mealsRemoteDataSource.makeNetworkCallbackByCategoryName(categoryName);
    }
    @Override
    public Observable getAllCountryMeals(String countryMeal) {
        return mealsRemoteDataSource.makeNetworkCallbackByCountryName(countryMeal);
    }
    @Override
    public Observable getAllIngredients() {
        return mealsRemoteDataSource.makeNetworkCallbackIngredients();
    }
    @Override
    public Observable getAllIngredientMeals(String ingredientMeal) {
        return mealsRemoteDataSource.makeNetworkCallBackByIngredient(ingredientMeal);
    }
    ////////////RX/////////////////////





    @Override
    public Flowable<List<Meal>> getStoredMeals(String email) {
        return mealsLocalDataSource.getAllStoredMeals(email);
    }
    @Override
    public Completable insertMeal(Meal meal) {
        return mealsLocalDataSource.insertMeal(meal);
    }
    @Override
    public Completable deleteMeal(Meal meal) {
        return mealsLocalDataSource.deleteMeal(meal);
    }
    @Override
    public Single<Boolean> checkMealExist(String mealId) {
        return mealsLocalDataSource.checkMealExist(mealId)
                .map(count -> count > 0);
    }

    @Override
    public Flowable<List<MealPlan>> getPlan(String email, String day) {
        return mealsLocalDataSource.getPlan(email , day);
    }
    @Override
    public Completable deletePlanMeal(MealPlan mealPlan) {
        return mealsLocalDataSource.deletePlanMeal(mealPlan);
    }
    @Override
    public Completable addMealToPlan(MealPlan mealPlan) {
        return mealsLocalDataSource.addMealToPlan(mealPlan);
    }

    @Override
    public Completable deletePlan(String email) {
        return mealsLocalDataSource.deletePlan(email);
    }
}
