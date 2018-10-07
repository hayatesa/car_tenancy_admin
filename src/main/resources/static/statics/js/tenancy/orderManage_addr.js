$(function () {
    doRequestProvince();
})
$('#searchStore').click(function () {
    var getStore = {
        gp:$('#province_id').val(),
        gc:$('#city_id').val(),
        ga:$('#area_id').val()
    };
    var backStore = {
        bp:$('#province_id_back').val(),
        bc:$('#city_id_back').val(),
        ba:$('#area_id_back').val()
    };
    layui.use('table', function(){
        var table = layui.table;
        console.log(getStore.gc);
    table.reload('orderstable', {
        url: '/api/order/selectByWord'
        ,where: {
            search:$('#word').val(),
            status:$('#selectID_text').val(),
            gp:getStore.gp,
            gc:getStore.gc,
            ga:getStore.ga,
            bp:backStore.bp,
            bc:backStore.bc,
            ba:backStore.ba
        } //设定异步数据接口的额外参数
        //,height: 300
    });
    })
});
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
            console.log(res);
            if(res.code ==0){
                var b_data = res.data;
                for(var i=0;i<b_data.length;i++){
                    $("#province_id").append('<option value="'+b_data[i].id+'">'+b_data[i].name+'</option>');
                    $("#province_id_back").append('<option value="'+b_data[i].id+'">'+b_data[i].name+'</option>');
                }
                layui.form.render('select','store_addr');
                layui.form.render('select','store_addr_back');
            }
        },
    })
}
layui.use('form', function(){
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
    form.on('select(province_back)',function(res){
        var data = {
            id:res.value,
            level:1
        }
        doReset_back(data.level);
        doRequestAddr(data,"#city_id_back");
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
    form.on('select(city_back)',function(res){
        var data = {
            id:res.value,
            level:2
        }
        doReset_back(data.level);
        doRequestAddr(data,"#area_id_back");
    });


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
                layui.form.render('select','store_addr_back');
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
    }else if (level == 2){
        $("#area_id").empty();
        $("#area_id").append('<option value="">请选择县/区</option>');
    }
}
    function doReset_back(level) {

        if(level ==1){
            $("#city_id_back").empty();
            $("#city_id_back").append('<option value="">请选择市</option>');
            $("#area_id_back").empty();
            $("#area_id_back").append('<option value="">请选择县/区</option>');
        }else if (level == 2){
            $("#area_id_back").empty();
            $("#area_id_back").append('<option value="">请选择县/区</option>');
        }
    }
});