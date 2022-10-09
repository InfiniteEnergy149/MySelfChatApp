package com.example.myselfchatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.LayoutInflaterCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UCD_RecyclerViewAdapter extends RecyclerView.Adapter<UCD_RecyclerViewAdapter.MyViewHolder> {
Context context;
ArrayList<UserChatDisplay> userChatDisplays;

    public UCD_RecyclerViewAdapter(Context context, ArrayList<UserChatDisplay> userChatDisplays){
        this.context = context;
        this.userChatDisplays = userChatDisplays;
    }

    @NonNull
    @Override
    public UCD_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      // Create the layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_row,parent,false);
        return new UCD_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UCD_RecyclerViewAdapter.MyViewHolder holder, int position) {
//Assign values to the recyclerView
       //holder.imageView.setText(userChatDisplays.get(position).getLogo());
       holder.tvName.setText(userChatDisplays.get(position).getName());
       holder.tvColour.setText(userChatDisplays.get(position).getColour());

    }

    @Override
    public int getItemCount() {
        //Number of items displayed
        return userChatDisplays.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        //ImageView imageView;
         TextView tvName,tvColour;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //imageView = itemView.findViewById(R.id.logoView)
            tvName = itemView.findViewById(R.id.textName);
           tvColour = itemView.findViewById(R.id.textColour);
        }
    }
}
