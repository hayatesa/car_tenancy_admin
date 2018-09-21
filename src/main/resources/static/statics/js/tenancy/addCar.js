const vueData={
    brands:[],
    carTypes:[],
    storeAddr:[],
    packages:[],
    selected: '',
    province: []
}
const  doCreated =function ()
{
    /*请求品牌列表*/
    doRequestBrand();
    /*请求车类型*/
    doRequestCarType();
    /*请求门店地址*/
    doRequestProvince();
    doRequestArea();
    doRequestCity();
    doRequestStore();
    /*请求套餐类型*/
    //doRequestPackages();
}

const vueMethods={
    doSubmitCarBaseMsg:doSubmitCarBaseMsg
}

var vueObj = new Vue({
    el: "#addCar",
    data:vueData,
     created:doCreated,
    // methods:vueMethods
});

 /*上传汽车图片*/
layui.use('upload', function(){
    var upload = layui.upload;

    //执行实例
    var uploadInst = upload.render({
        elem: '#uploadPhoto' //绑定元素
        ,url: '/api/pic/upload' //上传接口
        ,auto: false //选择文件后不自动上传
        ,multiple: true
        ,bindAction: '#uploadListAction' //指向一个按钮触发上传
        ,before: function(obj){
            //预读本地文件示例，不支持ie8
            obj.preview(function(index, file, result){
                $('#demo2').append('<img src="'+ result +'" alt="'+ file.name +'" class="layui-upload-img">')
            });
        }
        ,choose: function(obj){
            //将每次选择的文件追加到文件队列
            var files = obj.pushFile();

            //预读本地文件，如果是多文件，则会遍历。(不支持ie8/9)
            obj.preview(function(index, file, result){
                console.log(index); //得到文件索引
                console.log(file); //得到文件对象
                console.log(result); //得到文件base64编码，比如图片
                var pit = '<img src="'+result+'" class="layui-upload-img" style="width: 120px;height: 80px;margin-right: 10px;"/>';
                $("#previewPicture").append(pit);
                //obj.resetFile(index, file, '123.jpg'); //重命名文件名，layui 2.3.0 开始新增

                //这里还可以做一些 append 文件列表 DOM 的操作

                //obj.upload(index, file); //对上传失败的单个文件重新上传，一般在某个事件中使用
                //delete files[index]; //删除列表中对应的文件，一般在某个事件中使用
            });
        }
        ,done: function(res){
            //上传完毕回调
            console.log(res);
        }
        ,error: function(res){
            //请求异常回调
            console.log(res);
        }
    });
});
/*
* 套餐表
* */
layui.use('table', function(){
    var table = layui.table;
    var tableData=new Array();
    tableData = {
        "code": 0,
        "msg": "",
        "count": 3000000,
        "data": [
            {
                "id": "10001",
                "packageName": "日租",
                "deposit":"3000",
                "basePrice": "500",
                "ServicePrice": "300",
                "discount": "浙江杭州"
            },
            {
                "id": "10002",
                "packageName": "月租",
                "deposit":"3000",
                "basePrice": "600",
                "ServicePrice": "200",
                "discount": "浙江杭州"
            }]
    };
    //第一个实例
    table.render({
        elem: '#package'
        ,height: 312
        ,toolbar: '#tnc_package_toolbar'
        // ,url: "" //数据接口
        ,data:tableData.data
        ,page: false //关闭分页
        ,cols:  [
            [{type: 'checkbox', fixed: 'left'}
                ,{field:'packageName', title:'套餐名'}
                ,{field:'basePrice', title:'基础价',  edit: 'text', sort: true, totalRow: true}
                ,{field:'ServicePrice', title:'服务费', edit: 'text', sort: true, totalRow: true}
                ,{field:'deposit', title:'押金', edit: 'text', sort: true}
                ,{field:'discount', title:'折扣',  edit: 'text', sort: true, totalRow: true}
                ,{fixed: 'right', title:'操作', toolbar: '#barDemo',align:'center'}]
        ]
    });
    //工具栏事件
    table.on('toolbar(fpackage)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);
        console.log(obj);
        switch(obj.event){
            case 'getCheckData':
                var data = checkStatus.data;
                layer.alert(JSON.stringify(data));
                break;
            case 'getCheckLength':
                var data = checkStatus.data;
                layer.msg('选中了：'+ data.length + ' 个');
                break;
            case 'isAll':
                layer.msg(checkStatus.isAll ? '全选': '未全选')
                break;
            case 'addPackage':
                // layer.msg("asd");
                var oldData =  table.cache["package"];
                var data1={
                };
                oldData.push(data1);
                table.reload('package',{
                    data : oldData
                });
                table.render(); //更新全部
                break;
        };
    });
    table.on('edit(fpackage)', function(obj){ //注：edit是固定事件名，test是table原始容器的属性 lay-filter="对应的值"
        console.log(obj.value); //得到修改后的值
        console.log(obj.field); //当前编辑的字段名
        console.log(obj.data); //所在行的所有相关数据
    });
    table.on('tool(fpackage)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象

        if(layEvent === 'detail'){ //查看
            //do somehing
        } else if(layEvent === 'del'){ //删除
            layer.confirm('真的删除行么', function(index){
                obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                layer.close(index);
                //向服务端发送删除指令
            });
        } else if(layEvent === 'edit'){ //编辑
            //do something

            //同步更新缓存对应的值
            obj.update({
                username: '123'
                ,title: 'xxx'
            });
        }
    });

});

/*请求品牌列表*/
function  doRequestBrand() {
    $.ajax({
        type:"GET",
        url:"/statics/brand_data.json",
        success:function (res) {
            if(res.code ==0){
                console.log(res);
                var b_data = res.data;
                // vueObj.data.brands = res.data;

                for(var i=0;i<b_data.length;i++){
                    $("#brand_id").append('<option value="'+b_data[i].id+'">'+b_data[i].name+'</option>');
                    console.log(i);
                }
                layui.form.render('select','brandFilter');
            }

        },
    })
}
/*请求车类型*/
function doRequestCarType() {
    $.ajax({
        type:"GET",
        url:"/statics/carType_data.json",
        success:function (res) {
            if(res.code ==0){
                console.log(res);
                var b_data = res.data;
                // vueObj.data.brands = res.data;
                for(var i=0;i<b_data.length;i++){
                    $("#car_type_id").append('<option value="'+b_data[i].id+'">'+b_data[i].name+'</option>');
                    console.log(i+"ui");
                }
                layui.form.render('select','carTypeFilter');
            }

        },
    })
}
/*请求门店地址*/
function doRequestProvince() {
    $.ajax({
        type:"GET",
        url:"/statics/province_data.json",
        success:function (res) {
            if(res.code ==0){
                console.log(res);
                var b_data = res.data;
                 // vueObj.data.province = res.data;
                for(var i=0;i<b_data.length;i++){
                    $("#province_id").append('<option value="'+b_data[i].id+'">'+b_data[i].name+'</option>');
                    console.log(i+"ui");
                }
                layui.form.render('select','store_addr');
            }
        },
    })
}
/*联动地址请求 城市*/
function doRequestCity() {
    $.ajax({
        type:"GET",
        url:"/statics/city_data.json",
        success:function (res) {
            if(res.code ==0){
                console.log(res);
                var b_data = res.data;
                // vueObj.data.province = res.data;
                for(var i=0;i<b_data.length;i++){
                    $("#city_id").append('<option value="'+b_data[i].id+'">'+b_data[i].name+'</option>');
                    console.log(i+"city_id");
                }
                layui.form.render('select','store_addr');
            }
        },
    })
}
/*联动地址请求 县区*/
function doRequestArea() {
    $.ajax({
        type:"GET",
        url:"/statics/city_data.json",
        success:function (res) {
            if(res.code ==0){
                console.log(res);
                var b_data = res.data;
                // vueObj.data.province = res.data;
                for(var i=0;i<b_data.length;i++){
                    $("#area_id").append('<option value="'+b_data[i].id+'">'+b_data[i].name+'</option>');
                    console.log(i+"area_id");
                }
                layui.form.render('select','store_addr');
            }
        },
    })
}
/*联动地址请求 门店*/
function doRequestStore() {
    $.ajax({
        type:"GET",
        url:"/statics/city_data.json",
        success:function (res) {
            if(res.code ==0){
                console.log(res);
                var b_data = res.data;
                // vueObj.data.province = res.data;
                for(var i=0;i<b_data.length;i++){
                    $("#store_id").append('<option value="'+b_data[i].id+'">'+b_data[i].name+'</option>');
                    console.log(i+"store_id");
                }
                layui.form.render('select','store_addr');
            }
        },
    })
}
/*请求套餐类型*/
function doRequestPackages() {
    $.ajax({
        type:"GET",
        url:"/statics/table_data.json",
        success:function (res) {
            if(res.code ==0){
                console.log(res);
                // vueObj.data.packages = res.data;
            }
        },
    })
}
/*提交车辆基本信息*/
 function doSubmitCarBaseMsg() {
    console.log("submit");
}