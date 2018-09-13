var packageData
//data
const pa = {
    id: "",
    opType: "",
    pName: "",
    pMin: "",
    pMax: "",
    pDiscount: "",
    btn_add: false,
    btn_edit: false
}
//Vue data
const p_data = {
    package: pa
}
//Vue methods
const p_app_methods = {
    // addPackage:doAdd,
    // editPackage:doEdit
}
//Vue app
const p_app = new Vue({
    el: "#packageAdd",
    data: p_data,
    methods: p_app_methods
})


$(document).ready(function () {
    var str = decodeURIComponent(window.location.search.slice(6))
    if (str != "") {
        packageData = JSON.parse(str)

        if (packageData != "") {
            p_app.package.opType = "编辑"
            p_app.package.id = packageData.id
            p_app.package.pName = packageData.pName
            p_app.package.pMax = packageData.pMax
            p_app.package.pMin = packageData.pMin
            p_app.package.pDiscount = packageData.discount
            p_app.package.btn_edit = true
        }
    } else {
        p_app.package.opType = "添加"
        p_app.package.btn_add = true
    }

})

//添加套餐
function doAdd() {
    var data = p_app.package
    console.log(data)
    layer.msg("添加成功")
    // $.ajax({
    //     type:"post",
    //     url:"",
    //     data:data,
    //     success:function (res) {
    //     },
    //     fail:function (res) {
    //     }
    // })
}

//编辑套餐
function doEdit() {
    var data = p_app.package
    console.log(data)
    layer.msg("编辑成功")

}

//layui form
layui.use('form', function () {
    var form = layui.form;

    //监听提交
    form.on('submit(formDemo)', function (data) {
        if (p_app.package.btn_add) {
            doAdd()
            console.log("add")
        } else {
            doEdit()
            console.log("edit")
        }
        //layer.msg(JSON.stringify(data.field));
        // console.log(JSON.stringify(data.field))
        return false;
    });
    form.verify({
        day_min: function (value, item) { //value：表单的值、item：表单的DOM对象
            if (value <= 0) {
                return "天数不能少于一天"
            }
        }
        , day_max: function (value, item) { //value：表单的值、item：表单的DOM对象
            if (value <= 0) {
                return "天数不能少于一天"
            }
            if (Number($('#minDay').val()) >= value) {
                return "上限比下限小？"
            }
        },
        discount: function (value, item) {
            if (value <= 0) {
                return "大于0"
            }
            if (value >= 1) {
                return "小于1"
            }
        }
    });
});



