package com.example.foodplanner.Network;

import com.example.foodplanner.Model.Category;
import com.example.foodplanner.Model.Meal;
import java.util.List;
import io.reactivex.rxjava3.core.Observable;

public interface MealsRemoteDataSource {
    Observable<List<Meal>> makeNetworkCallback();
    Observable<List<Category>> makeNetworkCallbackForCategory();
    Observable<List<Meal>> makeNetworkCallbackForCountry();
    Observable<List<Meal>> makeNetworkCallbackForMealName(String mealName);
    Observable<List<Meal>> makeNetworkCallbackByCategoryName(String categoryName);
    Observable makeNetworkCallbackByCountryName(String countryName);
    Observable makeNetworkCallbackIngredients();
    Observable makeNetworkCallBackByIngredient(String ingredientName);
}
