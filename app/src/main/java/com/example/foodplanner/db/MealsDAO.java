package com.example.foodplanner.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.Model.MealPlan;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;


@Dao
public interface MealsDAO {
    @Query("SELECT * FROM meals_table WHERE email = :email")
    Flowable<List<Meal>> getAllMeals(String email);
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertMeal (Meal meal);
    @Delete
    Completable deleteMeal (Meal meal);
    @Query("SELECT COUNT(*) FROM meals_table WHERE idMeal = :mealId")
    Single<Integer> checkIfMealExists(String mealId);






    @Query("SELECT * FROM meals_plan_table WHERE email = :email and day = :day")
    Flowable<List<MealPlan>> getPlan(String email , String day);
    @Delete
    Completable deletePlanMeal (MealPlan mealPlan);
    @Query("DELETE FROM meals_plan_table WHERE email = :email")
    Completable deletePlan(String email);
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertMealPlan (MealPlan mealPlan);
}
