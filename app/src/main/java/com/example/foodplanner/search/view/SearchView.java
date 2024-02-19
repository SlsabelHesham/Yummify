package com.example.foodplanner.search.view;

import com.example.foodplanner.Model.Category;
import com.example.foodplanner.Model.Meal;

import java.util.List;

public interface SearchView {
    public void showCategoryData(List<Category> categoryList);
    public void showCountryData(List<Meal> mealList);
    public void showIngredientsData(List<Meal> mealList);

    public void showMealsData(List<Meal> mealList);
    public void showErrorMsg(String error);

}