<%--
  Created by IntelliJ IDEA.
  User: xuejinsheng
  Date: 2021/3/24
  Time: 15:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style> td.details-control {
        background: url('/js/DataTables/DataTables-1.10.20/images/details_open.png') no-repeat center center;
        cursor: pointer;
    }
    tr.shown td.details-control {
        background: url('/js/DataTables/DataTables-1.10.20/images/details_close.png') no-repeat center center;
    }
    </style>
    <title>sup展示页面</title>
    <jsp:include page="/common/head.jsp"></jsp:include>

</head>

<body>
<jsp:include page="/common/nav.jsp"></jsp:include>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">条件查询展示</h3>
                </div>
                <div class="panel-body">
                    <form class="form-horizontal" >
                        <div class="form-group">
                            <label  class="col-sm-2 control-label">商品名:</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="spuName"  placeholder="请输入商品名">
                            </div>

                            <label class="col-sm-2 control-label">价格区间:</label>
                            <div class="col-md-4">
                                <div class="input-group">
                                    <input type="text" class="form-control"  id="priceMin">
                                    <span class="input-group-addon">--</span>
                                    <input type="text" class="form-control"  id="priceMax">
                                </div>
                            </div>


                        </div>
                        <div class="form-group">
                            <label  class="col-sm-2 control-label">品牌名:</label>
                            <div class="col-sm-4" id="brandDiv">

                            </div>
                            <label class="col-sm-2 control-label">库存区间:</label>
                            <div class="col-md-4">
                                <div class="input-group">
                                    <input type="text" class="form-control"  id="stockMin">
                                    <span class="input-group-addon">--</span>
                                    <input type="text" class="form-control"  id="stockMax">
                                </div>
                            </div>
                        </div>

                        <div class="form-group"id="cateDiv">
                            <label class="col-sm-2 control-label">分类:</label>

                        </div>


                        <div style="text-align: center;">
                            <button type="button" onclick="search()" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span>查询</button>
                            <button type="button" onclick="initcearch()" class="btn btn-danger"><span class="glyphicon glyphicon-refresh"></span>重置</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <button type="button" class="btn btn-primary" onclick="toAdd();"><span class="glyphicon glyphicon-plus"></span>增加</button>
    <button type="button" class="btn btn-danger" onclick="deleteBatch();"><span class="glyphicon glyphicon-trash"></span>批量删除</button>
    <button type="button" class="btn btn-danger" onclick="goodsRedis();"><span class="glyphicon glyphicon-baby-formula"></span>商品缓存立即生效</button>
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">数据列表展示</h3>
                </div>
                <div class="panel-body">
                    <table id="spuTable" class="table table-striped table-bordered" style="width:100%">
                        <thead>
                        <tr>
                            <th></th>
                            <th>选项</th>
                            <th>商品名</th>
                            <th>价格</th>
                            <th>库存</th>
                            <th>品牌名</th>
                            <th>分类名</th>
                            <th>上架状态</th>
                            <th>是否新品</th>
                            <th>推荐状态</th>
                            <th>选择</th>
                        </tr>
                        </thead>
                        <tfoot>
                        <tr>
                            <th></th>
                            <th>选项</th>
                            <th>商品名</th>
                            <th>价格</th>
                            <th>库存</th>
                            <th>品牌名</th>
                            <th>分类名</th>
                            <th>上架状态</th>
                            <th>是否新品</th>
                            <th>推荐状态</th>
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
    initSpuTable();
    initCate(0);
    initBrand();
})

function goodsRedis() {
    $.ajax({
        type:"get",
        url:"/spu/goodsRedis.jhtml",
        success:function (res) {
            if(res.code==200){
                alert("商品缓存开启");
            }
        }
    })
}


function initCate(id,obj) {
    if(obj!=null){
        $(obj).parent().nextAll().remove();
    }
    if(id>0){
        console.log("========"+id);
        $.ajax({
            type:"post",
            url:"/brand/findBrandAll.jhtml?cateId="+id,
            success:function (res) {
                if(res.code==200){
                    var v_brandList= res.data;
                    var v_html='<select id="brandSelect" multiple class="form-control"><option value="-1">--请选择--</option>';
                    for (let brand of v_brandList) {
                        v_html+='<option value="'+brand.id+'" data-brandname="'+brand.brandName+'">'+brand.brandName+'</option>';
                    }
                    v_html+='</select>';

                    $("#brandDiv").html(v_html);
                    $('#brandSelect').prop('disabled', false);
                    $('#brandSelect').selectpicker('refresh');
                }
            }
        })
    }

    $.ajax({
        type:"get",
        url:"/cate/findById.jhtml?id="+id,
        success:function (result) {
            if(result.code==200){
                var cateList =result.data;
                console.log(cateList);
                if(cateList.length==0){
                    return ;
                }
                var typeIds=[];
                for(let cate of cateList){
                    typeIds.push(cate.typeId);
                }
                //initbrand(typeIds);
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





function initBrand() {
    $.ajax({
        type:"post",
        url:"/brand/findBrandAll.jhtml",
        success:function (result) {
            if(result.code==200){
               var v_brandList= result.data;
                var v_html='<select id="brandSelect" multiple class="selectpicker" ><option value="-1">--请选择--</option>';
                for (let brand of v_brandList) {
                    v_html+='<option value="'+brand.id+'" data-brandname="'+brand.brandName+'">'+brand.brandName+'</option>';
                }
                v_html+='</select>';

                $("#brandDiv").html(v_html);
                $('#brandSelect').prop('disabled', false);
                $('#brandSelect').selectpicker('refresh');

            }
        }
    })
}

function format ( d ) {
    var v_skuList = d.skuList;
    var html='';
    for(let sku of v_skuList){
         html+= '<table cellpadding="5" cellspacing="20" border="1" style="float:left ;margin-left: 30px;margin-top:20px ">'+
            '<tr>'+
            '<td>Sku图片:</td>'+
            '<td><img src="'+sku.image+'" style="width: 50px" ></td>'+
            '</tr>'+
             '<tr>'+
            '<td>Sku名字:</td>'+
            '<td>'+sku.skuName+'</td>'+
            '</tr>'+
            '<tr>'+
            '<td>价格:</td>'+
            '<td>'+sku.price+'</td>'+
            '</tr>'+
            '<tr>'+
            '<td>库存:</td>'+
            '<td>'+sku.stock+'</td>'+
            '</tr>'+
            '</table>';
    }

    return html;
}



function  search() {
    //id选择器获取文本框的值
    var  v_param={};
      v_param.brandIds= $("#brandSelect").val();



      v_param.spuName  = $("#spuName").val();
      v_param.priceMin = $("#priceMin").val();
      v_param.priceMax = $("#priceMax").val();
      v_param.stockMin = $("#stockMin").val();
      v_param.stockMax = $("#stockMax").val();


      var let =$("select[name='cateSelect']").length;
    for (let i = 0; i < let; i++) {
        v_param["cate"+(i+1)]=$($("select[name='cateSelect']")[i]).val();
    }
    //通过这个方法给ajax的data传值到后台
    v_SpuTable.settings()[0].ajax.data=v_param;
    //刷新表格
    v_SpuTable.ajax.reload();

}



    var  v_SpuTable;
    function initSpuTable() {
            v_SpuTable = $('#spuTable').DataTable({
                "language": {
                    "url": "/js/DataTables/Chinese.json" // 汉化
                },
                // 是否允许检索
                "searching": false,
                "processing": true,
                "lengthMenu": [5,10,15,20],
                "serverSide": true,
                "ajax": {
                    "url": "/spu/findSpu.jhtml",
                    "type": "POST",
                    traditional : true,//数组**
                },
                "columns": [
                    {
                        "className": 'details-control',
                        "orderable": false,
                        "data":      null,
                        "defaultContent": ''
                    },
                    {  "data": "spu.id",

                        "render": function (data, type, row, meta) {
                            return '<input type="checkbox" name="ids" value="'+data+'"/>';
                        }
                    },

                    { "data": "spu.spuName" },
                    { "data": "spu.price" },
                    { "data": "spu.stock" },
                    { "data": "spu.brandName" },
                    { "data": "spu.cateName" },
                    { "data": "spu.status",
                        "render": function (data, type, row, meta) {
                            return data=='0'?"下架":"上架";
                        }
                    },
                    { "data": "spu.newOld",
                        "render": function (data, type, row, meta) {
                            return data=='0'?"非新品":"新品";
                        }
                    },
                    { "data": "spu.hot",
                        "render": function (data, type, row, meta) {
                            return data=='0'?"不推荐":"推荐";
                        }
                    },
                    {  "data": "spu.id",
                        "render": function (data, type, row, meta) {

                            //     var status_text;
                            //     var status_class;
                            //     var status_color;
                            //     var status;
                            //     if(row.spu.status=='0'){
                            //         status_text='上架';
                            //         status_class='glyphicon glyphicon-arrow-up';
                            //         status_color='btn btn-success';
                            //         status='1';
                            //     }else {
                            //         status_text='下架';
                            //         status_class='glyphicon glyphicon-arrow-down';
                            //         status_color='btn btn-danger';
                            //         status='0';
                            //     }
                            //
                            // var newOld_text;
                            // var newOld_class;
                            // var newOld_color;
                            // if(row.spu.newOld=='0'){
                            //     newOld_text='新品';
                            //     newOld_class='glyphicon glyphicon-ok-sign';
                            //     newOld_color='btn btn-success';
                            //     status='1';
                            // }else {
                            //     newOld_text='非新品';
                            //     newOld_class='glyphicon glyphicon-remove-sign';
                            //     newOld_color='btn btn-danger';
                            //     status='0';
                            // }
                            //
                            // var hot_text;
                            // var hot_class;
                            // var hot_color;
                            // if(row.spu.hot=='0'){
                            //     hot_text='推荐';
                            //     hot_class='glyphicon glyphicon-star';
                            //     hot_color='btn btn-success';
                            //     status='1';
                            // }else {
                            //     hot_text='不推荐';
                            //     hot_class='glyphicon glyphicon-star-empty';
                            //     hot_color='btn btn-danger';
                            //     status='0';
                            // }



                            var  buttons="";
                            buttons+='<button class="btn btn-warning" type="button"  onclick="update(\''+data+'\')">修改</button>';
                            buttons+='<button class="btn btn-danger" type="button"  onclick="deleteSpu(\''+data+'\')">删除</button>';
                            // buttons+='<button class="'+status_color+'" type="button"  onclick="updateStatus(\''+data+'\',\''+status+'\',\'status\')"><span class="'+status_class+'"></span>'+status_text+'</button>';
                            // buttons+='<button class="'+newOld_color+'" type="button"  onclick="updateStatus(\''+data+'\',\''+status+'\',\'newOld\')"><span class="'+newOld_class+'"></span>'+newOld_text+'</button>';
                            // buttons+='<button class="'+hot_color+'" type="button"  onclick="updateStatus(\''+data+'\',\''+status+'\',\'hot\')"><span class="'+hot_class+'"></span>'+hot_text+'</button>';

                            if (row.spu.status==1){
                                buttons+='<button class="btn btn-danger" type="button" onclick="updateStatus('+data+',\''+0+'\',\'status\')"><span class="glyphicon glyphicon-arrow-down"></span>下架</button>';
                            }else{
                                buttons+='<button class="btn btn-success" type="button" onclick="updateStatus('+data+',\''+1+'\',\'status\')"><span class="glyphicon glyphicon-arrow-up"></span>上架</button>';
                            }
                            if (row.spu.newOld==1){
                                buttons+='<button class="btn btn-danger" type="button" onclick="updateStatus('+data+',\''+0+'\',\'newOld\')"><span class="glyphicon glyphicon-remove-sign"></span>非新品</button>';
                            }else{
                                buttons+='<button class="btn btn-success" type="button" onclick="updateStatus('+data+',\''+1+'\',\'newOld\')"><span class="glyphicon glyphicon-ok-sign"></span>新品</button>';
                            }
                            if (row.spu.hot==1){
                                buttons+='<button class="btn btn-danger" type="button" onclick="updateStatus('+data+',\''+0+'\',\'hot\')"><span class="glyphicon glyphicon-star-empty"></span>不推荐</button>';
                            }else{
                                buttons+='<button class="btn btn-success" type="button" onclick="updateStatus('+data+',\''+1+'\',\'hot\')"><span class="glyphicon glyphicon-star"></span>推荐</button>';
                            }
                                return buttons;

                        }
                    }
                ]

            });
        // Add event listener for opening and closing details
        $('#spuTable tbody').on('click', 'td.details-control', function () {
            var tr = $(this).closest('tr');
            var row = v_SpuTable.row( tr );

            if ( row.child.isShown() ) {
                // This row is already open - close it
                row.child.hide();
                tr.removeClass('shown');
            }
            else {
                // Open this row
                row.child( format(row.data()) ).show();
                tr.addClass('shown');
            }
        })

    }


    function updateStatus(id,status,flag) {
        $.ajax({
            type:"post",
            data:{"spuId":id,"status":status,"flag":flag},
            url:"/spu/updateStatus.jhtml",
            success:function (res) {
                if(res.code==200){
                    search();
                }
            }
        })
    }


function  deleteSpu(id) {
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
                    url:"/spu/deleteSpu.jhtml",
                    data:{"id":id},

                    success:function (data) {
                        if (data.code==200){
                            search();
                        }
                    }
                })
            } else {
                // alert('点击取消按钮了');
            }
        },
    })

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
                    var v_ids =v_idArr.join(",");
                    if(v_ids){
                        $.ajax({
                            type:"post",
                            url:"/spu/deleteBatch.jhtml",
                            data:{"ids":v_ids},
                            success:function(result){
                                if (result.code==200){
                                    search();
                                }
                            }
                        })
                    }

                }
            }
        });
    }
}






    function toAdd() {
        location.href="/spu/toAdd.jhtml";
    }


    function update(id) {
        location.href="/spu/toEdit.jhtml?id="+id;
    }














</script>




</body>
</html>
