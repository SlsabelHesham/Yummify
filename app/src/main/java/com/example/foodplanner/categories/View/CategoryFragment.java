package com.example.foodplanner.categories.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodplanner.Model.Category;
import com.example.foodplanner.Model.MealsRepositoryImpl;
import com.example.foodplanner.Network.MealsRemoteDataSourceImpl;
import com.example.foodplanner.R;
import com.example.foodplanner.categories.presenter.CategoryPresenter;
import com.example.foodplanner.categories.presenter.CategoryPresenterImpl;
import com.example.foodplanner.db.MealsLocalDataSourceImpl;

import java.util.ArrayList;
import java.util.List;


public class CategoryFragment extends Fragment implements CategoryView {
    RecyclerView categoryRecyclerView;
    GridLayoutManager  layoutManager;
    CategoryAdapter categoryAdapter;
    CategoryPresenter categoryPresenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        categoryRecyclerView = view.findViewById(R.id.categoriesRecyclerView);
        layoutManager = new GridLayoutManager(view.getContext(), 2);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        categoryAdapter = new CategoryAdapter(view.getContext() , new ArrayList<>() , "category");

        categoryPresenter = new CategoryPresenterImpl(this, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(),
                MealsLocalDataSourceImpl.getInstance(view.getContext())));
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryPresenter.getCategories();
    }

    @Override
    public void showData(List<Category> categories) {
        Log.i("TAG", "showData: " + categories.size());
        categoryAdapter.updateData(categories);
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorMsg(String error) {
        Toast.makeText(this.getContext(), error, Toast.LENGTH_SHORT).show();
    }
}