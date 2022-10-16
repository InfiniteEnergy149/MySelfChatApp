package com.example.myselfchatapp;

public class UserChatMessage {
    Boolean sendRecieve;
    String name;
    String logo;
    String message;
    String date;
    String time;

    public UserChatMessage(Boolean sendRecieve, String name, String logo, String message, String date, String time) {
        this.sendRecieve = sendRecieve;
        this.name = name;
        this.logo = logo;
        this.message = message;
        this.date = date;
        this.time = time;

    }


    public Boolean getSendRecieve() {
        return sendRecieve;
    }

    public String getName() {
        return name;
    }

    public String getLogo() {
        return logo;
    }

    public String getMessage(){
        return message;
    }

    public String getDate(){

        return date;
    }

    public String getTime(){

        return time;
    }


}
