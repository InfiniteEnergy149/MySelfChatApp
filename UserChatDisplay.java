package com.example.myselfchatapp;

public class UserChatDisplay {
    String name;
    String logo;
    String colour;
    Boolean newMessage;

    public UserChatDisplay(String name, String logo, String colour, Boolean newMessage) {
        this.name = name;
        this.logo = logo;
        this.colour = colour;
        this.newMessage = newMessage;
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

    public void setNewMessage(Boolean newMess){
        this.newMessage = newMess;
    }
    public Boolean getNewMessage(){
        return newMessage;
    }
}
