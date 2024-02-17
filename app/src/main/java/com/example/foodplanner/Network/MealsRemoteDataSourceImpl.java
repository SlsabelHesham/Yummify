package com.example.foodplanner.Network;

import com.example.foodplanner.Model.Category;
import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.Model.MealResponse;
import java.util.List;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
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
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
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
    public Observable<List<Meal>> makeNetworkCallback() {
        Observable<MealResponse> observable = mealService.getRandom();
        return observable
                .flatMap(mealResponse -> Observable.just(mealResponse.getMeals()));
    }

    @Override
    public Observable<List<Category>> makeNetworkCallbackForCategory() {
        Observable<MealResponse> observable = mealService.getCategories();
        return observable
                .flatMap(mealResponse -> Observable.just(mealResponse.getCategories()));
    }

    @Override
    public Observable<List<Meal>> makeNetworkCallbackForCountry() {
        Observable<MealResponse> observable = mealService.getCountries("list");
        return observable
                .flatMap(mealResponse -> Observable.just(mealResponse.getMeals()));
    }

    @Override
    public Observable<List<Meal>> makeNetworkCallbackForMealName(String mealName) {
        Observable<MealResponse> observable = mealService.getMealDetails(mealName);
        return observable
                .flatMap(mealResponse -> Observable.just(mealResponse.getMeals()));
    }

    @Override
    public Observable<List<Meal>> makeNetworkCallbackByCategoryName(String categoryName) {
        Observable<MealResponse> observable = mealService.getAllMeals(categoryName);
        return observable
                .flatMap(mealResponse -> Observable.just(mealResponse.getMeals()));
    }

    @Override
    public Observable<List<Meal>> makeNetworkCallbackByCountryName(String countryName) {
        Observable<MealResponse> observable = mealService.getAllCountryMeals(countryName);
        return observable
                .flatMap(mealResponse -> Observable.just(mealResponse.getMeals()));
    }

    @Override
    public Observable<List<Meal>> makeNetworkCallbackIngredients() {
        Observable<MealResponse> observable = mealService.getIngredients("list");
        return observable
                .flatMap(mealResponse -> Observable.just(mealResponse.getMeals()));
    }

    @Override
    public Observable makeNetworkCallBackByIngredient(String ingredientName) {
        Observable<MealResponse> observable = mealService.getAllIngredientMeals(ingredientName);
        return observable
                .flatMap(mealResponse -> Observable.just(mealResponse.getMeals()));
    }
}