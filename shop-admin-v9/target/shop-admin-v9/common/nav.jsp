
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

</head>
<body>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">飞狐教育</a>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav" >
                <li>
                    <a href="<%=request.getContextPath()%>/brand/list.jhtml" >品牌模块</a>
                </li>
            </ul>

            <ul class="nav navbar-nav" >
                <li>
                    <a href="<%=request.getContextPath()%>/log/log.jhtml" >日志模块</a>
                </li>
            </ul>

            <ul class="nav navbar-nav" >
                <li>
                    <a href="<%=request.getContextPath()%>/user/toList.jhtml" >用户模块</a>
                </li>
            </ul>

            <ul class="nav navbar-nav" >
                <li>
                    <a href="<%=request.getContextPath()%>/spec/toList.jhtml" >规格管理</a>
                </li>
            </ul>

            <ul class="nav navbar-nav" >
                <li>
                    <a href="<%=request.getContextPath()%>/type/toList.jhtml" >类型管理</a>
                </li>
            </ul>

            <ul class="nav navbar-nav" >
                <li>
                    <a href="<%=request.getContextPath()%>/cate/toIndex.jhtml" >分类管理</a>
                </li>
            </ul>

            <ul class="nav navbar-nav" >
                <li>
                    <a href="<%=request.getContextPath()%>/spu/toList.jhtml" >商品管理</a>
                </li>
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">欢迎${uesrDB.realName}登录</a></li>
                <li><a href="#">您今天第${uesrDB.count}次登录</a></li>
                <li><a href="#">上次登陆时间${uesrDB.showDate}登录</a></li>

                <li >
                    <a href="/user/logout.jhtml"  >注销 </a>

                </li>
            </ul>


        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
</body>


</html>