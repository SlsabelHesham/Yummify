package com.example.foodplanner;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeFragment extends Fragment {
    Button login, signup;
    DrawerLayout drawerLayout;
    TextView guest;
    NavController navController;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        login = view.findViewById(R.id.welcomeLoginBtn);
        signup = view.findViewById(R.id.welcomeSignupBtn);
        guest = view.findViewById(R.id.welcome_guest);
        drawerLayout = getActivity().findViewById(R.id.drawerLayout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        navController = Navigation.findNavController((Activity) view.getContext(), R.id.fragmentNavHost);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_welcomeFragment_to_loginFragment);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_welcomeFragment_to_signupFragment);
            }
        });
        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("You'll miss out on personalized content and saving our delicious recipes.");
                builder.setTitle("Wait! Are you sure?");
                builder.setCancelable(false);
                builder.setPositiveButton("YES, I'M SURE", (DialogInterface.OnClickListener) (dialog, which) -> {
                    SharedPreferences preferences = getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("guest", true);
                    editor.apply();
                    navController.navigate(R.id.action_welcomeFragment_to_homeFragment);
                });
                builder.setNegativeButton("NO, GO BACK", (DialogInterface.OnClickListener) (dialog, which) -> {
                    dialog.cancel();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
}