<%--
  Created by IntelliJ IDEA.
  User: xuejinsheng
  Date: 2021/3/9
  Time: 18:33
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
            <div class="col-md-12" id="ss">
                <button type="button" class="btn btn-primary" onclick="addSpec();"><span class="glyphicon glyphicon-plus"></span>新增规格</button>
                <button type="button" onclick="submit()"  class="btn btn-primary" onclick=""><span class="glyphicon glyphicon-ok"></span>提交</button>
                <table id="specTable"  class="table table-striped table-bordered" style="width:100%">
                    <tr>
                        <td>规格名称</td>
                        <td> <input type="text" class="form-control" name="specName"></td>
                        <td>规格排序</td>
                        <td> <input type="text" class="form-control" name="specSort"></td>

                        <td><button type="button" class="btn btn-primary" onclick="addSpecValue(this);"><span class="glyphicon glyphicon-plus"></span>新增规格值</button>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
</div>

<textarea id="addSpecText" style="display: none">
         <tr>
            <td>规格值</td>
            <td> <input type="text" class="form-control" name="specValue"></td>
            <td>规格值排序</td>
            <td> <input type="text" class="form-control" name="specValueSort"></td>
            <td>
                <button type="button" class="btn btn-danger" onclick="deleteSpecValue(this);">
                    <span class="glyphicon glyphicon-trash">
                    </span>删除规格值</button>
            </td>
        </tr>
</textarea>

<textarea id="addSpecInfo" style="display: none">
    <table  class="table table-striped table-bordered" style="width:100%">
        <tr>
            <td>
            <button type="button" class="btn btn-danger" onclick="deleteSpec(this);">
                <span class="glyphicon glyphicon-trash"></span>删除规格</button>
        </tr>
             </td>
                    <tr>
                        <td>规格名称</td>
                        <td> <input type="text" class="form-control" name="specName"></td>
                        <td>规格排序</td>
                        <td> <input type="text" class="form-control" name="specSort"></td>
                        <td><button type="button" class="btn btn-primary" onclick="addSpecValue(this);"><span class="glyphicon glyphicon-plus"></span>新增规格值</button>
                        </td>
                    </tr>
                </table>
</textarea>


<jsp:include page="/common/script.jsp"></jsp:include>

<script>

    function addSpecValue(obj) {
        $(obj).parents("tbody").append($("#addSpecText").val());
    }

    function deleteSpecValue(obj){
        $(obj).parents("tr").remove();
    }
    function addSpec(){
        $("#ss").append($("#addSpecInfo").val());
    }
    function deleteSpec(obj) {
        $(obj).parents("table").remove();
    }



    function submit(){
        //获取我们的规格名
        var  v_specNameArr=[];
        $("input[name='specName']").each(function(){
            v_specNameArr.push(this.value);
        })

        //获取规格排序
        var  v_specSortArr=[];
        $("input[name='specSort']").each(function(){
            v_specSortArr.push(this.value);
        })
        //将规格名还有规格排序转换成字符串
        var v_specNames = v_specNameArr.join(",");
        var v_specSorts = v_specSortArr.join(",");

        //组装值 value="黑色=1,红色=2;8G=1,16G=2,32G=3"
        var v_specValueAll=[];

        $("table").each(function () {
            var v_specValue=[];
            var v_specValueSort=[];
            // v_specValue=黑色，红色
            $(this).find("input[name='specValue']").each(function () {
                v_specValue.push(this.value);
            })

            //v_specValueSort=1，2
            $(this).find("input[name='specValueSort']").each(function () {
                v_specValueSort.push(this.value);
            })

            var temp="";
            for (var i = 0; i < v_specValue.length; i++) {
                temp+=","+v_specValue[i]+"="+v_specValueSort[i];
            }
            if(temp.length>0){
                temp=temp.substring(1);
            }
            v_specValueAll.push(temp);
        })

        var v_specValueAlls = v_specValueAll.join(";");
        console.log(v_specNames);
        console.log(v_specSorts);
        console.log(v_specValueAlls);

        $.ajax({
            type:"post",
            data:{"specNames":v_specNames,"specSorts":v_specSorts,"specValueAlls":v_specValueAlls},
            url:"/spec/addSpec.jhtml",
            success:function(data){
                if(data.code==200){
                   location.href="/spec/toList.jhtml";
                }else {
                    alert(data.message);
                }
            }
        })

    }

</script>
</body>
</html>
