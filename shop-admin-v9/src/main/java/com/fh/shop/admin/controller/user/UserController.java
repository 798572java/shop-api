package com.fh.shop.admin.controller.user;

import com.fh.shop.admin.annotation.LogInfo;
import com.fh.shop.admin.biz.user.IUserService;
import com.fh.shop.common.DataTableResult;
import com.fh.shop.common.ResponseEnum;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.common.SystemConstant;
import com.fh.shop.admin.param.UserParam;
import com.fh.shop.admin.po.user.User;
import com.fh.shop.util.DateCalUtil;
import com.fh.shop.util.Md5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class UserController {
    @Resource(name = "userService")
    private IUserService userService;

    @RequestMapping(value = "/user/toList",method = RequestMethod.GET)
    public String  toList(){
        return  "/user/user";
    }




    @RequestMapping(value = "/user/findUser",method = RequestMethod.POST)
    @ResponseBody
    public DataTableResult findUser(UserParam userParam){

    return  userService.findUser(userParam);
    }


    @RequestMapping(value = "/user/addUser",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse addUser(User user){
        return userService.addUser(user);
    }

    @RequestMapping(value = "/user/selectUserById",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse selectUserById(Long id){
        return userService.selectUserById(id);
    }

    @RequestMapping(value = "/user/updateUser",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateUser(User user){
        return userService.updateUser(user);
    }


    @RequestMapping(value = "/user/deleteUser",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteUser(Long id){
        return userService.deleteUser(id);
    }


    @RequestMapping(value = "/user/deleteBatch",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteBatch(String ids){
        return userService.deleteBatch(ids);
    }


    @RequestMapping(value = "/user/inportExcel",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse inportExcel(String fileUrl){

        return userService.inportExcel(fileUrl);

    }

    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @ResponseBody
    @LogInfo(info = "登录")
    public ServerResponse login(User user, HttpServletRequest request){
        //获取前台传来的用户名
        String userName = user.getUserName();
        //获取前台传来的密码
        String passWord = user.getPassWord();
        //用户名为空
        if(!StringUtils.isNotEmpty(userName)){
            return ServerResponse.error(ResponseEnum.USER_EMPTY);
        }
        // 密码为空
        if(!StringUtils.isNotEmpty(passWord)){
            return ServerResponse.error(ResponseEnum.PASSS_EMPTY);
        }
        //用户是否存在
        User userDB=userService.fineUser(userName);
        if (userDB == null) {
            return ServerResponse.error(ResponseEnum.USER_NAME_ERROR);
        }
        String salt = userDB.getSalt();
        //密码不正确
        if (!userDB.getPassWord().equals(Md5Util.md5(passWord+","+salt))) {
            return ServerResponse.error(ResponseEnum.PASSWORD_IS_ERROR);
        }
        //用户登陆成功放到session中
        SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        userDB.setShowDate(sim.format(userDB.getLastDayTime()));

        boolean oneDay = DateCalUtil.isOneDay(new Date(), userDB.getLastDayTime());
        if(oneDay){
            userDB.setCount(userDB.getCount()+1);
        }else {
            userDB.setCount(0);
        }

        userDB.setLastDayTime(new Date());
        request.getSession().setAttribute(SystemConstant.CURR_USER,userDB);
        userDB.setConfirmPassWord(userDB.getPassWord());
        userService.updateUser(userDB);
        return ServerResponse.success();
    }

    @RequestMapping(value = "/user/logout",method = RequestMethod.GET)
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/login.jsp";
    }

}
