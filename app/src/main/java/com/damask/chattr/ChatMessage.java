package com.damask.chattr;

/**
 * Created by demouser on 8/5/15.
 */
public class ChatMessage {

    private String author;
    private String date;
    private String picUrl;
    private String title;

    public ChatMessage(String author, String date, String picUrl, String title) {
        this.author = author;
        this.date = date;
        this.picUrl = picUrl;
        this.title = title;
    }

    public String getAuthor() {return author;}
    public String getDate() {return date;}
    public String getPicUrl() {return picUrl;}
    public String getTitle() {return title;}

    public void setAuthor(String author) {this.author = author;}
    public void setDate(String date) {this.date = date;}
    public void setPicUrl(String picUrl) {this.picUrl = picUrl;}
    public void setTitle(String title) {this.title = title;}

    public ChatMessage() {}
}
