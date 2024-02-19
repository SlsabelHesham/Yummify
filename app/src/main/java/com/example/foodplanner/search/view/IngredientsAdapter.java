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
import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.R;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
    private static final String TAG = "MyAdapter";
    Context context;
    List<Meal> mealsIngredients;

    public IngredientsAdapter(Context context , List<Meal> mealsIngredients) {
        this.context = context;
        this.mealsIngredients = mealsIngredients;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());

        View v = inflater.inflate(R.layout.ingredient_layout, recyclerView, false);
        ViewHolder vh = new ViewHolder(v);

        Log.i(TAG, "onCreateViewHolder: ");

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meal meal = mealsIngredients.get(position);
        //holder.countryName.setText(meal.getStrIngredient());
        Glide.with(context).load("https://www.themealdb.com/images/ingredients/"+meal.getStrIngredient()+".png").into(holder.ingredientImage);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController((Activity) context, R.id.fragmentNavHost);
                Bundle bundle = new Bundle();
                bundle.putString("ingredientName", meal.getStrIngredient());
                navController.navigate(R.id.action_searchFragment_to_allIngredientMealsFragment, bundle);

            }
        });

           Log.i(TAG, "***** onBindViewHolder *****");
    }

    @Override
    public int getItemCount() {
        return mealsIngredients.size();
    }

    public void updateData(List<Meal> meals) {
        this.mealsIngredients = meals;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ingredientImage;
        ConstraintLayout constraintLayout;
        public View layout;
        public ViewHolder (View v) {
            super(v);
            layout = v;
            ingredientImage = v.findViewById(R.id.ingredientImage);
            constraintLayout = v.findViewById(R.id.ingredientLayout);
        }
    }
}
