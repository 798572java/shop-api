<%--
  Created by IntelliJ IDEA.
  User: xuejinsheng
  Date: 2021/2/28
  Time: 19:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>




<html>
<head>
    <meta charset="utf-8">
    <jsp:include page="/common/head.jsp"></jsp:include>

    <jsp:include page="/common/nav.jsp"></jsp:include>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <form class="form-horizontal" action="/brand/addBrand.jhtml" method="post">
                <div class="form-group">
                    <label  class="col-sm-2 control-label">品牌名:</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="brandName" name="brandName" placeholder="请输入品牌名">
                    </div>
                </div>
                <div class="form-group" style="height: 400px">
                    <label  class="col-sm-2 control-label">logo:</label>
                    <div class="col-sm-4" style="height: 300px">
                        <input type="file" class="form-control" id="logoFile" name="image">
                        <input type="hidden" id="logo" name="logo">
                    </div>
                </div>
                <div class="form-group">
                    <label  class="col-sm-2 control-label">成立年份:</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="createYear" name="createYear" placeholder="请输入年份">
                    </div>
                </div>
                <div style="text-align: center;">
                    <button type="submit" class="btn btn-primary">提交</button>
                    <button type="reset" class="btn btn-danger">重置</button>
                </div>
            </form>
        </div>
    </div>
</div>
<jsp:include page="/common/script.jsp"></jsp:include>

</body>
<script>
    $(function(){
        inintFileInput();
        initDateTime();
    })


    function inintFileInput(){
        var setting = {
            language: 'zh',
            uploadUrl: "/file/uploadImage.jhtml", // 后台上传文件的url地址
            showUpload : false,
            showRemove : false
        };
        // 渲染组件
        $("#logoFile").fileinput(setting).on("fileuploaded", function (event, r, previewId, index) {
            console.log(r.response.data);
            $("#logo").val(r.response.data);
        });

    }
    function initDateTime(){
        //使用选择器如果说要获取多个 用逗号隔开
        $("#createYear").datetimepicker({
            format:"YYYY",
            locale:"zh-cn",
            showClear:true
        })
    }


</script>
</html>
