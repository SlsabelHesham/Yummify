package com.example.foodplanner.categories.presenter;

import com.example.foodplanner.Model.Category;
import com.example.foodplanner.Model.MealsRepositoryImpl;
import com.example.foodplanner.categories.View.CategoryFragment;
import com.example.foodplanner.categories.View.CategoryView;
import java.util.List;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoryPresenterImpl implements CategoryPresenter {

    MealsRepositoryImpl repository;
    CategoryView view;


    public CategoryPresenterImpl(CategoryFragment categoryFragment, MealsRepositoryImpl productsRepository) {
        this.repository = productsRepository;
        this.view = categoryFragment;
    }

    @Override
    public void getCategories() {
        Observable<List<Category>> observable = repository.getAllCategories();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categoryList -> {
                    view.showData(categoryList);
                });
    }
}
