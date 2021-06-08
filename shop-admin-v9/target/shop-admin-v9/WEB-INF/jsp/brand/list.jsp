<%--
  Created by IntelliJ IDEA.
  User: xuejinsheng
  Date: 2021/2/28
  Time: 20:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="/common/head.jsp"></jsp:include>
    <jsp:include page="/common/nav.jsp"></jsp:include>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">条件查询展示</h3>
                </div>
                <div class="panel-body">
                    <form class="form-horizontal" >
                        <div class="form-group">
                            <label  class="col-sm-2 control-label">品牌名:</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="brandName"  placeholder="请输入品牌名">
                            </div>
                        </div>
                        <div style="text-align: center;">
                            <button type="button" onclick="search()" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span>查询</button>
                            <button type="reset" class="btn btn-danger"><span class="glyphicon glyphicon-refresh"></span>重置</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <button type="button" class="btn btn-primary" onclick="toadd();"><span class="glyphicon glyphicon-plus"></span>增加</button>
    <button type="button" class="btn btn-primary" onclick="toaddLog();"><span class="glyphicon glyphicon-plus"></span>增加</button>
    <button type="button" class="btn btn-danger" onclick="deleteBatch();"><span class="glyphicon glyphicon-plus"></span>批量删除</button>
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">数据列表展示</h3>
                </div>
                <div class="panel-body">
                    <table id="brandTable" class="table table-striped table-bordered" style="width:100%">
                        <thead>
                        <tr>
                            <th>选项</th>
                            <th>品牌名称</th>
                            <th>logo</th>
                            <th>成立时间</th>
                            <th>选择</th>
                        </tr>
                        </thead>
                        <tfoot>
                        <tr>
                            <th>选项</th>
                            <th>品牌名称</th>
                            <th>logo</th>
                            <th>成立时间</th>
                            <th>选择</th>
                        </tr>
                        </tfoot>
                    </table>

                </div>
            </div>
        </div>
    </div>
</div>




<div id="brandDialog" style="display: none">
    <form class="form-horizontal" >
        <div class="form-group">
            <label  class="col-sm-2 control-label">品牌名:</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="add_brandName"  placeholder="请输入品牌名">
            </div>
        </div>
        <div class="form-group" style="height: 400px">
            <label  class="col-sm-2 control-label">logo:</label>
            <div class="col-sm-6" style="height: 300px">
                <input type="file" class="form-control" id="add_logoFile" name="image">
                <input type="text" id="add_logo">
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">成立年份:</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="add_createYear"  placeholder="请输入年份">
            </div>
        </div>
        <div style="text-align: center;">
        </div>
    </form>
</div>

<div id="updateBrand" style="display: none">
    <form class="form-horizontal" >
        <div class="form-group">
            <label  class="col-sm-2 control-label">品牌名:</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="edit_brandName"  placeholder="请输入品牌名">
            </div>
        </div>
        <div class="form-group" style="height: 400px">
            <label  class="col-sm-2 control-label">logo:</label>
            <div class="col-sm-5" style="height: 270px">
                <input type="file" class="form-control" id="edit_logoFile" name="image">
                <input type="text" id="edit_logo">
                <input type="text" id="edit_oldlogo">
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">成立年份:</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="edit_createYear"  placeholder="请输入年份">
            </div>
        </div>
        <div style="text-align: center;">
        </div>
    </form>
</div>

<jsp:include page="/common/script.jsp"></jsp:include>


<script>
        $(function () {
            initBrandTable();
        })

        function toadd() {
            window.location.href="/brand/toadd.jhtml";
        }

        function  update(id){
            $.ajax({
                type:"post",
                data:{"id":id},
                url:"/brand/selectBrandById.jhtml",
                success:function (data) {
                    if (data.code==200){
                       var brand= data.data;
                        $("#edit_brandName,v_brandUpdate").val(brand.brandName);
                        $("#edit_oldlogo,v_brandUpdate").val(brand.logo);
                        $("#edit_createYear,v_brandUpdate").val(brand.createYear);
                        var imageArr=[];
                        imageArr.push(brand.logo);

                        //备份
                        var v_brandHtml= $("#updateBrand").html();
                        var v_brandUpdate=bootbox.dialog({
                            title:"添加",
                            message: $("#updateBrand form"),
                            buttons: {
                                confirm: {
                                    label: '确认',
                                    className: 'btn-success',
                                    callback:function(){
                                        var param = {};

                                        param.brandName = $("#edit_brandName",v_brandUpdate).val();
                                        param.logo = $("#edit_logo",v_brandUpdate).val();
                                        param.createYear = $("#edit_createYear",v_brandUpdate).val();
                                        param.oldLogo = $("#edit_oldlogo",v_brandUpdate).val();
                                        param.id=brand.id;
                                        $.ajax({
                                            "type":"post",
                                            "data":param,// JSON.stringify  把json对象转换为json字符串
                                            "url":"/brand/updateBrand.jhtml",
                                            complete:function(xjs, x) {
                                                var responseHeader = xjs.getResponseHeader("Xjs");
                                                console.log(responseHeader);
                                                if (responseHeader && responseHeader == 'Xjs') {
                                                    // session超时了
                                                    window.location.href="/login.jsp";
                                                }
                                            },
                                            "success":function(data){
                                                if(data.code==200){
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
                        //渲染
                        var setting = {
                            language: 'zh',
                            uploadUrl: "/file/uploadImage.jhtml", // 后台上传文件的url地址
                            showUpload : false,
                            showRemove : false,
                            initialPreviewAsData: true,
                            initialPreview:imageArr
                        };
                        // 渲染组件
                        $("#edit_logoFile,updateBrand").fileinput(setting).on("fileuploaded", function (event, r, previewId, index) {
                            console.log(r.response.data);
                            $("#edit_logo,updateBrand").val(r.response.data);
                        });
                        initDateTime2();
                        //还原
                        $("#updateBrand").html(v_brandHtml);
                    }
                }
            })
        }

        function  toaddLog() {
            //备份
            var v_brandHtml= $("#brandDialog").html();
            var v_brandAdd=bootbox.dialog({
                title:"添加",
                message: $("#brandDialog").html(),
                buttons: {
                    confirm: {
                        label: '确认',
                        className: 'btn-success',
                        callback:function(){
                            var param = {};

                            param.brandName = $("#add_brandName",v_brandAdd).val();
                            param.logo = $("#add_logo",v_brandAdd).val();
                            param.createYear = $("#add_createYear",v_brandAdd).val();
                            $.ajax({
                                "type":"post",
                                "data":param,// JSON.stringify  把json对象转换为json字符串
                                "url":"/brand/toAddBrand.jhtml",
                                "success":function(data){
                                    if(data.code==200){
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
            initDateTime();
            inintFileInput();
            //还原
            $("#brandDialog").html(v_brandHtml);
        }



        function  search() {
            //idu选择器获取文本框的值
            var v_brandName = $("#brandName").val();
            //创建js对象
            var  v_param={};
            //给对象赋值
            v_param.brandName=v_brandName;
            //通过这个方法给ajax的data传值到后台
            v_brandTable.settings()[0].ajax.data=v_param;
            //刷新表格
            v_brandTable.ajax.reload();

        }

        var  v_brandTable
        function initBrandTable() {
              v_brandTable = $('#brandTable').DataTable({
                "language": {
                    "url": "/js/DataTables/Chinese.json" // 汉化
                },
                // 是否允许检索
                "searching": false,
                "processing": true,
                "lengthMenu": [5,10,15,20],
                "serverSide": true,
                "ajax": {
                    "url": "/brand/findList.jhtml",
                    "type": "POST",

                },
                "columns": [
                    {  "data": "id",
                        "render": function (data, type, row, meta) {
                            console.log(data);
                            return '<input type="checkbox" name="ids" value="'+data+'"/>';
                        }

                    },
                    { "data": "brandName" },
                    { "data": "logo",
                        "render": function (data, type, row, meta) {
                            return '<img src="'+data+'" width="50px" height="50px"/>';
                        }
                    },

                    { "data": "createYear"},
                    {  "data": "id",
                        "render": function (data, type, row, meta) {
                            return '<button class="btn btn-warning" type="button"  onclick="update(\''+data+'\')">修改</button>'+
                                '<button class="btn btn-danger" type="button"  onclick="deleteBrand(\''+data+'\')">删除</button>'
                                ;
                        }
                    }
                ]
            });
        }


        function deleteBatch() {
           //获取选中复选框
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
                                url:"/brand/deleteBatch.jhtml",
                                data:{"ids":v_ids},

                                success:function(result){
                                    if (result.code==200){
                                        search();
                                    }
                                }
                            })
                        }
                    }
                });
            }
        }









        function  deleteBrand(id) {


            bootbox.confirm({
                message: '你确认删除吗',
                title:"删除",
                buttons: {
                    confirm: {
                        label: '确认',
                        className: 'btn-success'
                    },
                    cancel: {
                        label: '取消',
                        className: 'btn-danger'
                    }
                },

                callback: function(result) {
                    if(result) {
                       $.ajax({
                           type:"post",
                           url:"/brand/deleteBrand.jhtml",
                           data:{"id":id},

                           success:function (data) {
                               if (data.code==200){
                                   search();
                               }


                           }

                       })


                    } else {
                        // alert('点击取消按钮了');
                    }
                },
            })

        }

        function inintFileInput(){
            var setting = {
                language: 'zh',
                uploadUrl: "/file/uploadImage.jhtml", // 后台上传文件的url地址
                showUpload : false,
                showRemove : false
            };
            // 渲染组件
            $("#add_logoFile,brandDialog").fileinput(setting).on("fileuploaded", function (event, r, previewId, index) {
                console.log(r.response.data);
                $("#add_logo,brandDialog").val(r.response.data);
            });

        }




        function initDateTime(){
            //使用选择器如果说要获取多个 用逗号隔开
            $("#add_createYear,brandDialog").datetimepicker({
                format:"YYYY",
                locale:"zh-cn",
                showClear:true
            })
        }

        function initDateTime2(){
            //使用选择器如果说要获取多个 用逗号隔开
            $("#edit_createYear,updateBrand").datetimepicker({
                format:"YYYY",
                locale:"zh-cn",
                showClear:true
            })
        }


</script>
</html>
