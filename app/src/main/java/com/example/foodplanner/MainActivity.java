package com.example.foodplanner;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.foodplanner.authentication.LoginFragment;
import com.example.foodplanner.authentication.SignupFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    NavController navController;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    boolean guest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawerLayout);
    /*    actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
*/

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
                            navController.navigate(R.id.action_mealFragment_to_signupFragment);
                        });
                        builder.setNegativeButton("Cansel", (DialogInterface.OnClickListener) (dialog, which) -> {
                            dialog.cancel();
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                    else{
                        navController.navigate(R.id.action_homeFragment_to_favouritesFragment);
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
                    builder.setNegativeButton("Cansel", (DialogInterface.OnClickListener) (dialog, which) -> {
                        dialog.cancel();
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else{
                    navController.navigate(R.id.action_homeFragment_to_planFragment);
                    Log.i("SSD", "onOptionsItemSelected: FAV");
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }
/*
    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
*/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.i("TEST", "onOptionsItemSelected: ");
        if (item.getItemId() == android.R.id.home) {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentNavHost);
            if (!(currentFragment instanceof SplashFragment) &&
                    !(currentFragment instanceof LoginFragment) &&
                    !(currentFragment instanceof SignupFragment)&&
                    !(currentFragment instanceof WelcomeFragment)) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        }
        } else if (item.getItemId() == R.id.favouritesFragment) {
            SharedPreferences preferences = this.getSharedPreferences("pref", Context.MODE_PRIVATE);
            boolean guest = preferences.getBoolean("guest", false);
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
                builder.setNegativeButton("Cansel", (DialogInterface.OnClickListener) (dialog, which) -> {
                    dialog.cancel();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            else{
                navController.navigate(R.id.action_homeFragment_to_favouritesFragment);
                Log.i("SSD", "onOptionsItemSelected: FAV");
            }

        } else if (item.getItemId() == R.id.planFragment) {
            navController.navigate(R.id.action_homeFragment_to_planFragment);
        }
        else if (item.getItemId() == R.id.welcomeFragment) {
            Log.i("SSD", "onOptionsItemSelected: 0");

            Log.i("SSD", "onOptionsItemSelected: 1");
            navController.navigate(R.id.action_homeFragment_to_welcomeFragment);
            Log.i("SSD", "onOptionsItemSelected: 2");
            finish();
        }
        else{
            Log.i("SSD", "onOptionsItemSelected: 3");
        }

        Log.i("TAG", "onOptionsItemSelected: 88");
        return super.onOptionsItemSelected(item);
    }
}