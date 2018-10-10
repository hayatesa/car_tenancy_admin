var carId = window.location.search.slice(7);

/*车牌*/
layui.use('table', function(){
    var table = layui.table;

    //第一个实例
    var tableIns=table.render({
        elem: '#car_num'
        ,height: 523
        ,url:"/api/carItem/"+carId
        ,toolbar: '#tnc_toolbar'
        ,page: true
        ,cols:  [
            [{type: 'checkbox', fixed: 'left'}
                ,{field:'id', title:'序号',  fixed: 'left', unresize: true, sort: true}
                ,{field:'number', title:'车牌号', sort: true,edit: 'text'}
                ,{field:'status', title:'状态', sort: true,templet:function (res) {
                    return getStatus(res.status);
                }}
                ,{fixed: 'right', title:'操作', toolbar: '#barDemo',align:'center'}]
        ]
    });

    /*侧边栏事件*/
    table.on('tool(fcar_num)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        console.log(data);

        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象
        function doUpdateCarItem(obj, data) {
            $.ajax({
                url: "/api/carItem/update",
                data: JSON.stringify(data),
                dataType:"json",
                contentType:"application/json",
                method: "POST",
                success: function (res) {
                    console.log(res);
                    if (res.code == 0) {
                        tableIns.reload();
                    }
                }
            })
        }

        function doDelete(obj, data) {
                //向服务端发送删除指令
                $.ajax({
                    url: "/api/carItem/"+data.id,
                    method:"DELETE",
                    success:function (res) {
                        console.log(res);
                        if (res.code ==0){
                            layer.msg("删除成功");
                            tableIns.reload({
                                page: {
                                    curr: 1 //重新从第 1 页开始
                                }
                            });
                        }
                    }
                })
        }

        function doUpdateCarItemStatus(obj, data, options) {
            $.ajax({
                url: "/api/carItem/"+data.id+"/"+data.status,
                contentType:"application/json",
                method:"PUT",
                success:function (res) {
                    console.log(res);
                    if (res.code ==0){
                        showOptionsTips(options);
                        //同步更新缓存对应的值
                        obj.update({
                            status:data.status
                        });
                        table.reload("car_num");
                    }
                }
            })
        }

        switch (layEvent) {
            case 'edit':
                layer.confirm('真的修改么', function(index){
                    doUpdateCarItem(obj,data);
                    layer.close(index);
                });
                break;
            case  'del':
                layer.confirm('真的删除行么', function(index){
                    doDelete(obj,data,-1);
                    layer.close(index);
                });
                break;
            case 'repair':
                layer.confirm('确定下架维修么', function(index){
                    data.status = 2;
                    doUpdateCarItemStatus(obj,data,2);
                    layer.close(index);
                });
                break;
            case 'scrap':
                layer.confirm('确定报废么，报废后该车将无法再次上架', function(index){
                    data.status = 3;
                    doUpdateCarItemStatus(obj,data,3);
                    layer.close(index);
                });
                break;
            case 'shelves':
                data.status = 0;
                doUpdateCarItemStatus(obj,data,4);
                break;
        }

    });
    //工具栏事件
    table.on('toolbar(fcar_num)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);

        function doRepairSelected(obj, data) {
            console.log(data);
            for(var i=0;i<data.length;i++){
                data[i].status=2;
            }
            $.ajax({
                url:"/api/carItem/batchrepair",
                method:"POST",
                data:{dataList:JSON.stringify(data)},
                success:function (res) {
                    if (res.code ==0){
                        layer.msg("下架成功");
                        tableIns.reload();
                        // window.location.reload();
                    }
                }
            })
        }

        function doUploadSelected(obj, data) {
            console.log(data);
            for(var i=0;i<data.length;i++) {
                data[i].status = 0;
            }
            $.ajax({
                url:"/api/carItem/batchshelves",
                method:"GET",
                data:{dataList:JSON.stringify(data)},
                success:function (res) {
                    if (res.code ==0){
                        layer.msg("上架成功");
                        tableIns.reload();
                        // window.location.reload();
                    }
                }
            })

        }
        //查询
        function doSearch() {
            var searchText = $("#searchText").val();
            if(searchText ==""||searchText ==null){
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

        switch(obj.event){
            case 'search':
                doSearch();
                break;
            case 'repairSelected':
                var data = checkStatus.data;
                doRepairSelected(obj,data);
                break;
            case 'uploadSelected':
                var data = checkStatus.data;
                doUploadSelected(obj,data);
                break;
        };
    });

    //监听排序事件
    //注：tool是工具条事件名，fcar_num是table原始容器的属性 lay-filter="对应的值"
    table.on('sort(fcar_num)', function (obj) {
        //当前排序的字段名
        var orderField = obj.field;
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
});


// function getOptions(code) {
//     switch (code) {
//         case 0:
//             return "可出租";
//         case 1:
//             return "正在出租";
//         case 2:
//             return "维修中";
//         case 3:
//             return "报废";
//         case -1:
//             return "未录入";
//         case -2:
//             return "下架";
//         default:
//             return "其他";
//     }
// }


function getStatus(status) {
    switch (status) {
        case 0:
            return "可出租";
        case 1:
            return "正在出租";
        case 2:
            return "维修中";
        case 3:
            return "报废";
        case -1:
            return "未录入";
        case -2:
            return "下架";
        default:
            return "其他";
    }

}

function showOptionsTips(code) {
    switch (code) {
        case -1:
            layer.msg("删除成功");
            break;
        case 0:
            layer.msg("添加成功");
            break;
        case 2:
            layer.msg("维修中");
            break;
        case 3:
            layer.msg("报废成功");
            break;
        case 4:
            layer.msg("上架成功");
            break;
        case 5:
            layer.msg("上传成功");
            break;

    }
}


function addCarItem(){
    var text = $("#carItemInput").val();
    if( text== ""){
        layer.msg("车牌号不能为空！");
        $("#carItemInput").focus();
        return;
    }
    var data = {
        "number":text,
        "carId":carId,
        "status":0
    }
    $.ajax({
        url: "/api/carItem/upload",
        data:data,
        method:"POST",
        success:function (res) {
            console.log(res);
            if (res.code ==0){
                layer.msg("添加成功");
                layui.table.reload('car_num', {
                    url: "/api/carItem/"+carId
                });
            }else{
                layer.msg(res.msg);
            }
        }
    })


}

