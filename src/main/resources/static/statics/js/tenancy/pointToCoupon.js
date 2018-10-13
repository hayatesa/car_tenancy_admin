layui.use('table', function () {
    var table = layui.table;
    table.render({
        elem: '#tableDemo'
        , url: '/api/tncCouponPoint/list'
        , toolbar: '#toolbarDemo'
        , title: '积分商城'
        , page: true
        , cols: [
            [{
                field: 'id',
                title: 'ID',
                width: '10%',
                fixed: 'left',
                unresize: true,
                sort: true
            }
                , {field: 'amount', title: '优惠券面值(元)', edit: 'text', sort: true}
                , {field: 'point', title: '可兑换的积分', edit: 'text', sort: true}
                , {field: 'gmtCreate', title: '创建时间', width: '20%', sort: true}
                , {field: 'gmtModified', title: '修改时间', width: '20%', sort: true}
                , {
                fixed: 'right', title: '操作', width: '15%', templet: function (res) {
                    var a = '<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>';
                    if (res.status === 1) {
                        a = a + '<a class="layui-btn layui-btn-normal layui-btn-xs" onclick="disableCoupon(' + res.id + ')">禁用</a>'
                    } else {
                        a = a + '<a class="layui-btn layui-btn-warm layui-btn-xs" onclick="disabledCoupon(' + res.id + ')">解禁</a>'
                    }
                    return a;
                }
            }]
        ]
    });
    //操作事件
    table.on('tool(pointToCoupon)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm('真的删除行么', function (index) {
                delB(data.id);
                layer.close(index);
            });
        } else if (obj.event === 'edit') {
            editCoupon(data.id);
        }
    });

});
function editCoupon(id){
    var url = '/tenancy/p/PointToCouponAdd.html?id=' + id;
    layer.open({
        type: 2 //此处以iframe举例
        , title: '编辑优惠券'
        , area: ['500px', '250px']
        , shade: 0
        , id: "2"
        , anim: 4
        , maxmin: true
        , offset: 'auto'
        , content: url
        , zIndex: layer.zIndex //重点1
        , success: function (layero) {
            layer.setTop(layero); //重点2
        }
    });
}

//打开添加窗口
function coupon_add() {
    var url = './PointToCouponAdd.html';
    layer.open({
        type: 2 //此处以iframe举例
        , title: '添加优惠券'
        , area: ['500px', '250px']
        , shade: 0
        , id: "2"
        , anim: 4
        , maxmin: true
        , offset: 'auto'
        , content: url
        , zIndex: layer.zIndex //重点1
        , success: function (layero) {
            layer.setTop(layero); //重点2
        }
    });
}

//取消编辑 执行关闭
function doCancel() {
    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.close(index); //再执行关闭
}
/**添加优惠券*/
function addCoupon() {
    var data = JSON.stringify(couponData.coupon);
    $.ajax({
        type:"post",
        url:"/api/tncCouponPoint/add",
        contentType: "application/json",
        data:data,
        success:function (res) {
            console.log(res);
            if(res.code===0){
                layer.msg("操作成功", {
                    time: 1500
                }, function () {
                    doCancel();
                    parent.layui.table.reload("tableDemo");
                })
            }else {
                handleAjax();
            }
        }
    })
}
/*禁用客户*/
function disableCoupon(id){
    $.ajax({
        type:"POST",
        url:"/api/tncCouponPoint/disable",
        data:{
            id:id,
            select:0
        },
        success:function (res) {
            if(res.code == 0){
                layer.msg("操作成功", {
                    time:1500
                }, function () {
                    layui.table.reload("tableDemo")
                })
            }
        }
    })
}
/*解禁客户*/
function disabledCoupon(id){
    $.ajax({
        type:"POST",
        url:"/api/tncCouponPoint/disable",
        data:{
            id:id,
            select:1
        },
        success:function (res) {
            if(res.code == 0){
                layer.msg("操作成功", {
                    time:1500
                }, function () {
                    layui.table.reload("tableDemo")
                })
            }
        }
    })
}

var couponData = {
    coupon: {
        id: "",
        amount: "",
        point: ""
    }
}
var coupon_app = new Vue({
    el: "#couponSave",
    data: couponData,
    methods:{
        coupon_add:coupon_add,
        disableCoupon:disableCoupon,
        disabledCoupon:disabledCoupon,
    },
    mounted: function () {
        layui.use('form', function () {
            var form = layui.form;
            form.render();
        });
    }
});
//监听提交
//layui form
layui.use('form', function () {
    var form = layui.form;
    //监听提交
    form.on('submit(demo1)', function () {
        addCoupon();
        return false;
    });
});

