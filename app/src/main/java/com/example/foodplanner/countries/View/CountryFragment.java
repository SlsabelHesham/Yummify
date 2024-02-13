package com.example.foodplanner.countries.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplanner.AllMeals.presenter.AllMealsPresenter;
import com.example.foodplanner.AllMeals.presenter.AllMealsPresenterImpl;
import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.Model.MealsRepositoryImpl;
import com.example.foodplanner.Network.MealsRemoteDataSourceImpl;
import com.example.foodplanner.R;
import com.example.foodplanner.countries.presenter.CountryPresenter;
import com.example.foodplanner.countries.presenter.CountryPresenterImpl;
import com.example.foodplanner.db.MealsLocalDataSourceImpl;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class CountryFragment extends Fragment implements CountryView{

    RecyclerView countryRecyclerView;
    LinearLayoutManager layoutManager;
    CountryAdapter countryAdapter;

    CountryPresenter countryPresenter;
    ChipGroup countriesChipGroup;
    AllMealsPresenter allMealsPresenter;

    TextView countriesHint;
    LottieAnimationView countryAnimation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        countryRecyclerView = view.findViewById(R.id.countriesMealsRecyclerView);
        countriesChipGroup = view.findViewById(R.id.chipGroup);
        countriesHint = view.findViewById(R.id.selectCountryTV);
        countryAnimation = view.findViewById(R.id.countryAnimation);
        layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        countryAdapter = new CountryAdapter(view.getContext() , new ArrayList<>());

        countryPresenter = new CountryPresenterImpl(this, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(),
                MealsLocalDataSourceImpl.getInstance(view.getContext())));
         countryRecyclerView.setAdapter(countryAdapter);
        countryRecyclerView.setLayoutManager(layoutManager);
        countryPresenter.getCountries();

        countriesChipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, int checkedId) {
                Chip chip = group.findViewById(checkedId);
                Log.i("test", "onCheckedChanged: enterr");
                countryAnimation.setVisibility(View.GONE);
                countriesHint.setVisibility(View.GONE);
                if(chip != null){
                    String chipText = chip.getText().toString();
                    Log.i("test", "onCheckedChanged: "+ chipText);
                    allMealsPresenter = new AllMealsPresenterImpl(CountryFragment.this, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(),
                            MealsLocalDataSourceImpl.getInstance(view.getContext())));
                    allMealsPresenter.getAllCountryMeals(chipText);
                }
            }
        });
    }

    @Override
    public void showData(List<Meal> countries) {
        for(int i = 0;i<countries.size(); i++) {
            if (countries.get(i).getStrArea().equals("Unknown")) {
                continue;
            }
            else{
                Chip chip = new Chip(getContext());
                countriesChipGroup.addView(chip);
                //ChipDrawable chipDrawable = ChipDrawable.createFromResource(getContext(), com.google.android.material.R.style.Widget_MaterialComponents_Chip_Choice);
                //chip.setChipDrawable(chipDrawable);
                chip.setText(countries.get(i).getStrArea());
                chip.setHeight(160);
                chip.setTextColor(ContextCompat.getColor(getContext(), R.color.main_green));
                chip.setTextSize(18);
            }
        }
    }

    @Override
    public void showErrorMsg(String error) {

    }

    @Override
    public void showPopularMeals(List<Meal> meals) {
        countryAdapter.updateData(meals);
        countryAdapter.notifyDataSetChanged();
    }
}