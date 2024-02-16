package com.example.foodplanner.plan.view;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodplanner.R;

import java.util.List;

public class WeekItemAdapter extends RecyclerView.Adapter<WeekItemAdapter.WeekViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<WeekItem> weekItemList;
    Context context;
    OnPlanClickListener listener;

    public WeekItemAdapter(Context context , List<WeekItem> weekItemList, OnPlanClickListener listener)
    {
        this.context = context;
        this.weekItemList = weekItemList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WeekViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.week_item, viewGroup, false);
        return new WeekViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeekViewHolder weekViewHolder, int position) {

        WeekItem weekItem = weekItemList.get(position);
        weekViewHolder.weekDay.setText(weekItem.getWeekDay());
        LinearLayoutManager layoutManager = new LinearLayoutManager(weekViewHolder.dayRecyclerView
                .getContext(), LinearLayoutManager.HORIZONTAL, false);

        layoutManager.setInitialPrefetchItemCount(weekItem.getMealPlanList().size());

        DayItemAdapter childItemAdapter = new DayItemAdapter(context ,weekItem.getMealPlanList(),listener);
        weekViewHolder.dayRecyclerView.setLayoutManager(layoutManager);
        weekViewHolder.dayRecyclerView.setAdapter(childItemAdapter);
        weekViewHolder.dayRecyclerView.setRecycledViewPool(viewPool);

        weekViewHolder.addToPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController((Activity) view.getContext(),R.id.fragmentNavHost);
                Bundle bundle = new Bundle();
                bundle.putString("day", weekItem.getWeekDay());
                navController.navigate(R.id.action_planFragment_to_favouritesFragment , bundle);
            }
        });
    }
    public void updateData(List<WeekItem> weekItemList) {
        this.weekItemList = weekItemList;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount()
    {
        return weekItemList.size();
    }
    class WeekViewHolder extends RecyclerView.ViewHolder {

        private TextView weekDay;
        private RecyclerView dayRecyclerView;
        private ImageView addToPlan;

        WeekViewHolder(final View itemView)
        {
            super(itemView);
            weekDay = itemView.findViewById(R.id.planDay);
            dayRecyclerView = itemView.findViewById(R.id.dayRecyclerView);
            addToPlan = itemView.findViewById(R.id.addToPlan);
        }
    }
}
