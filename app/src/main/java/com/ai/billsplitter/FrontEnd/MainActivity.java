package com.ai.billsplitter.FrontEnd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ai.billsplitter.FrontEnd.Group.GroupFragment;
import com.ai.billsplitter.FrontEnd.History.HistoryFragment;
import com.ai.billsplitter.FrontEnd.Home.HomeFragment;
import com.ai.billsplitter.FrontEnd.Settings.SettingsFragment;
import com.ai.billsplitter.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    // dark mode
    private static boolean darkMode;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    // fragment
    private Fragment selectedFragment = null;

    // bottom navigation bar
    private BottomNavigationView bottomNavigationView;
    private LinearLayout homeIndicator, groupIndicator, historyIndicator, settingsIndicator; // indicators
    private int startingPosition = 1, newPosition = 0; // for animation
    private String fragment_tag = "homeFragment"; // initial fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        darkModeStatus(); // check dark mode status

        // initialize views
        // bottom navigation view
        bottomNavigationView = findViewById(R.id.bottomNavigationView_id);
        // indicator layouts
        homeIndicator = findViewById(R.id.homeIndicator_id);
        groupIndicator = findViewById(R.id.groupIndicator_id);
        historyIndicator = findViewById(R.id.historyIndicator_id);
        settingsIndicator = findViewById(R.id.settingsIndicator_id);

        setIndicators("home"); // initially visible home indicator only

        // for navigation animation
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment(), 1, fragment_tag);
        }

        // bottom navigation
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_id,new HomeFragment()).commit();  //set default fragment (home fragment)

    }

    private void setIndicators(String fragment){

        if(fragment.toLowerCase().equals("home")){

            homeIndicator.setVisibility(View.VISIBLE); // show indicator
            groupIndicator.setVisibility(View.GONE); // hide indicator
            historyIndicator.setVisibility(View.GONE); // hide indicator
            settingsIndicator.setVisibility(View.GONE); // hide indicator

        }else if(fragment.toLowerCase().equals("group")){

            groupIndicator.setVisibility(View.VISIBLE); // show indicator
            homeIndicator.setVisibility(View.GONE); // hide indicator
            historyIndicator.setVisibility(View.GONE); // hide indicator
            settingsIndicator.setVisibility(View.GONE); // hide indicator

        }else if(fragment.toLowerCase().equals("history")){

            historyIndicator.setVisibility(View.VISIBLE); // show indicator
            homeIndicator.setVisibility(View.GONE); // hide indicator
            groupIndicator.setVisibility(View.GONE); // hide indicator
            settingsIndicator.setVisibility(View.GONE); // hide indicator

        }else if(fragment.toLowerCase().equals("settings")){

            settingsIndicator.setVisibility(View.VISIBLE); // show indicator
            homeIndicator.setVisibility(View.GONE); // hide indicator
            groupIndicator.setVisibility(View.GONE); // hide indicator
            historyIndicator.setVisibility(View.GONE); // hide indicator

        }

    }

    // navigation animation
    private boolean loadFragment(Fragment fragment, int newPosition, String fragment_tag) {
        if(fragment != null) {
            if(newPosition == 0) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout_id, fragment,fragment_tag).commit();

            }
            if(startingPosition > newPosition) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right )
                        .replace(R.id.frameLayout_id, fragment,fragment_tag).commit();

            }
            if(startingPosition < newPosition) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                        .replace(R.id.frameLayout_id, fragment,fragment_tag).commit();

            }
            startingPosition = newPosition;
            return true;
        }

        return false;
    }

    // navigation on select action
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @SuppressLint("ResourceAsColor")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){

                case R.id.home_menu:
                    selectedFragment = new HomeFragment(); // select home fragment
                    newPosition = 1;
                    fragment_tag = "homeFragment";
                    setIndicators("home"); // set indicators
                    break;

                case R.id.group_menu:
                    selectedFragment = new GroupFragment(); // select group fragment
                    newPosition = 2;
                    fragment_tag = "groupFragment";
                    setIndicators("group"); // set indicators
                    break;

                case R.id.history_menu:

                    selectedFragment = new HistoryFragment(); // select history fragment
                    newPosition = 3;
                    fragment_tag = "historyFragment";
                    setIndicators("history"); // set indicators
                    break;

                case R.id.settings_menu:

                    selectedFragment = new SettingsFragment(); // select settings fragment
                    newPosition = 4;
                    fragment_tag = "settingsFragment";
                    setIndicators("settings"); // set indicators
                    break;

            }

            return loadFragment(selectedFragment, newPosition, fragment_tag); // for animation

        }
    };

    @SuppressLint("ResourceType")
    private void darkModeStatus() {

        preferences = getSharedPreferences(String.valueOf(R.string.user_settings), Context.MODE_PRIVATE); // initialize the shared preference

        if(preferences.contains(String.valueOf(R.string.dark_Mode))){ // read from shared preference

             darkMode = preferences.getBoolean(String.valueOf(R.string.dark_Mode), true); // read data from shared preference
            // set dark mode status
            if(darkMode == true){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); // set dark mode
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); // set light mode
            }

        }else{ // write to shared preference

            editor = preferences.edit();
            editor.putBoolean(String.valueOf(R.string.dark_Mode), false); // set default value
            editor.commit(); // write to shared preference

        }

    }

    @Override
    public void onBackPressed() {

        // get current fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.frameLayout_id);

        // check current fragment
        if(!currentFragment.getTag().equals("homeFragment")){
            // select home fragment
            selectedFragment = new HomeFragment();
            newPosition = 1;
            fragment_tag = "homeFragment";
            setIndicators("home"); // set indicators
            loadFragment(selectedFragment, newPosition, fragment_tag); // for animation
        }else{
            super.onBackPressed();
        }

    }
}