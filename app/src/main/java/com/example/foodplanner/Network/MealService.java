package com.example.foodplanner.Network;

import com.example.foodplanner.Model.MealResponse;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {
    @GET("random.php")
    Observable<MealResponse> getRandom();
    @GET("categories.php")
    Observable<MealResponse> getCategories();
    @GET("list.php")
    Observable<MealResponse> getCountries(@Query("a") String countryList);
    @GET("search.php")
    Observable<MealResponse> getMealDetails(@Query("s") String mealName);
    @GET("filter.php")
    Observable<MealResponse> getAllMeals(@Query("c") String categoryName);
    @GET("filter.php")
    Observable<MealResponse> getAllCountryMeals(@Query("a") String area);
    @GET("list.php")
    Observable<MealResponse> getIngredients(@Query("i") String ingredientList);
    @GET("filter.php")
    Observable<MealResponse> getAllIngredientMeals(@Query("i") String ingredient);
}
