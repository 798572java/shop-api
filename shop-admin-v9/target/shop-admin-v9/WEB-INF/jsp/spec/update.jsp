<%--
  Created by IntelliJ IDEA.
  User: xuejinsheng
  Date: 2021/3/9
  Time: 19:45
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
        <div class="col-md-12" id="specDiv">
            <button type="button" class="btn btn-success" onclick="submit();"><span class="glyphicon glyphicon-ok
"></span>提交</button>
            <table id="specTable" class="table table-striped table-bordered"  style="width:100%">
                <tr>
                    <td>规格名称</td>
                    <td><input type="text" class="form-control" name="specName" id="specName" placeholder="请输入规格名"></td>
                    <td>规格排序</td>
                    <td><input type="text" class="form-control" name="specSort"  id="specSort" ></td>
                    <td><button type="button" class="btn btn-primary" onclick="addSpecValue();"><span class="glyphicon glyphicon-plus"></span>增加规格值</button>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>


<textarea id="addDviSpec" style="display: none">
    <tr>
        <td>规格名称</td>
        <td><input type="text" class="form-control" name="specValue" placeholder="请输入规格值名"></td>
        <td>规格排序</td>
        <td><input type="text" class="form-control"  name="specValueSort" placeholder="请输入规格值排序"></td>
        <td><button type="button" class="btn btn-danger" onclick="deleteSpecValue(this);"><span class="glyphicon glyphicon-trash"></span>删除规格值</button>
        </td>
    </tr>
</textarea>

<textarea id="toAddDivTable" style="display: none">
    <table class="table table-striped table-bordered"  style="width:100%">
                <tr>
                    <td>
                    <button type="button" class="btn btn-danger" onclick="deleteSpace(this)"><span class="glyphicon glyphicon-trash"></span>删除规格</button>
                    </td>
                </tr>
                <tr>
                    <td>规格名称</td>
                    <td><input type="text" class="form-control" name="specName" placeholder="请输入规格名"></td>
                    <td>规格排序</td>
                    <td><input type="text" class="form-control"  name="specSort" ></td>
                    <td><button type="button" class="btn btn-primary" onclick="addSpecValue(this);"><span class="glyphicon glyphicon-plus"></span>增加规格值</button>
                    </td>
                </tr>
    </table>
</textarea>

<jsp:include page="/common/script.jsp"></jsp:include>
<script>

    var v_id=${param.id};

    function addSpecValue() {
        $("#specTable tbody").append($("#addDviSpec").val());
    }
    function deleteSpecValue(obj){
        $(obj).parents("tr").remove();
    }


$(function () {
    inintUpdate();
})


    function inintUpdate() {
        $.ajax({
            type:"post",
            url:"/spec/selectById.jhtml?id="+v_id,
            success:function (result) {
                console.log(result);
                //回显规格数据
                var v_spec = result.data.spec;
                $("#specName").val(v_spec.specName);
                $("#specSort").val(v_spec.sort);
                //回显规格值数据
                var v_specValueList = result.data.specValueList;
                var v_temp="";
                for (let specValue of v_specValueList) {
                        v_temp+='<tr>\n' +
                            '        <td>规格名称</td>\n' +
                            '        <td><input type="text" class="form-control" value="'+specValue.specValue+'" name="specValue" placeholder="请输入规格值名"></td>\n' +
                            '        <td>规格排序</td>\n' +
                            '        <td><input type="text" class="form-control" value="'+specValue.valueSort+'"  name="specValueSort" placeholder="请输入规格值排序"></td>\n' +
                            '        <td><button type="button" class="btn btn-danger" onclick="deleteSpecValue(this);"><span class="glyphicon glyphicon-trash"></span>删除规格值</button>\n' +
                            '        </td>\n' +
                            '    </tr>';
                }
                $("#specTable tbody").append(v_temp);
            }
        })
    }



    function submit() {
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
        $.ajax({
            type:"post",
            data:{"id":v_id,"specNames":v_specNameArr[0],"specSorts":v_specSortArr[0],"specValueAlls":v_specValueAll[0]},
            url:"/spec/updateSpec.jhtml",
            success:function(data){
                if(data.code==200){
                    location.href="/spec/toList.jhtml";
                }else {
                    alert(data.message);
                }
            }
        })
    }
    //    function inintUpdate() {
    //
    //     $.ajax({
    //         type:"post",
    //         url:"/spec/selectById.jhtml?id="+v_id,
    //         success:function (result) {
    //             console.log(result);
    //             //回显规格数据
    //             var v_spec = result.data.spec;
    //             $("#specName").val(v_spec.specName);
    //             $("#specSort").val(v_spec.sort);
    //             //回显规格值数据
    //             var v_specValueList = result.data.specValueList;
    //             for (var i = 0; i <v_specValueList.length ; i++) {
    //                 //循环有几条数据就循环几次
    //                 $("#specTable tbody").append($("#addDviSpec").val());
    //             }
    //             var arr1 = document.getElementsByName("specValue");
    //             for (var i = 0; i <arr1.length ; i++) {
    //                 $(arr1[i]).val(v_specValueList[i].specValue)
    //             }
    //             var arr2 = document.getElementsByName("specValueSort");
    //             for (var i = 0; i <arr2.length ; i++) {
    //                 $(arr2[i]).val(v_specValueList[i].valueSort)
    //             }
    //
    //         }
    //     })
    //
    // }





</script>

</body>
</html>
