package com.example.foodplanner.search.presenter;

import android.util.Log;

import com.example.foodplanner.Model.Category;
import com.example.foodplanner.Model.Ingredients;
import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.Model.MealsRepositoryImpl;
import com.example.foodplanner.Network.NetworkCallback;
import com.example.foodplanner.search.view.SearchFragment;

import java.util.List;

public class SearchPresenterImpl implements NetworkCallback, SearchPresenter {

    MealsRepositoryImpl repository;
    SearchFragment view;
    List<Category> list;
    public SearchPresenterImpl(SearchFragment searchFragment, MealsRepositoryImpl productsRepository) {
        this.repository = productsRepository;
        this.view = searchFragment;
    }

    @Override
    public void onSuccessResult(List list) {
        if(list.get(0) instanceof Category){
            view.showCategoryData(list);
        } else if (list.get(0) instanceof Meal && ((Meal) list.get(0)).getStrIngredient()==null && ((Meal) list.get(0)).getStrMeal()==null) {
            view.showCountryData(list);
        }
        else if (list.get(0) instanceof Meal && ((Meal) list.get(0)).getStrIngredient()!=null) {
            Log.i("TEST123", "showIngredientsData2222: ");
            view.showIngredientsData(list);
        }
        else{
            view.showMealsData(list);
        }
    }

    @Override
    public void onFailureResult(String errorMsg) {
        view.showErrorMsg(errorMsg);
    }


    @Override
    public void getCountries() {
        repository.getAllCountries(this);
    }

    @Override
    public void getCategories() {
        repository.getAllCategories(this);
    }

    @Override
    public void getIngredients() {
        repository.getAllIngredients(this);
    }

    @Override
    public void getMealsByName(String mealName) {
        repository.getMeal(this ,mealName);
    }
}
