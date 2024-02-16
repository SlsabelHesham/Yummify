package com.example.foodplanner.FavouriteMeals.View;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.Model.MealPlan;
import com.example.foodplanner.R;

import java.io.Serializable;
import java.util.List;

public class FavMealsAdapter extends RecyclerView.Adapter<FavMealsAdapter.ViewHolder> {
    private static final String TAG = "MyAdapter";
    Context context;
    List<Meal> meals;

    String day = null , email = null;
    OnFavoriteClickListener listener;
    public FavMealsAdapter(Context context , List<Meal> meals , OnFavoriteClickListener listener, String day, String email) {
        this.context = context;
        this.meals = meals;
        this.listener = listener;
        this.day = day;
        this.email = email;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());

        View v = inflater.inflate(R.layout.favourite_item_layout, recyclerView, false);
        ViewHolder vh = new ViewHolder(v);

        Log.i(TAG, "onCreateViewHolder: ");

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.mealName.setText(meal.getStrMeal());
        Glide.with(context).load(meal.getStrMealThumb()).into(holder.mealImage);
        holder.mealFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDeleteFavMealClick(meal);
            }
        });
        holder.mealFavLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(day == null){
                    NavController navController = Navigation.findNavController((Activity) context , R.id.fragmentNavHost);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("meal", (Serializable) meal);
                    navController.navigate(R.id.action_favouritesFragment_to_mealFragment, bundle);
                }
                else{
                    MealPlan mealPlan = new MealPlan(email , day , meal);
                    listener.addToPlan(mealPlan);
                    Toast.makeText(view.getContext(),"Meal added to "+day+" plan",Toast.LENGTH_SHORT).show();
                    NavController navController = Navigation.findNavController((Activity) context , R.id.fragmentNavHost);
                    navController.navigate(R.id.action_favouritesFragment_to_planFragment);
                }
            }
        });
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
        public ImageView mealImage , mealFav;
        public ConstraintLayout mealFavLayout;
        public View layout;
        public ViewHolder (View v) {
            super(v);
            layout = v;
            mealName = v.findViewById(R.id.tvMealName);
            mealImage = v.findViewById(R.id.mealImage);
            mealFavLayout = v.findViewById(R.id.mealLayout);
            mealFav = v.findViewById(R.id.favMealImage);
        }
    }
}
