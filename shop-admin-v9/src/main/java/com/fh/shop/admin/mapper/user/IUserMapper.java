package com.fh.shop.admin.mapper.user;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.admin.param.UserParam;
import com.fh.shop.admin.po.user.User;

import java.util.List;


public interface IUserMapper extends BaseMapper<User>{
   Long fineUserCount(UserParam userParam);

   List<User> findUserList(UserParam userParam);

    void addBatch(List<User> userList);


//
//    Long findCount(UserParam userParam);
//
//    List<User> findList(UserParam userParam);
//
//    void addUser(User user);
//
//    User selectUserById(Long id);
//
//    void updateUser(User user);
//
//    void deleteUser(Long id);
//
//    void deleteBatch(List<Long> idsList);

}
