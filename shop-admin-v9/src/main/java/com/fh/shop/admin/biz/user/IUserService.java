package com.fh.shop.admin.biz.user;

import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.admin.param.UserParam;
import com.fh.shop.admin.po.user.User;

public interface IUserService {


  User fineUser(String userName);

  DataTableResult findUser(UserParam userParam);

    ServerResponse addUser(User user);

  ServerResponse selectUserById(Long id);

  ServerResponse updateUser(User user);

  ServerResponse deleteUser(Long id);

  ServerResponse deleteBatch(String ids);


    ServerResponse inportExcel(String fileUrl);



//
//    DataTableResult findUser(UserParam userParam);
//
//    void addUser(User user);
//
//    ServerResponse selectUserById(Long id);
//
//    void updateUser(User user);
//
//    void deleteUser(Long id);
//
//    ServerResponse deleteBatch(String ids);

}
