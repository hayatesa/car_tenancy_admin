const orderchange_data = {
    order:{},
    car:{},
    getadd:{},
    returnadd:{},
    user:{},
    reasondiv:false,
    orderbackdiv:false,
    userdiv:true,
    orderdiv:false,
    btn_carchange:false,
    btn_userchange:false,
    backrant:false,
    getrant:false
}
const orderchange_methods = {

}

const orderchange_app = new Vue({
    el: '#orderchange_app',
    data: orderchange_data,
    methods: orderchange_methods,
    computed: {
        status:function(){
            if(orderchange_data.order.status==0) return "等待付款";else if(orderchange_data.order.status==1) return "租赁中";
            else if(orderchange_data.order.status==2) return "预定成功";else if(orderchange_data.order.status==3) return "已取消";
            else if(orderchange_data.order.status==4) return "已完成";else if(orderchange_data.order.status==5) return "处理中";
        },
    }
    //created: init_data()
});
$(document).ready(function () {
    var str = decodeURIComponent(window.location.search.slice(6));
    var packageData = JSON.parse(str);
    init_data(packageData.orderid);
    if (packageData.type == 1) {
        orderchange_data.btn_carchange = true;
        orderchange_data.btn_userchange = true;
        orderchange_data.orderdiv = true;
        orderchange_data.getrant = true;
    } else if (packageData.type == 2) {
        orderchange_data.orderdiv = true;
        orderchange_data.backrant = true;
    }else if (packageData.type == 3) {

    }else if (packageData.type == 4) {
        orderchange_data.reasondiv = true;
        orderchange_data.userdiv=false;
        orderchange_data.orderbackdiv=true
    }
})
function init_data(str) {
    $.ajax({
        url: "/api/order/selectById?id="+str,
        dataType: "json",//若数据不是json则直接进入error,json数据里面不能有注释
       success : function(data) {
            if (data.code==0) {
                console.log(data);
                orderchange_data.car = data.data;
                orderchange_data.user = data.data.tncCustomer;
                orderchange_data.order = data.data.tncOrder;
                orderchange_data.getadd = data.data.tncOrder.getStore.tncAddress.province.name + data.data.tncOrder.getStore.tncAddress.city.name + data.data.tncOrder.getStore.tncAddress.area.name + data.data.tncOrder.getStore.tncAddress.detail
                orderchange_data.returnadd = data.data.tncOrder.returnStore.tncAddress.province.name + data.data.tncOrder.returnStore.tncAddress.city.name + data.data.tncOrder.returnStore.tncAddress.area.name + data.data.tncOrder.returnStore.tncAddress.detail
                getdate();
            } else {
                console.log("gg")
            }
        },
        error:function () {
            console.log("no")
        }
    })
}
function getdate() {
    orderchange_data.order.startDate = orderchange_data.order.startDate.substr(0,orderchange_data.order.startDate.length-3)
    orderchange_data.order.payTime = orderchange_data.order.payTime.substr(0,orderchange_data.order.payTime.length-3)
    orderchange_data.order.returnDate = orderchange_data.order.returnDate.substr(0,orderchange_data.order.returnDate.length-3)
    orderchange_data.order.gmtModified = orderchange_data.order.gmtModified.substr(0,orderchange_data.order.gmtModified.length-3)
}