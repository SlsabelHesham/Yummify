package com.example.foodplanner.plan.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplanner.Model.MealPlan;
import com.example.foodplanner.Model.MealsRepositoryImpl;
import com.example.foodplanner.Network.MealsRemoteDataSourceImpl;
import com.example.foodplanner.R;
import com.example.foodplanner.db.MealsLocalDataSourceImpl;
import com.example.foodplanner.plan.presenter.PlanPresenter;
import com.example.foodplanner.plan.presenter.PlanPresenterImpl;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;

public class PlanFragment extends Fragment implements PlanView , OnPlanClickListener {

    WeekItem saturday , sunday,monday,tuesday,wednesday,thursday,friday;
    List<WeekItem> itemList = new ArrayList<>();
    PlanPresenter planPresenter;
    Flowable<List<MealPlan>> plan;
    String email;
    WeekItemAdapter weekItemAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        RecyclerView ParentRecyclerViewItem = view.findViewById(R.id.weekRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        weekItemAdapter = new WeekItemAdapter(view.getContext() , itemList , this);
        ParentRecyclerViewItem.setAdapter(weekItemAdapter);
        ParentRecyclerViewItem.setLayoutManager(layoutManager);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        email = user != null ? user.getEmail() : null;

        planPresenter = new PlanPresenterImpl(this, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(),
                MealsLocalDataSourceImpl.getInstance(view.getContext())));

        Calendar calendar = Calendar.getInstance();
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        int lastSavedWeek = sharedPreferences.getInt("lastSavedWeek", -1);

        Log.i("sh", "onViewCreated: "+lastSavedWeek);
        if (currentWeek != lastSavedWeek) {
            removeWeekPlan(email);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("lastSavedWeek", currentWeek);
            editor.apply();
        }
        if(itemList.isEmpty()){
            ParentItemList();
        }

        plan = planPresenter.getWeekPlan(email , "Saturday");
        if(plan != null){
            showSaturdayData(plan);
        }

        plan = planPresenter.getWeekPlan(email , "Sunday");
        if(plan != null){
            showSundayData(plan);
        }

        plan = planPresenter.getWeekPlan(email , "Monday");
        if(plan != null){
            showMondayData(plan);
        }

        plan = planPresenter.getWeekPlan(email , "Tuesday");
        if(plan != null){
            showTuesdayData(plan);
        }

        plan = planPresenter.getWeekPlan(email , "Wednesday");
        if(plan != null){
            showWednesdayData(plan);
        }

        plan = planPresenter.getWeekPlan(email , "Thursday");
        if(plan != null){
            showThursdayData(plan);
        }

        plan = planPresenter.getWeekPlan(email , "Friday");
        if(plan != null){
            showFridayData(plan);
        }
    }

    private void ParentItemList() {
        saturday = new WeekItem("Saturday", new ArrayList<>());
        itemList.add(saturday);
        sunday = new WeekItem("Sunday", new ArrayList<>());
        itemList.add(sunday);
        monday = new WeekItem("Monday", new ArrayList<>());
        itemList.add(monday);
        tuesday = new WeekItem("Tuesday", new ArrayList<>());
        itemList.add(tuesday);
        wednesday = new WeekItem("Wednesday", new ArrayList<>());
        itemList.add(wednesday);
        thursday = new WeekItem("Thursday", new ArrayList<>());
        itemList.add(thursday);
        friday = new WeekItem("Friday", new ArrayList<>());
        itemList.add(friday);
    }


    @Override
    public void showSaturdayData(Flowable<List<MealPlan>> mealPlan) {
        Log.i("plan", "showData: ");
        mealPlan.observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealPlanList -> {
                    saturday = new WeekItem("Saturday", mealPlanList);
                    itemList.set(0 , saturday);
                    weekItemAdapter.updateData(itemList);
                    weekItemAdapter.notifyDataSetChanged();

                });
    }
    @Override
    public void showSundayData(Flowable<List<MealPlan>> mealPlan) {
        mealPlan.observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealPlanList -> {
                    sunday = new WeekItem("Sunday", mealPlanList);
                    itemList.set(1 , sunday);
                    weekItemAdapter.updateData(itemList);
                    weekItemAdapter.notifyDataSetChanged();

                });
    }
    @Override
    public void showMondayData(Flowable<List<MealPlan>> mealPlan) {
        mealPlan.observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealPlanList -> {
                    monday = new WeekItem("Monday", mealPlanList);
                    itemList.set(2 , monday);
                    weekItemAdapter.updateData(itemList);
                    weekItemAdapter.notifyDataSetChanged();

                });


    }
    @Override
    public void showTuesdayData(Flowable<List<MealPlan>> mealPlan) {
        mealPlan.observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealPlanList -> {
                    tuesday = new WeekItem("Tuesday", mealPlanList);
                    itemList.set(3 , tuesday);
                    weekItemAdapter.updateData(itemList);
                    weekItemAdapter.notifyDataSetChanged();

                });


    }
    @Override
    public void showWednesdayData(Flowable<List<MealPlan>> mealPlan) {
        mealPlan.observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealPlanList -> {
                    wednesday = new WeekItem("Wednesday", mealPlanList);
                    itemList.set(4 , wednesday);
                    weekItemAdapter.updateData(itemList);
                    weekItemAdapter.notifyDataSetChanged();

                });

    }
    @Override
    public void showThursdayData(Flowable<List<MealPlan>> mealPlan) {
        mealPlan.observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealPlanList -> {
                    thursday = new WeekItem("Thursday", mealPlanList);
                    itemList.set(5 , thursday);
                    weekItemAdapter.updateData(itemList);
                    weekItemAdapter.notifyDataSetChanged();

                });


    }
    @Override
    public void showFridayData(Flowable<List<MealPlan>> mealPlan) {
        mealPlan.observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealPlanList -> {
                    friday = new WeekItem("Friday", mealPlanList);
                    itemList.set(6 , friday);
                    weekItemAdapter.updateData(itemList);
                    weekItemAdapter.notifyDataSetChanged();

                });


    }
    @Override
    public void removeMeal(MealPlan mealPlan) {
        planPresenter.removeFromPlan(mealPlan);
    }

    @Override
    public void removeWeekPlan(String email) {
        planPresenter.removeWeekPlan(email);
    }

    @Override
    public void addToPlan(MealPlan mealPlan) {
        planPresenter.addMealToPlan(mealPlan);
    }
    @Override
    public void onDeleteMealPlanClick(MealPlan mealPlan) {
        removeMeal(mealPlan);
    }
}