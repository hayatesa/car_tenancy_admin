var carId =window.location.search.slice(7);
var form = layui.form;
//console.log(carId);


doRequestPackage();
/*请求套餐列表*/
function doRequestPackage() {
    $("#package_id").empty();
    $.ajax({
        type:"GET",
        data:{carId:carId},
        url:"/api/PackageScheme/unselect",
        success:function (res) {
            //console.log(res);
            if(res.code ==0){
                var b_data = res.data;
                for(var i=0;i<b_data.length;i++){
                    $("#package_id").append('<option value="'+b_data[i].id+'">'+b_data[i].name+'</option>');
                }
                form.render('select','package_form');
            }else{
                layer.msg(res.msg);
            }
        },
    })
}

form.on('select(package)',function(res){
    var data = {
        areaId:res.value,
    }
})
function showTips(tips) {
    layer.msg(tips+"不能为空")
}

function getData() {

    var packageId = $("#package_id").val();
    var basePrice = $("#basePrice").val();
    var baseHourPrice = $("#baseHourPrice").val();
    var servicePrice = $("#servicePrice").val();
    var deposit = $("#deposit").val();
    var discount = $("#discount").val();

    var data={
        carId:carId,
        packageId:packageId,
        basePrice:basePrice,
        baseHourPrice :baseHourPrice,
        servicePrice:servicePrice,
        deposit :deposit,
        discount:discount
    }

    return data;
}
function doCheck() {
    if ($("#package_id").val() == ""){
        showTips("套餐");
        return false;
    }
    if ($("#basePrice").val() == ""){
        showTips("基础价");
        return false;
    }
    if ($("#baseHourPrice").val() == ""){
        showTips("单位时价");
        return false;
    }
    if ( $("#servicePrice").val() == ""){
        showTips("服务费");
        return false;
    }
    if ( $("#deposit").val() == ""){
        showTips("押金");
        return false;
    }
    if ($("#discount").val() == ""){
        showTips("折扣");
        return false;
    }else if(!isDecimal($("#discount").val())){
        layer.msg("折扣请输入[0,1]之间的小数");
        return false;
    }
    return true;
}

function isDecimal(n){
    if(/^(0.\d+|0|1)$/.test(n)){
        return true;
    }else{
        return false;
    }
}
function addPriceScheme() {
    if(!doCheck())
        return;
    var data = getData();
    $.ajax({
        url:"/api/priceScheme/add",
        data:data,
        success:function (res) {
            if (res.code == 0){
                layer.msg("添加成功!");
                doResetForm();
                doRequestPackage();
            }else{
                layer.msg(res.msg);
            }
        }
    })
}

function doResetForm() {
    $("#basePrice").val("") ;
    $("#baseHourPrice").val("");
    $("#servicePrice").val("");
    $("#deposit").val("");
    $("#discount").val("");
}