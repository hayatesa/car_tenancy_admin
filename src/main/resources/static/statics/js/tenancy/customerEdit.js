var addressProvinceId;
var addressCityId;
var addressAreaId;
var genderId;
//data
var customerData = {
    customer:{
        id:"",
        name:"",
        phone:"",
        gender:"",
        idCard:"",
        email:"",
        tncAddress:{
            id:"",
            province:{id:"",name:""},
            city:{id:"",name:""},
            area:{id:"",name:""},
            detail:"",
        },
        emergencyName:"",
        emergencyPhone:"",
        password:"",
    }
};
//Vue app，编辑页
var customerEdit_app = new Vue({
    el: "#customerEdit",
    data: customerData,
    mounted: function () {
        layui.use('form', function () {
            var form = layui.form;
            form.render();
        });
    }
});

$(document).ready(function () {
    var uid = T.p('id');
    $.ajax({
        type: "get",
        url: "/api/customer/edit",
        data:{
            uid: uid
        },
        success:function(res) {
            if(res.code == 0) {
                customerEdit_app.customer = res.data;
                console.log(res.data);
                genderId = "gender"+customerEdit_app.customer.gender;
                if(customerEdit_app.customer.gender==1) {
                    $($("#gender").siblings().eq(0).children("div").children("input")[0]).attr('value', "男");
                } else{
                    $($("#gender").siblings().eq(0).children("div").children("input")[0]).attr('value', "女");
                }
                loadAddress(0, 0);
                if(res.data.tncAddress!=null) {
                    var provinceName = res.data.tncAddress.province.name;
                    var cityName = res.data.tncAddress.city.name;
                    var areaName = res.data.tncAddress.area.name;
                    var detailAddress = res.data.tncAddress.detail;
                    $($("#province").siblings().eq(0).children("div").children("input")[0]).attr('value', provinceName);
                    $($("#city").siblings().eq(0).children("div").children("input")[0]).attr('value', cityName);
                    $($("#area").siblings().eq(0).children("div").children("input")[0]).attr('value', areaName);
                    $("#detail").attr('value', detailAddress);
                    addressProvinceId = "province" + res.data.tncAddress.province.id;
                    addressCityId = "city" + res.data.tncAddress.city.id;
                    addressAreaId = "area" + res.data.tncAddress.area.id;
                }
            } else {
                handleAjax(res);
            }
        }
    })
});
//监听提交
//layui form
layui.use('form', function () {
    var form = layui.form;
    //监听提交
    form.on('submit(demo1)', function () {
        saveCustomer();
        return false;
    });
    form.verify({
        repeat: function (value, item) {
            if($("#password").val()!='' && $("#password").val()!=$("#repeatPassword").val()) {
                return "两次密码不一致";
                //layer.msg("两次密码不一致", { time: 1500})
            }
        },
        allAddress:function (value, item) {
            var pAddress = $($("#province").siblings().eq(0).children("div").children("input")[0]).val();
            var cAddress = $($("#city").siblings().eq(0).children("div").children("input")[0]).val();
            var aAddress = $($("#area").siblings().eq(0).children("div").children("input")[0]).val();
            var dAddress = $("#detail").val();
            if(!((pAddress!=''&&cAddress!=''&&aAddress!=''&&dAddress!='')
                ||(pAddress==''&&cAddress==''&&aAddress==''&&dAddress==''))) {
                return "地址填写不完整";
            }
        }
    });
});

var saveCustomer = function () {
    customerEdit_app.customer.gender = genderId.substring(6);
    console.log(addressProvinceId);
    if(addressProvinceId!=null) {
        var tncAddress = {};
        var province = {};
        var city = {};
        var area = {};
        if(customerEdit_app.customer.tncAddress.id != null) {
            var id = customerEdit_app.customer.tncAddress.id;
    }
        var detail = $("#detail").val();
        province.id = addressProvinceId.substring(8);
        city.id= addressCityId.substring(4);
        area.id=addressAreaId.substring(4);
        tncAddress.province=province;
        tncAddress.city=city;
        tncAddress.area=area;
        tncAddress.detail=detail;
        tncAddress.id = id;
        customerEdit_app.customer.tncAddress = tncAddress;
    }
    var data = JSON.stringify(customerEdit_app.customer);
    password = $("#password").val;
    $.ajax({
        type: "POST",
        url: "/api/customer/change",
        contentType: "application/json",
        data: data,
        success: function (res) {
            if (res.code === 0) {
                layer.msg("操作成功",{
                    time:1500
                },function () {
                    window.location.href='/tenancy/p/customerList'
                });
            }else {
                handleAjax(res);
            }
        }
    })
}

var  clickGender = function () {
    var optionGender = "<option value='1'>男</option>"+"<option value='0'>女</option>"
    $("#gender").html(optionGender);
    var ddGender = "<dd id='gender1' value='男'>男</dd>"+ "<dd id='gender0' value='女'>女</dd>"
    $($("#gender").siblings().eq(0).children("dl")[0]).html(ddGender);
    $("#"+ genderId).addClass('layui-this');
    $($("#gender").siblings().eq(0).children("dl").children("dd")).click(function () {
        /*添加选中样式*/
        $(this).siblings('dd').removeClass('layui-this');
        $(this).addClass('layui-this');
        /*获取选中的值将其显示在输入框里*/
        var genderName = $(this)[0].getAttribute('value');
        genderId = $(this)[0].getAttribute('id');
        $($("#gender").siblings().eq(0).children("div").children("input")[0]).attr('value', genderName);
    })
}

/*省市区已选项样式加载*/
var loadStyle = function () {
    $("#"+ addressProvinceId).addClass('layui-this');
    $("#"+ addressCityId).addClass('layui-this');
    $("#"+ addressAreaId).addClass('layui-this');
}

/*加载省内容及其点击事件，其他的就不多说了*/
var loadProvince = function (res) {
    /*因vue与lay冲突缘故，只能手动渲染*/
    var optionProvince = "<option value='0'>请选择省</option>"
    var ddProvince = "<dd id='province0' class='layui-select-tips'>请选择省</dd>"
    for(var i=0; i<res.data.length; i++) {
        optionProvince += "<option value='"+res.data[i].id+"'>"+res.data[i].name+"</option>";
        ddProvince += "<dd id=province"+res.data[i].id+" value='"+res.data[i].name+"'>"+res.data[i].name+"</dd>";
    }
    $("#province").html(optionProvince);
    /*怎么说呢？无法用言语来表达，结合layui的代码来写的*/
    /*提示框内容*/
    $($("#province").siblings().eq(0).children("div").children("input")[0]).attr('placeholder','请输入省');
    /*添加省项*/
    $($("#province").siblings().eq(0).children("dl")[0]).html(ddProvince);
    loadStyle();
    /*点击事件*/
    $($("#province").siblings().eq(0).children("dl").children("dd")).click(function () {
        /*使市、区下拉框回复为默认状态*/
        $($("#city").siblings().eq(0).children("div").children("input")[0]).attr('placeholder','请输入市');
        $($("#city").siblings().eq(0).children("div").children("input")[0]).attr('value','');
        $($("#area").siblings().eq(0).children("div").children("input")[0]).attr('placeholder','请输入区（县）');
        $($("#area").siblings().eq(0).children("div").children("input")[0]).attr('value','');
        /*添加选中样式*/
        $(this).siblings('dd').removeClass('layui-this');
        $(this).addClass('layui-this');
        /*获取选中的值将其显示在输入框里*/
        var provinceName = $(this)[0].getAttribute('value');
        addressProvinceId = $(this)[0].getAttribute('id');
        $($("#province").siblings().eq(0).children("div").children("input")[0]).attr('value',provinceName);
        //console.log(this);
    });
}

/*字面意思，自己体会*/
var clickCity = function () {
    if(addressProvinceId!=null) {
        /*获取省id*/
        var parent_cityId = addressProvinceId.substring(8);
        /*获取市数据*/
        loadAddress(parent_cityId,1);
    }else {
        layer.msg("请选择省");
    }

}

var loadCity = function (res) {
    var optionCity = "<option value='0'>请选择市</option>"
    var ddCity = "<dd id='city0' class='layui-select-tips'>请选择市</dd>"
    for(var i=0; i<res.data.length; i++) {
        optionCity += "<option value='"+res.data[i].id+"'>"+res.data[i].name+"</option>";
        ddCity += "<dd id=city"+res.data[i].id+" value='"+res.data[i].name+"'>"+res.data[i].name+"</dd>";
    }
    $("#city").html(optionCity);
    /*怎么说呢？无法用言语来表达，结合layui的代码来写的*/
    /*提示框内容*/
    $($("#city").siblings().eq(0).children("div").children("input")[0]).attr('placeholder','请输入市');
    /*添加省项*/
    $($("#city").siblings().eq(0).children("dl")[0]).html(ddCity);
    loadStyle();
    /*点击事件*/
    $($("#city").siblings().eq(0).children("dl").children("dd")).click(function () {
        /*使市、区下拉框回复为默认状态*/
        $($("#area").siblings().eq(0).children("div").children("input")[0]).attr('placeholder','请输入区（县）');
        $($("#area").siblings().eq(0).children("div").children("input")[0]).attr('value','');
        /*添加选中样式*/
        $(this).siblings('dd').removeClass('layui-this');
        $(this).addClass('layui-this');
        /*获取选中的值将其显示在输入框里*/
        var cityName = $(this)[0].getAttribute('value');
        addressCityId = $(this)[0].getAttribute('id');
        $($("#city").siblings().eq(0).children("div").children("input")[0]).attr('value',cityName);
        //console.log(this);
    });
}

var clickArea = function () {
    if(addressCityId!=null) {
        var parent_AreaId = addressCityId.substring(4);
        loadAddress(parent_AreaId, 2);
    } else {
        layer.msg("请选择市");
    }
}

var loadArea = function (res) {
    var optionArea = "<option value='0'>请选择区（县）</option>"
    var ddArea = "<dd id='area0' class='layui-select-tips'>请选择区（县）</dd>"
    for(var i=0; i<res.data.length; i++) {
        optionArea += "<option value='"+res.data[i].id+"'>"+res.data[i].name+"</option>";
        ddArea += "<dd id=area"+res.data[i].id+" value='"+res.data[i].name+"'>"+res.data[i].name+"</dd>";
    }
    $("#area").html(optionArea);
    /*怎么说呢？无法用言语来表达，结合layui的代码来写的*/
    /*提示框内容*/
    $($("#area").siblings().eq(0).children("div").children("input")[0]).attr('placeholder','请输入区（县）');
    /*添加省项*/
    $($("#area").siblings().eq(0).children("dl")[0]).html(ddArea);
    loadStyle();
    /*点击事件*/
    $($("#area").siblings().eq(0).children("dl").children("dd")).click(function () {
        /*使市、区下拉框回复为默认状态*/
        $($("#area").siblings().eq(0).children("div").children("input")[0]).attr('placeholder','请输入区（县）');
        $($("#area").siblings().eq(0).children("div").children("input")[0]).attr('value','');
        /*添加选中样式*/
        $(this).siblings('dd').removeClass('layui-this');
        $(this).addClass('layui-this');
        /*获取选中的值将其显示在输入框里*/
        var areaName = $(this)[0].getAttribute('value');
        addressAreaId = $(this)[0].getAttribute('id');
        $($("#area").siblings().eq(0).children("div").children("input")[0]).attr('value',areaName);
        //console.log(this);
    });
}

var loadAddress = function (aid, level) {
    $.ajax({
        type: "get",
        url: "/api/customer/address",
        data: {
            aid : aid,
            level : level
        },
        success:function (res) {
            if(level===0) {
                loadProvince(res);
            }else if(level===1) {
                loadCity(res);
            }else {
                loadArea(res);
            }
        }
    })
}

var back = function() {
    window.history.back();
}