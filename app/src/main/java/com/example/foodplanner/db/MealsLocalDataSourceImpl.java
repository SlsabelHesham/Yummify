package com.example.foodplanner.db;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;


import com.example.foodplanner.Model.Meal;

import java.util.List;

public class MealsLocalDataSourceImpl implements MealsLocalDataSource{

    MealsDAO mealsDAO;
    static MealsLocalDataSourceImpl mealsLocalDataSourceImpl;
    LiveData<List<Meal>> storedMeals;

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
    public void insertMeal(Meal meal) {
        new Thread(new Runnable() {
            public void run() { mealsDAO.insertMeal(meal); }
        }).start();
    }

    @Override
    public void deleteMeal(Meal meal) {
        new Thread(new Runnable() {
            public void run() { mealsDAO.deleteMeal(meal); }
        }).start();
    }

    @Override
    public LiveData<List<Meal>> getAllStoredMeals(String email) {
        storedMeals = mealsDAO.getAllMeals(email);
        return storedMeals;
    }

    @Override
    public boolean checkMealExist(String mealId) {
        final int[] count = {0};

        new Thread(new Runnable() {
            public void run() {
                count[0] = mealsDAO.checkIfMealExists(mealId);
            }
        }).start();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return count[0] > 0;
    }
}
