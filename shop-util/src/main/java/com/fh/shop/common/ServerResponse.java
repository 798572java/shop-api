package com.fh.shop.common;

import java.io.Serializable;

public class ServerResponse implements Serializable {

    private   int  code;

    private    String   message;

    private   Object   data;


    private  ServerResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public static ServerResponse success(){

        return   new ServerResponse(200,"ok",null);
    }

    public static ServerResponse success(Object data){

        return   new ServerResponse(200,"ok",data);
    }

    public static ServerResponse success(ResponseEnum responseEnum){

        return   new ServerResponse(responseEnum.getCode(),responseEnum.getMessage(),null);
    }


    public static ServerResponse error(){

        return   new ServerResponse(-1,"error",null);
    }

    public static ServerResponse error(int code, String message){

        return   new ServerResponse(code,message,null);
    }

    public static ServerResponse error(ResponseEnum responseEnum){

        return   new ServerResponse(responseEnum.getCode(),responseEnum.getMessage(),null);
    }

    public static ServerResponse error(ResponseEnum responseEnum,Object data){

        return   new ServerResponse(responseEnum.getCode(),responseEnum.getMessage(),data);
    }







    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
