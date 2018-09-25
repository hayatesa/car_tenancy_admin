var tableId = "carList",tableFilter = "lf_carList";
layui.use('table', function() {
    var table = layui.table;
    table.render({
        elem: '#carList'
        , height: 525
        , url: '/api/car/list'
        , toolbar: '#carListToolbar'
        , limit: 10
        , title: '车辆数据表'
        , cols: [
            [{type: 'checkbox', fixed: 'left'}
                , {field: 'id', title: 'ID', fixed: 'left', unresize: true, sort: true, width: 80}
                , {field: 'tncBrand', title: '品牌',templet:function (res) {
                    return res.tncBrand.name;
                }}
                , {field: 'tncCarType', title: '车型',templet:function (res) {
                    return res.tncCarType.name;
                }}
                , {field: 'tncStore', title: '门店',templet:function (res) {
                    return res.tncStore.name;
                }}
                , {field: 'quantity', title: '数量', sort: true}
                , {field: 'residual', title: '剩余车辆', sort: true}
                , {field: 'accessTimes', title: '访问次数', sort: true}
                , {field: 'status', title: '状态'}
                , {fixed: 'right', title: '操作', toolbar: '#sideBar', unresize: true, width: 300}]
        ]
        , page: true
    });

    //工具栏事件
    table.on('toolbar(lf_carList)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'search':
                doSearch();
                break;
            case 'uploadSelected':
                var data = checkStatus.data;
                doUploadData(data);
                break;
            case 'addCar':
                showAddWindows();
                break;
            case 'deleteSelected':
                var data = checkStatus.data;
                doDeleteData(data);
                break;
        }
        ;
    });
    //侧边栏事件
    table.on('tool(lf_carList)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象

        if (layEvent === 'detail') { //查看
            //do somehing
            // console.log("detail");
            showDetailView();
        }else if(layEvent ==='license'){
            showAddLicense();
        } else if (layEvent === 'del') { //删除
            doDeleteByBtn(obj);
        } else if (layEvent === 'edit') { //编辑
            //do something
            console.log("edit");
            //同步更新缓存对应的值
            /*obj.update({
                store_id: '123'
                ,title: 'xxx'
            });*/
        }
    });
    //监听排序事件
    //注：tool是工具条事件名，carList是table原始容器的属性 lay-filter="对应的值"
    table.on('sort(lf_carList)', function (obj) {

        //当前排序的字段名
        var orderField = obj.field;
        //当前排序类型：desc（降序）、asc（升序）、null（空对象，默认排序）
        var orderType = obj.type;
        /*$.ajax({
                url:"/statics/carListReflush_data.json",
                success:function (res) {
                    console.log(res);
                    if (res.code == 0){
                        table.reload();
                        table.render(); //更新全部
                    }
                }

            })
    });*/
    });
});
/*点击按钮删除*/
function doDeleteByBtn(tableObj) {
        layer.confirm('真的删除行么', function(index){
            tableObj.del(); //删除对应行（tr）的DOM结构，并更新缓存
            layer.close(index);
            //向服务端发送删除指令
        });
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
//查询
function doSearch() {

    var searchText = $("#searchText").val();
    if(searchText ===""){
        layer.msg("请输入数据");
        $("#searchText").focus();
        return;
    }
    console.log(searchText);
    // $.ajax({
    //     url:"",
    //     data:'',
    //     success:function (res) {
    //         if (res.code == 0){
    //             table.reload(tableId,{
    //                 data : res.data
    //             });
    //         }
    //     }
    //
    // })

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
    });
}

/*显示车辆详情*/
function showDetailView() {
    layer.open({
        type: 2 //此处以iframe举例
        , title: '车辆详情'
        , area: ['100%', '100%']
        , shade: 0
        , id: "3"
        , anim: 3
        , maxmin: true
        , offset: 'auto'
        , content: './addCar.html'
        , zIndex: layer.zIndex //重点1
        , success: function (layero) {
            layer.setTop(layero); //重点2
        }
    });
}

function showAddLicense() {
    layer.open({
        type: 2 //此处以iframe举例
        ,title: '添加车辆'
        ,area: ['100%', '100%']
        ,shade: 0
        ,id:"4"
        ,anim: 4
        ,maxmin: true
        ,offset: 'auto'
        ,content: './licensePlate.html'
        ,zIndex: layer.zIndex //重点1
        ,success: function(layero){
            layer.setTop(layero); //重点2
        }
    });
}
