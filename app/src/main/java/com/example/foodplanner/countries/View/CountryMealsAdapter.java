package com.example.foodplanner.countries.View;

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
import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.R;

import java.io.Serializable;
import java.util.List;

public class CountryMealsAdapter extends RecyclerView.Adapter<CountryMealsAdapter.ViewHolder> {
    private static final String TAG = "MyAdapter";
    Context context;
    List<Meal> meals;

    public CountryMealsAdapter(Context context , List<Meal> meals) {
        this.context = context;
        this.meals = meals;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());

        View v = inflater.inflate(R.layout.all_meals_layout, recyclerView, false);
        ViewHolder vh = new ViewHolder(v);

        Log.i(TAG, "onCreateViewHolder: ");

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meal meal = meals.get(position);
        if(meal.getStrMeal().length() > 21){
            String mealName = meal.getStrMeal().substring(0 , 21);
            holder.mealName.setText(mealName+" ...");
        }
        else{
            Log.i(TAG, "onBindViewHolder: " + meal.getStrMeal());
            holder.mealName.setText(meal.getStrMeal());
        }
        Glide.with(context).load(meal.getStrMealThumb()).into(holder.mealImage);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController((Activity) context, R.id.fragmentNavHost);
                Bundle bundle = new Bundle();
                bundle.putSerializable("meal", (Serializable) meal);
                navController.navigate(R.id.action_countryFragment_to_mealFragment, bundle);

            }
        });
        Log.i(TAG, "***** onBindViewHolder *****");
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void updateData(List<Meal> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mealName;
        public ImageView mealImage;
        ConstraintLayout constraintLayout;
        public View layout;
        public ViewHolder (View v) {
            super(v);
            layout = v;
            mealName = v.findViewById(R.id.tvMealName);
            mealImage = v.findViewById(R.id.mealImage);
            constraintLayout = v.findViewById(R.id.mealLayout);
        }
    }
}
