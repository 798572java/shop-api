package com.fh.shop.common;

import java.util.UUID;

public final class SystemConstant {

    public  static final  String  CURR_USER="uesrDB";

    public  static final  String  LOGIN="/login.jsp";

    public  static final  String UPLOAD="/upload/";

    public  static final  String  UPLOAD_EXCEL="e:/upload/";

    public  static final  String  CODE="code";

    public  static final  String  EMAILUUID="statusUrl";

    public static final String  CARTSTATUS="0";

    public  static final  int   ACTION=-1;

    public  static final  int InACTION=1;

    public  static final  int STATUS=1;

    public  static final  String FIELD="cartVo";

    public  static final  String COUNT="count";

    public static final String DEFAULT="0";

    public static final String NODEFAULT="1";

    public static final String PAY_SELL_ID = "2088621955811420";

    public static final String PAY_APP_ID = "2021000117658341";

    public static final int mailMessage = 1;

    public interface ORDER_STATUS{
        int WAIT_PAY = 0;
        int PAY_SUCCESS = 10;
        int PAY_ORDER_SUCCESS = 20;
        int TRADE_CLOSE = 40;
    }


    public interface MESSAGE_LOG_STATUS{
        int SENDING = 0;//发送中
        int SEND_SUCCESS = 1;//发送成功
        int SEND_FAIL = 2;//发送失败
        int CONSUME_SUCCESS = 3;//接受成功
    }

    public static final int RETRY_COUNT = 3;

}
