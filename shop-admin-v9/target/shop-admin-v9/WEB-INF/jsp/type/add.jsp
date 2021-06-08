<%--
  Created by IntelliJ IDEA.
  User: xuejinsheng
  Date: 2021/3/11
  Time: 18:36
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
            <%--类型模块--%>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">类型列表</h3>
                        <form class="form-horizontal" >
                            <div class="form-group">
                                <label  class="col-sm-2 control-label">类型名称:</label>
                                <div class="col-sm-9">
                                    <input type="text" class="form-control" id="typeName"  placeholder="请输入规格名">
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
        </div>
    </div>
    <div class="row">

            <div class="col-md-6">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title">品牌展示</h3>
                    </div>
                    <div class="panel-body">
                        <table id="brandTable" class="table table-bordered">
                            <tbody>


                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title">规格展示</h3>
                    </div>
                    <div class="panel-body">
                        <table id="specTable" class="table table-bordered" style="width:100%">
                            <tbody>

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">规格展示</h3>
            </div>
            <div class="panel-body">
                <table id="attrTable" class="table table-bordered" style="width:100%">
                    <thead>
                    <button type="button" onclick="addAttr()" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span>增加属性</button>
                    <tr>
                            <td>属性名</td>
                            <td>属性值</td>
                            <td>操作</td>
                        </tr>
                    </thead>
                    <tbody>

                    <tr>
                        <td>
                            <input type="text" class="form-control" name="attrName"  placeholder="请输入属性名">
                        </td>
                        <td>
                            <input type="text" class="form-control" name="attrValue"  placeholder="请输入属性值">
                        </td>
                        <td></td>
                    </tr>
                    <tr></tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

    <div style="text-align: center;">
        <button type="button" onclick="submit()" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span>提交</button>
        <button type="reset" class="btn btn-danger"><span class="glyphicon glyphicon-refresh"></span>重置</button>
    </div>
<textarea id="addAttrDiv" style="display: none">
      <tr>
            <td>
                <input type="text" class="form-control" name="attrName"  placeholder="请输入属性名">
            </td>
            <td>
                <input type="text" class="form-control" name="attrValue"  placeholder="请输入属性值">
            </td>
            <td><button class="btn btn-danger" type="button"  onclick="deleteAttr(this)">删除属性</button></td>
        </tr>
</textarea>


    <jsp:include page="/common/script.jsp"></jsp:include>


<script>


$(function () {
    initTypeInfo();

})

function addAttr() {
        $("#attrTable tbody").append($("#addAttrDiv").val());
}
function deleteAttr(obj) {
    $(obj).parents("tr").remove();
}

            /**
             *
             *
             {type:{typeName:"xxx"},brandList:[12,13,14],specIdList :[13,14,15],attrInfoParamList:[

	               {attr:"颜色",attrInfoParamList:[{attrValue:"红色"},{attrValue:"蓝色"},{attrValue:"绿色"}]},
	               {attr:"颜色",attrInfoParamList:[{attrValue:"红色"},{attrValue:"蓝色"},{attrValue:"绿色"}]},
	               {attr:"颜色",attrInfoParamList:[{attrValue:"红色"},{attrValue:"蓝色"},{attrValue:"绿色"}]}
	      ]}

             */
            function submit() {
            var v_type={};
                v_type.type={};
                v_type.type.typeName=$("#typeName").val();
                v_type.brandTypeList=[];
                $("input[name='brandNames']:checkbox:checked").each(function () {
                    v_type.brandTypeList.push({"brandId":this.value});
                })
                v_type.specTypeList=[];
                $("input[name='specNames']:checkbox:checked").each(function () {
                    v_type.specTypeList.push({"specId":this.value});
                })
                //获取属性，属性值信息
                v_type.attrInfoParamList=[];
                var attrNameArr=[];
                $("input[name='attrName']").each(function () {
                    attrNameArr.push(this.value);
                })
                //获取属性值
                var attrValueArr=[];
                $("input[name='attrValue']").each(function () {
                    attrValueArr.push(this.value);
                })
                for (var i = 0; i <attrNameArr.length ; i++) {
                    var v_attr={};
                    v_attr.attr={};
                    v_attr.attr.attrName=attrNameArr[i];
                    v_attr.attrValueList=[];
                    var attrValueList  = attrValueArr[i].split(",");
                        for(let a of attrValueList){
                            var attrValue={};
                            attrValue.attrValue=a;
                            v_attr.attrValueList.push(attrValue);
                        }
                    v_type.attrInfoParamList.push(v_attr);
                }

                console.log(v_type);

                $.ajax({
                    type:"post",
                    data:JSON.stringify(v_type),
                    contentType:"application/json",
                    url:"/type/add.jhtml",
                    success:function (result) {
                        if(result.code==200){
                            location.href="/type/toList.jhtml";
                        }
                    }
                })
            }




        function initTypeInfo() {
           $.ajax({
               type:"get",
               url:"/type/findInFo.jhtml",
               success:function (result) {
                   console.log(result);
                   //品牌
                   let brandList = result.data.brandList;
                   var tr_aaa = 5;
                   var td_aaa1 = Math.ceil(brandList.length / tr_aaa);
                   let brandInfo = "";
                   for (let i = 0; i < td_aaa1; i++) {
                       var start = tr_aaa * i;
                       brandInfo += "<tr>";
                       for (let j = 0; j < tr_aaa; j++) {
                           if (brandList[start + j]) {
                               brandInfo += '<td><input type="checkbox"  value="' + brandList[start + j].id + '" name="brandNames"/>' + brandList[start + j].brandName + '</td>';
                           } else {
                               brandInfo += "<td></td>";
                           }

                       }
                       brandInfo += "</tr>";
                   }
                   $("#brandTable tbody").append(brandInfo);


                   //规格
                   let specList = result.data.specList;
                   var tr_bbb = 3;
                   var td_bbb1 = Math.ceil(specList.length / tr_bbb);

                   let specInFo = "";
                   for (let i = 0; i < td_bbb1; i++) {
                       var start2 = tr_bbb * i;
                       specInFo += "<tr>";
                       for (let j = 0; j < tr_bbb; j++) {
                           if (specList[start2 + j]) {
                               specInFo += '<td><input type="checkbox"  value="' + specList[start2 + j].id + '" name="specNames"/>' + specList[start2 + j].specName + '</td>';
                           } else {
                               specInFo += "<td></td>";
                           }
                       }
                       specInFo += "</tr>";
                   }
                   $("#specTable tbody").append(specInFo)
               }
           })
        }



</script>
</body>
</html>
