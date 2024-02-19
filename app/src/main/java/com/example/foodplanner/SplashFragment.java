package com.example.foodplanner;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashFragment extends Fragment {

DrawerLayout drawerLayout;
    ImageView logo;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        logo = view.findViewById(R.id.splashLogo);
        drawerLayout = getActivity() != null ? getActivity().findViewById(R.id.drawerLayout) : null;
        if (drawerLayout != null) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
        final Animation myAnimation = AnimationUtils.loadAnimation(view.getContext(), R.anim.animation);
        myAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                try {
                Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                NavController navController = Navigation.findNavController((Activity) view.getContext(), R.id.fragmentNavHost);

                SharedPreferences preferences = getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
                boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

                if (isLoggedIn) {
                    navController.navigate(R.id.action_splashFragment_to_homeFragment);
                } else {
                    navController.navigate(R.id.action_splashFragment_to_welcomeFragment);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        logo.animate().rotation(360f).setDuration(1500).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                logo.startAnimation(myAnimation);
            }
        }).start();

    }
}