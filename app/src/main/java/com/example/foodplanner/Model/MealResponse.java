package com.example.foodplanner.Model;

import java.util.List;

public class MealResponse {
    private List<Meal> meals;
    private List<Category> categories;
    private List<Country> countries;
    private List<Ingredients> ingredients;
    private Meal meal;

    public List<Meal> getMeals() {
        return meals;
    }
    public List<Category> getCategories() {
        return categories;
    }
    public List<Country> getCountries() {
        return countries;
    }
    public Meal getMeal() {
        return meal;
    }




}
