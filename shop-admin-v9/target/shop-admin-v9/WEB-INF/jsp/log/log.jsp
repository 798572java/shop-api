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
                    <form class="form-horizontal" id="serach_from">
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
                            <label  class="col-sm-2 control-label">操作信息:</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="info" name="info" placeholder="请输入操作信息">
                            </div>
                            <label class="col-sm-2 control-label">时间:</label>
                            <div class="col-md-4">
                                <div class="input-group">
                                    <input type="text" class="form-control" name="beginTime" id="beginDate">
                                    <span class="input-group-addon">--</span>
                                    <input type="text" class="form-control" name="endTime" id="endDate">
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

    <button type="button" class="btn btn-success" onclick="exportExcel();"><span class="glyphicon glyphicon-download-alt"></span>导出Excel</button>
    <button type="button" class="btn btn-success" onclick="exportPDF();"><span class="glyphicon glyphicon-download-alt"></span>导出PGD</button>
    <button type="button" class="btn btn-success" onclick="exportWord();"><span class="glyphicon glyphicon-download-alt"></span>导出Word</button>
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">数据列表展示</h3>
                </div>
                <div class="panel-body">
                    <table id="logTable" class="table table-striped table-bordered" style="width:100%">
                        <thead>
                        <tr>
                            <th>用户名称</th>
                            <th>真实名称</th>
                            <th>什么时间</th>
                            <th>什么操作</th>
                            <th>详情</th>

                        </tr>
                        </thead>
                        <tfoot>
                        <tr>
                            <th>用户名称</th>
                            <th>真实名称</th>
                            <th>什么时间</th>
                            <th>什么操作</th>
                            <th>详情</th>
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
            initBrandTable();
            initDateTime();
        })

        function exportExcel(){
            var v_elementById = document.getElementById("serach_from");
            v_elementById.action="/log/exportExcel.jhtml";
            v_elementById.method="post";
            v_elementById.submit();
        }

        function exportPDF(){
            var v_elementById = document.getElementById("serach_from");
            v_elementById.action="/log/exportPDF.jhtml";
            v_elementById.method="post";
            v_elementById.submit();
        }

        function exportWord() {
            var v_elementById = document.getElementById("serach_from");
            v_elementById.action="/log/exportWord.jhtml";
            v_elementById.method="post";
            v_elementById.submit();
        }


        function  search() {
            //idu选择器获取文本框的值
            var v_realName = $("#realName").val();
            var v_userName = $("#userName").val();
            var v_beginDate = $("#beginDate").val();
            var v_endDate = $("#endDate").val();
            var v_info = $("#info").val();

            //创建js对象
            var  v_param={};
            //给对象赋值
            v_param.realName=v_realName;
            v_param.beginDate=v_beginDate;
            v_param.endDate=v_endDate;
            v_param.userName=v_userName;
            v_param.info=v_info;
            //通过这个方法给ajax的data传值到后台
            v_brandTable.settings()[0].ajax.data=v_param;
            //刷新表格
            v_brandTable.ajax.reload();

        }

        var  v_brandTable
        function initBrandTable() {
              v_brandTable = $('#logTable').DataTable({
                "language": {
                    "url": "/js/DataTables/Chinese.json" // 汉化
                },
                // 是否允许检索
                "searching": false,
                "processing": true,
                "lengthMenu": [5,10,15,20],
                "serverSide": true,
                "ajax": {
                    "url": "/log/findLog.jhtml",
                    "type": "POST"
                },
                "columns": [

                    { "data": "userName"},
                    { "data": "realName"},
                    { "data": "insertTime"},
                    { "data": "info"},
                    {  "data": "paramInfo",
                        "render": function (data, type, row, meta) {
                            return '<button class="btn btn-warning" type="button"  onclick="detalis(\''+data+'\')">详情</button>';
                        }
                    }


                ]
            });
        }


        function detalis(paramInfo){
            bootbox.alert({
                message:"详情如下"+paramInfo,
                size:"large",
                title:"提示信息+"
            })
        }

        function initDateTime(){
            //使用选择器如果说要获取多个 用逗号隔开
            $("#beginDate,#endDate").datetimepicker({
                format:"YYYY-MM-DD HH:mm",
                locale:"zh-cn",
                showClear:true
            })
        }


</script>
</html>
