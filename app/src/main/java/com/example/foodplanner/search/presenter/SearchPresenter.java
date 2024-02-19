package com.example.foodplanner.search.presenter;

public interface SearchPresenter {

    public void getCountries();
    public void getCategories();
    public void getIngredients();
    public void getMealsByName(String mealName);

}
