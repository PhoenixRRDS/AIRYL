package com.phoenix.airyl.Pojo;

/**
 * Created by mouri on 10/11/17.
 */

public class ChatMsg {
    public boolean isImg, isMine;
    private String content;

    public ChatMsg(String msg, boolean mine, boolean img) {
        content = msg;
        mine = isMine;
        img = isImg;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content) {
        this.content = content
    }

    public boolean isMine() {
        return isMine;
    }

    public void setIsMine(boolean isMine) {
        this.isMine = isMine;
    }

    public boolean isImg() {
        return isImg;
    }

    public void setIsImg(boolean isImg) {
        this.isImg = isImg;
    }
}
