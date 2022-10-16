package com.example.myselfchatapp;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public class Chat_MyHolder extends RecyclerView.ViewHolder {

        // must have the ViewHolder default constructor
        public Chat_MyHolder(View itemView) {
            super(itemView);
        }

        public static Chat_MyHolder inflateViewByType(boolean type,
                                                      LayoutInflater layoutInflater, ViewGroup parent) {
            View view;
            if (type) {
                view = layoutInflater.inflate(R.layout.chat_send_recyclerview_row, parent, false);
                return new Chat_SendMyViewHolder(view);
            }else{
                view = layoutInflater.inflate(R.layout.chat_recieve_recyclerview_row, parent, false);
                return new Chat_RecieveMyViewHolder(view);
            }
        }
    }

