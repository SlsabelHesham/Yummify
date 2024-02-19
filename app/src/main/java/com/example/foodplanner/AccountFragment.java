package com.example.foodplanner;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.FavouriteMeals.View.FavMealsView;
import com.example.foodplanner.FavouriteMeals.presenter.FavMealsPresenter;
import com.example.foodplanner.FavouriteMeals.presenter.FavMealsPresenterImpl;
import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.Model.MealPlan;
import com.example.foodplanner.Model.MealsRepositoryImpl;
import com.example.foodplanner.Network.MealsRemoteDataSourceImpl;
import com.example.foodplanner.db.MealsLocalDataSourceImpl;
import com.example.foodplanner.meal.presenter.MealPresenter;
import com.example.foodplanner.meal.presenter.MealPresenterImpl;
import com.example.foodplanner.meal.view.MealsView;
import com.example.foodplanner.plan.presenter.PlanPresenter;
import com.example.foodplanner.plan.presenter.PlanPresenterImpl;
import com.example.foodplanner.plan.view.PlanView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;

public class AccountFragment extends Fragment implements FavMealsView, MealsView, PlanView {

    NavController navController;
    Button button , store , restore;

    TextView username , email;
    private FirebaseFirestore db;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //button = view.findViewById(R.id.logout);
        username = view.findViewById(R.id.username);
        email = view.findViewById(R.id.email);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String name = user != null ? user.getDisplayName() : null;
        String userEmail = user != null ? user.getEmail() : null;
        username.setText("Username: "+name);
        email.setText("Email: "+userEmail);
        store = view.findViewById(R.id.store);
        db = FirebaseFirestore.getInstance();
        restore = view.findViewById(R.id.restore);
        FavMealsPresenter favMealsPresenter = new FavMealsPresenterImpl(this, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(),
                MealsLocalDataSourceImpl.getInstance(view.getContext())));
        MealPresenter mealPresenter = new MealPresenterImpl(this, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(),
                MealsLocalDataSourceImpl.getInstance(view.getContext())));

        PlanPresenter planPresenter = new PlanPresenterImpl(this, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(),
                MealsLocalDataSourceImpl.getInstance(view.getContext())));

        navController = Navigation.findNavController((Activity) view.getContext(), R.id.fragmentNavHost);
   /*     button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                SharedPreferences preferences = view.getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isLoggedIn", false);
                editor.apply();
                navController.navigate(R.id.action_accountFragment_to_welcomeFragment);
            }
        });
*/
        store.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View view) {
                ConnectivityManager connectivityManager = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                CollectionReference dbMeals = db.collection("Meals");
                Flowable<List<Meal>> mealsList;

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String email = user != null ? user.getEmail() : null;
                mealsList = favMealsPresenter.getStoredMeals(email);
                dbMeals.get().addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            document.getReference().delete();
                                        }
                                        mealsList.observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(mealList -> {
                                                    for (Meal meal : mealList) {
                                                        dbMeals.add(meal).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                            @Override
                                                            public void onSuccess(DocumentReference documentReference) {
                                                                Toast.makeText(getContext(), "Your Course has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(getContext(), "Fail to add course \n" + e, Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    }
                                                });
                                    }
                });
                CollectionReference dbMealsPlan = db.collection("MealsPlan");
                Flowable<List<MealPlan>> mealPlanList;
                mealPlanList = planPresenter.getAllWeekPlan(email);
                dbMealsPlan.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            document.getReference().delete();
                        }
                        mealPlanList.observeOn(AndroidSchedulers.mainThread())
                                .subscribe(mealsPlanList -> {
                                    for (MealPlan mealPlan : mealsPlanList) {
                                        dbMealsPlan.add(mealPlan).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Toast.makeText(getContext(), "Your Course has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getContext(), "Fail to add course \n" + e, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                });
                    }
                });
                } else {
                    Toast.makeText(getContext(), "Please Connect to the Internet!", Toast.LENGTH_SHORT).show();                }

            }
        });

        restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connectivityManager = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                    CollectionReference dbMeals = db.collection("Meals");
                    CollectionReference dbMealsPlan = db.collection("MealsPlan");
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String email = user != null ? user.getEmail() : null;
                    dbMeals.whereEqualTo("email", email)
                            .get()
                            .addOnSuccessListener(queryDocumentSnapshots -> {
                                List<Meal> mealsList = new ArrayList<>();
                                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                    Meal meal = document.toObject(Meal.class);
                                    mealsList.add(meal);
                                }
                                for (Meal meal : mealsList) {
                                    mealPresenter.addToFav(meal);
                                }
                                Toast.makeText(getContext(), "Your favourite Meals are restored", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getContext(), "Failed to load meals from Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });

                    dbMealsPlan.whereEqualTo("email", email)
                            .get()
                            .addOnSuccessListener(queryDocumentSnapshots -> {
                                List<MealPlan> mealsPlanList = new ArrayList<>();
                                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                    MealPlan meal = document.toObject(MealPlan.class);
                                    mealsPlanList.add(meal);
                                }
                                for (MealPlan mealPlan : mealsPlanList) {
                                    planPresenter.addMealToPlan(mealPlan);
                                }
                                Toast.makeText(getContext(), "Your plan are restored", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getContext(), "Failed to load meals from Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                }
                else {
                    Toast.makeText(getContext(), "Please Connect to the Internet!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }





















    public void showData(Flowable<List<Meal>> mealsList) {
    }

    @Override
    public void showData(List<Meal> meal) {

    }

    @Override
    public void showErrorMsg(String error) {

    }

    @Override
    public void addMeal(Meal meal) {

    }

    @Override
    public void removeMeal(Meal meal) {

    }

    @Override
    public void showSaturdayData(Flowable<List<MealPlan>> mealPlan) {

    }

    @Override
    public void showSundayData(Flowable<List<MealPlan>> mealPlan) {

    }

    @Override
    public void showMondayData(Flowable<List<MealPlan>> mealPlan) {

    }

    @Override
    public void showTuesdayData(Flowable<List<MealPlan>> mealPlan) {

    }

    @Override
    public void showWednesdayData(Flowable<List<MealPlan>> mealPlan) {

    }

    @Override
    public void showThursdayData(Flowable<List<MealPlan>> mealPlan) {

    }

    @Override
    public void showFridayData(Flowable<List<MealPlan>> mealPlan) {

    }

    @Override
    public void removeMeal(MealPlan mealPlan) {

    }

    @Override
    public void removeWeekPlan(String email) {

    }

    @Override
    public void addToPlan(MealPlan mealPlan) {

    }

}