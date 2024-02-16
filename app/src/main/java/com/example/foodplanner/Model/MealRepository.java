package com.example.foodplanner.Model;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.Network.NetworkCallback;

import java.util.List;

public interface MealRepository {
    public LiveData<List<Meal>> getStoredMeals(String email);

    LiveData<List<MealPlan>> getPlan(String email , String day);

    public void getAllRandoms(NetworkCallback networkCallBack);
    public void getAllCategories(NetworkCallback networkCallBack);
    public void getAllCountries(NetworkCallback networkCallBack);
    public void getAllMeals(NetworkCallback networkCallBack , String categoryName);
    public void getAllCountryMeals(NetworkCallback networkCallBack , String countryMeal);
    public void getMeal(NetworkCallback networkCallBack , String mealName);
    public void insertMeal(Meal meal);
    public void deleteMeal(Meal meal);
    public void deletePlanMeal(MealPlan mealPlan);
    public boolean checkMealExist(String mealId);

    public void addMealToPlan(MealPlan mealPlan);

}
