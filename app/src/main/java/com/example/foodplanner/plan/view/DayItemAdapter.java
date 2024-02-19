package com.example.foodplanner.plan.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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
import com.example.foodplanner.Model.MealPlan;
import com.example.foodplanner.R;

import java.io.Serializable;
import java.util.List;

public class DayItemAdapter extends RecyclerView.Adapter<DayItemAdapter.WeekViewHolder> {

    private List<MealPlan> meals;
    Context context;
    OnPlanClickListener listener;

    DayItemAdapter(Context context, List<MealPlan> meals, OnPlanClickListener listener)
    {
        this.context = context;
        this.meals = meals;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WeekViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.plan_meals_layout,
                        viewGroup, false);
        return new WeekViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeekViewHolder weekViewHolder, int position) {
        MealPlan mealPlan = meals.get(position);
        weekViewHolder.ChildItemTitle.setText(mealPlan.getMeal().getStrMeal());
        Glide.with(context).load(mealPlan.getMeal().getStrMealThumb()).into(weekViewHolder.mealImage);
        weekViewHolder.removeFromPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDeleteMealPlanClick(mealPlan);
            }
        });
        weekViewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController((Activity) context , R.id.fragmentNavHost);
                Bundle bundle = new Bundle();
                bundle.putSerializable("meal", (Serializable) mealPlan.getMeal());
                navController.navigate(R.id.action_planFragment_to_mealFragment, bundle);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return meals.size();
    }
    class WeekViewHolder extends RecyclerView.ViewHolder {

        TextView ChildItemTitle;
        ImageView mealImage, removeFromPlan;
        ConstraintLayout constraintLayout;

        WeekViewHolder(View itemView)
        {
            super(itemView);
            ChildItemTitle = itemView.findViewById(R.id.tvMealName);
            mealImage = itemView.findViewById(R.id.mealImage);
            removeFromPlan = itemView.findViewById(R.id.removeFromPlan);
            constraintLayout = itemView.findViewById(R.id.mealLayout);
        }
    }
}