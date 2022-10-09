package com.example.myselfchatapp;

public class UserChatDisplay {
    String name;
    String logo;
    String colour;

    public UserChatDisplay(String name, String logo, String colour) {
        this.name = name;
        this.logo = logo;
        this.colour = colour;
    }


    public String getName() {
        return name;
    }

    public String getLogo() {
        return logo;
    }

    public String getColour() {
        return colour;
    }
}
