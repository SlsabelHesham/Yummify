package com.example.foodplanner.Network;

import android.util.Log;


import com.example.foodplanner.Model.MealResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsRemoteDataSourceImpl implements MealsRemoteDataSource {
    private static final String TAG = "retrofit";
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

    private MealService mealService;

    private static MealsRemoteDataSourceImpl client = null;

    private MealsRemoteDataSourceImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        mealService = retrofit.create(MealService.class);
    }

    public static MealsRemoteDataSourceImpl getInstance() {
        if (client == null) {
            client = new MealsRemoteDataSourceImpl();
        }
        return client;
    }


    @Override
    public void makeNetworkCallack(NetworkCallback networkCallback , String query) {
        Call<MealResponse> call = null;
        if(query.equals("random")){
            call = mealService.getRandom();
        }
        else if(query.equals("category")){
            call = mealService.getCategories();
        } else if (query.equals("country")) {
            call = mealService.getCountries("list");
        }
        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: ");
                    if(query.equals("random")){
                        networkCallback.onSuccessResult(response.body().getMeals());
                    } else if (query.equals("category")) {
                        Log.i("TAG", "onResponse catggggggg: " + response.body().getCategories().size());
                        networkCallback.onSuccessResult(response.body().getCategories());
                    }
                    else if (query.equals("country")) {
                        Log.i("TAG", "onResponse catggggggg: " + response.body().getMeals().size());
                        networkCallback.onSuccessResult(response.body().getMeals());
                    }

                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: ");
                networkCallback.onFailureResult(t.getMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void makeNetworkCallBackByCategory(NetworkCallback networkCallback, String categoryName) {
        Call<MealResponse> call = mealService.getAllMeals(categoryName);

        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: ");
                        networkCallback.onSuccessResult(response.body().getMeals());

                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: ");
                networkCallback.onFailureResult(t.getMessage());
                t.printStackTrace();
            }
        });
    }
    @Override
    public void makeNetworkCallBackByCountry(NetworkCallback networkCallback, String countryName) {
        Call<MealResponse> call = mealService.getAllCountryMeals(countryName);

        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: ");
                    networkCallback.onSuccessResult(response.body().getMeals());

                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: ");
                networkCallback.onFailureResult(t.getMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void makeNetworkCallBackByMeal(NetworkCallback networkCallback, String mealName) {
        Call<MealResponse> call = mealService.getMealDetails(mealName);

        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse: ");
                    networkCallback.onSuccessResult(response.body().getMeals());

                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: ");
                networkCallback.onFailureResult(t.getMessage());
                t.printStackTrace();
            }
        });
    }
}