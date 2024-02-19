package com.example.foodplanner.meal.view;

import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.foodplanner.Model.Meal;
import com.example.foodplanner.Model.MealsRepositoryImpl;
import com.example.foodplanner.Network.MealsRemoteDataSourceImpl;
import com.example.foodplanner.R;
import com.example.foodplanner.db.MealsLocalDataSourceImpl;
import com.example.foodplanner.meal.presenter.MealPresenter;
import com.example.foodplanner.meal.presenter.MealPresenterImpl;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealFragment extends Fragment implements MealsView , OnMealClickListener{
    private Meal receivedObject;

    boolean inFavourites;
    Single<Boolean> checkMealObservable;
    private WebView webView;
    String embedHtml;
    OnMealClickListener onMealClickListener;
    TextView mealName , mealCategory , mealArea, mealIng1 , mealMes1, mealIng2 , mealMes2, mealIng3 , mealMes3, mealIng4 , mealMes4, mealIng5 , mealMes5, mealIng6 , mealMes6, mealIng7 , mealMes7, mealIng8 , mealMes8, mealIng9 , mealMes9, mealIng10 , mealMes10, mealIng11, mealMes11, mealIng12 , mealMes12, mealIng13 , mealMes13, mealIng14 , mealMes14, mealIng15 , mealMes15, mealIng16 , mealMes16, mealIng17 , mealMes17, mealIng18 , mealMes18, mealIng19 , mealMes19, mealIng20 , mealMes20, mealInstructionTV , mealInstruction;
    NavController navController;

    ImageView mealFavImage ,mealVideoImage , mealImage, ing1Img,ing2Img,ing3Img,ing4Img,ing5Img,ing6Img,ing7Img,ing8Img,ing9Img,ing10Img,
    ing11Img,ing12Img,ing13Img,ing14Img,ing15Img,ing16Img,ing17Img,ing18Img,ing19Img,ing20Img;
    CardView mealInstructionsCard , videoCard , description;
    LinearLayout layout1 , layout2, layout3, layout4,layout5,layout6,layout7,layout8,layout9,layout10;
    MealPresenter mealPresenter;

    ImageView[] ingredientsImages = null;
    LinearLayout[] layouts = null;
    TextView[] measuresTV = null;

    TextView[] ingredintsTV = null;

    String[] measuresArray = null;

    String[] ingredientsArray = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            receivedObject = (Meal) getArguments().getSerializable("meal");
            Log.i("TAG", "onCreate: "+receivedObject.getIdMeal());
            mealPresenter = new MealPresenterImpl(this, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(),
                    MealsLocalDataSourceImpl.getInstance(getContext())));
            checkMealObservable = mealPresenter.checkMealExist(receivedObject.getIdMeal());
            checkMeal(checkMealObservable);
            //if (receivedObject.getStrYoutube() == null) {
              //  mealPresenter.getMealDetails(receivedObject.getStrMeal());
            //}

            onMealClickListener = this;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_meal, container, false);

        return view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mealName = view.findViewById(R.id.mealName);
        mealCategory = view.findViewById(R.id.mealCategory);
        mealArea = view.findViewById(R.id.mealArea);
        mealIng1 = view.findViewById(R.id.ing1);
        mealIng2 = view.findViewById(R.id.ing2);
        mealIng3 = view.findViewById(R.id.ing3);
        mealIng4 = view.findViewById(R.id.ing4);
        mealIng5 = view.findViewById(R.id.ing5);
        mealIng6 = view.findViewById(R.id.ing6);
        mealIng7 = view.findViewById(R.id.ing7);
        mealIng8 = view.findViewById(R.id.ing8);
        mealIng9 = view.findViewById(R.id.ing9);
        mealIng10 = view.findViewById(R.id.ing10);
        mealIng11 = view.findViewById(R.id.ing11);
        mealIng12 = view.findViewById(R.id.ing12);
        mealIng13 = view.findViewById(R.id.ing13);
        mealIng14 = view.findViewById(R.id.ing14);
        mealIng15 = view.findViewById(R.id.ing15);
        mealIng16 = view.findViewById(R.id.ing16);
        mealIng17 = view.findViewById(R.id.ing17);
        mealIng18 = view.findViewById(R.id.ing18);
        mealIng19 = view.findViewById(R.id.ing19);
        mealIng20 = view.findViewById(R.id.ing20);

        mealMes1 = view.findViewById(R.id.mes1);
        mealMes2 = view.findViewById(R.id.mes2);
        mealMes3 = view.findViewById(R.id.mes3);
        mealMes4 = view.findViewById(R.id.mes4);
        mealMes5 = view.findViewById(R.id.mes5);
        mealMes6 = view.findViewById(R.id.mes6);
        mealMes7 = view.findViewById(R.id.mes7);
        mealMes8 = view.findViewById(R.id.mes8);
        mealMes9 = view.findViewById(R.id.mes9);
        mealMes10 = view.findViewById(R.id.mes10);
        mealMes11 = view.findViewById(R.id.mes11);
        mealMes12 = view.findViewById(R.id.mes12);
        mealMes13 = view.findViewById(R.id.mes13);
        mealMes14 = view.findViewById(R.id.mes14);
        mealMes15 = view.findViewById(R.id.mes15);
        mealMes16 = view.findViewById(R.id.mes16);
        mealMes17 = view.findViewById(R.id.mes17);
        mealMes18 = view.findViewById(R.id.mes18);
        mealMes19 = view.findViewById(R.id.mes19);
        mealMes20 = view.findViewById(R.id.mes20);
        mealInstructionTV = view.findViewById(R.id.mealInstructionsTV);
        mealInstruction = view.findViewById(R.id.mealInstructions);
        //mealVideoImage = view.findViewById(R.id.mealVideoImage);
        mealImage = view.findViewById(R.id.mealImage);

        layout1 = view.findViewById(R.id.layout1);
        layout2 = view.findViewById(R.id.layout2);
        layout3 = view.findViewById(R.id.layout3);
        layout4 = view.findViewById(R.id.layout4);
        layout5 = view.findViewById(R.id.layout5);
        layout6 = view.findViewById(R.id.layout6);
        layout7 = view.findViewById(R.id.layout7);
        layout8 = view.findViewById(R.id.layout8);
        layout9 = view.findViewById(R.id.layout9);
        layout10 = view.findViewById(R.id.layout10);

        ing1Img = view.findViewById(R.id.ing1Img);
        ing2Img = view.findViewById(R.id.ing2Img);
        ing3Img = view.findViewById(R.id.ing3Img);
        ing4Img = view.findViewById(R.id.ing4Img);
        ing5Img = view.findViewById(R.id.ing5Img);
        ing6Img = view.findViewById(R.id.ing6Img);
        ing7Img = view.findViewById(R.id.ing7Img);
        ing8Img = view.findViewById(R.id.ing8Img);
        ing9Img = view.findViewById(R.id.ing9Img);
        ing10Img = view.findViewById(R.id.ing10Img);
        ing11Img = view.findViewById(R.id.ing11Img);
        ing12Img = view.findViewById(R.id.ing12Img);
        ing13Img = view.findViewById(R.id.ing13Img);
        ing14Img = view.findViewById(R.id.ing14Img);
        ing15Img = view.findViewById(R.id.ing15Img);
        ing16Img = view.findViewById(R.id.ing16Img);
        ing17Img = view.findViewById(R.id.ing17Img);
        ing18Img = view.findViewById(R.id.ing18Img);
        ing19Img = view.findViewById(R.id.ing19Img);
        ing20Img = view.findViewById(R.id.ing20Img);
        webView = view.findViewById(R.id.webViewMeal);
        videoCard = view.findViewById(R.id.video);
        mealInstructionsCard = view.findViewById(R.id.instructionsCard);
        description = view.findViewById(R.id.description);
        mealFavImage = view.findViewById(R.id.favImage);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user != null ? user.getEmail() : null;

        navController = Navigation.findNavController((Activity) view.getContext() , R.id.fragmentNavHost);

        ConnectivityManager connectivityManager = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            mealPresenter.getMealDetails(receivedObject.getStrMeal());
        } else {
            showMealDetails();
            mealFavImage.setImageResource(R.drawable.favourite);
        }
        /*

        if(receivedObject.getStrCategory() != null){
            checkMeal(checkMealObservable);
            if(inFavourites) {
                Log.i("sora", "true ");
                mealFavImage.setImageResource(R.drawable.favourite);
            }
        }
        else{
            checkMeal(checkMealObservable);
            if(inFavourites){
                Log.i("sora", "true ");
                mealFavImage.setImageResource(R.drawable.favourite);
            }
        }

        */
        mealFavImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
                boolean guest = preferences.getBoolean("guest", false);
                if (guest) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
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
                } else {
                    if (!inFavourites) {
                        inFavourites = true;
                        mealFavImage.setImageResource(R.drawable.favourite);
                        receivedObject.setEmail(email);
                        onMealClickListener.onFavMealClick(receivedObject);
                        Log.i("TEST", "onClick: doneeeeeeeeeee");
                        Toast.makeText(MealFragment.this.getContext(), "Meal Added To Favourites", Toast.LENGTH_SHORT).show();
                    } else {
                        inFavourites = false;
                        mealFavImage.setImageResource(R.drawable.not_favourite);
                        onMealClickListener.onFavMealUnClick(receivedObject);
                        Toast.makeText(view.getContext(), "Meal Removed From Favourites", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    public void showVideo(){
        String videoId = null;
        if(receivedObject.getStrYoutube() != null && !receivedObject.getStrYoutube().equals("")){
            videoCard.setVisibility(VISIBLE);
            videoId = receivedObject.getStrYoutube().split("v=")[1];
        }
        else{
            videoCard.setVisibility(View.GONE);
        }
        embedHtml = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <body>\n" +
                "    <!-- 1. The <iframe> (and video player) will replace this <div> tag. -->\n" +
                "    <div id=\"player\"></div>\n" +
                "\n" +
                "    <script>\n" +
                "      // 2. This code loads the IFrame Player API code asynchronously.\n" +
                "      var tag = document.createElement('script');\n" +
                "\n" +
                "      tag.src = \"https://www.youtube.com/iframe_api\";\n" +
                "      var firstScriptTag = document.getElementsByTagName('script')[0];\n" +
                "      firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);\n" +
                "\n" +
                "      // 3. This function creates an <iframe> (and YouTube player)\n" +
                "      //    after the API code downloads.\n" +
                "      var player;\n" +
                "      function onYouTubeIframeAPIReady() {\n" +
                "        player = new YT.Player('player', {\n" +
                "          height: '288',\n" +
                "          width: '350',\n" +
                "          videoId: '" + videoId    +"'   ,\n" +
                "          playerVars: {\n" +
                "            'playsinline': 1\n" +
                "          },\n" +
                "          events: {\n" +
                "            'onReady': onPlayerReady,\n" +
                "            'onStateChange': onPlayerStateChange\n" +
                "          }\n" +
                "        });\n" +
                "      }\n" +
                "\n" +
                "      // 4. The API will call this function when the video player is ready.\n" +
                "      function onPlayerReady(event) {\n" +
                "        event.target.playVideo();\n" +
                "      }\n" +
                "\n" +
                "      // 5. The API calls this function when the player's state changes.\n" +
                "      //    The function indicates that when playing a video (state=1),\n" +
                "      //    the player should play for six seconds and then stop.\n" +
                "      var done = false;\n" +
                "      function onPlayerStateChange(event) {\n" +
                "        if (event.data == YT.PlayerState.PLAYING && !done) {\n" +
                "          setTimeout(stopVideo);\n" +
                "          done = true;\n" +
                "        }\n" +
                "      }\n" +
                "      function stopVideo() {\n" +
                "        player.stopVideo();\n" +
                "      }\n" +
                "    </script>\n" +
                "  </body>\n" +
                "</html>";


        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.loadData(embedHtml, "text/html", "utf-8");
    }
    public void showMeal(){
        mealName.setText(receivedObject.getStrMeal());
        mealCategory.setText(receivedObject.getStrCategory());
        mealArea.setText(receivedObject.getStrArea());
        Glide.with(this).load(receivedObject.getStrMealThumb()).into(mealImage);
    }
    public void showIngredient(){
        ingredientsArray = new String[]{receivedObject.getStrIngredient1(), receivedObject.getStrIngredient2(), receivedObject.getStrIngredient3(),
                receivedObject.getStrIngredient4(), receivedObject.getStrIngredient5(), receivedObject.getStrIngredient6(),
                receivedObject.getStrIngredient7(), receivedObject.getStrIngredient8(), receivedObject.getStrIngredient9(),
                receivedObject.getStrIngredient10(), receivedObject.getStrIngredient11(), receivedObject.getStrIngredient12(),
                receivedObject.getStrIngredient13(), receivedObject.getStrIngredient14(), receivedObject.getStrIngredient15(),
                receivedObject.getStrIngredient16(), receivedObject.getStrIngredient17(), receivedObject.getStrIngredient18(),
                receivedObject.getStrIngredient19(), receivedObject.getStrIngredient20()};

        measuresArray = new String[]{receivedObject.getStrMeasure1(), receivedObject.getStrMeasure2(), receivedObject.getStrMeasure3(),
                receivedObject.getStrMeasure4(), receivedObject.getStrMeasure5(), receivedObject.getStrMeasure6(),
                receivedObject.getStrMeasure7(), receivedObject.getStrMeasure8(), receivedObject.getStrMeasure9(),
                receivedObject.getStrMeasure10(), receivedObject.getStrMeasure11(), receivedObject.getStrMeasure12(),
                receivedObject.getStrMeasure13(), receivedObject.getStrMeasure14(), receivedObject.getStrMeasure15(),
                receivedObject.getStrMeasure16(), receivedObject.getStrMeasure17(), receivedObject.getStrMeasure18(),
                receivedObject.getStrMeasure19(), receivedObject.getStrMeasure20()};

        ingredintsTV = new TextView[]{mealIng1, mealIng2, mealIng3, mealIng4, mealIng5,
                mealIng6, mealIng7, mealIng8, mealIng9, mealIng10,
                mealIng11, mealIng12, mealIng13, mealIng14, mealIng15,
                mealIng16, mealIng17, mealIng18, mealIng19, mealIng20};

        measuresTV = new TextView[]{mealMes1, mealMes2, mealMes3, mealMes4, mealMes5,
                mealMes6, mealMes7, mealMes8, mealMes9, mealMes10,
                mealMes11, mealMes12, mealMes13, mealMes14, mealMes15,
                mealMes16, mealMes17, mealMes18, mealMes19, mealMes20};

        layouts = new LinearLayout[]{layout1, layout2, layout3, layout4, layout5, layout6, layout7, layout8, layout9, layout10};


        ingredientsImages = new ImageView[]{ing1Img, ing2Img, ing3Img, ing4Img, ing5Img, ing6Img, ing7Img, ing8Img, ing9Img, ing10Img
                , ing11Img, ing12Img, ing13Img, ing14Img, ing15Img, ing16Img, ing17Img, ing18Img, ing19Img, ing20Img};

        for (int i = 0; i< ingredientsArray.length ;i++) {
            if (ingredientsArray[i] != null && !ingredientsArray[i].equals("")) {
                if(i%2==0){
                    layouts[i/2].setVisibility(VISIBLE);
                }
                ingredintsTV[i].setVisibility(VISIBLE);
                measuresTV[i].setVisibility(VISIBLE);
                ingredientsImages[i].setVisibility(VISIBLE);
                // image
                ingredintsTV[i].setText(ingredientsArray[i]);
                measuresTV[i].setText(measuresArray[i]);
                Glide.with(this).load("https://www.themealdb.com/images/ingredients/"+ingredientsArray[i]+".png").into(ingredientsImages[i]);
                //image
            } else {
                break;
            }
        }
    }
    public void showInstructions(){
        mealInstruction.setText(receivedObject.getStrInstructions());
        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mealInstructionsCard.getVisibility() == VISIBLE){
                    mealInstructionsCard.setVisibility(view.GONE);
                }
                else{
                    mealInstructionsCard.setVisibility(VISIBLE);
                }
            }
        });
    }
    @SuppressLint("CheckResult")
    public void checkMeal(Single<Boolean> checkMealObservable){
        checkMealObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mealExists -> {
                            if (mealExists) {
                                inFavourites = true;
                            } else {
                                inFavourites = false;
                            }
                        }
                );
    }
    public void showMealDetails(){
        showVideo();
        showMeal();
        showInstructions();
        showIngredient();
        if(inFavourites){
            Log.i("sora", "trueueueueueu ");
            mealFavImage.setImageResource(R.drawable.favourite);
            Log.i("sora", "showMealDetails: ");
        }
        else{
            Log.i("sora", "showMealDetails: in else");
        }
    }

    @Override
    public void showData(List<Meal> meal) {
        receivedObject = meal.get(0);
        checkMeal(checkMealObservable);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("TAG", "showData: " + meal.get(0).getStrMeasure1());
        showMealDetails();
    }

    @Override
    public void showErrorMsg(String error) {
        Toast.makeText(this.getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addMeal(Meal meal) {
        mealPresenter.addToFav(meal);
    }

    @Override
    public void removeMeal(Meal meal) {
        mealPresenter.removeFromFav(meal);
    }

    @Override
    public void onFavMealClick(Meal meal) {
        addMeal(meal);
    }

    @Override
    public void onFavMealUnClick(Meal meal) {
        removeMeal(meal);
    }
}