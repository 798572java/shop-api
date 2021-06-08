package com.fh.shop.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZzUtil {

    public static Boolean Zzphone(String phone){
        String value="13253879073";
        String regExp = "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(value);
        return m.find();
    }

    public static Boolean ZzEmail(String email){
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(email);
        boolean m = matcher.matches();
        return m;
    }




}
