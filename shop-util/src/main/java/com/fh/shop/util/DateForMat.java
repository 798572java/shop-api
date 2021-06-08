package com.fh.shop.util;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateForMat {

    public  static final  String  Date_Str="yyyy-MM-dd HH:mm:ss";
    public  static final  String  Date_Str_Y_M_D="yyyy-MM-dd";
    public  static final  String  Date_Y_M="yyyy-MM";

    public static Date addMinute(Date date,int minutes){
        DateTime dateTime = new DateTime(date);
        DateTime newDateTime = dateTime.plusMinutes(minutes);
        return newDateTime.toDate();
    }

    public static String date2str(Date date,String patter ){
        SimpleDateFormat sim=new SimpleDateFormat(patter);
        return  sim.format(date);
    }

    public static Date str2Date(String data,String patter){
        SimpleDateFormat sim=new SimpleDateFormat(patter);
        try {
            return sim.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}
