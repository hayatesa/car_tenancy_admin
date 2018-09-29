var carId =window.location.search.slice(7);

/*
* 套餐表
* */
layui.use('table', function(){
    var table = layui.table;
    //第一个实例
    var tableIns = table.render({
        elem: '#package'
        ,height: 312
        ,toolbar: '#tnc_package_toolbar'
        ,url: "/api/priceScheme/list?carId="+carId //数据接口
        ,parseData: function(res){ //res 即为原始返回的数据
            return {
                "code": res.code, //解析接口状态
                "msg": res.msg, //解析提示文本
                "count": res.count, //解析数据长度
                "data": res.data //解析数据列表
            };
        }
        ,page: false //关闭分页
        ,cols:  [
            [{type: 'checkbox', fixed: 'left'}
                ,{field:'name', title:'套餐名',templet:function (res) {
                    return res.tncPackageScheme.name;
                }}
                ,{field:'basePrice', title:'基础价',  edit: 'text', sort: true, totalRow: true}
                ,{field:'baseHourPrice', title:'小时价',  edit: 'text', sort: true, totalRow: true}
                ,{field:'servicePrice', title:'服务费', edit: 'text', sort: true, totalRow: true}
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
            case 'cancelPackage':
                var data = checkStatus.data;
                doCancelPackage(data);
                break;
            case 'addPackage':
                doAddPackage();
                break;
        }

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

        if(layEvent === 'del'){ //删除
            layer.confirm('真的删除行么', function(index){
                obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                layer.close(index);
                //向服务端发送删除指令
                doDeletePrice(data);
            });
        } else if(layEvent === 'edit'){ //编辑
            //do something
            doUpdatePrice(data);
        }
    });
    function doAddPackage() {
        console.log("asda");
        layer.open({
            type: 2 //此处以iframe举例
            ,title: '价格方案'
            ,area: ['100%', '100%']
            ,shade: 0
            ,id:"7"
            ,anim: 4
            ,maxmin: true
            ,offset: 'auto'
            ,content: './addPriceScheme.html?carId='+carId
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
/*批量删除套餐*/
function doCancelPackage(data) {
    console.log(data);
    if(data.length<=0){
        layer.msg("未选中！");
        return;
    }
    $.ajax({
        url: "/api/priceScheme/batchDelete",
        data:{dataList:JSON.stringify(data)},
        success:function (res) {
            if (res.code == 0) {
                layer.msg("删除成功");
            }
        }
    })
}

/*修改价格*/
function doUpdatePrice(data) {
     // console.log(data);
    delete  data.gmtCreate;
    delete  data.gmtModified;
    $.ajax({
        url: "/api/priceScheme/update",
        data:data,
        success:function (res) {
            if (res.code == 0) {
                layer.msg("修改成功");
            }
        }
    })
}
/*删除套餐*/
function doDeletePrice(data) {

    $.ajax({
        url: "/api/priceScheme/delete",
        data:{id:data.id},
        success:function (res) {
            if (res.code == 0) {
                layer.msg("删除成功");
            }
        }
    })
}

