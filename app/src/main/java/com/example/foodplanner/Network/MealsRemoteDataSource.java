package com.example.foodplanner.Network;

public interface MealsRemoteDataSource {
    void makeNetworkCallack(NetworkCallback networkCallback , String query);

    void makeNetworkCallBackByCategory(NetworkCallback networkCallback , String categoryName);
    void makeNetworkCallBackByCountry(NetworkCallback networkCallback , String countryName);

    void makeNetworkCallBackByMeal(NetworkCallback networkCallback , String mealName);
}
