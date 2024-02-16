package com.example.foodplanner.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "meals_plan_table")
public class MealPlan implements Serializable {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int id;
    String day;
    String email;
    Meal meal;

    public MealPlan(String email, String day, Meal meal) {
        this.day = day;
        this.email = email;
        this.meal = meal;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
