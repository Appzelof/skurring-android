package com.appzelof.skurring.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.appzelof.skurring.R;
import com.squareup.picasso.Picasso;

public class RadioViewHolder extends RecyclerView.ViewHolder{

    private ImageView radioImage;
    private TextView radioText;

    public RadioViewHolder(View itemView) {
        super(itemView);
        radioImage = itemView.findViewById(R.id.radio_image);
        radioText = itemView.findViewById(R.id.radio_text);
    }

    public void updateUI(int image, String channel) {
        Picasso.get().load(image).into(radioImage);
        radioText.setText(channel);
    }

}
