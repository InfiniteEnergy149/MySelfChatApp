package com.example.myselfchatapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UCD_RecyclerViewAdapter extends RecyclerView.Adapter<UCD_RecyclerViewAdapter.MyViewHolder> {
private final RecyclerViewInterface recyclerViewInterface;

Context context;
ArrayList<UserChatDisplay> userChatDisplays;

    public UCD_RecyclerViewAdapter(Context context, ArrayList<UserChatDisplay> userChatDisplays,RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.userChatDisplays = userChatDisplays;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public UCD_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      // Create the layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.user_recyclerview_row,parent,false);
        return new UCD_RecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull UCD_RecyclerViewAdapter.MyViewHolder holder, int position) {
//Assign values to the recyclerView

        holder.tvName.setText("Name : " + userChatDisplays.get(position).getName());

        switch (userChatDisplays.get(position).getLogo()) {
            case "arrow":
                holder.imageView.setImageResource(R.drawable.arrow);
                break;
            case "circle":
                holder.imageView.setImageResource(R.drawable.circle);
                break;
            case "diamond":
                holder.imageView.setImageResource(R.drawable.diamond);
                break;
            case "heart":
                holder.imageView.setImageResource(R.drawable.heart);
                break;
            case "square":
                holder.imageView.setImageResource(R.drawable.square);
                break;
            case "star":
                holder.imageView.setImageResource(R.drawable.star);
                break;
            default:
                holder.imageView.setImageResource(R.drawable.question);
                break;
        }

        //holder.tvColour.setText(userChatDisplays.get(position).getColour());
        switch (userChatDisplays.get(position).getColour()) {
            case "red":
                holder.tvColour.setText("Colour: red");
                //holder.tvColour.setBackgroundResource(R.color.red);
                holder.cardViewLayout.setBackgroundResource(R.color.red);
                break;
            case "orange":
                holder.tvColour.setText("Colour: orange");
                holder.cardViewLayout.setBackgroundResource(R.color.orange);
                break;
            case "yellow":
                holder.tvColour.setText("Colour: yellow");
                holder.cardViewLayout.setBackgroundResource(R.color.yellow);
                break;
            case "green":
                holder.tvColour.setText("Colour: green");
                holder.cardViewLayout.setBackgroundResource(R.color.green);
                break;
            case "blue":
                holder.tvColour.setText("Colour: blue");
                holder.cardViewLayout.setBackgroundResource(R.color.blue);
                break;
            case "pink":
                holder.tvColour.setText("Colour: pink");
                holder.cardViewLayout.setBackgroundResource(R.color.pink);
                break;
            case "purple":
                holder.tvColour.setText("Colour: purple");
                holder.cardViewLayout.setBackgroundResource(R.color.purple);
                break;
            case "grey":
                holder.tvColour.setText("Colour: grey");
                //holder.tvColour.setBackgroundResource(R.color.red);
                holder.cardViewLayout.setBackgroundResource(R.color.grey);
                break;
            default:
                holder.tvColour.setText("Colour: unknown");
                holder.cardViewLayout.setBackgroundResource(R.color.grey);
                break;
        }
        //orange,yellow,green,blue,pink,purple
        if (userChatDisplays.get(position).getNewMessage() == true) {
            holder.tvNotification.setText("NEW MESSAGE");
        }else{
            holder.tvNotification.setText("");
        }
    }
    @Override
    public int getItemCount() {
        //Number of items displayed
        return userChatDisplays.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
         TextView tvName,tvColour,tvNotification;
         CardView cardViewLayout;
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            imageView = itemView.findViewById(R.id.logoView);
            tvName = itemView.findViewById(R.id.content);
            tvColour = itemView.findViewById(R.id.time);
            tvNotification = itemView.findViewById(R.id.notification);
            cardViewLayout = itemView.findViewById(R.id.cardViewRV);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
