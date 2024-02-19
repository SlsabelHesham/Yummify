package com.example.foodplanner.AllMeals.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.AllMeals.presenter.AllMealsPresenter;
import com.example.foodplanner.AllMeals.presenter.AllMealsPresenterImpl;
import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.Model.MealsRepositoryImpl;
import com.example.foodplanner.Network.MealsRemoteDataSourceImpl;
import com.example.foodplanner.R;
import com.example.foodplanner.db.MealsLocalDataSourceImpl;

import java.util.ArrayList;
import java.util.List;


public class AllMealsFragment extends Fragment implements MealsView, OnMealClickListener {

    RecyclerView allMealsRecyclerView;
    LinearLayoutManager layoutManager;
    MealsAdapter allMealsAdapter;
    AllMealsPresenter allMealsPresenter;
    TextView categoryNameTV;
    String categoryName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            categoryName =  getArguments().getString("categoryName");
        }
        allMealsRecyclerView = view.findViewById(R.id.allMealsRecyclerView);
        categoryNameTV = view.findViewById(R.id.categoryName);
        categoryNameTV.setText(categoryName);
        layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        allMealsAdapter = new MealsAdapter(view.getContext() , new ArrayList<>() , this,"");

        allMealsPresenter = new AllMealsPresenterImpl(this, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(),
                MealsLocalDataSourceImpl.getInstance(view.getContext())));
        allMealsRecyclerView.setAdapter(allMealsAdapter);
        allMealsRecyclerView.setLayoutManager(layoutManager);
        allMealsPresenter.getAllMeals(categoryName);
    }

    @Override
    public void showData(List<Meal> meals) {
        allMealsAdapter.updateData(meals);
        allMealsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorMsg(String error) {
        Toast.makeText(this.getContext(), error, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onFavProductClick(Meal product) {

    }
}