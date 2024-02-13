package com.example.foodplanner.categories.View;

import com.example.foodplanner.Model.Category;
import java.util.List;

public interface CategoryView {
    public void showData(List<Category> Categories);
    public void showErrorMsg(String error);
}