<%--
  Created by IntelliJ IDEA.
  User: xuejinsheng
  Date: 2021/3/27
  Time: 15:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>编辑spu</title>
    <jsp:include page="/common/head.jsp"></jsp:include>
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
                    <div class="col-sm-4" id="cateDivName">

                    </div>


                </div>

            </form>
        </div>

    </div>
    <div style="text-align: center;">
        <button type="button" onclick="submit()" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span>提交</button>
        <button type="reset" class="btn btn-danger"><span class="glyphicon glyphicon-refresh"></span>重置</button>
    </div>
</div>








<jsp:include page="/common/script.jsp"></jsp:include>
<script>


    var t_id='${param.id}';

    $(function () {
        initEdit();
    })


    function submit() {
        var param={};
        param["spu.id"]=t_id;
        param["spu.spuName"]=$("#spuName").val();
        param["spu.price"]=$("#price").val();
        param["spu.stock"]=$("#stock").val();
        param["spu.brandName"]=$("#brandSelect").find("option:selected").data("brand-name");
        param["spu.brandId"]=$("#brandSelect").val();
        if(flag==0){
            param["spu.cate1"]=$($("select[name='cateSelect']")[0]).val();
            param["spu.cate2"]=$($("select[name='cateSelect']")[1]).val();
            param["spu.cate3"]=$($("select[name='cateSelect']")[2]).val();

            param["spu.cateName"]=$($("select[name='cateSelect']")[0]).find("option:selected").data("cate-name")+"/"+
                $($("select[name='cateSelect']")[1]).find("option:selected").data("cate-name")+"/"+
                $($("select[name='cateSelect']")[2]).find("option:selected").data("cate-name");
        }else if(flag==1) {
            param["spu.cateName"]=v_spu.cateName;
            param["spu.cate1"]=v_spu.cate1;
            param["spu.cate2"]=v_spu.cate2;
            param["spu.cate3"]=v_spu.cate3;
        }

        //获取属性 “16:cpu型号, 18:晓龙879; 20:屏幕大小,40:20尺寸”
        var v_attrArr=[];
        $("select[name='attrSelect']").each(function () {
            v_attrArr.push($(this).data("attrid")+":"+$(this).data("attrname")+","+$(this).val()+":"+$(this).find("option:selected").data("attr-value"));
        })
        param["spu.attrInfo"]=v_attrArr.join(";");
        //获取分类  "20:颜色，3:红色,4:蓝色 ；19:内存，17：28G，19：68G";
        var specStr=[];
        for (let spec of v_spuSpecVos){
            var id= spec.id;
            var specName= spec.specName;
            var specValue=[];
            $("input[name='specSelect_"+spec.id+"']:checkbox:checked").each(function () {
                specValue.push($(this).val()+":"+$(this).data("specvalue"));
            })
            if(specValue.length>0){
                specStr.push(id+":"+specName+","+specValue.join(","));
            }
        }
        var v_prices=[];
        $("input[name='Skusprice']").each(function () {
            v_prices.push(this.value);
        })

        var v_stocks=[];
        $("input[name='Skustock']").each(function () {
            v_stocks.push(this.value);
        })

        param.prices=v_prices.join(",");
        param.stocks=v_stocks.join(",");
        var  v_skuImage=[];

        for(let colorId of  v_color){
            var id= colorId.colorId;
            v_skuImage.push(id+"="+$("#logo"+id).val().substring(1));
        }
        param.skuImages=v_skuImage.join(";");
        console.log(param);

        param["spu.specInfo"]=specStr.join(";");
        var temp=[];
        for(let specinfo of v_Skubody){
           // ["16:黑色，3:8g,4:蓝色 ；16:白色，3:8g,4:蓝色";

            temp.push(specInfo(specinfo));
        }
        param.specInfos=temp.join(";");


        console.log(param);

        $.ajax({
            type:"post",
            url:"/spu/editSpu.jhtml",
            data:param,
            success:function (result) {
                if(result.code==200){
                    location.href="/spu/toList.jhtml";
                }
            }
        })
    }

        function specInfo(specinfo) {
            for(let skuDB of v_skuList){
               // console.log("++++++++")
                //console.log()
                //console.log("===========================")
            if(specinfo==skuDB.specInfo){
                return skuDB.id+"_"+specinfo;
             }
            }
                return "-1"+"_"+specinfo;
        }


    var v_spu;
    var v_skuList;
    var v_skuImage;
    function initEdit() {
        $.ajax({
            url:"/spu/selectSpu.jhtml?spuId="+t_id,
            type:"post",
            success:function (result) {
                if(result.code==200){
                    console.log(result);
                    //类型id
                    var typeId=result.data.typeId;
                    //spu需要回填的数据
                    v_spu=result.data.spu;
                    //sku的数据聚合
                    v_skuList=result.data.skuList;
                    //图片的数据
                    v_skuImage= result.data.skuImageList;

                    console.log("==================")
                    console.log(v_skuImage);
                    console.log("==================")
                    $("#spuName").val(v_spu.spuName);
                    $("#stock").val(v_spu.stock);
                    $("#price").val(v_spu.price);
                    //初始化界面
                    initAll(typeId,1);

                }
            }
        })
    }


    //点击分类的编辑触发
    var flag;
    function update(id) {
        $("#cateDivName").remove();
        initCate(id);
        flag=0;
    }


    //初始化
    var v_spuSpecVos;
    function initAll(typeId,flage) {
        $.ajax({
            type:"post",
            url:"/spu/findspuInfo.jhtml?typeId="+typeId,
            success:function (result) {
                if(result.code==200){
                    console.log(result);
                    var v_brandList=result.data.brandList;
                    var v_spuAttrVoList= result.data.spuAttrVoList;
                    v_spuSpecVos=  result.data.spuSpecVos;

                    //先删除
                    $("#brandDiv").remove();
                    $("#specDiv").remove();
                    $("#skuDiv").remove();
                    $("div[name='colorimage']").remove();
                    spuBrand(v_brandList);
                    spuAttr(v_spuAttrVoList);
                    spuSpec(v_spuSpecVos);

                    // 回填
                    if(flage ==1){
                        huitian();
                    }

                }
            }
        })
    }

    function huitian() {
        $("#cateDivName").append(v_spu.cateName+'<button class="btn btn-warning" type="button"  onclick="update(0)">编辑</button>');
        //回填品牌
        $("#brandSelect").val(v_spu.brandId);
        //回填属性
        //69:大容量2,140:16 ; 70:高性能,142:90; 71:大屏幕,146:46cm;72:大电池,149:1000
        var v_attrInfo=v_spu.attrInfo;
       var attrArr= v_attrInfo.split(";");
        var count=0;
            $("select[name='attrSelect']").each(function () {
                    $(this).val(attrArr[count].split(",")[1].split(":")[0]);
                    count++;
            })
        flag=1;
        //规格回填
        //6:颜色,42:黑色,45:红色 ;  7:内存,12:8G,13:32G;23:CPU,51:骁龙654
           var v_specInfo= v_spu.specInfo.split(";");
            var v_headArr=[];
            for(let spec of v_specInfo){
               var temparr= spec.split(",");
                var specId= temparr[0].split(":")[0];
                v_headArr.push(temparr[0].split(":")[1]);
                var specIdArr=[];
                for (let i = 1; i < temparr.length; i++) {
                    specIdArr.push(temparr[i].split(":")[0]);
                }
                    $("input[name='specSelect_"+specId+"']:checkbox").each(function () {
                       for(let a of specIdArr){
                           if(this.value==a){
                               this.checked=true;
                               break;
                           }
                       }
                    })
            }
            //回填Skp表

        var v_html=' <div class="form-group" id="skuDiv">\n' +
            '                    <label  class="col-sm-2 control-label">SKU表格:</label>' +
            ' <div class="col-md-10">' +
            '                    <table id="skuTable" class="table table-striped table-bordered"  style="width:100%">\n' +
            '                    </table></div></div>';
        $("#specDiv").after(v_html);
                //回填sku
                //回填表格头

        var headSku='<tr>';
        for (let skuheadTd  of v_headArr) {
            headSku+='<td>'+skuheadTd+'</td>';
        }
        headSku+='<td>价格</td><td>库存</td></tr>';

        $("#skuTable").append(headSku);



                //回填表格内容
         var count=0;
        for(let skubody of v_skuList){

            v_Skubody.push(skubody.specInfo);
                    var v_body='<tr>';
                    var tempArr=skubody.specInfo.split(",");
                    for (let tem of tempArr){
                        v_body+='<td>'+tem.split(":")[1]+'</td>';
                    }

                    v_body+='<td><input type="text" value="'+skubody.price+'" class="form-control" data-skuinfo="'+skubody+'" name="Skusprice" placeholder="请输入价格"></td>';
                    v_body+='<td><input type="text" value="'+skubody.stock+'" class="form-control" data-skuinfo="'+skubody+'" name="Skustock" placeholder="请输入库存"></td>';
                    v_body+='</tr>';
                    $("#skuTable").append(v_body);
                    count++;
                }

                //回填image
                for(let skuImage of v_skuImage){
                    initimage({"colorId":skuImage.colorId,"colorName":skuImage.colorName},skuImage.skuImageList);
                    v_color.push({"colorId":skuImage.colorId,"colorName":skuImage.colorName})
                   }
             }


    //生成分类下拉框
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
                    var v_html='<div class="col-sm-2" ><select name="cateSelect" class="form-control" onchange="initCate(this.value, this)"><option value="-1">--请选择--</option>';
                    for (let cate of cateList) {
                        v_html+='<option value="'+cate.id+'" data-typeid="'+cate.typeId+'" data-catename="'+cate.cateName+'">'+cate.cateName+'</option>';
                    }
                    v_html+='</select></div>';
                    $("#cateDiv").append(v_html);
                }
            }
        })
    }


    //生成属性下拉框
    function spuAttr(v_spuAttrVoList){
        var v_html='<div class="form-group" id="attrSelect">\n' +
            '                    <label  class="col-sm-2 control-label" >属性:</label>\n' +
            '\n' +
            '\n' +
            '\n' +
            '</div>';
        $("#spuform").append(v_html);
        var count=1;
        for(let attr of v_spuAttrVoList){
            var c= count>1 && count%3==1?"col-md-offset-2":"";
            var v_attrSelect='<div class="col-md-3 '+c+'" style="margin-top: 20px"><div class="input-group">' +
                '<span class="input-group-addon">'+attr.attrName+'</span>' +
                '<select class="form-control" name="attrSelect" data-attrname="'+attr.attrName+'" data-attrid="'+attr.id+'" ><option value="-1">--请选择--</option>';
            var attrValueList= attr.attrValueList;
            for(let a of attrValueList){
                v_attrSelect+='<option value="'+a.id+'"  data-attr-value="'+a.attrValue+'">'+a.attrValue+'</option>';
            }
            $("#attrSelect").append(v_attrSelect);
            count++;
        }
    }

    //生成规格表格
    function spuSpec(v_spuSpecVos) {
        var v_html=' <div class="form-group" id="specDiv">\n' +
            '                    <label  class="col-sm-2 control-label">规格:</label>' +
            ' <div class="col-md-10">' +
            '                    <table id="brandTable" class="table table-striped table-bordered"  style="width:50%">\n' +
            '                        <tbody>';
        for (let x of v_spuSpecVos) {
            v_html+='<tr><td>'+x.specName+'</td><td>';
            var v_specValueList= x.specValueList;
            for (let s of v_specValueList) {
                v_html+='&nbsp;&nbsp;&nbsp;<input  onclick="initSkuTable()"  type="checkbox" name="specSelect_'+x.id+'" value="'+s.id+'"  data-specvalue="'+s.specValue+'">'+s.specValue+'';
            }
            v_html+='</td></tr>';
        }
        v_html+='</tbody></table></div></div>';
        $("#spuform").append(v_html);
    }

    //生成sku表格
    var flage;
    var v_color=[];
    var v_Skubody=[];
    function initSkuTable() {
        flage=0;
        //首先我们要再删除之前获取到价格还有库存的值信息
        var v_price=[];
        $("input[name='Skusprice']").each(function () {
            v_price.push(this.value);
        })

        var v_stock=[];
        $("input[name='Skustock']").each(function () {
            v_stock.push(this.value);
        })

        //然后要把数据存储成json格式的对象
        //({"42:黑色,12:8G,51:骁龙654","价格,库存"})
        var v_tem={};
        var i=0;
        //[["42:黑色,12:8G,51:骁龙654"],["41:白色,12:8G,51:骁龙654"]]
        for(let spec of v_Skubody){
            v_tem[spec]=v_price[i]+","+v_stock[i];
            i++;
        }
        $("#skuDiv").remove();

        var v_html=' <div class="form-group" id="skuDiv">\n' +
            '                    <label  class="col-sm-2 control-label">SKU表格:</label>' +
            ' <div class="col-md-10">' +
            '                    <table id="skuTable" class="table table-striped table-bordered"  style="width:100%">\n' +
            '                    </table></div></div>';
        $("#specDiv").after(v_html);
        //表头数据
        var  head=[];
        //内容数据
        var body=[];
        var count=0;
        v_color=[];
        //要删除的
        var v_delete=[];
        for(let a of v_spuSpecVos){
            var id= a.id;
            var specName= a.specName;
            var specValue=[];
            //获取被选中
            $("input[name='specSelect_"+a.id+"']:checkbox:checked").each(function () {
                specValue.push($(this).val()+":"+$(this).data("specvalue"));
                if(count==0){
                    v_color.push({"colorId":$(this).val(),"colorName":$(this).data("specvalue")})
                }
            });
            $("input[name='specSelect_"+a.id+"']").each(function () {
                if(count==0 && !this.checked){
                    v_delete.push($(this).val());
                }
            });
            if(specValue.length>0){
                head.push(specName);
                body.push(specValue);
            }
            count++;
        }
        var headSku='<tr>';
        for (let skuheadTd  of head) {
            headSku+='<td>'+skuheadTd+'</td>';
        }
        headSku+='<td>价格</td><td>库存</td></tr>';

        $("#skuTable").append(headSku);
        v_Skubody = aa(body);
        console.log(v_Skubody);


        for(let skubody of v_Skubody){
            var v_body='<tr>';
            var tempArr=skubody.split(",");
            for (let tem of tempArr){
                v_body+='<td>'+tem.split(":")[1]+'</td>';
            }

            var price="";
            var stock="";
            if(v_tem[skubody]){
                price= v_tem[skubody].split(",")[0];
                stock= v_tem[skubody].split(",")[1];
            }



            v_body+='<td><input type="text" class="form-control" value="'+price+'" name="Skusprice" placeholder="请输入价格"></td>';
            v_body+='<td><input type="text" class="form-control" value="'+stock+'" name="Skustock" placeholder="请输入库存"></td>';
            v_body+='</tr>';
            $("#skuTable").append(v_body);
        }
        for(let color of v_color){
            initimage(color);
        }
        for(let color of v_delete){
            $("#colorDiv"+color).remove();
        }
    }

    function spuBrand(v_brandList) {
        var v_html='<div class="form-group" id="brandDiv">\n' +
            '                    <label  class="col-sm-2 control-label" >品牌:</label>';
        v_html+='<div class="col-sm-2"><select id="brandSelect" class="form-control"><option value="-1">--请选择--</option>';
        for (let brand of v_brandList) {
            v_html+='<option value="'+brand.id+'" data-brand-name="'+brand.brandName+'">'+brand.brandName+'</option>';
        }
        v_html+='</select></div></div>';
        $("#spuform").append(v_html);
    }



    //点击颜色复选框触发文件域
    function initimage(color,skuImage) {
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

            var deleteArr=[];
            if(skuImage){
                var imageArr=[];
                for(let skuimage of skuImage){
                    imageArr.push(skuimage.image);
                    deleteArr.push({"url":"/spu/deleteSpuImage.jhtml","key":skuimage.id});
                }
                $("#logo"+colorId).val(","+imageArr.join(","));
            }
            var setting = {
                language: 'zh',
                uploadUrl: "/file/uploadBatchImage.jhtml", // 后台上传文件的url地址
                uploadAsync: false, //设置上传同步异步 此为同步
                dropZoneEnabled: false,//是否显示拖拽区域
                maxFileCount: 10, //表示允许同时上传的最大文件个数
               initialPreview:imageArr,
                initialPreviewAsData: true,
                overwriteInitial: false,
                //要删除的
                initialPreviewConfig:deleteArr,
            };
            // 渲染组件
            let fileinput = $("#imageFile"+colorId).fileinput(setting);
            fileinput.on("filebatchuploadsuccess", function (event, r, previewId, index) {
                console.log(r);
                $("#logo"+colorId).val($("#logo"+colorId).val()+r.response.data);

            });
            fileinput.on('filepredelete', function(event, key, jqXHR, data) {
                if(!confirm("确定删除原文件？删除后不可恢复")){
                    return false;
                }
            });
            fileinput.on('filedeleted', function(event, key,jqXHR, data) {
                console.log(jqXHR);
                console.log(data);
               var image= jqXHR.responseJSON;
               console.log(image.data);
                $("#logo"+colorId).val($("#logo"+colorId).val().replace(","+image.data,""));
            });
        }
    }



//递归，二维数组 转换一维数组
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

</script>
</body>
</html>
