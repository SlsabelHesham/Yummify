package com.example.foodplanner.Randoms.View;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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
import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplanner.Randoms.presenter.RandomPresenter;
import com.example.foodplanner.Randoms.presenter.RandomPresenterImpl;
import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.Model.MealsRepositoryImpl;
import com.example.foodplanner.Network.MealsRemoteDataSourceImpl;
import com.example.foodplanner.R;
import com.example.foodplanner.db.MealsLocalDataSourceImpl;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements RandomView, OnRandomClickListener {

    RecyclerView randomRecyclerView;
    LinearLayoutManager layoutManager;
    RandomAdapter randomAdapter;
    RandomPresenter randomPresenter;
    ImageView profile_menu , search;

    TextView categories , countries , planMeals;
    DrawerLayout drawerLayout;
    LottieAnimationView noInternet;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setHomeAsUpIndicator(R.drawable.profile_menu);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        randomRecyclerView = view.findViewById(R.id.my_recycler_view);
        profile_menu = view.findViewById(R.id.profile_menu);
        noInternet = view.findViewById(R.id.noInternetAnimation);
        drawerLayout = requireActivity().findViewById(R.id.drawerLayout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        search = view.findViewById(R.id.search);
        categories = view.findViewById(R.id.categoriesTV);
        countries = view.findViewById(R.id.countriesTV);
        planMeals = view.findViewById(R.id.planMeals);
        layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        randomAdapter = new RandomAdapter(view.getContext() , new ArrayList<>() , this);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user != null ? user.getEmail() : null;
        if(email != null){
            SharedPreferences preferences = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("guest", false);
            editor.apply();
        }
        randomPresenter = new RandomPresenterImpl(this, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(),
                MealsLocalDataSourceImpl.getInstance(view.getContext())));
        randomRecyclerView.setAdapter(randomAdapter);
        randomRecyclerView.setLayoutManager(layoutManager);
        getContext();
        ConnectivityManager connectivityManager = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            noInternet.setVisibility(View.GONE);
            randomPresenter.getRandoms();
        } else {
                noInternet.setVisibility(View.VISIBLE);
        }
        profile_menu.setOnClickListener(view1 -> drawerLayout.openDrawer(GravityCompat.START));
        search.setOnClickListener(view12 -> {
            if (networkInfo != null && networkInfo.isConnected()) {
                NavController navController = Navigation.findNavController((Activity) view12.getContext(), R.id.fragmentNavHost);
                navController.navigate(R.id.action_homeFragment_to_searchFragment);
            } else {
                Toast.makeText((Activity) view12.getContext(), "Please connect to the Internet!", Toast.LENGTH_SHORT).show();
            }
        });

        planMeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController((Activity) view.getContext(), R.id.fragmentNavHost);
                navController.navigate(R.id.action_homeFragment_to_planFragment);
            }
        });
        categories.setOnClickListener(view13 -> {
            if (networkInfo != null && networkInfo.isConnected()) {
                NavController navController = Navigation.findNavController((Activity) view13.getContext(), R.id.fragmentNavHost);
                navController.navigate(R.id.action_homeFragment_to_categoryFragment);
            } else {
                Toast.makeText((Activity) view13.getContext(), "Please connect to the Internet!", Toast.LENGTH_SHORT).show();
            }
        });

        countries.setOnClickListener(view14 -> {
            if (networkInfo != null && networkInfo.isConnected()) {
                NavController navController = Navigation.findNavController((Activity) view14.getContext(), R.id.fragmentNavHost);
                navController.navigate(R.id.action_homeFragment_to_countryFragment);
            } else {
                Toast.makeText((Activity) view14.getContext(), "Please connect to the Internet!", Toast.LENGTH_SHORT).show();
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