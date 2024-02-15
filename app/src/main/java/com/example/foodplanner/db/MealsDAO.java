package com.example.foodplanner.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.example.foodplanner.Model.Meal;

import java.util.List;


@Dao
public interface MealsDAO {
    @Query("SELECT * FROM meals_table WHERE email = :email")
    LiveData<List<Meal>> getAllMeals(String email);
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMeal (Meal meal);
    @Delete
    void deleteMeal (Meal meal);

    @Query("SELECT COUNT(*) FROM meals_table WHERE idMeal = :mealId")
     int checkIfMealExists(String mealId);
}
