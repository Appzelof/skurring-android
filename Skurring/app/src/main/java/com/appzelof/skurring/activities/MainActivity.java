package com.appzelof.skurring.activities;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.appzelof.skurring.enums.DataToAdapterFrom;
import com.appzelof.skurring.Interfaces.UpdateMainFragmentUI;
import com.appzelof.skurring.R;
import com.appzelof.skurring.SQLite.DatabaseManager;
import com.appzelof.skurring.fragments.MainFragment;
import com.appzelof.skurring.fragments.PlayerFragment;
import com.appzelof.skurring.fragments.StationsFragment;

import com.appzelof.skurring.sharePrefsManager.SharePrefsManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements UpdateMainFragmentUI {

    private FragmentManager fragmentManager;

    private String TAG = "MainActivity";
    private SharePrefsManager sharePrefsManager;
    MainFragment mainFragment;
    public static Boolean startedOnce = false;

    public static DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharePrefsManager = new SharePrefsManager(this);


        initializeComponents();
        FirebaseDatabase theDatabase = FirebaseDatabase.getInstance();
        final String radioStationReference = "radiostasjoner";
        databaseReference = theDatabase.getReference(radioStationReference);
        loadFragment(mainFragment, R.id.main_container);


    }

    private void initializeComponents(){
        fragmentManager = getSupportFragmentManager();
        mainFragment = new MainFragment();
        mainFragment.stationsFragment = new StationsFragment();
        mainFragment.stationsFragment.initializeRadioStationAdapter(DataToAdapterFrom.INAPP, null);
        mainFragment.stationsFragment.getRadioStationAdapter().updateMainFragmentUI = this;
        mainFragment.playerFragment = new PlayerFragment();
        DatabaseManager.INSTANCE = new DatabaseManager(this.getBaseContext());

        com.appzelof.skurring.SQLiteFirebase.DatabaseManager.INSTANCE = new com.appzelof.skurring.SQLiteFirebase.DatabaseManager(this.getBaseContext());

    }

    public void loadFragment(Fragment fragment, int id){
        if (fragment != null){
            fragmentManager.beginTransaction().add(id, fragment)
                    .commit();
        }
    }

    public void replaceFragment(Fragment fragment, int i){
        fragmentManager.beginTransaction().setTransition(i)
                .replace(R.id.main_container, fragment).addToBackStack("")
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (mainFragment.stationsFragment.isVisible()) {
            mainFragment.stationsFragment.onDestroyView();
            System.out.println("SHOULD DESTROY FRAGMENT");
        } else {
            mainFragment.playerFragment.onDestroyView();
        }
        super.onBackPressed();
    }

    @Override
    public void updateUI() {
        super.onBackPressed();
        this.replaceFragment(mainFragment, FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharePrefsManager.saveInt("icon", 0);
        sharePrefsManager.saveString("temp", null);
    }
}
