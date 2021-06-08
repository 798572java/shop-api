package com.fh.shop.admin.biz.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ResponseEnum;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.admin.mapper.user.IUserMapper;
import com.fh.shop.admin.param.UserParam;
import com.fh.shop.admin.po.user.User;
import com.fh.shop.util.Md5Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service("userService")
public class IUserServiceImpl implements IUserService {

        @Autowired
    private IUserMapper userMapper;


    @Override
    public User fineUser(String userName) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userName", userName);
        User user = userMapper.selectOne(userQueryWrapper);
        return user;

    }

    @Override
    public DataTableResult findUser(UserParam userParam) {
        Long count =userMapper.fineUserCount(userParam);


        List<User> userList =userMapper.findUserList(userParam);

        return new DataTableResult(userParam.getDraw(),count,count,userList);
    }

    @Override
    public ServerResponse addUser(User user) {
        String passWord = user.getPassWord();
        if(StringUtils.isEmpty(passWord)){
            return ServerResponse.error(ResponseEnum.PASSS_EMPTY);
        }
        if(StringUtils.isEmpty(user.getConfirmPassWord())){
            return ServerResponse.error(ResponseEnum.PASSS_EMPTY);
        }
        if(!passWord.equals(user.getConfirmPassWord())){
            return ServerResponse.error(ResponseEnum.PASSWORD_IS_ERROR);
        }
        String salt = UUID.randomUUID().toString();
        user.setSalt(salt);
        passWord = Md5Util.md5(Md5Util.md5(passWord)+","+salt);
        user.setPassWord(passWord);
        userMapper.insert(user);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse selectUserById(Long id) {
        User user = userMapper.selectById(id);
        return ServerResponse.success(user);
    }

    @Override
    public ServerResponse updateUser(User user) {
        if(StringUtils.isEmpty(user.getPassWord())){
            return ServerResponse.error(ResponseEnum.PASSS_EMPTY);
        }
        if(StringUtils.isEmpty(user.getConfirmPassWord())){
            return ServerResponse.error(ResponseEnum.PASSS_EMPTY);
        }
        if(!user.getPassWord().equals(user.getConfirmPassWord())){
            return ServerResponse.error(ResponseEnum.PASSWORD_IS_ERROR);
        }
        userMapper.updateById(user);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteUser(Long id) {
        userMapper.deleteById(id);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteBatch(String ids) {
        if(StringUtils.isEmpty(ids)){
            return ServerResponse.error(ResponseEnum.USER_IDS_NULL);
        }
        String[] idsArr = ids.split(",");
        List<Long> idsList=new ArrayList<>();
        for (String s : idsArr) {
            idsList.add(Long.parseLong(s));
        }
        userMapper.deleteBatchIds(idsList);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse inportExcel(String fileUrl) {
        FileInputStream fileInputStream=null;
        try {
             fileInputStream = new FileInputStream(fileUrl);
            XSSFWorkbook xssfSheets = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = xssfSheets.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            List<User> userList=new ArrayList<>();
            for (int i = 1; i <lastRowNum ; i++) {
                int count=0;
                XSSFRow row = sheet.getRow(i);
                String userName = row.getCell(count++).getStringCellValue();
                String realName = row.getCell(count++).getStringCellValue();
                String sex = row.getCell(count++).getStringCellValue();
                String phone = row.getCell(count++).getStringCellValue();
                Date birthday = row.getCell(count++).getDateCellValue();
                String mail = row.getCell(count++).getStringCellValue();
                User user = new User();
                user.setUserName(userName);
                user.setRealName(realName);
                user.setBirthday(birthday);
                user.setMail(mail);
                user.setPhone(phone);
                user.setSex(sex.equals("男")?1:2);
                userList.add(user);
            }
            userMapper.addBatch(userList);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            if(fileInputStream!=null){
                try {
                    fileInputStream.close();
                    fileInputStream=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        File file = new File(fileUrl);
        if(file.exists()){
            file.delete();
        }
        return ServerResponse.success();
    }
//
//    @Override
//    public DataTableResult findUser(UserParam userParam) {
//
//      Long count=  userMapper.findCount(userParam);
//      List<User> userList= userMapper.findList(userParam);
//        return new DataTableResult(userParam.getDraw(),count,count,userList);
//    }
//
//    @Override
//    public void addUser(User user) {
//        userMapper.addUser(user);
//    }
//
//    @Override
//    public ServerResponse selectUserById(Long id) {
//        User user=userMapper.selectUserById(id);
//        return ServerResponse.success(user);
//    }
//
//    @Override
//    public void updateUser(User user) {
//        userMapper.updateUser(user);
//    }
//
//    @Override
//    public void deleteUser(Long id) {
//        userMapper.deleteUser(id);
//    }
//
//    @Override
//    public ServerResponse deleteBatch(String ids) {
//        if(StringUtils.isNotEmpty(ids)){
//            //将字符串分割为数组
//            String[] idsArr = ids.split(",");
//            List<Long> idsList=new ArrayList<>();
//            for (String s : idsArr) {
//                idsList.add(Long.parseLong(s));
//            }
//
//            userMapper.deleteBatch(idsList);
//            return ServerResponse.success();
//        }
//        return ServerResponse.error();
//    }
}
