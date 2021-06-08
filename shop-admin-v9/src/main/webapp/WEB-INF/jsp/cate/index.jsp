<%--
  Created by IntelliJ IDEA.
  User: xuejinsheng
  Date: 2021/3/16
  Time: 14:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>分类管理</title>
    <jsp:include page="/common/head.jsp"></jsp:include>
    <jsp:include page="/common/nav.jsp"></jsp:include>
</head>
<body>

<div class="container">
    <div class="row">

        <div class="col-md-12" id="tableDiv">
            <table id="cateTable" class="table table-striped table-bordered"  style="width:100%">
                <thead>
                <tr>
                    <th>分类名 <button type="button" class="btn btn-primary" onclick="addBrantch();"><span class="glyphicon glyphicon-plus"></span>增加</button>
                    </th>
                    <th>类型名</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
        </div>
    </div>
</div>


<div id="add_cateDiv" style="display: none">
    <form class="form-horizontal" >
        <div class="form-group">
            <label  class="col-sm-2 control-label">分类名:</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="add_cateName"  placeholder="请输入品牌名">
            </div>
        </div>
        <div class="form-group" >
            <label  class="col-sm-2 control-label">分类选择:</label>
            <div class="col-sm-6" id="add_cate">

            </div>
        </div>

        <div class="form-group">
            <label  class="col-sm-2 control-label">类型:</label>
            <div class="col-sm-6" id="add_type">

            </div>
        </div>

        <div style="text-align: center;">
        </div>
    </form>
</div>

<div id="edit_cateDiv" style="display: none">
    <form class="form-horizontal" >
        <div class="form-group">
            <label  class="col-sm-2 control-label">分类名:</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="edit_cateName"  placeholder="请输入品牌名">
            </div>
        </div>
        <div class="form-group" >
            <label  class="col-sm-2 control-label">分类选择:</label>
            <div class="col-sm-6" id="edit_cate">

            </div>
        </div>

        <div class="form-group">
            <label  class="col-sm-2 control-label">类型:</label>
            <div class="col-sm-6" id="edit_type">

            </div>
        </div>

        <div style="text-align: center;">
        </div>
    </form>
</div>

<div id="root_cateDiv" style="display: none">


    <form class="form-horizontal" >
        <div class="form-group">
            <label  class="col-sm-2 control-label">分类名:</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="root_cateName"  placeholder="请输入品牌名">
            </div>
        </div>
        <div class="form-group" >
            <label  class="col-sm-2 control-label">分类选择:</label>
            <div class="col-sm-6" id="root_cate">

            </div>
        </div>

        <div class="form-group">
            <label  class="col-sm-2 control-label">类型:</label>
            <div class="col-sm-6" id="root_type">

            </div>
        </div>

        <div style="text-align: center;">
        </div>
    </form>
</div>

<jsp:include page="/common/script.jsp"></jsp:include>
<script>

    var v_html;
$(function () {
     v_html=$("#tableDiv").html();
    inintCateTable();
})

    function deleteCate(obj) {
        $(obj).parents("tr").remove();
    }


      var sortList;
        function inintCateTable() {
            $("#tableDiv").html(v_html);
            $.ajax({
                type:"get",
                url:"/cate/findCate.jhtml",
                success:function (result) {
                    //console.log(result);
                    var v_cateList=result.data;
                   sortList=[];
                  getSortList(v_cateList,sortList,0,0);

                    var v_html="";
                    for(let cate of sortList){
                        v_html+='<tr data-tt-id="'+cate.id+'" data-tt-parent-id="'+cate.fatherId+'">\n' +
                            '            <td>'+cate.cateName+'' +
                            '<button type="button" class="btn btn-primary" onclick="addCate('+cate.id+');"><span class="glyphicon glyphicon-plus"></span>增加</button>' +
                            '</td>\n' +
                            '            <td>'+cate.typeName+'</td>\n' +
                            '            <td><button type="button" class="btn btn-warning" onclick="editCate('+cate.id+');"><span class="glyphicon glyphicon-pencil"></span>更新</button>\n' +
                            '    <button type="button" class="btn btn-danger" onclick="deleteCate('+cate.id+');"><span class="glyphicon glyphicon-trash"></span>删除</button></td>\n' +
                            '        </tr>';

                    }
                    $("#cateTable tbody").html(v_html);
                        //treeTable
                    $("#cateTable").treetable({expandable: true,initialState:'expanded'})
                }
            })
        }


        function editCate(id) {
            $.ajax({
                type:"post",
                data:{"id":id},
                url:"/cate/findCateById.jhtml",
                success:function (result) {
                    if(result.code==200){
                        console.log(result);
                       var cate= result.data.cate;
                        var v_typeList=result.data.typeList;
                        //备份
                        var v_eit=$("#edit_cateDiv").html();

                        //回显分类名
                        $("#edit_cateName").val(cate.cateName);
                        //回显单选
                        radio(v_typeList,"radio_type","edit_type");

                        $("input[name='radio_type']:radio").each(function () {
                            this.checked=this.value==cate.typeId;
                        })
                        //回显下拉框
                        getSelect("edit_select","edit_cate",cate.fatherId);

                        var v_editVate=bootbox.dialog({
                            title:"添加",
                            message: $("#edit_cateDiv form"),
                            buttons: {
                                confirm: {
                                    label: '确认',
                                    className: 'btn-success',
                                    callback:function(){
                                        var param ={};
                                        param["cate.cateName"]=$("#edit_cateName",v_editVate).val();
                                        param["cate.fatherId"]=$("#edit_select",v_editVate).val();
                                        param["cate.typeId"]=$("input[name='radio_type']:radio:checked",v_editVate).val();
                                        param["cate.typeName"]=$("input[name='radio_type']:radio:checked",v_editVate).attr("xjs");
                                        param["cate.id"]=id;
                                        var chilIds=[];
                                        getChilderns(id,chilIds);
                                        console.log(param);
                                        param.ids=chilIds.join(",");
                                        $.ajax({
                                            type:"post",
                                            data:param,
                                            url:"/cate/updateCate.jhtml",
                                            success:function (result) {
                                                if(result.code==200){
                                                    inintCateTable();
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
                        $("#edit_cateDiv").html(v_eit);
                    }
                }
            })

        }



        function addCate(id) {
            $.ajax({
                type:"get",
                url:"/type/findAll.jhtml",
                success:function (result) {
                    if(result.code==200){
                        var v_typeList=result.data;

                        //单选按钮
                        radio(v_typeList,"addType","add_type");

                        //备份
                       var html= $("#add_cateDiv").html();

                        //下拉框
                        getSelect("add_selectCate","add_cate",id)

                        var v_addVate=bootbox.dialog({
                            title:"添加",
                            message: $("#add_cateDiv form"),
                            buttons: {
                                confirm: {
                                    label: '确认',
                                    className: 'btn-success',
                                    callback:function(){
                                        var param ={};
                                        param.cateName=$("#add_cateName",v_addVate).val();
                                        param.fatherId=id;
                                        param.typeId=$("input[name='addType']:radio:checked",v_addVate).val();
                                        param.typeName=$("input[name='addType']:radio:checked",v_addVate).attr("xjs");

                                        console.log(param);

                                        $.ajax({
                                            type:"post",
                                            data:param,
                                            url:"/cate/addCate.jhtml",
                                            success:function (result) {
                                                if(result.code==200){
                                                    inintCateTable();
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

                        $("#add_cateDiv").html(html);
                    }
                }
            })
        }

    function  deleteCate(id) {
        bootbox.confirm({
            message: '将会删除自身以及子孙！你确认删除吗',
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
                    var childernIds=[];
                    getChilderns(id,childernIds);
                    childernIds.push(id);
                    $.ajax({
                        type:"post",
                        data:{"ids":childernIds.join(",")},
                        url:"/cate/deleteCate.jhtml",
                        success:function (result) {
                            if(result.code==200){
                                inintCateTable();
                            }else {
                                alert(result.message);
                            }
                        }
                    })
                }
            },
        })
    }



    function addBrantch() {
        $.ajax({
            type:"get",
            url:"/type/findAll.jhtml",
            success:function (res) {
                console.log(res)
                if (res.code==200) {
                    //备份
                    var v_rootHtml=$("#root_cateDiv").html()

                    var v_typeList = res.data;
                    //单选按钮回显
                    radio(v_typeList,"typeId","root_type");
                }
                //弹框
                var v_rootDiv = bootbox.dialog({
                    title:"添加",
                    message: $("#root_cateDiv form"),
                    size:"middle",
                    buttons: {
                        confirm: {
                            label: '确认',
                            className: 'btn-primary',
                            callback:function(){
                                //获取参数
                                var param ={};
                                param.cateName = $("#root_cateName",v_rootDiv).val();
                                param.fatherId = 0;
                                param.typeId = $("[name=typeId]:checked",v_rootDiv).val();
                                param.typeName = $("[name=typeId]:checked",v_rootDiv).attr("xjs")

                                console.log(param)
                                $.ajax({
                                    type:"post",
                                    data:param,
                                    url:"/cate/addCate.jhtml",
                                    success:function (result) {
                                        if(result.code==200){
                                            inintCateTable();
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
                $("#root_cateDiv").html(v_rootHtml);
            }
        })
    }









    function getSelect(selectCate,cateAll,id) {
            //回显分类下拉框
            var cateTemp='<select class="form-control" id="'+selectCate+'">';
            for (let x of sortList) {
                var tem="";
                for (let i = 0; i <x.level ; i++) {
                    tem+="&nbsp&nbsp";
                }
                cateTemp+='<option value="'+x.id+'">'+tem+x.cateName+'</option>';
            }
            cateTemp+='</select>';
            $("#"+cateAll).html(cateTemp);
            $("#"+selectCate).val(id);
        }


        function radio(v_typeList,nametype,catetype) {
            var typeList='';
            for (let x of v_typeList) {
                typeList+='<input type="radio" name="'+nametype+'" xjs="'+x.typeName+'" value="'+x.id+'" >'+x.typeName+'';
            }
            $("#"+catetype).html(typeList);
        }


       function getSortList(v_cateList,sortList,id,level){
           var v_children=queryChildren(v_cateList,id);
            for(let a of v_children){
                a.level=level;
                sortList.push(a);
                getSortList(v_cateList,sortList,a.id,level+1);
            }
       }

        function queryChildren(v_cateList,id) {
            var  v_children=[];
            for(let cate of v_cateList){
                if(cate.fatherId==id){
                    v_children.push(cate);
                }
            }
            return v_children;
        }

        function getChilderns(id,chilIds) {
            for (let x of sortList) {
                if(id==x.fatherId){
                    chilIds.push(x.id);
                    getChilderns(x.id,chilIds);
                }
            }
        }



</script>


</body>
</html>
