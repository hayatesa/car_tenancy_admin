const orderchange_data = {
    order:{
    },
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
    //computed: getname
    //created: init_data()
});
$(document).ready(function () {
    var str = decodeURIComponent(window.location.search.slice(6));
    var packageData = JSON.parse(str);
    init_data();
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
function init_data() {
    $.ajax({
        url: "/statics/order_change_data.json",
        dataType: "json",//若数据不是json则直接进入error,json数据里面不能有注释
       success : function(data) {
            if (data.code==0) {
                orderchange_data.order = data.data;
            } else {
                console.log("gg")
            }
        },
        error:function () {
            console.log("no")
        }
    })
}