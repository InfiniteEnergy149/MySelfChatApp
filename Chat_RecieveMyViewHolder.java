package com.example.myselfchatapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

public class Chat_RecieveMyViewHolder extends Chat_MyHolder{
    ImageView tvRLogo;
    TextView tvRName,tvRDate,tvRTime,tvRMessage;
    CardView cardViewRLayout;

    public Chat_RecieveMyViewHolder(View recieveItemView) {
        super(recieveItemView);

        tvRName = recieveItemView.findViewById(R.id.name);
        tvRLogo = recieveItemView.findViewById(R.id.logo);
        tvRMessage = recieveItemView.findViewById(R.id.content);
        tvRDate = recieveItemView.findViewById(R.id.date);
        tvRTime = recieveItemView.findViewById(R.id.time);
        cardViewRLayout = recieveItemView.findViewById(R.id.sendCardView);

    }
}
