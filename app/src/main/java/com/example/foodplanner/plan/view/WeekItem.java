package com.example.foodplanner.plan.view;

import com.example.foodplanner.Model.MealPlan;
import java.util.List;

public class WeekItem {
    private String weekDay;
    private List<MealPlan> mealPlanList;
    public WeekItem(String weekDay, List<MealPlan> mealPlanList) {
        this.weekDay = weekDay;
        this.mealPlanList = mealPlanList;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public List<MealPlan> getMealPlanList() {
        return mealPlanList;
    }

    public void setMealPlanList(List<MealPlan> mealPlanList) {
        this.mealPlanList = mealPlanList;
    }
}