package com.fh.shop;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestZZ {

    @Test
    public void test1(){
        String value="13253879073";
      String regExp = "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$";
      Pattern p = Pattern.compile(regExp);
      Matcher m = p.matcher(value);
      if(m.find()==true){
          System.out.println("ok");
      }else {
          System.out.println("no");
      }
      // return m.find();//boolean
    }

    @Test
    public void test2(){
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher("2344@qq.com");
        boolean m = matcher.matches();
        if(m==true){
            System.out.println("ok");
        }else {
            System.out.println("no");
        }
    }



}


