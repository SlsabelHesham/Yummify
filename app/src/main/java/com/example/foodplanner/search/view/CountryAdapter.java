package com.example.foodplanner.search.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.Model.Category;
import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.R;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {
    private static final String TAG = "MyAdapter";
    Context context;
    List<Meal> mealsCountries;

    public CountryAdapter(Context context , List<Meal> mealsCountries) {
        this.context = context;
        this.mealsCountries = mealsCountries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());

        View v = inflater.inflate(R.layout.country_layout, recyclerView, false);
        ViewHolder vh = new ViewHolder(v);

        Log.i(TAG, "onCreateViewHolder: ");

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meal meal = mealsCountries.get(position);
        holder.countryName.setText(meal.getStrArea());

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController((Activity) context, R.id.fragmentNavHost);
                Bundle bundle = new Bundle();
                bundle.putString("countryName", meal.getStrArea());
                navController.navigate(R.id.action_searchFragment_to_allCountryMealsFragment, bundle);

            }
        });

           Log.i(TAG, "***** onBindViewHolder *****");
    }

    @Override
    public int getItemCount() {
        return mealsCountries.size();
    }

    public void updateData(List<Meal> meals) {
        this.mealsCountries = meals;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView countryName;
        ConstraintLayout constraintLayout;
        public View layout;
        public ViewHolder (View v) {
            super(v);
            layout = v;
            countryName = v.findViewById(R.id.countryName);
            constraintLayout = v.findViewById(R.id.countryLayout);
        }
    }
}
