package com.example.foodplanner.countries.View;

import com.example.foodplanner.Model.Category;
import com.example.foodplanner.Model.Country;
import com.example.foodplanner.Model.Meal;

import java.util.List;

public interface CountryView {
    public void showData(List<Meal> countries);
    public void showErrorMsg(String error);

    public void showPopularMeals(List<Meal> meals);
}