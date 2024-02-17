package com.example.foodplanner.search.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodplanner.AllMeals.View.MealsAdapter;
import com.example.foodplanner.Model.Category;
import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.Model.MealsRepositoryImpl;
import com.example.foodplanner.Network.MealsRemoteDataSourceImpl;
import com.example.foodplanner.R;
import com.example.foodplanner.categories.View.CategoryAdapter;
import com.example.foodplanner.categories.presenter.CategoryPresenterImpl;
import com.example.foodplanner.db.MealsLocalDataSourceImpl;
import com.example.foodplanner.search.presenter.SearchPresenter;
import com.example.foodplanner.search.presenter.SearchPresenterImpl;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchFragment extends Fragment implements SearchView {
    CategoryAdapter categoryAdapter;
    CountryAdapter countryAdapter;
    MealsAdapter mealsAdapter;
    ChipGroup chipGroup;
    Chip categoryChip, countryChip, ingredientChip;
    String chipType;
    EditText searchET;
    RecyclerView searchRecyclerView;
    GridLayoutManager  layoutManager;
    GridLayoutManager ingredientLayoutManager;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    SearchPresenter searchPresenter;
    List<Category> categoryList;
    List<Meal> countryList;
    List<Meal> ingredientList;
    IngredientsAdapter ingredientsAdapter;
    Observable<String> observable;
    boolean searchMeal = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        chipGroup = view.findViewById(R.id.searchChipGroup);
        categoryChip = view.findViewById(R.id.categoryChip);
        countryChip = view.findViewById(R.id.countryChip);
        ingredientChip = view.findViewById(R.id.ingredientsChip);
        categoryChip.setChecked(false);
        countryChip.setChecked(false);
        ingredientChip.setChecked(false);
        searchET = view.findViewById(R.id.searchET);
        searchRecyclerView = view.findViewById(R.id.searchRecyclerView);
        layoutManager = new GridLayoutManager(view.getContext(), 2);
        ingredientLayoutManager = new GridLayoutManager(view.getContext(), 3);

        layoutManager.setOrientation(RecyclerView.VERTICAL);
        categoryAdapter = new CategoryAdapter(view.getContext() , new ArrayList<>() , "search");
        countryAdapter = new CountryAdapter(view.getContext() , new ArrayList<>());
        ingredientsAdapter = new IngredientsAdapter(view.getContext(),new ArrayList<>());
        mealsAdapter = new MealsAdapter(view.getContext() , new ArrayList<>() , "mealSearch");

        searchPresenter = new SearchPresenterImpl(this, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(),
                MealsLocalDataSourceImpl.getInstance(view.getContext())));
        searchRecyclerView.setAdapter(categoryAdapter);
        searchRecyclerView.setLayoutManager(layoutManager);
        searchPresenter.getCategories();
        searchPresenter.getCountries();
        searchPresenter.getIngredients();

        observable = Observable.create((ObservableOnSubscribe<String>) emitter -> {
            if (searchET != null) {
            searchET.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                    Log.i("TEST12345", "onTextChanged: 3");
                    emitter.onNext(charSequence.toString());
                    if (!categoryChip.isChecked() && !countryChip.isChecked() && !ingredientChip.isChecked()) {
                        Log.i("TEST12345", "onTextChanged: ");
                        searchMeal = true;
                        mealObserver(observable);
                    } else {
                        Log.i("TEST12345", "onTextChanged: 2");

                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
            }
        });

        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, int checkedId) {
                Chip chip = group.findViewById(checkedId);
                if(chip != null){
                    chipType = chip.getText().toString();
                    if(chipType.equals("Category")){
                        searchMeal = false;
                        searchRecyclerView.setAdapter(categoryAdapter);
                        layoutManager.setSpanCount(2);
                        searchRecyclerView.setLayoutManager(layoutManager);
                        categoryAdapter.updateData(categoryList);
                        categoryObserver(observable);

                    }
                    else if(chipType.equals("Country")){
                        Toast.makeText(getContext(), "country" , Toast.LENGTH_SHORT).show();
                        searchMeal = false;
                        layoutManager.setSpanCount(2);
                        searchRecyclerView.setLayoutManager(layoutManager);
                        searchRecyclerView.setAdapter(countryAdapter);
                        countryAdapter.updateData(countryList);

                        countryObserver(observable);
                    }
                    else if(chipType.equals("Ingredients")){
                        searchMeal = false;
                        Toast.makeText(getContext(), "Ingredients" , Toast.LENGTH_SHORT).show();
                        searchRecyclerView.setLayoutManager(ingredientLayoutManager);
                        searchRecyclerView.setAdapter(ingredientsAdapter);
                        ingredientsAdapter.updateData(ingredientList);

                        ingredientObserver(observable);
                    }
                }
            }
        });


    }

    @SuppressLint("CheckResult")
    public void categoryObserver(Observable<String> observable){
        observable.subscribe(
                categoryName -> {
                    List<Category> filteredList = categoryList.stream()
                            .filter(category -> category.getStrCategory().toLowerCase().contains(categoryName.toLowerCase()))
                            .collect(Collectors.toList());
                    categoryAdapter.updateData(filteredList);
                });
    }
    @SuppressLint("CheckResult")
    public void countryObserver(Observable<String> observable){
        observable.subscribe(
                countryName -> {
                    List<Meal> filteredList = countryList.stream()
                            .filter(country -> country.getStrArea().toLowerCase().contains(countryName.toLowerCase()))
                            .collect(Collectors.toList());
                    countryAdapter.updateData(filteredList);
                });
    }
    @SuppressLint("CheckResult")
    public void ingredientObserver(Observable<String> observable){
        observable.subscribe(
                ingredientName -> {
                    List<Meal> filteredList = ingredientList.stream()
                            .filter(ingredient -> ingredient.getStrIngredient().toLowerCase().contains(ingredientName.toLowerCase()))
                            .collect(Collectors.toList());
                    ingredientsAdapter.updateData(filteredList);
                });
    }

    @SuppressLint("CheckResult")
    public void mealObserver(Observable<String> observable){
        observable.debounce(1000, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        .subscribe(
                mealName -> {
                    if(searchMeal){
                        searchPresenter.getMealsByName(mealName);
                    }

                });
    }
    @Override
    public void showCategoryData(List<Category> categoryList) {
        this.categoryList = categoryList;
        categoryAdapter.updateData(categoryList);
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCountryData(List<Meal> countryList) {
        this.countryList = countryList;
    }

    @Override
    public void showIngredientsData(List<Meal> ingredientList) {
        this.ingredientList = ingredientList;
        Log.i("TEST123", "showIngredientsData1: ");
    }

    @Override
    public void showMealsData(List<Meal> mealList) {
        searchRecyclerView.setAdapter(mealsAdapter);
        layoutManager.setSpanCount(1);
        searchRecyclerView.setLayoutManager(layoutManager);
        mealsAdapter.updateData(mealList);
    }

    @Override
    public void showErrorMsg(String error) {
        Toast.makeText(this.getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
    }
}