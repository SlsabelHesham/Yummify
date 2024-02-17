package com.example.foodplanner.Model;

import androidx.lifecycle.LiveData;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface MealRepository {
    Observable getAllRandoms();
    Observable getAllCategories();
    Observable getAllCountries();
    Observable getMeal(String mealName);
    Observable getAllMeals(String categoryName);

    Observable getAllCountryMeals(String countryMeal);
    Observable getAllIngredients();

    Observable getAllIngredientMeals(String ingredientMeal);




    ////// Roooooooom
    Flowable<List<Meal>> getStoredMeals(String email);
    Completable insertMeal(Meal meal);
    Completable deleteMeal(Meal meal);
    Single<Boolean> checkMealExist(String mealId);





    Flowable<List<MealPlan>> getPlan(String email , String day);
    Completable deletePlanMeal(MealPlan mealPlan);
    Completable addMealToPlan(MealPlan mealPlan);
    Completable deletePlan(String email);
}
