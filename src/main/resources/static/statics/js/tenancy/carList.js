layui.use('table', function() {
    var table = layui.table;
    var tableIns=table.render({
        elem: '#carList'
        , height: 525
        , url: '/api/car/list'
        , toolbar: '#carListToolbar'
        , title: '车辆数据表'
        , cols: [
            [{type: 'checkbox', fixed: 'left'}
                , {field: 'id', title: 'ID', fixed: 'left', unresize: true, sort: true, width: 80}
                , {field: 'tncBrand', title: '品牌',templet:function (res) {
                    return res.tncBrand.name;
                }}
                , {field: 'series', title: '车系'}
                , {field: 'tncCarType', title: '车型',templet:function (res) {
                    return res.tncCarType.name;
                }}
                , {field: 'tncStore', title: '门店',templet:function (res) {
                    return res.tncStore.name;
                }}
                , {field: 'quantity', title: '总数量', sort: true}
                , {field: 'residual', title: '剩余数', sort: true}
                , {field: 'accessTimes', title: '热度', sort: true}
                , {field: 'status', title: '状态',templet:function (res) {
                    if(res.status == 1)
                        return "上架";
                    else if (res.status == 2)
                        return "下架";
                    else
                        return "其他";
                }}
                , {fixed: 'right', title: '操作', toolbar: '#sideBar', unresize: true, width: 260}]
        ]
        , page: true
    });

    //工具栏事件
    table.on('toolbar(lf_carList)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        //console.log(obj);
        switch (obj.event) {
            case "addCar":
                showAddWindows();
                break;
            case 'search':
                doSearch();
                break;
            case 'deleteSelected':
                var data = checkStatus.data;
                doDeleteData(data);
                break;
        }
    });
    //侧边栏事件
    table.on('tool(lf_carList)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象

        if (layEvent === 'detail') { //查看
            showDetailView(data.id);
        }else if(layEvent ==='license'){
            showAddLicense(data.id);
        } else if(layEvent ==='package'){
            showPackage(data.id);
        } else if(layEvent ==='carPic'){
            showCarPic(data.id);
        } else if (layEvent === 'del') { //删除
            doDeleteByBtn(obj);
        }
    });
    //监听排序事件
    //注：tool是工具条事件名，carList是table原始容器的属性 lay-filter="对应的值"
    table.on('sort(lf_carList)', function (obj) {
        //当前排序的字段名
        var orderField = obj.field;
        if (orderField =="accessTimes"){
            orderField="access_times"
        }
        //当前排序类型：desc（降序）、asc（升序）、null（空对象，默认排序）
        var orderType = obj.type;

        tableIns.reload({
            initSort: obj //记录初始排序，如果不设的话，将无法标记表头的排序状态。 layui 2.1.1 新增参数
            ,where: { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
                orderField: orderField //排序字段
                ,orderType: orderType//排序方式
            }
            ,page: {
                curr: 1 //重新从第 1 页开始
            }
        });

    });

    //查询
    function doSearch() {
        var searchText = $("#searchText").val();
        if(searchText ===""){
            layer.msg("请输入数据");
            $("#searchText").focus();
            return;
        }
        tableIns.reload({
            where: { //设定异步数据接口的额外参数，任意设
                search: searchText
            }
            ,page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    }

    /*显示车辆详情*/
    function showDetailView(carId) {
        layer.open({
            type: 2 //此处以iframe举例
            , title: '详情'
            , area: ['100%', '100%']
            , shade: 0
            , id: "3"
            , anim: 3
            , maxmin: true
            , offset: 'auto'
            , content: './updateCar.html?carId='+carId
            , zIndex: layer.zIndex //重点1
            , success: function (layero) {
                layer.setTop(layero); //重点2
            }
            ,cancel: function(index, layero){
                tableIns.reload({
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
                return true;
            }
        });
    }

    /*显示添加窗口*/
    function showAddWindows() {
//多窗口模式，层叠置顶
        layer.open({
            type: 2 //此处以iframe举例
            ,title: '添加车辆'
            ,area: ['100%', '100%']
            ,shade: 0
            ,id:"2"
            ,anim: 4
            ,maxmin: true
            ,offset: 'auto'
            ,content: './addCar.html'
            ,zIndex: layer.zIndex //重点1
            ,success: function(layero){
                layer.setTop(layero); //重点2
            }
            ,cancel: function(index, layero){
                tableIns.reload({
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
                return true;
            }
        });
    }


    function showAddLicense(carId) {
        layer.open({
            type: 2 //此处以iframe举例
            ,title: '添加车辆'
            ,area: ['100%', '100%']
            ,shade: 0
            ,id:"4"
            ,anim: 4
            ,maxmin: true
            ,offset: 'auto'
            ,content: './licensePlate.html?carId='+carId
            ,zIndex: layer.zIndex //重点1
            ,success: function(layero){
                layer.setTop(layero); //重点2
            }
            ,cancel: function(index, layero){
                tableIns.reload({
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                });
                return true;
            }
        });
    }

});
/*点击按钮删除*/
function doDeleteByBtn(tableObj) {
        layer.confirm('真的删除行么', function(index){
            tableObj.del(); //删除对应行（tr）的DOM结构，并更新缓存
            layer.close(index);
            //向服务端发送删除指令
            var data = tableObj.data;
            doDelete(data);
        });
    }
/*删除*/
function doDelete(data) {
    //console.log(data);
    $.ajax({
        url:'/api/car/delete',
        data:{carId:data.id},
        success:function (res) {
            if (res.code == 0){
                layer.msg("删除成功");
            }else{
                layer.msg("删除失败");
            }
        }
    })
}

//上传选中项
function doUploadData(data) {
    if(data.length<=0){
        layer.msg("请选择数据");
    }else{
        layer.confirm('确定上传么', function(index){
            layer.close(index);
            //向服务端发送删除指令
            window.location.reload();
        });
    }
}
/*按钮删除*/
function doDeleteData(data) {
    if(data.length<=0){
        layer.msg("请选择数据");
    }else{
        layer.confirm('真的删除行么', function(index){
            layer.close(index);
            //向服务端发送删除指令
            window.location.reload();
        });
    }
}


function showPackage(carId) {
    layer.open({
        type: 2 //此处以iframe举例
        ,title: '车辆套餐'
        ,area: ['100%', '100%']
        ,shade: 0
        ,id:"5"
        ,anim: 4
        ,maxmin: true
        ,offset: 'auto'
        ,content: './carPackage.html?carId='+carId
        ,zIndex: layer.zIndex //重点1
        ,success: function(layero){
            layer.setTop(layero); //重点2
        }
    });
}

function showCarPic(carId) {
    layer.open({
        type: 2 //此处以iframe举例
        ,title: '车辆照片'
        ,area: ['100%', '100%']
        ,shade: 0
        ,id:"6"
        ,anim: 4
        ,maxmin: true
        ,offset: 'auto'
        ,content: './carPic.html?carId='+carId
        ,zIndex: layer.zIndex //重点1
        ,success: function(layero){
            layer.setTop(layero); //重点2
        }
    });
}