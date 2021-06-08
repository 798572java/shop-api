<%--
  Created by IntelliJ IDEA.
  User: xuejinsheng
  Date: 2021/3/12
  Time: 14:49
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
            <form class="form-horizontal" >
                <div class="form-group">
                    <label  class="col-sm-2 control-label">类型名:</label>
                    <div class="col-sm-7">
                        <input type="text" class="form-control" id="typeName" name="brandName" placeholder="请输入规格名">
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div class="row">
        <div class="col-md-6">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">品牌列表展示</h3>
                </div>
                <div class="panel-body">
                    <table id="brandTable" class="table table-striped table-bordered"  style="width:100%">
                        <tbody>

                        </tbody>
                    </table>
                </div>
            </div>

        </div>

        <div class="col-md-6">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">规格列表展示</h3>
                </div>
                <div class="panel-body">
                    <table id="specTable" class="table table-striped table-bordered"  style="width:100%">
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
                        <button type="button" onclick="addAttr()" class="btn btn-primary"><span class="glyphicon glyphicon-plus
"></span>增加属性</button>
                        <tr>
                            <td>属性名</td>
                            <td>属性值</td>
                            <td>操作</td>
                        </tr>
                        </thead>
                        <tbody>

                        <tr>
                            <td>
                                 </td>
                            <td>
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
</div>


<div style="text-align: center;">
    <button type="button" onclick="submit()" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span>提交</button>
    <button type="reset" class="btn btn-danger"><span class="glyphicon glyphicon-refresh"></span>重置</button>
</div>



<textarea id="attrValueText" style="display:none;">
    <tr>
        <td><input type="text" class="form-control" name="update_attrValue" placeholder="请输入属性值"></td>
        <td><button type="button" onclick="removeAttrValue(this)" class="btn btn-danger"><span class="glyphicon glyphicon-trash"></span>删除属性</button></td>
    </tr>
</textarea>




<textarea id="editAttrDiv" style="display: none">

      <tr>
            <td>
                <input type="text" class="form-control" name="attrName"  placeholder="请输入属性名">
            </td>
            <td>
              <input type="text" class="form-control" name="attrValue"  placeholder="请输入属性值">
            </td>
            <td><button class="btn btn-danger" type="button"  onclick="deleteAttr(this)"><span class="glyphicon glyphicon-trash"></span> 删除属性</button></td>
        </tr>
</textarea>



<div id="attrValueDiv" style="display: none">
    <form class="form-horizontal" >
        <table  id="attrValueTable" class="table table-striped table-bordered" style="width:100%">
            <button type="button" onclick="addAttrValue(this)" class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span>增加属性值</button>
            <tbody>

            </tbody>
        </table>
    </form>
</div>








<jsp:include page="/common/script.jsp"></jsp:include>

<script>
    var t_id='${param.id}';


        $(function () {
            initEdit();
        })
function removeAttrValue(obj) {
    $(obj).parents("tr").remove()
}
        function addAttr() {
            $("#attrTable tbody").append($("#editAttrDiv").val());
        }

        function deleteAttr(obj) {
            $(obj).parents("tr").remove();
        }

    function addAttrValue() {
        $("#attrValueTable tbody").append($("#attrValueText").val());
    }

    function updateAttr(obj) {
            //初始化
        var v_attrHtml = $("#attrValueDiv").html();
        var attrValues=$(obj).parents("tr").find("[name=attrValue]").val();
        var attrValueList=attrValues.split(",");

        var v_html='';
        for (const attrValue of attrValueList) {
            v_html+='<tr>\n' +
                '        <td><input type="text" class="form-control" value='+attrValue+' name="update_attrValue" placeholder="请输入属性名"></td>\n' +
                '        <td><button type="button" class="btn btn-danger" onclick="deleteAttr(this)"><span class="glyphicon glyphicon-trash"></span>删除</button></td>\n' +
                '    </tr>'
        }
        $("#attrValueTable tbody").html(v_html);
        var v_showForm=bootbox.dialog({
            title:"添加",
            message: $("#attrValueDiv form"),
            size:"middle",
            buttons: {
                confirm: {
                    label: '确认',
                    className: 'btn-primary',
                    callback:function(){
                        debugger
                        var strAtt='';
                        $("[name=update_attrValue]",v_showForm).each(function () {
                            strAtt+="，"+this.value;
                        })
                        if(strAtt.length>0){
                            strAtt = strAtt.substring(1);
                        }
                        $(obj).parents("tr").find("[name=attrValue]").val(strAtt);

                    }
                },
                cancel: {
                    label: '取消',
                    className: 'btn-danger'
                }
            }
        });
        //还原
        $("#attrValueDiv").html(v_attrHtml);
    }


    
    
    function submit() {
        var brandNames=[];
        var specNames=[];
        $("input[name='brandName']:checkbox:checked").each(function (result) {
            brandNames.push($(this).val());
        })
        $("input[name='specName']:checkbox:checked").each(function (result) {
            specNames.push($(this).val());
        })
        //获取属性
        var attrArr=[];
        $("input[name='attrName']").each(function (result) {
            attrArr.push(this.value);
        })
        //获取属性值
        var attrValueArr=[];
        $("input[name='attrValue']").each(function (result) {
            attrValueArr.push(this.value);
        })



        let brandNamesStr = brandNames.join(",");
        let specNamesStr = specNames.join(",");

        let typeName =  $("#typeName").val();

        $.ajax({
            type:"post",
            data:{
                "attrValueArr":attrValueArr.join(";"),
                "attrStrArr":attrArr.join(","),
                "id":t_id,
                "brandIds":brandNamesStr,
                "specIds":specNamesStr,
                "typeName":typeName},
            url:"/type/editType.jhtml",
            success:function (result) {
                if(result.code==200){
                    location.href="/type/toList.jhtml";
                }
            }
        })
    }
    

        function initEdit(){
            $.ajax({
                type:"post",
                data:{"id":t_id},
                url:"/type/selectByTypeId.jhtml",
                success:function (result) {
                    console.log(result);
                   var v_type= result.data.type;//类型
                   var v_brandList= result.data.brandList;//品牌id
                   var  v_brandTypeidsList=result.data.brandTypeidsList;//品牌id
                   var specList= result.data.specList;//规格
                   var v_specTypeidsList= result.data.specTypeidsList;//规格id


                   var v_attrList = result.data.attrList;
                   var v_attrValueList =result.data.attrValueList;
                    //回填类型名称
                    $("#typeName").val(v_type.typeName);

                    var v_td_count=5;
                    var v_tr_count=Math.ceil(v_brandList.length/v_td_count);
                    var brandTemp="";
                    for (let i = 0; i <v_tr_count ; i++) {
                        brandTemp+='<tr>';
                        var v_b_start=i*v_td_count;
                        for (let j = 0; j <v_td_count ; j++) {
                            if(v_brandList[v_b_start+j]){
                                brandTemp += '<td><input type="checkbox"  value="' + v_brandList[v_b_start + j].id + '" name="brandName"/>' + v_brandList[v_b_start + j].brandName + '</td>';
                            }else{
                                brandTemp+='<td></td>';
                            }
                        }
                        brandTemp+='</tr>';
                    }
                    $("#brandTable tbody").append(brandTemp);

                    //规格
                    var v_counr_td=5;
                    var v_count_tr=Math.ceil(specList.length/v_counr_td);
                    var specTemp="";
                    for (let i = 0; i <v_count_tr ; i++) {
                        specTemp+='<tr>';
                        var v_s_start=i*v_counr_td;
                        for (let j = 0; j <v_counr_td ; j++) {
                            if(specList[v_s_start+j]){
                                specTemp += '<td><input type="checkbox"  value="' + specList[v_s_start + j].id + '" name="specName"/>' + specList[v_s_start + j].specName + '</td>';
                            }else{
                                specTemp+='<td></td>';
                            }
                        }
                        specTemp+='</tr>';
                    }
                    $("#specTable tbody").append(specTemp);


                    //回显品牌

                    CheckBoxchecked('brandName',v_brandTypeidsList);
                    CheckBoxchecked('specName',v_specTypeidsList);

                    //回显属性
                    var temp="";
                    for (let i = 0; i <v_attrList.length ; i++) {
                            temp += ' <tr>\n' +
                                '            <td>\n' +
                                '                <input type="text" size="10px" class="form-control" value="' + v_attrList[i].attrName + '" name="attrName"  placeholder="请输入属性名">\n' +
                                '            </td>\n' +
                                '            <td>\n' +
                                '               <input type="text" disabled  size="10px" class="form-control" value="" name="attrValue"  placeholder="请输入属性名">  \n' +
                                '            </td>\n' +
                                '            <td><button class="btn btn-primary" type="button"  onclick="updateAttr(this)"><span class="glyphicon glyphicon-warning-sign"></span> 更新属性</button>' +
                                '<button class="btn btn-danger" type="button"  onclick="deleteAttr(this)"><span class="glyphicon glyphicon-trash"></span> 删除属性</button></td>\n' +
                                '        </tr>';

                    }
                    $("#attrTable tbody").append(temp);

                    var attrValues=document.getElementsByName("attrValue");//2
                    for (var i = 0; i <attrValues.length; i++) {
                        var attrValue='';
                        for (let x of v_attrValueList) {
                            if(v_attrList[i].id==x.attrId){
                                attrValue+=","+x.attrValue
                            }
                        }
                        attrValue=attrValue.substring(1);
                        attrValues[i].value=attrValue
                    }
                }
            })
        }







    function  CheckBoxchecked(name,list) {
        $("input[name='"+name+"']:checkbox").each(function () {
            this.checked=ischecked(this.value,list);
        })
    }



        function ischecked(id,list){
          for(let s of list){
              if(id==s){
                  return true;
              }
          }
                return false;
        }






















</script>
</body>
</html>
