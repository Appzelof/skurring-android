package com.appzelof.skurring.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.appzelof.skurring.R;
import com.appzelof.skurring.activities.MainActivity;


public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageButton imageButton;
    private ImageButton imageButton2;
    private ImageButton imageButton3;
    private ImageButton imageButton4;
    private ImageButton imageButton5;
    private ImageButton imageButton6;
    private ImageButton imageButton7;

    private SparseArray<ImageButton> imageButtonSparseArray;
    private StationsFragment stationsFragment;




    public MainFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        initializeComponents(v);
        imageButtonHandler();


        return v;
    }

    private void initializeComponents(View v){

        imageButton = v.findViewById(R.id.imageButton);
        imageButton2 = v.findViewById(R.id.imageButton2);
        imageButton3 = v.findViewById(R.id.imageButton3);
        imageButton4 = v.findViewById(R.id.imageButton4);
        imageButton5 = v.findViewById(R.id.imageButton5);
        imageButton6 = v.findViewById(R.id.imageButton6);
        imageButton7 = v.findViewById(R.id.imageButton7);

        imageButtonSparseArray = new SparseArray<>();

        imageButtonSparseArray.append(1, imageButton);
        imageButtonSparseArray.append(2, imageButton2);
        imageButtonSparseArray.append(3, imageButton3);
        imageButtonSparseArray.append(4, imageButton4);
        imageButtonSparseArray.append(5, imageButton5);
        imageButtonSparseArray.append(6, imageButton6);
        imageButtonSparseArray.append(7, imageButton7);

    }

    private void imageButtonHandler(){
        for (int i = 1; i < 8; i++){
            System.out.println(i);
            imageButtonSparseArray.get(i).setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ((MainActivity) getContext()).replaceFragment(new StationsFragment(), R.id.main_container);
                    return false;
                }
            });

            imageButtonSparseArray.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) getContext()).replaceFragment(new PlayerFragment(), R.id.main_container);
                }
            });
        }
    }



}
