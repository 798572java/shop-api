<%--
  Created by IntelliJ IDEA.
  User: xuejinsheng
  Date: 2021/3/12
  Time: 14:18
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
                    <form class="form-horizontal" action="/brand/addBrand.jhtml" method="post" >
                        <div class="form-group">
                            <label  class="col-sm-2 control-label">类型名:</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="typeName"  placeholder="请输入类型名">
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
    <button type="button" class="btn btn-danger" onclick="deleteBatch();"><span class="glyphicon glyphicon-"></span>批量删除</button>
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">数据列表展示</h3>
                </div>
                <div class="panel-body">
                    <table id="typeTable" class="table table-striped table-bordered"  style="width:100%">
                        <thead>
                        <tr>
                            <th>选项</th>
                            <th>类型名称</th>
                            <th>选择</th>
                        </tr>
                        </thead>
                        <tfoot>
                        <tr>
                            <th>选项</th>
                            <th>类型名称</th>
                            <th>选择</th>
                        </tr>
                        </tfoot>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>


<jsp:include page="/common/script.jsp"></jsp:include>

<script>
    $(function () {
        inintTable();
    })




    function toadd() {
        location.href="/type/toAdd.jhtml";
    }
    function update(id) {
        location.href="/type/toEdit.jhtml?id="+id;
    }



    function search(){
        //idu选择器获取文本框的值
        var v_typeName = $("#typeName").val();
        //创建js对象
        var  v_param={};
        //给对象赋值
        v_param.typeName=v_typeName;
        //通过这个方法给ajax的data传值到后台
        v_typeTable.settings()[0].ajax.data=v_param;
        //刷新表格
        v_typeTable.ajax.reload();
    }


    var v_typeTable;
    function inintTable(){

        v_typeTable = $('#typeTable').DataTable({
            "language": {
                "url": "/js/DataTables/Chinese.json" // 汉化
            },
            // 是否允许检索
            "searching": false,
            "processing": true,
            "lengthMenu": [5,10,15,20],
            "serverSide": true,
            "ajax": {
                "url": "/type/findType.jhtml",
                "type": "POST"

            },
            "columns": [
                {  "data": "id",
                    "render": function (data, type, row, meta) {
                        console.log(data);
                        return '<input type="checkbox" name="ids" value="'+data+'"/>';
                    }

                },
                { "data": "typeName"},
                {  "data": "id",
                    "render": function (data, type, row, meta) {
                        return '<button class="btn btn-warning" type="button"  onclick="update(\''+data+'\')">修改</button>'+
                            '<button class="btn btn-danger" type="button"  onclick="deleteType(\''+data+'\')">删除</button>'
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
                        $.ajax({
                            type:"post",
                            url:"/type/deleteBatchIds.jhtml",
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


    function  deleteType(id) {


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
                        url:"/type/deleteType.jhtml",
                        data:{"id":id},
                        success:function (data) {
                            if (data.code==200){
                                search();
                            }else if (data.code==201){
                                bootbox.alert({
                                    size: "small",
                                    title: "删除",
                                    message: "删除失败"
                                })
                            }


                        }

                    })


                } else {
                    // alert('点击取消按钮了');
                }
            },
        })

    }
</script>
</body>
</html>
