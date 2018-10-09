const orderdetail_data = {
    price:{},
    order:{},
    car:{},
    getadd:{},
    returnadd:{},
    user:{},
    days:{}
}
const orderdetail_methods = {

}

const orderdetail_app = new Vue({
    el: '#orderdetail_app',
    data: orderdetail_data,
    methods: orderdetail_methods,
    computed: {
        status:function(){
            if(orderdetail_data.order.status==0) return "等待付款";else if(orderdetail_data.order.status==1) return "租赁中";
            else if(orderdetail_data.order.status==2) return "预定成功";else if(orderdetail_data.order.status==3) return "已取消";
            else if(orderdetail_data.order.status==4) return "已完成";else if(orderdetail_data.order.status==5) return "处理中";
        },
    }
    //created: init_data()
});
$(document).ready(function () {
    var id = window.location.search.slice(4);
    // console.log("id="+id);
    init_data(id);
})

function getdays(date1,date2) {
    date1 = new Date(Date.parse(date1.replace(/-/g, "/")))
    date2 = new Date(Date.parse(date2.replace(/-/g, "/")))
    var date3=date2.getTime()-date1.getTime();
    var days=Math.floor(date3/(24*3600*1000));
     return days;
}
function init_data(str) {
    $.ajax({
        url: "/api/order/selectById?id="+str,
        dataType: "json",//若数据不是json则直接进入error,json数据里面不能有注释
       success : function(data) {
            console.log(data);
            if (data.code==0) {
                orderdetail_data.price = data.price;
                orderdetail_data.car = data.data;
                orderdetail_data.user = data.data.tncCustomer;
                orderdetail_data.order = data.data.tncOrder;
                orderdetail_data.days =getdays(orderdetail_data.order.startDate,orderdetail_data.order.returnDate);
                orderdetail_data.getadd = data.data.tncOrder.getStore.tncAddress.province.name + data.data.tncOrder.getStore.tncAddress.city.name + data.data.tncOrder.getStore.tncAddress.area.name + data.data.tncOrder.getStore.tncAddress.detail
                orderdetail_data.returnadd = data.data.tncOrder.returnStore.tncAddress.province.name + data.data.tncOrder.returnStore.tncAddress.city.name + data.data.tncOrder.returnStore.tncAddress.area.name + data.data.tncOrder.returnStore.tncAddress.detail
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
    //str = str.substring(0,str.length-1);
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
        "            <div class=\"layui-col-md6\"><label class=\"mylabel\"  style='margin-left:45px;border:0px;height:30px;width: 100px'>"+orderdetail_data.order.otherAmount+"</label></div></div>");
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