package com.example.foodplanner.db;

import androidx.room.TypeConverter;

import com.example.foodplanner.Model.Meal;
import com.google.gson.Gson;

public class Converters {

    @TypeConverter
    public static Meal fromString(String value) {
        Gson gson = new Gson();
        return gson.fromJson(value, Meal.class);
    }

    @TypeConverter
    public static String mealToString(Meal meal) {
        Gson gson = new Gson();
        return gson.toJson(meal);
    }
}

