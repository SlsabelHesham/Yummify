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


@Dao
public interface MealsDAO {
    @Query("SELECT * FROM meals_table WHERE email = :email")
    LiveData<List<Meal>> getAllMeals(String email);

    @Query("SELECT * FROM meals_plan_table WHERE email = :email and day = :day")
    LiveData<List<MealPlan>> getPlan(String email , String day);
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMeal (Meal meal);
    @Delete
    void deleteMeal (Meal meal);

    @Delete
    void deletePlanMeal (MealPlan mealPlan);

    @Query("DELETE FROM meals_plan_table WHERE email = :email")
    void deletePlan(String email);

    @Query("SELECT COUNT(*) FROM meals_table WHERE idMeal = :mealId")
     int checkIfMealExists(String mealId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMealPlan (MealPlan mealPlan);
}
