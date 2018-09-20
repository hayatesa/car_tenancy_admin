var carId = 1;
var tableData=new Array(); // 用于存放表格数据


/*车牌*/
layui.use('table', function(){
    var table = layui.table;

    function doRequestCarItemList() {
        $.ajax({
            url: "/api/carItem/"+carId
            ,type:"get"
            ,async:false
            ,dataType:"json"
            , success: function(result){
                console.log(result);
                tableData=result;
            }
        });
    }

    doRequestCarItemList();


    //第一个实例
    table.render({
        elem: '#car_num'
        ,height: 523
        ,data:tableData.data
        ,toolbar: '#tnc_toolbar'
        ,count:tableData.count
        ,page: true
        ,cols:  [
            [{type: 'checkbox', fixed: 'left'}
                ,{field:'id', title:'序号',  fixed: 'left', unresize: true, sort: true}
                ,{field:'number', title:'车牌号',edit: 'text'}
                ,{field:'status', title:'状态', sort: true,templet:function (res) {
                    switch (res.status) {
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
                }}
                ,{fixed: 'right', title:'操作', toolbar: '#barDemo',align:'center'}]
        ]
    });

    /*侧边栏事件*/
    table.on('tool(fcar_num)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        console.log("modeify");
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
                        //同步更新缓存对应的值
                        obj.update({
                            number: data.number,
                            status: data.status
                        });
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
                        }
                    }
                })
        }

        function doUpload(obj, data) {
            if(!data.hasOwnProperty("number") || data.number == ""){
                layer.msg("输入不能为空");
                return;
            }
            $.ajax({
                url: "/api/carItem/upload",
                data:data,
                method:"POST",
                success:function (res) {
                    console.log(res);
                    if (res.code ==0){
                        layer.msg("添加成功");
                        obj.update({
                            status: 0
                        });
                        table.reload("car_num");
                    }
                }
            })
            //重新请求数据刷新数据
            doReflushCarItemData();
        }

        function doReflushCarItemData() {
            $.ajax({
                url: "/api/carItem/"+carId
                ,type:"get"
                ,dataType:"json"
                , success: function(result){
                    tableData=result.data;
                    table.reload("car_num");
                }
            });
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
            case  'upload':
                data.status = 0;
                doUpload(obj,data);
                break;
        }

    });
    //工具栏事件
    table.on('toolbar(fcar_num)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);

        function doRepairSelected(obj, data) {
            console.log(data);
            var flag = false;
            for(var i=0;i<data.length;i++){
                if(data[i].id == ""){
                    layer.msg("只能选择已上传车牌");
                    flag=true;
                    break;
                }
            }
            if(flag){
                return;
            }
            for(var i=0;i<data.length;i++){
                data[i].status=2;
            }
            $.ajax({
                url:"/api/carItem/batchrepair",
                method:"POST",
                data:{dataList:JSON.stringify(data)},
                success:function (res) {
                    if (res.code ==0){
                        // layer.msg("上传成功");
                        window.location.reload();
                    }
                }
            })
        }

        function doUploadSelected(obj, data) {
            var flag = false;
            for(var i=0;i<data.length;i++){
                if(data[i].id != ""){
                    layer.msg("只能选择未上传车牌");
                    flag=true;
                    break;
                }
                if(!data[i].hasOwnProperty("number") || data[i].number == ""){
                    layer.msg("上传车牌号码不能为空");
                    flag=true;
                    break;
                }
            }
            if(flag){
                return;
            }
            $.ajax({
                url:"/api/carItem/batchupload",
                method:"POST",
                data:{dataList:JSON.stringify(data)},
                success:function (res) {
                    if (res.code ==0){
                        // layer.msg("上传成功");
                        window.location.reload();
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
            $.ajax({
                url:"/api/carItem/search",
                method:"POST",
                data:{
                    "search":searchText
                },
                success:function (res) {
                    console.log(res);
                    if (res.code == 0){
                        table.reload('car_num',{
                            data : res.data
                        });
                    }
                }

            })

        }

        function doAddCarNum() {
            var oldData = [];
            var newdata={
                "id":'',
                "number":"",
                "carId":carId,
                "status":-1
            };
            oldData.push(newdata);
            var tableBak = table.cache["car_num"];
            for (var i=0;i<tableBak.length;i++){
                oldData.push(tableBak[i]);
            }
            table.reload('car_num',{
                data : oldData
            });
            table.render(); //更新全部
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
            case 'addCarNum':
                doAddCarNum();
                break;
        };
    });
});


function getOptions(code) {
    switch (code) {
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

