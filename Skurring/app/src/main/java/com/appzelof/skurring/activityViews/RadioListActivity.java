package com.appzelof.skurring.activityViews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;

import com.appzelof.skurring.Interface.RecyclerOnViewClickListener;
import com.appzelof.skurring.R;
import com.appzelof.skurring.adapters.RadioStationAdapter;
import com.appzelof.skurring.model.RadioObject;
import com.appzelof.skurring.model.RadioObjectListCreator;
import java.util.ArrayList;

public class RadioListActivity extends AppCompatActivity implements RecyclerOnViewClickListener {


    private RadioStationAdapter radioStationAdapter;
    private RecyclerView rv;
    private RadioObjectListCreator radioObjectListCreator;
    private Bundle extra;
    private ArrayList<RadioObject> radioObjects;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        radioStationAdapter = new RadioStationAdapter();
        setContentView(R.layout.activity_radiolist);
        extra = getIntent().getExtras();

        rv = (RecyclerView) findViewById(R.id.radiolist_recyclerview);
        rv.setOnClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rv.setLayoutManager(linearLayoutManager);
        rv.setHasFixedSize(true);
        initializeData();
        initializeAdapter();
        radioStationAdapter.setOnClick(this);
        radioObjects = new ArrayList<>();

    }


    private void initializeData(){

        radioObjectListCreator = new RadioObjectListCreator();

    }


    private void initializeAdapter() { rv.setAdapter(radioStationAdapter);

    }


    @Override
    public void onClick(View view, int position) {


        System.out.println(position);
        this.radioObjects = radioObjects;

        Intent intent = new Intent(this, MainActivity.class);

        extra = getIntent().getExtras();

        int radioImage = radioObjectListCreator.getList().get(position).getRadioImage();
        String radioName = radioObjectListCreator.getList().get(position).getName();
        String radioURL = radioObjectListCreator.getList().get(position).getUrl();

        intent.putExtra("radioImage", radioImage);
        intent.putExtra("radioName", radioName);
        intent.putExtra("radioURL", radioURL);


        RadioObject radioObject = new RadioObject(radioName, radioURL, radioImage);



        if (extra.getInt("button") == 1) {
            intent.putExtra("btn1", 1);
        }

        if (extra.getInt("button2") == 2) {
            intent.putExtra("btn2", 2);
        }

        if (extra.getInt("button3") == 3) {
            intent.putExtra("btn3", 3);
        }

        if (extra.getInt("button4") == 4) {
            intent.putExtra("btn4", 4);
        }

        if (extra.getInt("button5") == 5) {
            intent.putExtra("btn5", 5);
        }

        if (extra.getInt("button6") == 6) {
            intent.putExtra("btn6", 6);
        }

        finish();
        startActivity(intent);


    }

    @Override
    public void onClick(View v) {

    }

}
