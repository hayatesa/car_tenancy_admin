layui.use('table', function () {
    var table = layui.table;
    table.render({
        elem: '#store'
        , url: '/api/tncStore/list'
        , toolbar: '#toolbarDemo'
        , title: '门店信息表'
        , page: true
        , cols: [
            [{
                field: 'id',
                title: 'ID',
                width: 80,
                align: 'center',
                fixed: 'left',
                unresize: true,
                sort: true
            }
                , {field: 'name', title: '门店名称', width: 150}
                , {field: 'managerName', title: '门店负责人', width: 150}
                , {field: 'managerPhone', title: '门店负责人手机', width: 150}
                , {
                field: 'tncAddress', title: '门店地址', templet: function (res) {
                    var province = res.tncAddress == null ? "" : res.tncAddress.province.name;
                    var city = res.tncAddress == null ? "" : res.tncAddress.city.name;
                    var area = res.tncAddress == null ? "" : res.tncAddress.area.name;
                    var detail = res.tncAddress == null ? "" : res.tncAddress.detail;
                    return province + city + area + detail;
                }
            }
                , {field: 'serviceTel', title: '客服电话', width: 100}
                , {field: 'gmtCreate', title: '创建时间', width: 150}
                , {field: 'gmtModified', title: '修改时间', width: 150}
                , {field: 'status', title: '状态', width: 80}
                , {fixed: 'right', title: '操作', toolbar: '#barDemo', width: 150, align: 'center'}]
        ]
    });

    //工具栏事件
    table.on('toolbar(store)', function (obj) {
        if (obj.event === 'add') {
            addWindow();
        }
    });

    //操作事件
    table.on('tool(store)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm('真的删除行么', function (index) {
                delStore(data.id);
                layer.close(index);
            });
        } else if (obj.event === 'edit') {
            editWindow(JSON.stringify(data));
        }
    });

});

//打开门店的添加窗口
function addWindow() {
    var url = './storeAdd.html';
    layer.open({
        type: 2 //此处以iframe举例
        , title: '添加门店'
        , area: ['800px', '450px']
        , shade: 0
        , id: "2"
        , anim: 4
        , maxmin: true
        , offset: 'auto'
        , content: url
        , zIndex: layer.zIndex //重点1
        , success: function (layero) {
            layer.setTop(layero); //重点2
        }
    });
}

//打开门店的编辑窗口
function editWindow(data) {
    var str = encodeURIComponent(data);    //编码
    var url = './storeEdit.html?store=' + str;
    layer.open({
        type: 2 //此处以iframe举例
        , title: '编辑门店'
        , area: ['800px', '450px']
        , shade: 0
        , id: "2"
        , anim: 4
        , maxmin: true
        , offset: 'auto'
        , content: url
        , zIndex: layer.zIndex //重点1
        , success: function (layero) {
            layer.setTop(layero); //重点2
        }
    });
}

//删除门店操作
function delStore(id) {
    $.ajax({
        type: "post",
        url: "/api/tncStore/delete",
        data: {
            id: id
        },
        success: function (res) {
            if (res === 0)
                layer.msg(res.msg, {
                        time: 1500
                    },
                    function () {
                        layui.table.reload("store");
                    })
        },
        fail: function (res) {
            console.log(res);
        }
    })
}