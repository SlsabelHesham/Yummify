package com.example.foodplanner.Network;


import com.example.foodplanner.Model.Meal;

import java.util.List;

public interface NetworkCallback<T> {
    public void onSuccessResult(List<T> list);
    public void onFailureResult(String errorMsg);
}
