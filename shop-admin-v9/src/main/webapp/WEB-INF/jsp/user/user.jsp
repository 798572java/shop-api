<%--
  Created by IntelliJ IDEA.
  User: love
  Date: 2021/3/7
  Time: 20:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <!-- Bootstrap -->
    <!-- Bootstrap -->
    <jsp:include page="/common/head.jsp"></jsp:include>
    <title>列表展示</title>
    <jsp:include page="/common/nav.jsp"></jsp:include>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">条件查询展示</h3>
                </div>
                <div class="panel-body">
                    <form class="form-horizontal" id="logForm">
                        <div class="form-group">
                            <label  class="col-sm-2 control-label">用户名:</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="userName" name="userName" placeholder="请输入用户名">
                            </div>
                            <label  class="col-sm-2 control-label">真实姓名:</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="realName" name="realName" placeholder="请输入真实姓名">
                            </div>
                        </div>
                        <div class="form-group">
                            <label  class="col-sm-2 control-label">性别:</label>
                            <div class="col-sm-4">
                                <label class="radio-inline">
                                    <input type="radio" name="search_sex"  value="1" >男
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="search_sex" value="2">女
                                </label>
                            </div>
                            <label class="col-sm-2 control-label">生日:</label>
                            <div class="col-md-4">
                                <div class="input-group">
                                    <input type="text" class="form-control" name="beginBirthday" id="beginBirthday">
                                    <span class="input-group-addon">--</span>
                                    <input type="text" class="form-control" name="endBirthday" id="endBirthday">
                                </div>
                            </div>
                        </div>
                        <div style="text-align: center;">
                            <button type="button" class="btn btn-primary" onclick="search();"><span class="glyphicon glyphicon-search"></span>查询</button>
                            <button type="reset" class="btn btn-danger"><span class="glyphicon glyphicon-refresh"></span>重置</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div>
        <button type="button" onclick="deleteBatch()" class="btn btn-danger"><span class="glyphicon glyphicon-trash"></span>批量删除</button>
        <button type="button" onclick="toAdd()" class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span>新增</button>
        <button type="button" onclick="inportExcel()" class="btn btn-success"><span class="glyphicon glyphicon-upload"></span>导入</button>
        <div class="row">
            <div class="col-md-12">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title">数据列表展示</h3>
                    </div>
                    <div class="panel-body">
                        <table id="userTable" class="table table-striped table-bordered" style="width:100%">
                            <thead>
                            <tr>
                                <th>选择</th>
                                <th>用户名</th>
                                <th>真实姓名</th>
                                <th>用户头像</th>
                                <th>用户性别</th>
                                <th>mail</th>
                                <th>手机号</th>
                                <th>生日</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tfoot>
                            <tr>
                                <th>选择</th>
                                <th>用户名</th>
                                <th>真实姓名</th>
                                <th>用户头像</th>
                                <th>用户性别</th>
                                <th>mail</th>
                                <th>手机号</th>
                                <th>生日</th>
                                <th>操作</th>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="userUpdateDialog" style="display: none;">
        <form class="form-horizontal"  >
            <div class="form-group">
                <label  class="col-sm-2 control-label">用户名:</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="update_userName"  placeholder="请输入用户名">
                </div>

                <label  class="col-sm-2 control-label">密码:</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="update_password"  placeholder="请输入密码">
                </div>
            </div>
            <div class="form-group">
                <label  class="col-sm-2 control-label">真实姓名:</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="update_realName"  placeholder="请输入真实姓名">
                </div>

                <label  class="col-sm-2 control-label">确认密码:</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="update_comfirmPassWord"  placeholder="请输入密码">
                </div>


            </div>

            <div class="form-group">
                <label  class="col-sm-2 control-label">mail:</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="update_mail"  placeholder="请输入mail">
                </div>

                <label  class="col-sm-2 control-label">用户性别</label>
                <div class="col-sm-4">
                    <label class="radio-inline">
                        <input type="radio" name="update_sex"  value="1" >男
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="update_sex"  value="2" >女
                    </label>
                </div>
            </div>
            <div class="form-group">
                <label  class="col-sm-2 control-label">手机号:</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="update_phone"  placeholder="请输入手机号">
                </div>
                <label  class="col-sm-2 control-label">生日:</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="update_birthday"  placeholder="请输入生日">
                </div>
            </div>
            <div class="form-group">

                <label  class="col-sm-2 control-label">用户头像:</label>
                <div class="col-sm-6" style="height: 300px;">
                    <input type="file" id="update_userPhoto" name="image">
                    <input type="text" id="update_photo" >
                    <input type="text" id="update_old_photo" >
                </div>
            </div>
            <div style="text-align: center;">
            </div>
        </form>
    </div>

    <div id="userDialog" style="display: none;">
        <form class="form-horizontal" >
            <div class="form-group">
                <label  class="col-sm-2 control-label">用户名:</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="add_userName"  placeholder="请输入用户名">
                </div>

                <label  class="col-sm-2 control-label">密码:</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="add_password"  placeholder="请输入密码">
                </div>
            </div>
            <div class="form-group">
                <label  class="col-sm-2 control-label">真实姓名:</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="add_realName"  placeholder="请输入真实姓名">
                </div>

                <label  class="col-sm-2 control-label">确认密码:</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="add_confirmPassWord"  placeholder="请输入真实姓名">
                </div>
            </div>


            <div class="form-group">
                <label  class="col-sm-2 control-label">用户性别</label>
                <div class="col-sm-4">
                    <label class="radio-inline">
                        <input type="radio" name="add_sex"  value="1" >男
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="add_sex"  value="2" >女
                    </label>
                </div>

                <label  class="col-sm-2 control-label">mail:</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="add_mail"  placeholder="请输入mail">
                </div>
            </div>
            <div class="form-group">
                <label  class="col-sm-2 control-label">手机号:</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="add_phone"  placeholder="请输入手机号">
                </div>

                <label  class="col-sm-2 control-label">生日:</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="add_birthday"  placeholder="请输入生日">
                </div>
            </div>

                <div class="form-group" style="height: 400px;">
                    <label  class="col-sm-2 control-label">用户头像:</label>
                    <div class="col-sm-6" style="height: 300px;">
                        <input type="file" id="add_userPhoto" name="image">
                        <input type="text" id="add_photo" >
                    </div>
                    </div>

            <div style="text-align: center;">
            </div>
        </form>
    </div>

    <div id="uploadFile" style="display: none;">
        <form class="form-horizontal" >

            <div class="form-group" style="height: 400px;">
                <label  class="col-sm-2 control-label">选择文件:</label>
                <div class="col-sm-6" style="height: 300px;">
                    <input type="file" id="upload_excel" name="file">
                    <input type="text" id="inport_url">
                </div>
            </div>

            <div style="text-align: center;">
            </div>
        </form>
    </div>
    <jsp:include page="/common/script.jsp"></jsp:include>
</body>
<script>
    //onready初始化加载函数
    $(function () {
        initBrandTable();
    })

    function inportExcel() {
        //备份
        var v_Html = $("#uploadFile").html();

        //渲染上传组件
        //配置信息
        var setting = {
            language: 'zh',
            uploadUrl: "/file/uploadFile.jhtml", // 后台上传文件的url地址
            showUpload : false,
            showRemove : false
        };
        // 渲染组件
        $("#upload_excel").fileinput(setting).on("fileuploaded", function (event, r, previewId, index) {
            console.log(r.response.data);

            $("#inport_url",v_fileDialog).val(r.response.data);
        });

        var v_fileDialog = bootbox.dialog({
            title:"添加",
            message: $("#uploadFile form"),
            size:"middle",
            buttons: {
                confirm: {
                    label: '确认',
                    className: 'btn-primary',
                    callback:function(){
                        $.ajax({
                            type:"post",
                            url:"/user/inportExcel.jhtml",
                            data:{"fileUrl":$("#inport_url",v_fileDialog).val()},
                            success:function (res) {
                                if(res.code==200){
                                    search();
                                }
                            }
                        })
                    }
                },
                cancel: {
                    label: '取消',
                    className: 'btn-danger'
                }
            }
        });
        //还原
        $("#uploadFile").html(v_Html);
    }





    //条件查询
    function search() {

        //获取参数值
        var v_userName = $("#userName").val();
        var v_beginBirthday = $("#beginBirthday").val();
        var v_endBirthday = $("#endBirthday").val();
        var v_sex = $("input[name='search_sex']:radio:checked").val();
        var v_realName = $("#realName").val();


        //传参
        var v_param ={};
        v_param.userName = v_userName;
        v_param.beginBirthday = v_beginBirthday;
        v_param.endBirthday = v_endBirthday;
        v_param.sex = v_sex;
        v_param.realName = v_realName;
        v_userTable.settings()[0].ajax.data = v_param;
        v_userTable.ajax.reload();
    }



    
    
    //定义表格，分页
    var v_userTable ;
    function initBrandTable() {
        v_userTable = $('#userTable').DataTable({
            "language": {
                "url": "/js/DataTables/Chinese.json" // 汉化
            },
            // 是否允许检索
            "searching": false,
            "processing": true,
            "lengthMenu": [5,10,15,20],
            "serverSide": true,
            "ajax": {
                "url": "/user/findUser.jhtml",
                "type": "POST"
            },
            "columns": [
                {"data":"id",
                    "render": function (data, type, row, meta) {
                        return '<input type="checkbox" name="ids" value="'+data+'"/>';
                    }
                },
                {"data":"userName"},
                {"data":"realName"},
                {"data":"userPhoto",
                    "render": function (data, type, row, meta) {
                        return  '<img src="'+data+'" width="50px" height="50px"/>';
                    }
                },
                {"data":"sex",
                    "render":function(data){
                        return data==1?"男":"女";
                    }
                },
                {"data":"mail"},
                {"data":"phone"},
                {"data":"birthday"},
                {"data":"id",
                    "render": function (data, type, row, meta) {
                        return '<button class="btn btn-warning" type="button"  onclick="updateUser('+data+')"><span class="glyphicon glyphicon-pencil">修改</button>'+
                            '<button class="btn btn-danger" type="button"  onclick="deleteUser(\''+data+'\')"><span class="glyphicon glyphicon-trash">删除</button>';
                    }
                }
            ]
        });
    }

    function toAdd() {
        //备份
        var v_userHtml = $("#userDialog").html();

        //渲染上传组件
        //配置信息
        var setting = {
            language: 'zh',
            uploadUrl: "/file/uploadImage.jhtml", // 后台上传文件的url地址
            showUpload : false,
            showRemove : false
        };
        // 渲染组件
        $("#add_userPhoto").fileinput(setting).on("fileuploaded", function (event, r, previewId, index) {
            console.log(r.response.data);

            $("#add_photo",v_userDialog).val(r.response.data);
        });

        var v_userDialog = bootbox.dialog({
            title:"添加",
            message: $("#userDialog form"),
            size:"middle",
            buttons: {
                confirm: {
                    label: '确认',
                    className: 'btn-primary',
                    callback:function(){
                        //获取参数
                        var v_userName= $("#add_userName",v_userDialog).val();
                        var v_password = $("#add_password",v_userDialog).val();
                        var v_confirmPassWord = $("#add_confirmPassWord",v_userDialog).val();
                        var v_realName = $("#add_realName",v_userDialog).val();
                        var v_userPhoto = $("#add_photo",v_userDialog).val();
                        var v_sex = $("[name='add_sex']:checked",v_userDialog).val();
                        var v_mail = $("#add_mail",v_userDialog).val();
                        var v_phone = $("#add_phone",v_userDialog).val();
                        var v_birthday = $("#add_birthday",v_userDialog).val();
                        var param ={};
                        param.userName = v_userName;
                        param.passWord = v_password;
                        param.confirmPassWord = v_confirmPassWord;
                        param.realName = v_realName;
                        param.userPhoto = v_userPhoto;
                        param.sex = v_sex;
                        param.mail = v_mail;
                        param.phone = v_phone;
                        param.birthday = v_birthday;
                        //发送ajax请求
                        $.ajax({
                            "type":"post",
                            "data":param,
                            "url":"/user/addUser.jhtml",
                            "success":function(result){
                                if(result.code==200){
                                    search();
                                }

                            }
                        })
                    }
                },
                cancel: {
                    label: '取消',
                    className: 'btn-danger'
                }
            }
        });
        //调用时间插件
        initAddDateTimePicker();
        //还原
        $("#userDialog").html(v_userHtml);

    }
    function initAddDateTimePicker(){
        $("#add_birthday,#update_birthday").datetimepicker({
            format:"YYYY-MM-DD",
            locale:"zh-cn",
            showClear:true
        })
    }

    //修改
    function updateUser(id) {
        $.ajax({
            type:"post",
            //回显
            url:"/user/selectUserById.jhtml",
            data:{"id":id},
            success:function (result) {
                if (result.code==200){
                    var v_userUpdate = result.data;
                    // 回显数据
                    $("#update_userName").val(v_userUpdate.userName)
                    $("#update_password").val(v_userUpdate.passWord)
                    $("#update_realName").val(v_userUpdate.realName)
                    $("[name=update_sex]").each(function(){
                        if(this.value==v_userUpdate.sex){
                            this.checked=true
                        }
                    });
                    $("#update_mail").val(v_userUpdate.mail);
                    $("#update_phone").val(v_userUpdate.phone);
                    $("#update_birthday").val(v_userUpdate.birthday);
                    var imageArr =[];
                    imageArr.push(v_userUpdate.userPhoto);
                    $("#update_old_photo").val(v_userUpdate.userPhoto)
                    // 备份
                    var v_userHtml =$("#userUpdateDialog").html();
                    // 渲染
                    //配置信息
                    var setting = {
                        language: 'zh',
                        uploadUrl: "/file/uploadImage.jhtml", // 后台上传文件的url地址
                        showUpload : false,
                        showRemove : false,
                        initialPreview:imageArr,
                        initialPreviewAsData:true
                    };
                    // 渲染组件
                    $("#update_userPhoto").fileinput(setting).on("fileuploaded", function (event, r, previewId, index) {
                        console.log(r.response.data);
                        $("#update_photo",v_userDialog).val(r.response.data);
                    });
                    //弹框
                    var v_userDialog = bootbox.dialog({
                        title:"修改品牌",
                        message: $("#userUpdateDialog form"),
                        size:"middle",
                        buttons: {
                            confirm: {
                                label: '确认',
                                className: 'btn-primary',
                                callback:function(){
                                    //获取参数
                                    var v_userName= $("#update_userName",v_userDialog).val();
                                    var v_password = $("#update_password",v_userDialog).val();
                                    var v_comfirmPassWord = $("#update_comfirmPassWord",v_userDialog).val();
                                    var v_realName = $("#update_realName",v_userDialog).val();
                                    var v_userPhoto = $("#update_photo",v_userDialog).val();
                                    var v_sex = $("[name='update_sex']:checked",v_userDialog).val();
                                    var v_mail = $("#update_mail",v_userDialog).val();
                                    var v_phone = $("#update_phone",v_userDialog).val();
                                    var v_birthday = $("#update_birthday",v_userDialog).val();
                                    var param ={};
                                    param.id=id;
                                    param.userName = v_userName;
                                    param.passWord = v_password;
                                    param.confirmPassWord = v_comfirmPassWord;
                                    param.realName = v_realName;
                                    param.userPhoto = v_userPhoto;
                                    param.sex = v_sex;
                                    param.mail = v_mail;
                                    param.phone = v_phone;
                                    param.birthday = v_birthday;
                                    //发送ajax请求
                                    $.ajax({
                                        "type":"post",
                                        "data":param,
                                        "url":"/user/updateUser.jhtml",
                                        "success":function(result){
                                            if(result.code==200){
                                                search();
                                            }

                                        }
                                    })
                                }
                            },
                            cancel: {
                                label: '取消',
                                className: 'btn-danger'
                            }
                        }
                    });
                    //调用时间插件
                    initAddDateTimePicker();
                    //还原
                    $("#userUpdateDialog").html(v_userHtml);

                }
            }
        })
    }
    //进行删除操作
    function deleteUser(id){
        bootbox.confirm({
            title:"提示信息",
            message: "你确定要删除吗？",
            size:"small",
            buttons: {
                confirm: {
                    label: '<span class="glyphicon glyphicon-ok">确定',
                    className: 'btn-success'
                },
                cancel: {
                    label: '<span class="glyphicon glyphicon-remove">取消',
                    className: 'btn-danger'
                }
            },
            callback: function (result) {
                if (result){
                    $.ajax({
                        type:"post",
                        url:"/user/deleteUser.jhtml",
                        data:{"id":id},
                        success:function(result){
                            if (result.code==200){
                                search();
                            } else if (result.code==-1){
                                bootbox.alert({
                                    size: "small",
                                    title: "提示信息",
                                    message: "删除失败,系统异常",
                                })
                            }
                        }
                    })
                }
            }
        });
    }
    function deleteBatch(ids) {
// 获取选中的复选框的集合
        var v_idArr=[];
        $("input[name='ids']:checkbox:checked").each(function (result) {
            v_idArr.push($(this).val());
        })
        var v_ids = v_idArr.join(",");
        if (v_idArr.length==0){
            bootbox.alert({
                message:"请选择要删除的记录",
                size:"small",
                title:"提示信息"
            })
        }else {
            bootbox.confirm({
                title:"提示信息",
                message: "你确定要删除吗？",
                size:"small",
                buttons: {
                    confirm: {
                        label: '<span class="glyphicon glyphicon-ok">确定',
                        className: 'btn-success'
                    },
                    cancel: {
                        label: '<span class="glyphicon glyphicon-remove">取消',
                        className: 'btn-danger'
                    }
                },
                callback: function (result) {
                    if (result){
                        var v_ids =v_idArr.join(",");
                        $.ajax({
                            type:"post",
                            url:"/user/deleteBatch.jhtml",
                            data:{"ids":v_ids},
                            success:function(result){
                                if (result.code==200){
                                    search();
                                } else if (result.code==-1){
                                    bootbox.alert({
                                        size: "small",
                                        title: "提示信息",
                                        message: "删除失败,系统异常",
                                    })
                                }
                            }
                        })
                    }
                }
            });
        }
    }
</script>
</html>