package com.appzelof.skurring.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appzelof.skurring.Interface.RecyclerOnViewClickListener;
import com.appzelof.skurring.R;
import com.appzelof.skurring.model.RadioObjectListCreator;
import com.squareup.picasso.Picasso;

/**
 * Created by daniel on 30/11/2017.
 */

public class RadioStationAdapter extends RecyclerView.Adapter<RadioStationAdapter.RadioStationViewHolder>  {

    private RadioObjectListCreator radioObjectListCreator;
    private RecyclerOnViewClickListener recyclerOnViewClickListener;
    private int img;
    private String radioURL;
    private Context context;


    public class RadioStationViewHolder extends RecyclerView.ViewHolder  {
        private TextView radioName;
        private ImageView radioImage;

        public RadioStationViewHolder(View v){
            super(v);
            radioName = (TextView) v.findViewById(R.id.my_text);
            radioImage = (ImageView) v.findViewById(R.id.my_image);

        }

    }

    public RadioStationAdapter(){
        radioObjectListCreator = new RadioObjectListCreator();

    }

    @Override
    public RadioStationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_radio_cards, parent, false);
        RadioStationViewHolder pvh = new RadioStationViewHolder(v);
        v.setBackgroundColor(Color.BLACK);

        return pvh;

    }


    @Override
    public void onBindViewHolder(final RadioStationViewHolder holder, final int position)  {
        holder.radioImage.setAdjustViewBounds(true);
        holder.radioName.setText(radioObjectListCreator.getList().get(position).getName());
        img = radioObjectListCreator.getList().get(position).getRadioImage();
        radioURL = radioObjectListCreator.getList().get(position).getUrl();
        System.out.println(radioURL);
        Context context = holder.radioImage.getContext();
        Picasso.with(context).load(img).fit().centerInside().into(holder.radioImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            recyclerOnViewClickListener.onClick(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {

    return  radioObjectListCreator.getList().size();

    }

    public void setOnClick(RecyclerOnViewClickListener recyclerOnViewClickListener){
        this.recyclerOnViewClickListener = recyclerOnViewClickListener;
    }

}
