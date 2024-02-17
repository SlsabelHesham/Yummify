package com.example.foodplanner.Network;


import com.example.foodplanner.Model.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {
    @GET("random.php")
    Call<MealResponse> getRandom();

    @GET("categories.php")
    Call<MealResponse> getCategories();

    @GET("filter.php")
    Call<MealResponse> getAllMeals(@Query("c") String category);

    @GET("search.php")
    Call<MealResponse> getMealDetails(@Query("s") String category);

    @GET("list.php")
    Call<MealResponse> getCountries(@Query("a") String countryList);
    @GET("list.php")
    Call<MealResponse> getIngredients(@Query("i") String ingredientList);

    @GET("filter.php")
    Call<MealResponse> getAllCountryMeals(@Query("a") String area);

    @GET("filter.php")
    Call<MealResponse> getAllIngredientMeals(@Query("i") String ingredient);

}
