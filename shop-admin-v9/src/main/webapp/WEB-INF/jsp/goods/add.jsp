<%--
  Created by IntelliJ IDEA.
  User: xuejinsheng
  Date: 2021/3/21
  Time: 20:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/common/head.jsp"></jsp:include>

    <title>Title</title>
</head>
<body>
<jsp:include page="/common/nav.jsp"></jsp:include>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <form class="form-horizontal" id="spuform">
                <div class="form-group">
                    <label  class="col-sm-2 control-label">商品名:</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="spuName" placeholder="请输入商品名">
                    </div>
                </div>
                <div class="form-group">
                    <label  class="col-sm-2 control-label">库存:</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="stock" placeholder="请输入库存">
                    </div>
                </div>
                <div class="form-group">
                    <label  class="col-sm-2 control-label">价格:</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="price" placeholder="请输入价格">
                    </div>
                </div>
                <div class="form-group" id="cateDiv">
                    <label  class="col-sm-2 control-label" >分类:</label>
                </div>

            </form>
        </div>
        <div style="text-align: center;">
            <button type="button" onclick="submit()" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span>提交</button>
            <button type="reset" class="btn btn-danger"><span class="glyphicon glyphicon-refresh"></span>重置</button>
        </div>
    </div>
</div>


<jsp:include page="/common/script.jsp"></jsp:include>
<script>


    $(function () {
        initCate(0);
    })
    
    
    
    
    function submit() {
        var param={};
        param["spu.spuName"]=$("#spuName").val();
        param["spu.price"]=$("#price").val();
        param["spu.stock"]=$("#stock").val();
        param["spu.brandName"]=$("#brandSelect").find("option:selected").data("brandname");
        param["spu.brandId"]=$("#brandSelect").val();
        param["spu.cate1"]=$($("select[name='cateSelect']")[0]).val();
        param["spu.cate2"]=$($("select[name='cateSelect']")[1]).val();
        param["spu.cate3"]=$($("select[name='cateSelect']")[2]).val();
        param["spu.cateName"]=$($("select[name='cateSelect']")[0]).find("option:selected").data("cate-name")+"/"+
                              $($("select[name='cateSelect']")[1]).find("option:selected").data("cate-name")+"/"+
                              $($("select[name='cateSelect']")[2]).find("option:selected").data("cate-name");


        //获取属性 “16:cpu型号, 18:晓龙879; 20:屏幕大小,40:20尺寸”
        var v_attrArr=[];
        $("select[name='attrSelect']").each(function () {
            v_attrArr.push($(this).data("attr-id")+":"+$(this).data("attr-name")+","+$(this).val()+":"+$(this).find("option:selected").data("attr-value"));
        })
        param["spu.attrInfo"]=v_attrArr.join(";");
        //获取分类  "20:颜色，3:红色,4:蓝色 ；19:内存，17：28G，19：68G";
            var specStr=[];
            for (let spec of v_spuSpecVos){
               var id= spec.id;
              var specName= spec.specName;
              var specValue=[];
            $("input[name='specValue_"+spec.id+"']:checkbox:checked").each(function () {
                specValue.push($(this).val()+":"+$(this).data("specvalue"));
            })
                if(specValue.length>0){
                    specStr.push(id+":"+specName+","+specValue.join(","));
                }
            }
            var v_prices=[];
            $("input[name='price']").each(function () {
                v_prices.push(this.value);
            })

              var v_stocks=[];
            $("input[name='stock']").each(function () {
                v_stocks.push(this.value);
            })

        param.prices=v_prices.join(",");
        param.stocks=v_stocks.join(",");
        param.specInfos=specInfo.join(";");

        var  v_skuImage=[];
        for(let colorId of  v_color){
            var id= colorId.colorId;
            v_skuImage.push(id+"="+$("#logo"+id).val().substring(1));
        }
        param.skuImages=v_skuImage.join(";");
        console.log(param);

             param["spu.specInfo"]=specStr.join(";");
            $.ajax({
                type:"post",
                url:"/spu/addSpu.jhtml",
                data:param,
                success:function (result) {
                    if(result.code==200){
                        location.href="/spu/toList.jhtml";
                    }
                }
            })
    }
    var v_color;
    var specInfo=[];
    function initSkuTable() {


    //首先我们要再删除之前获取到价格还有库存的值信息
        var v_price=[];
        $("input[name='price']").each(function () {
            v_price.push(this.value);
        })


        var v_stock=[];
        $("input[name='stock']").each(function () {
            v_stock.push(this.value);
        })

        //然后要把数据存储成json格式的对象
        //({"42:黑色,12:8G,51:骁龙654","价格,库存"})
        var v_tem={};
        var i=0;
        for(let spec of specInfo){
            v_tem[spec]=v_price[i]+","+v_stock[i];
            i++;
        }
        console.log("=====================================")
        console.log(v_tem);
        console.log("=====================================")

        $("#skuDiv").remove();
        var v_html=' <div class="form-group" id="skuDiv">\n' +
            '                    <label  class="col-sm-2 control-label">SKU:</label>' +
            ' <div class="col-md-10">' +
            '                    <table id="skuTable" class="table table-striped table-bordered"  style="width:80%">\n' +
            '</table>' +
            '</div>' +
            '</div>';
        $("#spuform").after(v_html);

        var v_head=[];
        var v_body=[];
        v_color=[];
        var v_delete=[];
        var count=0;
        for (let spec of v_spuSpecVos){
            var id= spec.id;
            var specName= spec.specName;
            var specValue=[];
            $("input[name='specValue_"+spec.id+"']:checkbox:checked").each(function () {
                specValue.push($(this).val()+":"+$(this).data("specvalue"));
                if(count==0){
                    v_color.push({"colorId":$(this).val(),"colorName":$(this).data("specvalue")})
                }
            })
            $("input[name='specValue_"+spec.id+"']").each(function () {
                if(count==0 && !this.checked){
                    v_delete.push($(this).val());
                }
            });
            if(specValue.length>0){
                v_head.push(specName);
                v_body.push(specValue);
            }
            count++;
        }
      //创建表头
        var v_headTr='<tr>';
        for(let head of v_head){
            v_headTr+='<td>'+head+'</td>';
        }
        v_headTr+='<td>价格</td><td>库存</td></tr>';
    $("#skuTable").append(v_headTr);

    //创建表身体,通过递归转换为一维数组
      specInfo= aa(v_body);
     console.log(specInfo);
        for(let spuBody of specInfo){
            var v_botr='<tr>';
           var bodyArr= spuBody.split(",");
           for(let tem of bodyArr){
               v_botr+='<td>'+tem.split(":")[1]+'</td>';
           }
            var price="";
            var stock="";
            if(v_tem[spuBody]){
                price= v_tem[spuBody].split(",")[0];
                stock= v_tem[spuBody].split(",")[1];
            }
            v_botr+='<td><input type="text" class="form-control" value="'+price+'" name="price" placeholder="请输入价格"></td>';
            v_botr+='<td><input type="text" class="form-control" value="'+stock+'" name="stock" placeholder="请输入库存"></td>';
            v_botr+='</tr>';
            $("#skuTable").append(v_botr);
        }

        for(let color of v_color){

            initimage(color);
        }

        for(let color of v_delete){

            $("#colorDiv"+color).remove();
        }

    }


    function aa(arr){
        if(arr.length>1){
            var result=[];
            var onearr=	arr[0];
            arr.splice(0,1);
            let nextarr = aa(arr);
            for (var i = 0; i < onearr.length; i++) {
                for (var  j = 0; j < nextarr.length; j++) {
                    result.push(onearr[i]+","+nextarr[j]);
                }
            }
            return result;
        }else{
            return arr[0];
        }
    }





    function initCate(id,obj) {
        if(obj!=null){
            $(obj).parent().nextAll().remove();
        }
        $.ajax({
            type:"get",
            url:"/cate/findById.jhtml?id="+id,
            success:function (result) {
                if(result.code==200){
                    var cateList =result.data;
                    console.log(cateList);
                    if(cateList.length==0){
                        $(obj).parent().parent().nextAll().remove();
                        let typeId = $(obj).find("option:selected").data("typeid");
                        initAll(typeId);
                        return ;
                    }
                    var v_html='<div class="col-sm-2" ><select class="form-control" name="cateSelect" onchange="initCate(this.value, this)"><option value="-1">--请选择--</option>';
                    for (let cate of cateList) {
                        v_html+='<option value="'+cate.id+'" data-typeid="'+cate.typeId+'" data-cate-name="'+cate.cateName+'">'+cate.cateName+'</option>';
                    }
                    v_html+='</select></div>';
                    $("#cateDiv").append(v_html);
                }
            }
        })
    }

    var v_spuSpecVos;
            function initAll(typeId) {
                $.ajax({
                    type:"post",
                    url:"/spu/findspuInfo.jhtml?typeId="+typeId,
                    success:function (result) {
                        if(result.code==200){
                            console.log(result);
                            var v_brandList=result.data.brandList;
                         var v_spuAttrVoList= result.data.spuAttrVoList;
                           v_spuSpecVos=  result.data.spuSpecVos;

                            $("#brandDiv").remove();
                            $("#attrfrom").remove();
                            $("#spuDiv").remove();
                            $("div[name='colorimage']").remove();
                            spuBrand(v_brandList);
                            spuAttr(v_spuAttrVoList);
                            spuSpec(v_spuSpecVos);
                        }
                    }
                })
            }



            function spuSpec(v_spuSpecVos) {
                var v_html=' <div class="form-group" id="spuDiv">\n' +
                    '                    <label  class="col-sm-2 control-label">规格:</label>' +
                    ' <div class="col-md-10">' +
                    '                    <table id="specTable" class="table table-striped table-bordered"  style="width:80%">\n' +
                    '</table>' +
                    '</div>' +
                    '</div>';
                $("#spuform").append(v_html);

                    for (let x of v_spuSpecVos) {
                        var v_spectr='<tr><td>'+x.specName+'</td><td>';
                      var v_specValueList= x.specValueList;
                        for (let s of v_specValueList) {
                            v_spectr+='&nbsp;&nbsp;&nbsp;<input type="checkbox" onclick="initSkuTable()" name="specValue_'+x.id+'" data-specvalue="'+s.specValue+'" value="'+s.id+'">'+s.specValue+'';
                        }
                        v_spectr+='</td></tr>';
                        $("#specTable").append(v_spectr);
                    }


            }

    function initimage(color) {
        var colorName= color.colorName;
        var colorId=color.colorId;
        if($("#colorDiv"+colorId).size()==0){
            var v_html=' <div class="form-group" name="colorimage" id="colorDiv'+colorId+'" >\n' +
                '                    <label  class="col-sm-2 control-label">'+colorName+':</label>\n' +
                '                    <div class="col-sm-10" >\n' +
                '                        <input type="file" class="form-control" id="imageFile'+colorId+'" name="image" multiple="multiple" >\n' +
                '                        <input type="" id="logo'+colorId+'" name="logo">\n' +
                '                    </div>\n' +
                '                </div>';
            $("#skuDiv").after(v_html);

            var setting = {
                language: 'zh',
                uploadUrl: "/file/uploadBatchImage.jhtml", // 后台上传文件的url地址
                uploadAsync: false, //设置上传同步异步 此为同步
                dropZoneEnabled: false,//是否显示拖拽区域
                maxFileCount: 10, //表示允许同时上传的最大文件个数
            };
            // 渲染组件
            $("#imageFile"+colorId).fileinput(setting).on("filebatchuploadsuccess", function (event, r, previewId, index) {
                console.log(r);
                $("#logo"+colorId).val(r.response.data);
            });
        }
    }



    function spuAttr(v_spuAttrVoList) {
                var v_html=' <div class="form-group" id="attrfrom">\n' +
                    '                    <label  class="col-sm-2 control-label" >属性:</label>\n' +
                    '\n' +
                    '\n' +
                    '\n' +
                    '                </div>';

                $("#spuform").append(v_html);
                var count=1;
                for (let x of v_spuAttrVoList) {
                    var s = count>1 && count%3==1?"col-md-offset-2":"";
                    var v_attrFrom='<div class="col-md-3 '+s+'" style="margin-top: 10px">\n<div class="input-group"><span class="input-group-addon">'+x.attrName+'</span>' +
                        '<select class="form-control" name="attrSelect" data-attr-id="'+x.id+'" data-attr-name="'+x.attrName+'"><option value="-1">--请选择--</option>';
                       var  attrValueList=  x.attrValueList;
                    for(let attrValue of attrValueList){
                        v_attrFrom+='<option value="'+attrValue.id+'" data-attr-value="'+attrValue.attrValue+'">'+attrValue.attrValue+'</option>';
                        }
                    v_attrFrom+='</select></div></div>';
                    $("#attrfrom").append(v_attrFrom);
                    count++;
                }
            }



    function spuBrand(v_brandList) {
        var v_html='<div class="form-group" id="brandDiv">\n' +
            '                    <label  class="col-sm-2 control-label" >品牌:</label>';
        v_html+='<div class="col-sm-2"><select id="brandSelect" class="form-control"><option value="-1">--请选择--</option>';
        for (let brand of v_brandList) {
            v_html+='<option value="'+brand.id+'" data-brandname="'+brand.brandName+'">'+brand.brandName+'</option>';
        }
        v_html+='</select></div></div>';

        $("#spuform").append(v_html);
    }







</script>




</body>

</html>
