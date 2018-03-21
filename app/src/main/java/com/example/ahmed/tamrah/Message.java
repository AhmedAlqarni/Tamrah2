package com.example.ahmed.tamrah;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by khalidalnamlah on 3/13/18.
 */

public class Message {

    private String content,name;
    private String date;

    public void setContent(String content,String name) {
        this.content = content;
        this.name = name;

    }

    public String getContent() {
        return content;

    }
    public void setContent(String content){
        this.content = content;
    }

    public Message(){


    }
    public Message (String content){
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        date = dateFormat.format(cal.getTime());
    }

    public String getDate(){
        return date;
    }
}
