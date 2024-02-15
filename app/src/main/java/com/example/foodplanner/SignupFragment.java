package com.example.foodplanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.auth.UserProfileChangeRequest;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

public class SignupFragment extends Fragment {

    private EditText username,email,password,confirmPassword;
    private TextInputLayout usernameLayout,emailLayout,passwordLayout, confirmPasswordLayout;
    private Button signup;
    private TextView switchToLogin;
    private FirebaseAuth firebaseAuth;
    private NavController navController;
    private AtomicBoolean result;
    private SignInButton googleBtn;
    private GoogleSignInClient googleSignInClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        username = view.findViewById(R.id.usernameET);
        email = view.findViewById(R.id.emailET);
        password = view.findViewById(R.id.passwordET);
        confirmPassword = view.findViewById(R.id.confirmPasswordET);
        signup = view.findViewById(R.id.signupBtn);
        switchToLogin = view.findViewById(R.id.switchToLogin);
        usernameLayout = view.findViewById(R.id.usernameLayout);
        emailLayout = view.findViewById(R.id.emailLayout);
        passwordLayout = view.findViewById(R.id.passwordLayout);
        confirmPasswordLayout = view.findViewById(R.id.confirmPasswordLayout);
        switchToLogin = view.findViewById(R.id.switchToLogin);
        googleBtn = view.findViewById(R.id.googleBtn);
        navController = Navigation.findNavController((Activity) view.getContext(), R.id.fragmentNavHost);
        result = new AtomicBoolean(false);

        firebaseAuth = FirebaseAuth.getInstance();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().isEmpty()) {
                    username.requestFocus();
                    username.setError("Required");
                    //usernameLayout.setError("Required");
                }
                else if(email.getText().toString().isEmpty()) {
                    email.requestFocus();
                    email.setError("Required");
                    //emailLayout.setError("Required");
                }
                else if(password.getText().toString().isEmpty()) {
                    password.requestFocus();
                    password.setError("Required");
                }
                else if(confirmPassword.getText().toString().isEmpty()) {
                    confirmPassword.requestFocus();
                    confirmPassword.setError("Required");
                }
                else if(username.length() < 3) {
                    username.requestFocus();
                    username.setError("Username should be at least 3 characters");
                }
                else if(!isValidEmail(email.getText().toString())) {
                    email.requestFocus();
                    email.setError("Please enter a valid email");
                }
                else if(password.length() < 6){
                    password.requestFocus();
                    password.setError("Password should bbe at least 6 characters");
                }
                else if(!password.getText().toString().equals(confirmPassword.getText().toString())) {
                    confirmPassword.requestFocus();
                        confirmPassword.setError("Passwords didn't match");
                }
                else {
                    checkUserExist(email.getText().toString());

                }
            }
        });
        switchToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_signupFragment_to_loginFragment);
            }
        });

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("737512346717-dr6simkpe2nhjbkt6rl8ui1g28k6ls9v.apps.googleusercontent.com")
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions);

        googleBtn.setOnClickListener(view1 -> {
            Intent intent = googleSignInClient.getSignInIntent();
            startActivityForResult(intent, 100);
        });
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        /*
        if (firebaseUser != null) {
            navController.navigate(R.id.acsi);
        }
        /* ***********************************************************
         *********************************************************** */
    }
    private void checkUserExist(String userEmail){
        firebaseAuth.fetchSignInMethodsForEmail(userEmail)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if (task.getResult().getSignInMethods().size() == 0) {
                            email.requestFocus();
                            email.setError("Email already exist, Try to login");
                            }
                        else {
                            SignUpUser(email.getText().toString(), password.getText().toString(), username.getText().toString());
                        }
                    }
                });
    }
    private void SignUpUser(String email, String password , String username){
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(getContext() , "Sign up successfully" , Toast.LENGTH_SHORT).show();
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(username)
                                .build();
                        user.updateProfile(profileUpdates)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                            String displayName = user != null ? user.getDisplayName() : null;
                                            navController.navigate(R.id.action_signupFragment_to_loginFragment);
                                        }
                                    }
                                });
                    }
                });
    }
    private boolean isValidEmail(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            if (signInAccountTask.isSuccessful()) {
                try {
                    GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                    if (googleSignInAccount != null) {
                        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    navController.navigate(R.id.action_signupFragment_to_homeFragment);
                                } else {
                                    Toast.makeText(getContext(),"Authentication Failed: " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
            else{
                Log.i("TEST", "onActivityResult: false;(((");
            }
        }
    }
}