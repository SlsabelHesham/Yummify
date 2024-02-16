package com.example.foodplanner.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.Model.MealPlan;

@Database(entities = {Meal.class , MealPlan.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase instance = null;
    public abstract MealsDAO getMealsDAO();

    public static synchronized AppDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "meals")
                    .build();
        }
        return instance;
    }
}
