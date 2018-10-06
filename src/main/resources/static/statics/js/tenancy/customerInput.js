//data
var customer_data = {
    customer:{
        id:"",
        name:"",
        phone:"",
        password:""
    }
};
//Vue app，添加页
var customerAdd_app = new Vue({
    el: "#customerAdd",
    data: customer_data
});

//监听提交
//layui form
layui.use('form', function () {
    var form = layui.form;
    //监听提交
    form.on('submit(demo1)', function () {
        addCustomer();
        return false;
    });
    form.verify({
        repeat: function (value, item) {
            if($("#password").val()!=$("#repeatPassword").val()) {
                return "两次密码不一致";
                //layer.msg("两次密码不一致", { time: 1500})
            }
        }
    });
});
var addCustomer = function () {
    var data = JSON.stringify(customerAdd_app.customer);
    console.log(data);
    $.ajax({
        type: "post",
        url: "/api/customer/save",
        contentType: "application/json",
        data: data,
        success: function (res) {
            if (res.code === 0) {
                layer.msg("操作成功", {
                    time: 1500
                }, function () {
                    doCancel();
                    parent.layui.table.reload("tableDemo");
                })
            }
        },
        fail: function (res) {
            console.log(res)
        }
    })
}
//取消编辑 执行关闭
function doCancel() {
    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.close(index); //再执行关闭
}
window.onkeydown = function(){
    if (event.keyCode===13){  //回车键的键值为13
        $("#add").click();//模拟按下submit确认按钮
    }
}