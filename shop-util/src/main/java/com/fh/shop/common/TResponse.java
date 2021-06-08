package com.fh.shop.common;

import java.io.Serializable;

public class TResponse implements Serializable {

    private int codeInfo;

    private String MessageInfo;

    private Object data;

    private TResponse(){

    }

    private TResponse(int codeInfo,String messageInfo,Object data){
        this.data=data;
        this.codeInfo=codeInfo;
        this.MessageInfo=messageInfo;
    }

    public static TResponse success(){
        return new TResponse(200,"ok",null);
    }

    public static TResponse success(Object data){
        return new TResponse(200,"ok",data);
    }

    public static TResponse error(){
        return new TResponse(201,"error",null);
    }


    public int getCodeInfo() {
        return codeInfo;
    }

    public String getMessageInfo() {
        return MessageInfo;
    }

    public Object getData() {
        return data;
    }
}
