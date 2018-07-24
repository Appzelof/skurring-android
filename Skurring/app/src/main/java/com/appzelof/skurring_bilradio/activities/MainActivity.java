package com.appzelof.skurring_bilradio.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.TranslateAnimation;

import com.appzelof.skurring_bilradio.R;
import com.appzelof.skurring_bilradio.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

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

    }

    public void loadFragment(Fragment fragment, int id){
        if (fragment != null){
            fragmentManager.beginTransaction().add(id, fragment)
                    .commit();
        }
    }

    public void replaceFragment(Fragment fragment, int i){
        fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.main_container, fragment).addToBackStack("")
                .commit();
    }


}
