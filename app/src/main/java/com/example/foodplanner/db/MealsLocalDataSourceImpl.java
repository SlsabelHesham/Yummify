package com.example.foodplanner.db;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;


import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.Model.MealPlan;

import java.util.List;

public class MealsLocalDataSourceImpl implements MealsLocalDataSource{

    MealsDAO mealsDAO;
    static MealsLocalDataSourceImpl mealsLocalDataSourceImpl;
    LiveData<List<Meal>> storedMeals;

    LiveData<List<MealPlan>> storedPlan;

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
    public void deletePlanMeal(MealPlan mealPlan) {
        new Thread(new Runnable() {
            public void run() {
                mealsDAO.deletePlanMeal(mealPlan);
            }
        }).start();
    }

    @Override
    public void deletePlan(String email) {
        new Thread(new Runnable() {
            public void run() {
                mealsDAO.deletePlan(email);
            }
        }).start();
    }

    @Override
    public LiveData<List<Meal>> getAllStoredMeals(String email) {
        storedMeals = mealsDAO.getAllMeals(email);
        Log.i("plan", "getAllStoredMeals: "+storedMeals.getValue());
        return storedMeals;
    }
    @Override
    public LiveData<List<MealPlan>> getPlan(String email , String day) {
        storedPlan = mealsDAO.getPlan(email , day);
        if (storedPlan != null) {
            List<MealPlan> mealPlanList = storedPlan.getValue();
            if (mealPlanList != null && !mealPlanList.isEmpty()) {
                Log.i("plan", "getPlan: " + mealPlanList.get(0).getEmail());
            } else {
                Log.e("plan", "getPlan: storedPlan value is null or empty");
            }
        } else {
            Log.e("plan", "getPlan: storedPlan is null");
        }
        return storedPlan;
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

    @Override
    public void addMealToPlan(MealPlan mealPlan) {
        new Thread(new Runnable() {
            public void run() {
                mealsDAO.insertMealPlan(mealPlan);
            }
        }).start();
    }
}
