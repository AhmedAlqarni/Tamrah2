package com.example.ahmed.tamrah;

/**
 * Created by khalidalnamlah on 3/13/18.
 */

public class Message {

    private String content,name;

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
}
