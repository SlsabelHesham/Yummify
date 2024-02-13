package com.example.foodplanner.Randoms.View;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
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

public class RandomAdapter extends RecyclerView.Adapter<RandomAdapter.ViewHolder> {
    private static final String TAG = "MyAdapter";
    Context context;
    List<Meal> meals;

    OnRandomClickListener listener;
    public RandomAdapter(Context context , List<Meal> meals , OnRandomClickListener listener) {
        this.context = context;
        this.meals = meals;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext());

        View v = inflater.inflate(R.layout.random_meal_layout, recyclerView, false);
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
        else {
            holder.mealName.setText(meal.getStrMeal());
        }
        holder.mealArea.setText(meal.getStrArea());
        holder.mealCategory.setText(meal.getStrCategory());
        Glide.with(context).load(meal.getStrMealThumb()).into(holder.mealImage);
        holder.randomMealLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController((Activity) context, R.id.fragmentNavHost);
                Bundle bundle = new Bundle();
                bundle.putSerializable("meal", (Serializable) meal);
                navController.navigate(R.id.action_homeFragment_to_mealFragment, bundle);

            }
        });
           Log.i(TAG, "***** onBindViewHolder *****");
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void updateData(List<Meal> meals) {
        this.meals.addAll(meals);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mealName;
        public TextView mealCategory;
        public TextView mealArea;
        public ImageView mealImage;
        public ConstraintLayout randomMealLayout;
        public View layout;
        public ViewHolder (View v) {
            super(v);
            layout = v;
            mealName = v.findViewById(R.id.tvMealName);
            mealCategory = v.findViewById(R.id.tvMealCategory);
            mealArea = v.findViewById(R.id.tvMealArea);
            mealImage = v.findViewById(R.id.mealImage);
            randomMealLayout = v.findViewById(R.id.rowLayout);
        }
    }
}
