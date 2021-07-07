package com.ai.billsplitter.FrontEnd.Settings;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.ai.billsplitter.R;

public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /* variables */
    // dark mode
    private static boolean darkMode;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor; // shared preferences
    private ConstraintLayout darkModeLayout;
    private Switch darkModeSwitch;
    private TextView darkModeTextView; // layouts

    public SettingsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // initialize views
        // dark mode
        darkModeLayout = view.findViewById(R.id.darkModeLayout_id);
        darkModeTextView = view.findViewById(R.id.darkModeTextView_id);
        darkModeSwitch = view.findViewById(R.id.darkModeSwitch_id);

        initialization();

        /* on click listeners*/
        // dark mode
        darkModeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(darkModeSwitch.isChecked()){ // already dark mode
                    setLightMode();
                }else{ // already light mode
                    setDarkMode();
                }

            }
        });

        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    setDarkMode();
                }else{
                    setLightMode();
                }
            }
        });

        return view;
    }

    private void initialization() {

        /* check dark mode */
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){ // dark mode
            setDarkMode();
        }else{ // light mode
            setLightMode();
        }

    }

    private void setDarkMode() {

        darkModeSwitch.setChecked(true);
        darkModeTextView.setText("On");

        preferences = getActivity().getSharedPreferences(String.valueOf(R.string.user_settings), Context.MODE_PRIVATE); // initialize the shared preference
        editor = preferences.edit();
        editor.putBoolean(String.valueOf(R.string.dark_Mode), true); // write dark mode
        editor.commit(); // write to shared preference

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); // set dark mode

    }

    private void setLightMode() {

        darkModeSwitch.setChecked(false);
        darkModeTextView.setText("Off");

        preferences = getActivity().getSharedPreferences(String.valueOf(R.string.user_settings), Context.MODE_PRIVATE); // initialize the shared preference
        editor = preferences.edit();
        editor.putBoolean(String.valueOf(R.string.dark_Mode), false); // write light mode
        editor.commit(); // write to shared preference

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); // set light mode

    }




}