package com.example.myselfchatapp;

import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

public class Chat_SendMyViewHolder extends Chat_MyHolder {
    TextView tvSDate,tvSTime,tvSMessage;
    CardView cardViewSLayout;

    public Chat_SendMyViewHolder(View sendItemView) {
        super(sendItemView);
        tvSMessage = sendItemView.findViewById(R.id.content);
        tvSDate = sendItemView.findViewById(R.id.date);
        tvSTime = sendItemView.findViewById(R.id.time);
        cardViewSLayout = sendItemView.findViewById(R.id.sendCardView);
    }
}