package com.fh.shop.admin.po.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {

    private Long  id;

    private  String  userName;

    private    String   realName;

    private  String  passWord;
    @TableField(exist = false)
    private  String  confirmPassWord;

    private  String  userPhoto;

    private  Integer  sex;

    private  String mail;

    private String   phone;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date birthday;

    private String salt;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-HH:mm:ss",timezone = "GMT+8")
    private Date lastDayTime;

    private Integer count;
    @TableField(exist = false)
    private String showDate;
}
