package com.ai.billsplitter.FrontEnd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.ai.billsplitter.R;

public class SplashScreen extends AppCompatActivity {

    // dark mode
    private static boolean darkMode;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        darkModeStatus();

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();

    }

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

}