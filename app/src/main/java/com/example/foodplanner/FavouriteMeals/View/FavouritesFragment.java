package com.example.foodplanner.FavouriteMeals.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplanner.FavouriteMeals.presenter.FavMealsPresenter;
import com.example.foodplanner.FavouriteMeals.presenter.FavMealsPresenterImpl;
import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.Model.MealPlan;
import com.example.foodplanner.Model.MealsRepositoryImpl;
import com.example.foodplanner.Network.MealsRemoteDataSourceImpl;
import com.example.foodplanner.R;
import com.example.foodplanner.db.MealsLocalDataSourceImpl;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class FavouritesFragment extends Fragment implements FavMealsView, OnFavoriteClickListener {

    RecyclerView favRecyclerView;
    LinearLayoutManager layoutManager;
    FavMealsAdapter favMealsAdapter;
    LiveData<List<Meal>> mealsList;
    FavMealsPresenter favMealsPresenter;
    LottieAnimationView favouriteAnimationView;
    TextView noFavouritesTV;
    String day =null , email = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if(getArguments()!=null){
            day = getArguments().getString("day");
            Log.i("TEST", "onViewCreated: "+day);
        }
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        email = user != null ? user.getEmail() : null;
        favRecyclerView = view.findViewById(R.id.favRecyclerView);
        noFavouritesTV = view.findViewById(R.id.noFavouritesTV);
        favouriteAnimationView = view.findViewById(R.id.favouriteAnimation);
        layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        favMealsAdapter = new FavMealsAdapter(view.getContext(), new ArrayList<>(), this, day , email);
        favRecyclerView.setAdapter(favMealsAdapter);
        favRecyclerView.setLayoutManager(layoutManager);

        favMealsPresenter = new FavMealsPresenterImpl(this, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(),
                MealsLocalDataSourceImpl.getInstance(view.getContext())));

        mealsList = favMealsPresenter.getStoredMeals(email);
        showData(mealsList);

    }
    @Override
    public void onDeleteFavMealClick(Meal meal) {
        removeMeal(meal);
    }

    @Override
    public void addToPlan(MealPlan mealPlan) {
        favMealsPresenter.addMealToPlan(mealPlan);
    }

    @Override
    public void showData(LiveData<List<Meal>> mealsList) {
        mealsList.observe(this , new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                favMealsAdapter.updateData(meals);
                if (meals != null && !meals.isEmpty()) {
                    favouriteAnimationView.setVisibility(View.GONE);
                    noFavouritesTV.setVisibility(View.GONE);
                } else {
                    Log.i("TEST", "testAnimation: ");
                    favouriteAnimationView.setVisibility(View.VISIBLE);
                    noFavouritesTV.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void removeMeal(Meal meal) {
        favMealsPresenter.removeFromFav(meal);
    }
}