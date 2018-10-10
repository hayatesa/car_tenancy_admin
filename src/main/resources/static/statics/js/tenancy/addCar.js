const vueData={
    brands:[],
    carTypes:[],
    storeAddr:[],
    packages:[],
    selected: '',
    province: []
}
const  doCreated =function ()
{
    /*请求品牌列表*/
    doRequestBrand();
    /*请求车类型*/
    doRequestCarType();
    /*请求门店地址*/
    doRequestProvince();

}

const vueMethods={
    doSubmitCarBaseMsg:doSubmitCarBaseMsg
}

var vueObj = new Vue({
    el: "#addCar",
    data:vueData,
     created:doCreated,
     methods:vueMethods
});


/*请求品牌列表*/
function  doRequestBrand() {
    $.ajax({
        type:"GET",
        url:"/api/tncBrand/list",
        success:function (res) {
            if(res.code ==0){
                //console.log(res);
                var b_data = res.data;
                // vueObj.data.brands = res.data;

                for(var i=0;i<b_data.length;i++){
                    $("#brand_id").append('<option value="'+b_data[i].id+'">'+b_data[i].name+'</option>');
                    //console.log(i);
                }
                layui.form.render('select','brandFilter');
            }

        },
    })
}
/*请求车类型*/
function doRequestCarType() {
    $.ajax({
        type:"GET",
        url:"/api/carType/list",
        success:function (res) {
            if(res.code ==0){
                //console.log(res);
                var b_data = res.data;
                // vueObj.data.brands = res.data;
                for(var i=0;i<b_data.length;i++){
                    $("#car_type_id").append('<option value="'+b_data[i].id+'">'+b_data[i].name+'</option>');
                    // //console.log(i+"ui");
                }
                layui.form.render('select','carTypeFilter');
            }

        },
    })
}

function showTips(idName,tips) {
    if($(idName).val() ==""){
        layer.msg(tips+"不能为空！");
        $(idName).focus();
        return false;
    }
    return true;
}
function showSelectTips(idName,tips) {
    if($(idName).val() ==""){
        layer.msg(tips+"不能为空！");
        return false;
    }
    return true;
}

function doCollectingData() {
    var series = $("#series").val();
    var year  = $("#year").val();
    var config_section  = $("#config_section").val();
    var province_id  = $("#province_id").val();
    var city_id  = $("#city_id").val();
    var area_id  = $("#area_id").val();
    var seat_quantity  = $("#seat_quantity").val();
    var door_quantity  = $("#door_quantity").val();
    var transmission_type = $("#transmission_type").val();
    var displacement = $("#displacement").val();
    var fuel_type  = $("#fuel_type").val();
    var octane_rating  = $("#octane_rating").val();
    var driven_method  = $("#driven_method").val();
    var en_itk_form  = $("#en_itk_form").val();
    var skylight  = $("#skylight").val();
    var tank_capacity  = $("#tank_capacity").val();
    var speaker  = $("#speaker").val();
    var box_quantity  = $("#box_quantity").val();
    var seat  = $("#seat").val();
    var reversing_radar  = $("#reversing_radar").val();
    var airbag  = $("#airbag").val();
    var dvd_cd  = $("#dvd_cd").val();
    var gps  = $("#gps").val();
    var type_id  = $("#car_type_id").val();
    var store_id  = $("#store_id").val();
    var brand_id  = $("#brand_id").val();


    var data ={
        series:series
        ,year:year
        ,configSection:config_section
        ,provinceId:province_id
        ,cityId:city_id
        ,areaId:area_id
        ,seatQuantity:seat_quantity
        ,doorQuantity:door_quantity
        ,transmissionType:transmission_type
        ,displacement:displacement
        ,fuelType:fuel_type
        ,octaneRating:octane_rating
        ,drivenMethod:driven_method
        ,enItkForm:en_itk_form
        ,skylight:skylight
        ,tankCapacity:tank_capacity
        ,speaker:speaker
        ,boxQuantity:box_quantity
        ,seat:seat
        ,reversingRadar:reversing_radar
        ,airbag:airbag
        ,dvdCd:dvd_cd
        ,gps:gps
        ,typeId:type_id
        ,storeId:store_id
        ,brandId:brand_id

}
    return data;
}

function doCheckData() {
    if(!showSelectTips("#brand_id","品牌"))
        return false;
    if(!showTips("#series","系列"))
        return false;
    if(!showSelectTips("#car_type_id","车型"))
        return false;
    if(!showTips("#year","年代款"))
        return false;
    if(!showTips("#config_section","配置款"))
        return false;
    if(!showSelectTips("#province_id","省"))
        return false;
    if(!showSelectTips("#city_id","市"))
        return false;
    if(!showSelectTips("#area_id","县/区"))
        return false;
    if(!showSelectTips("#store_id","门店"))
        return false;
    if(!showTips("#seat_quantity","座位数"))
        return false;
    if(!showTips("#door_quantity","车门数"))
        return false;
    if(!showTips("#fuel_type","燃料类型"))
        return false;
    if(!showTips("#transmission_type","变速箱类型"))
        return false;
    if(!showTips("#displacement","排量"))
        return false;
    if(!showTips("#octane_rating","燃油标号"))
        return false;
    if(!showTips("#driven_method","驱动方式"))
        return false;
    if(!showTips("#en_itk_form","发动机进气形式"))
        return false;
    if(!showSelectTips("#skylight","天窗"))
        return false;
    if(!showTips("#tank_capacity","油箱容量(升)"))
        return false;
    if(!showSelectTips("#speaker","音箱"))
        return false;
    if(!showTips("#box_quantity","箱数"))
        return false;
    if(!showTips("#seat","座椅材料"))
        return false;
    if(!showSelectTips("#reversing_radar","雷达"))
        return false;
    if(!showTips("#airbag","气囊数"))
        return false;
    if(!showTips("#dvd_cd","DVD/CD"))
        return false;
    if(!showSelectTips("#gps","GPS导航"))
        return false;


    return true;
}

/*提交车辆基本信息*/
 function doSubmitCarBaseMsg() {
    //console.log("submit");
    var flag = doCheckData();
    if (!flag)
        return;
     var data = doCollectingData();
     $.ajax({
         url:"/api/car/add",
         data:data,
         success:function (res) {
             if (res.code == 0){
                 layer.msg("上传成功");
             }else{
                 layer.msg(res.msg);
             }
         }
     })
}


var form = layui.form;
 /*监听省下拉列表变化*/
form.on('select(province)',function(res){
    var data = {
        id:res.value,
        level:1
    }
    doReset(data.level);
    doRequestAddr(data,"#city_id");
});

/*监听城市下拉列表变化*/
form.on('select(city)',function(res){
    var data = {
        id:res.value,
        level:2
    }
    doReset(data.level);
    doRequestAddr(data,"#area_id");
});
/*监听地区下拉列表变化*/
form.on('select(area)',function(res){
    var data = {
        areaId:res.value,
    }
    doReset(3);
    doRequestStore(data);
});
/*监听地区下拉列表变化*/
form.on('select(store)',function(res){
    var data = {
        areaId:res.value,
    }

})

/*请求门店地址*/
function doRequestProvince() {
    var data = {
        id:0,
        level:0
    }
    $.ajax({
        type:"GET",
        data:data,
        url:"/api/region/addr",
        success:function (res) {
            if(res.code ==0){
                var b_data = res.data;
                for(var i=0;i<b_data.length;i++){
                    $("#province_id").append('<option value="'+b_data[i].id+'">'+b_data[i].name+'</option>');
                }
                layui.form.render('select','store_addr');
            }
        },
    })
}

/*联动地址请求 门店*/
function doRequestStore(data) {
    $.ajax({
        type:"GET",
        data:data,
        url:"/api/tncStore/store",
        success:function (res) {
            if(res.code ==0){
                //console.log(res);
                var b_data = res.data;
                // vueObj.data.province = res.data;
                $("#store_id").empty();
                $("#store_id").append('<option value="">请选择门店</option>');
                for(var i=0;i<b_data.length;i++){
                    $("#store_id").append('<option value="'+b_data[i].id+'">'+b_data[i].name+'</option>');
                    // //console.log(i+"store_id");
                }
                layui.form.render('select','store_addr');
            }
        },
    })
}

/**请求市和县数据*/
function doRequestAddr(data,comIdName){
    if(data.id == "")
        return;
    $.ajax({
        type:"GET",
        data:data,
        url:"/api/region/addr",
        success:function (res) {
            if(res.code ==0){
                var b_data = res.data;
                for(var i=0;i<b_data.length;i++){
                    $(comIdName).append('<option value="'+b_data[i].id+'">'+b_data[i].name+'</option>');
                }
                layui.form.render('select','store_addr');
            }
        },
    })
}

/*重设下拉列表提示内容*/
function doReset(level) {

    if(level ==1){
        $("#city_id").empty();
        $("#city_id").append('<option value="">请选择市</option>');
        $("#area_id").empty();
        $("#area_id").append('<option value="">请选择县/区</option>');
        $("#store_id").empty();
        $("#store_id").append('<option value="">请选择门店</option>');
    }else if (level == 2){
        $("#area_id").empty();
        $("#area_id").append('<option value="">请选择县/区</option>');
        $("#store_id").empty();
        $("#store_id").append('<option value="">请选择门店</option>');
    }else if (level == 3){
        $("#store_id").empty();
        $("#store_id").append('<option value="">请选择门店</option>');
    }

}