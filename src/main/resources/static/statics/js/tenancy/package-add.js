    var packageData
    //data
    const pa = {
        id: "",
        opType: "",
        name: "",
        daysMin: "",
        daysMax: "",
        discount: "",
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
                p_app.package.name = packageData.name
                p_app.package.daysMax= packageData.daysMax
                p_app.package.daysMin = packageData.daysMin
                p_app.package.discount = packageData.discount
                p_app.package.btn_edit = true
            }
        } else {
            p_app.package.opType = "添加"
            p_app.package.btn_add = true
        }

    })

    //取消编辑 执行关闭
    function doCancel(){
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index); //再执行关闭
    }

    /**
     * 添加套餐 id = null
     * 编辑套餐 id != null
     */
    function doSave() {
        p_app.package.daysMax = Number(p_app.package.daysMax);
        p_app.package.daysMin = Number(p_app.package.daysMin);
        let data = JSON.stringify(p_app.package)
        console.log(data)
        $.ajax({
            type:"POST",
            url:"/api/PackageScheme/save",
            data:data,
            contentType:"application/json",
            success:function (res) {
                //console.log(res)
                if(res.code == 0){
                    layer.msg(res.msg,
                        {
                            time:1500
                        },
                        function () {
                          parent.layui.table.reload("PackageScheme")
                          doCancel()
                    })
                }else{
                    //layer.msg(res.msg)
                    handleAjax(res)
                }
            },
            fail:function (res) {
                console.log(res)
            }
        })
    }

    //查重
    function checkRepeat(){
        let data = JSON.stringify(p_app.package)
        $.ajax({
            type:"POST",
            url:"/api/PackageScheme/select",
            data:data,
            async:false,
            contentType:"application/json",
            success:function (res) {
                if(res.code == 0){
                    if(res.msg == "已存在"){
                        result = "used"
                    }else{
                        result = "use"
                    }
                }else{
                    handleAjax(res)
                    //layer.msg(res.msg)
                }
            },
            fail:function (res) {
                console.log(res)
            }
        })
        return result
    }

    //layui form
    layui.use('form', function () {
        var form = layui.form;

        //监听提交
        form.on('submit(formDemo)', function (data) {
            doSave()
            return false;
        });
        //表单校验
        form.verify({
            day_min: function (value, item) { //value：表单的值、item：表单的DOM对象
                var r = new RegExp("^[1-9]\\d*$");

                if(!r.test(Number($('#minDay').val()))){
                    return "天数不能为小数"
                }

                if (value <= 0) {
                    return "天数不能少于一天"
                }
            }
            , day_max: function (value, item) { //value：表单的值、item：表单的DOM对象
                var r = new RegExp("^[1-9]\\d*$");

                if(!r.test(Number($('#maxDay').val()))){
                    return "天数不能为小数"
                }
                if (value <= 0) {
                    return "天数不能少于一天"
                }
                if (value > 89) {
                    return "天数上限为89天"
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
            },
            repeat:function (value,item) {
               if ( checkRepeat() == "used" ){
                   return "套餐名已存在"
               }
            }
        });

    });



