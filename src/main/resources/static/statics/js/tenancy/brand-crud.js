//data
var b = {
    id: "",
    name: ""
};

//Vue data
var b_data = {
    brand: b
};

//Vue app，添加页
var brandAdd_app = new Vue({
    el: "#brandAdd",
    data: b_data
});

//Vue app，编辑页
var brandEdit_app = new Vue({
    el: "#brandEdit",
    data: b_data
});

//读取需要编辑时原有数据
$(document).ready(function () {
    var str = decodeURIComponent(window.location.search.slice(7));
    if (str !== "") {
        var data = JSON.parse(str);
        if (data !== "") {
            brandEdit_app.brand = data;
        }
    }
});

//layui form
layui.use('form', function () {
    var form = layui.form;

    //监听提交
    form.on('submit(demo1)', function () {
        doAdd();
        return false;
    });

    form.on('submit(demo2)', function () {
        doEdit();
        return false;
    });

    form.verify({
        repeat: function (value, item) {
            if (checkRepeat() == "exist") {
                return "该品牌已存在";
            }
        }
    });
});

//添加品牌
function doAdd() {
    var data = JSON.stringify(brandAdd_app.brand);
    $.ajax({
        type: "post",
        url: "/api/tncBrand/add",
        contentType: "application/json",
        data: data,
        success: function (res) {
            if (res.code === 0) {
                layer.msg(res.msg, {
                        time: 1500
                    },
                    function () {
                        doCancel();
                        parent.layui.table.reload("brand");
                    })
            }
        },
        fail: function (res) {
            console.log(res)
        }
    })
}

//编辑品牌
function doEdit() {
    var data = JSON.stringify(brandAdd_app.brand);
    $.ajax({
        type: "post",
        url: "/api/tncBrand/edit",
        contentType: "application/json",
        data: data,
        success: function (res) {
            if (res.code === 0) {
                layer.msg(res.msg, {
                        time: 1500
                    },
                    function () {
                        doCancel();
                        parent.layui.table.reload("brand");
                    })
            }
        },
        fail: function (res) {
            console.log(res)
        }
    })
}

//判断品牌名是否重复
function checkRepeat() {
    var data = JSON.stringify(brandAdd_app.brand);
    var result = "not exist";
    $.ajax({
        type: "post",
        url: "/api/tncBrand/find",
        contentType: "application/json",
        async: false,
        data: data,
        success: function (res) {
            if (res.code === 0)
                result = "exist";
            else
                result = "not exist";
        },
        fail: function (res) {
            console.log(res);
        }
    });
    return result;
}

//取消编辑 执行关闭
function doCancel() {
    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.close(index); //再执行关闭
}

