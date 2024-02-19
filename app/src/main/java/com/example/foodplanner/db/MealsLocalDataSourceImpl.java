package com.example.foodplanner.db;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;


import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.Model.MealPlan;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class MealsLocalDataSourceImpl implements MealsLocalDataSource{

    MealsDAO mealsDAO;
    static MealsLocalDataSourceImpl mealsLocalDataSourceImpl;
    Flowable<List<Meal>> storedMeals;

    Flowable<List<MealPlan>> storedPlan;

    private MealsLocalDataSourceImpl(Context context) {
        AppDataBase db = AppDataBase.getInstance(context.getApplicationContext());
        mealsDAO = db.getMealsDAO();
    }
    public static MealsLocalDataSourceImpl getInstance(Context context){
        if (mealsLocalDataSourceImpl == null) {
            mealsLocalDataSourceImpl = new MealsLocalDataSourceImpl(context);
        }
        return mealsLocalDataSourceImpl;
    }

    @Override
    public Flowable<List<Meal>> getAllStoredMeals(String email) {
        storedMeals = mealsDAO.getAllMeals(email);
        return storedMeals;
    }
    @Override
    public Flowable<List<MealPlan>> getPlan(String email , String day) {
        storedPlan = mealsDAO.getPlan(email , day);
        return storedPlan;
    }
    @Override
    public Flowable<List<MealPlan>> getPlan(String email) {
        storedPlan = mealsDAO.getPlan(email);
        return storedPlan;
    }



    @Override
    public Completable insertMeal(Meal meal) {
        Completable completable = mealsDAO.insertMeal(meal);
        return completable;
    }

    @Override
    public Completable deleteMeal(Meal meal) {
        Completable completable = mealsDAO.deleteMeal(meal);
        return completable;
    }

    @Override
    public Single<Integer> checkMealExist(String mealId) {
        return mealsDAO.checkIfMealExists(mealId);
    }

    @Override
    public Completable deletePlanMeal(MealPlan mealPlan) {
        Completable completable = mealsDAO.deletePlanMeal(mealPlan);
        return completable;
    }
    @Override
    public Completable deletePlan(String email) {
        Completable completable = mealsDAO.deletePlan(email);
        return completable;
    }

    @Override
    public Completable addMealToPlan(MealPlan mealPlan) {
        Completable completable = mealsDAO.insertMealPlan(mealPlan);
        return completable;
    }
}
