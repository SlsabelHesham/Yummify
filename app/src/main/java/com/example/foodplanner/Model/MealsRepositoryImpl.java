package com.example.foodplanner.Model;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.Network.NetworkCallback;
import com.example.foodplanner.Network.MealsRemoteDataSource;
import com.example.foodplanner.db.MealsLocalDataSource;
import java.util.List;

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
    @Override
    public LiveData<List<Meal>> getStoredMeals(String email) {
        return mealsLocalDataSource.getAllStoredMeals(email);
    }

    @Override
    public LiveData<List<MealPlan>> getPlan(String email, String day) {
        return mealsLocalDataSource.getPlan(email , day);
    }
    @Override
    public void getAllRandoms(NetworkCallback networkCallBack) {
        for(int i= 0; i<20;i++){
            mealsRemoteDataSource.makeNetworkCallack(networkCallBack , "random");
        }
    }

    @Override
    public void getAllCategories(NetworkCallback networkCallBack) {
        mealsRemoteDataSource.makeNetworkCallack(networkCallBack , "category");
    }
    @Override
    public void getAllCountries(NetworkCallback networkCallBack) {
        mealsRemoteDataSource.makeNetworkCallack(networkCallBack , "country");
    }


    @Override
    public void getAllMeals(NetworkCallback networkCallBack , String categoryName) {
        mealsRemoteDataSource.makeNetworkCallBackByCategory(networkCallBack , categoryName);
    }
    @Override
    public void getAllCountryMeals(NetworkCallback networkCallBack , String countryMeal) {
        mealsRemoteDataSource.makeNetworkCallBackByCountry(networkCallBack , countryMeal);
    }
    @Override
    public void getMeal(NetworkCallback networkCallBack , String mealName) {
        mealsRemoteDataSource.makeNetworkCallBackByMeal(networkCallBack , mealName);
    }
    @Override
    public void insertMeal(Meal meal) {
        mealsLocalDataSource.insertMeal(meal);
    }
    @Override
    public void deleteMeal(Meal meal) {
        mealsLocalDataSource.deleteMeal(meal);
    }

    @Override
    public void deletePlanMeal(MealPlan mealPlan) {
        mealsLocalDataSource.deletePlanMeal(mealPlan);
    }
    @Override
    public void addMealToPlan(MealPlan mealPlan) {
        mealsLocalDataSource.addMealToPlan(mealPlan);
    }

    @Override
    public boolean checkMealExist(String mealId) {
        boolean result = mealsLocalDataSource.checkMealExist(mealId);
        return result;
    }
}
