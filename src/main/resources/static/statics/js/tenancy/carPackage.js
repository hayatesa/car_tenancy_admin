var carId = window.location.search.slice(7);


/*
* 套餐表
* */
layui.use('table', function(){
    var table = layui.table;
    //第一个实例
    table.render({
        elem: '#package'
        ,height: 312
        ,toolbar: '#tnc_package_toolbar'
        ,url: "/api/PackageScheme/list" //数据接口
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
                ,{field:'name', title:'套餐名'}
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