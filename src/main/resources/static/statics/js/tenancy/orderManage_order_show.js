const orderdetail_data = {
    order:{
    },
}
const orderdetail_methods = {

}

const orderdetail_app = new Vue({
    el: '#orderdetail_app',
    data: orderdetail_data,
    methods: orderdetail_methods,
    //computed: getname
    //created: init_data()
});
$(document).ready(function () {
    var id = window.location.search.slice(4);
    // console.log("id="+id);
    init_data(id);
})
function init_data(str) {
    $.ajax({
        url: "/statics/order_detail_data.json",
        dataType: "json",//若数据不是json则直接进入error,json数据里面不能有注释
       success : function(data) {
            if (data.code==0) {
                orderdetail_data.order = data.data;
                loadtable();
            } else {
                console.log("gg")
            }
        },
        error:function () {
            console.log("no")
        }
    })
}
function loadtable() {
    var str = orderdetail_data.order.description;
    str = str.substring(0,str.length-1);
    $.each(str.split(";"),function(index,value){
        let state = value.split(":")[0];
        let money = value.split(":")[1];
        let $tr = $("<tr>\n" +
            "            <td><input class=\"layui-input\" type='text' style='border:0px;height:30px;width: 160px' disabled=\"true\" value='"+state+"'/></td>\n" +
            "            <td><input class=\"layui-input\" type='text' style='border:0px;height:30px;width: 100px' disabled=\"true\" value='"+money+"'/></td>\n" +
            "        </tr>\n");
        $("#mytable ").append($tr);
    });
    $("#mytable ").after('<hr id="hr" class="layui-bg-green" />');
    // var $ttr = $("<tr><td>\n" +
    //     "         <label class=\"mylabel\"  style='padding-left: 25px;border:5px;height:30px;width: 160px' >总计</label></td>\n" +
    //     "            <td><label class=\"mylabel\"  style='margin-left:60px;border:0px;height:30px;width: 100px'>"+orderdetail_data.order.other_amount+"</label></td>\n" +
    //     "        </tr>");
    var $ttr = $("<div class=\"layui-row\" ><div class=\"layui-col-md6\"><label class=\"mylabel\"  style='padding-left: 25px;border:5px;height:30px;width: 160px' >总计</label></div>" +
        "            <div class=\"layui-col-md6\"><label class=\"mylabel\"  style='margin-left:45px;border:0px;height:30px;width: 100px'>"+orderdetail_data.order.other_amount+"</label></div></div>");
    $("#hr").after($ttr);
}
function getother() {
    layui.use('layer', function() {
        var layer = layui.layer;
        layer.open({
            type: 1,
            content: $('#othermoney')
            , title: '其他费用详情'
            ,btn: ['关闭']
            , area: ['400px', '400px'] //自定义文本域宽高
            ,yes:function (index, layero) {
                $('#othermoney').hide();layer.close(index);
            }
            ,cancel: function(){
                //右上角关闭回调
                $('#othermoney').hide();
                //return false 开启该代码可禁止点击该按钮关闭
            }
        });
    });
}