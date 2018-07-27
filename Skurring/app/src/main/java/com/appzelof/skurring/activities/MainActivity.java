package com.appzelof.skurring.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.appzelof.skurring.Interfaces.UpdateMainFragmentUI;
import com.appzelof.skurring.MyMediaPlayer;
import com.appzelof.skurring.R;
import com.appzelof.skurring.SQLite.DatabaseManager;
import com.appzelof.skurring.fragments.MainFragment;
import com.appzelof.skurring.fragments.StationsFragment;
import com.appzelof.skurring.model.RadioObject;

public class MainActivity extends AppCompatActivity implements UpdateMainFragmentUI {

    private FragmentManager fragmentManager;
    MainFragment mainFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();
        loadFragment(mainFragment, R.id.main_container);

    }

    private void initializeComponents(){
        fragmentManager = getSupportFragmentManager();
        mainFragment = new MainFragment();
        mainFragment.stationsFragment = new StationsFragment();
        mainFragment.stationsFragment.initializeRadioStationAdapter();
        mainFragment.stationsFragment.getRadioStationAdapter().updateMainFragmentUI = this;
        DatabaseManager.INSTANCE = new DatabaseManager(this.getBaseContext());
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
        super.onBackPressed();

    }

    @Override
    public void updateUI() {
        this.replaceFragment(mainFragment, FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
    }
}
