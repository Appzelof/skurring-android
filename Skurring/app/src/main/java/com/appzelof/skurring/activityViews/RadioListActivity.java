package com.appzelof.skurring.activityViews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.appzelof.skurring.Interface.RecyclerOnViewClickListener;
import com.appzelof.skurring.R;
import com.appzelof.skurring.adapters.RadioStationAdapter;
import com.appzelof.skurring.radioObjects.RadioObjectListCreator;

public class RadioListActivity extends AppCompatActivity implements RecyclerOnViewClickListener {


    private RadioStationAdapter radioStationAdapter;
    private RecyclerView rv;
    private RadioObjectListCreator radioObjectListCreator;
    private Bundle extra;
    private int button;
    private int button2;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        radioStationAdapter = new RadioStationAdapter();
        setContentView(R.layout.activity_radiolist);

        extra = getIntent().getExtras();

        rv = (RecyclerView) findViewById(R.id.my_recycler_view);
        rv.setOnClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rv.setLayoutManager(linearLayoutManager);
        rv.setHasFixedSize(true);
        initializeData();
        initializeAdapter();
        radioStationAdapter.setOnClick(this);


    }


    private void initializeData(){

        radioObjectListCreator = new RadioObjectListCreator();



    }


    private void initializeAdapter() { rv.setAdapter(radioStationAdapter);


    }


    @Override
    public void onClick(View view, int position) {

        System.out.println(position);


        Intent intent = new Intent(this, MainActivity.class);

        int radioImage = radioObjectListCreator.getList().get(position).getRadioImage();
        String radioName = radioObjectListCreator.getList().get(position).getName();
        String radioURL = radioObjectListCreator.getList().get(position).getUrl();

        intent.putExtra("radioImage", radioImage);
        intent.putExtra("radioName", radioName);
        intent.putExtra("radioURL", radioURL);


        startActivity(intent);

    }



    @Override
    public void onClick(View v) {

    }

}
