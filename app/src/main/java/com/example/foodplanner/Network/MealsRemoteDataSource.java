package com.example.foodplanner.Network;

public interface MealsRemoteDataSource {
    void makeNetworkCallack(NetworkCallback networkCallback , String query);

    void makeNetworkCallbackIngredients(NetworkCallback networkCallback);

    void makeNetworkCallBackByCategory(NetworkCallback networkCallback , String categoryName);
    void makeNetworkCallBackByCountry(NetworkCallback networkCallback , String countryName);
    void makeNetworkCallBackByIngredient(NetworkCallback networkCallback , String ingredientName);



    void makeNetworkCallBackByMeal(NetworkCallback networkCallback , String mealName);

    //void makeNetworkCallackByName(NetworkCallback networkCallBack);

    //void makeNetworkCallbackByName(NetworkCallback networkCallBack, String mealName);
}
