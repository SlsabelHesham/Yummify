package com.example.foodplanner.Model;

import java.util.List;

public class IngredientResponse {
    private List<Ingredients> ingredients;
    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }
}
