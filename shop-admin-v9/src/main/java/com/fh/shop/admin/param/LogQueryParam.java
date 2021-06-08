package com.fh.shop.admin.param;

import com.fh.shop.common.Page;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class LogQueryParam extends Page implements Serializable {

    private  String  userName;

    private  String  info;

    private  String  realName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date	beginDate;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date	endDate;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
