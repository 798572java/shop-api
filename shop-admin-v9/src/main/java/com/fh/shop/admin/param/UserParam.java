package com.fh.shop.admin.param;

import com.fh.shop.common.Page;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class UserParam extends Page {

    private String userName;

    private String realName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginBirthday;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endBirthday;

    private Integer sex;


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

    public Date getBeginBirthday() {
        return beginBirthday;
    }

    public void setBeginBirthday(Date beginBirthday) {
        this.beginBirthday = beginBirthday;
    }

    public Date getEndBirthday() {
        return endBirthday;
    }

    public void setEndBirthday(Date endBirthday) {
        this.endBirthday = endBirthday;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}
