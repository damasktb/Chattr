package com.damask.chattr;

/**
 * Created by demouser on 8/5/15.
 */
public class ChatMessage {

    private String mUser;
    private String mText;
    private Long mTimeStamp;
    public ChatMessage(String mUser, String mText) {
        this.mUser = mUser;
        this.mText = mText;
    }
    public String getmUser() {
        return mUser;
    }
    public void setmUser(String mUser) {
        this.mUser = mUser;
    }
    public String getmText() {
        return mText;
    }
    public void setmText(String mText) {
        this.mText = mText;
    }
    public Long getmTimeStamp() {
        return mTimeStamp;
    }
    public void setmTimeStamp(Long mTimeStamp) {
        this.mTimeStamp = mTimeStamp;
    }

    public ChatMessage() {

    }
}
