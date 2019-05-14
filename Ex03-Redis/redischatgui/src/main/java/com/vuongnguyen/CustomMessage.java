package com.vuongnguyen;

import java.io.Serializable;
import java.util.Date;

public class CustomMessage implements Serializable {
    String chanel;
    String user;
    String message;
    String date;

    CustomMessage(){

    }

    public CustomMessage(String chanel, String user, String message, String date) {
        this.chanel = chanel;
        this.user = user;
        this.message = message;
        this.date = date;
    }

    public String getChanel() {
        return chanel;
    }

    public void setChanel(String chanel) {
        this.chanel = chanel;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return user+" : "+message+" \n"+"\t("+date+")"+"\n";
    }
}
