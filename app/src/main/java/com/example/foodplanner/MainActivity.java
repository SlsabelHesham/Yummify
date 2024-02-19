package com.example.foodplanner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    boolean guest;
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawerLayout);
        navController = Navigation.findNavController(this, R.id.fragmentNavHost);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(item -> {
            SharedPreferences preferences = this.getSharedPreferences("pref", Context.MODE_PRIVATE);
            guest = preferences.getBoolean("guest", false);
            if(item.getItemId() == R.id.favouritesFragment)
                if(guest){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Add Your favourites, plan your meals and more!");
                    builder.setTitle("Sign Up for More Features");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Sign Up", (DialogInterface.OnClickListener) (dialog, which) -> {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("guest", false);
                        editor.apply();
                        navController.navigate(R.id.signupFragment);
                    });
                    builder.setNegativeButton("Cansel", (DialogInterface.OnClickListener) (dialog, which) -> dialog.cancel());
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else{
                    navController.navigate(R.id.favouritesFragment);
                    Log.i("SSD", "onOptionsItemSelected: FAV");
                }

            else if (item.getItemId() == R.id.planFragment) {
                if(guest){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Add Your favourites, plan your meals and more!");
                    builder.setTitle("Sign Up for More Features");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Sign Up", (DialogInterface.OnClickListener) (dialog, which) -> {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("guest", false);
                        editor.apply();
                        navController.navigate(R.id.action_mealFragment_to_signupFragment);
                    });
                    builder.setNegativeButton("Cansel", (DialogInterface.OnClickListener) (dialog, which) -> dialog.cancel());
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else{
                    navController.navigate(R.id.planFragment);
                    Log.i("SSD", "onOptionsItemSelected: FAV");
                }
            }
            else if(item.getItemId() == R.id.accountFragment){
                if(guest){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Add Your favourites, plan your meals and more!");
                    builder.setTitle("Sign Up for More Features");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Sign Up", (DialogInterface.OnClickListener) (dialog, which) -> {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("guest", false);
                        editor.apply();
                        navController.navigate(R.id.signupFragment);
                    });
                    builder.setNegativeButton("Cansel", (DialogInterface.OnClickListener) (dialog, which) -> dialog.cancel());
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else {
                    navController.navigate(R.id.accountFragment);
                }
            } else if (item.getItemId() == R.id.welcomeFragment) {
                if(guest){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Add Your favourites, plan your meals and more!");
                    builder.setTitle("Sign Up for More Features");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Sign Up", (DialogInterface.OnClickListener) (dialog, which) -> {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("guest", false);
                        editor.apply();
                        navController.navigate(R.id.signupFragment);
                    });
                    builder.setNegativeButton("Cansel", (DialogInterface.OnClickListener) (dialog, which) -> dialog.cancel());
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else {
                    ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                    if (networkInfo != null && networkInfo.isConnected()) {
                        FirebaseAuth.getInstance().signOut();
                        SharedPreferences preferences2 = this.getSharedPreferences("pref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences2.edit();
                        editor.putBoolean("isLoggedIn", false);
                        editor.apply();
                        navController.navigate(R.id.welcomeFragment);
                    }
                    else{
                        Toast.makeText(this, "Please Connect to the Internet!", Toast.LENGTH_SHORT).show();                }
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }
}