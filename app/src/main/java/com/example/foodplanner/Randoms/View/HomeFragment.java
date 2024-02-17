package com.example.foodplanner.Randoms.View;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.Randoms.presenter.RandomPresenter;
import com.example.foodplanner.Randoms.presenter.RandomPresenterImpl;
import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.Model.MealsRepositoryImpl;
import com.example.foodplanner.Network.MealsRemoteDataSourceImpl;
import com.example.foodplanner.R;
import com.example.foodplanner.db.MealsLocalDataSourceImpl;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements RandomView, OnRandomClickListener {

    RecyclerView randomRecyclerView;
    LinearLayoutManager layoutManager;
    RandomAdapter randomAdapter;
    RandomPresenter randomPresenter;
    ImageView profile_menu , search;

    TextView categories , countries;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        MaterialToolbar toolbar = view.findViewById(R.id.toolbar);

        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(toolbar);

        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        randomRecyclerView = view.findViewById(R.id.my_recycler_view);
        profile_menu = view.findViewById(R.id.profile_menu);
        search = view.findViewById(R.id.search);
        categories = view.findViewById(R.id.categoriesTV);
        countries = view.findViewById(R.id.countriesTV);
        layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        randomAdapter = new RandomAdapter(view.getContext() , new ArrayList<>() , this);

        randomPresenter = new RandomPresenterImpl(this, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(),
                MealsLocalDataSourceImpl.getInstance(view.getContext())));
        randomRecyclerView.setAdapter(randomAdapter);
        randomRecyclerView.setLayoutManager(layoutManager);
        randomPresenter.getRandoms();

        profile_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController((Activity) view.getContext(), R.id.fragmentNavHost);
                navController.navigate(R.id.action_homeFragment_to_favouritesFragment);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController((Activity) view.getContext(), R.id.fragmentNavHost);
                navController.navigate(R.id.action_homeFragment_to_searchFragment);
            }
        });

        categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController((Activity) view.getContext(), R.id.fragmentNavHost);
                navController.navigate(R.id.action_homeFragment_to_categoryFragment);
            }
        });

        countries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController((Activity) view.getContext(), R.id.fragmentNavHost);
                navController.navigate(R.id.action_homeFragment_to_countryFragment);
            }
        });
    }

    @Override
    public void onFavProductClick(Meal meal) {
        addMeal(meal);
    }

    @Override
    public void showData(List<Meal> meals) {
        randomAdapter.updateData(meals);
        randomAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorMsg(String error) {
        Toast.makeText(this.getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addMeal(Meal meal) {
        randomPresenter.addToFav(meal);
    }
}