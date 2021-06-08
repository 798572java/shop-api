<%--
  Created by IntelliJ IDEA.
  User: xuejinsheng
  Date: 2021/3/3
  Time: 20:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<center>
    用户名 <input type="text" id="userName"><br>
    密码   &nbsp;&nbsp;&nbsp;<input type="text" id="password"><br>
    <input type="button" value="登录" onclick="login()">
    <script src="/js/jquery.min.js"></script>
    <script src="/js/md5.js"></script>
</center>
</body>
<script>

    function login() {
        //获取用户名
        var v_userName = $("#userName").val();
        //获取密码
        var v_password = $("#password").val();
        //前台js验证
        $.ajax({
            type:"post",
            url:"/user/login.jhtml",
            data:{"userName":v_userName,"passWord":hex_md5(v_password)},
            success:function(result){
                if (result.code==200){
                    window.location.href="/brand/list.jhtml"
                } else {
                    alert(result.message);
                }
            }
        })

    }







</script>
</html>
