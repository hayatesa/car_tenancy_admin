const orderchange_data = {

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
    var str = decodeURIComponent(window.location.search.slice(4));
    init_data(str);
})
function init_data(str) {
    $.ajax({
        url: "/api/order/selectById?id="+str,
        dataType: "json",//若数据不是json则直接进入error,json数据里面不能有注释
       success : function(data) {
            if (data.code==0) {
                console.log(data);

            } else {
                console.log("gg")
            }
        },
        error:function () {
            console.log("no")
        }
    })
}