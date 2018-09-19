layui.use('table', function () {
    var table = layui.table;
    table.render({
        elem: '#brand'
        , url: '/api/tncBrand/list'
        , toolbar: '#toolbarDemo'
        , title: '汽车品牌表'
        , page: true
        , cols: [
            [{
                field: 'id',
                title: 'ID',
                width: '10%',
                fixed: 'left',
                unresize: true,
                sort: true,
            }
                , {field: 'name', title: '品牌名称', edit: 'text', sort: true}
                , {field: 'gmtCreate', title: '创建时间', width: '20%', sort: true}
                , {field: 'gmtModified', title: '修改时间', width: '20%', sort: true}
                , {fixed: 'right', title: '操作', toolbar: '#barDemo', width: '15%', align: 'center'}]
        ]
    });

    //操作事件
    table.on('tool(brand)', function (obj) {
        var data = obj.data;
        console.log(data);
        if (obj.event === 'del') {
            layer.confirm('真的删除行么', function (index) {
                delB(data.id);
                layer.close(index);
            });
        } else if (obj.event === 'edit') {
            editB(JSON.stringify(data));
        }
    });

    //工具栏事件
    table.on('toolbar(brand)', function (obj) {
        if (obj.event === 'add') {
            addB()
        }
    });

});

//打开添加窗口
function addB() {
    var url = './brandAdd.html';
    layer.open({
        type: 2 //此处以iframe举例
        , title: '添加品牌'
        , area: ['350px', '160px']
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

//打开编辑窗口
function editB(data) {
    var str = encodeURIComponent(data);    //编码
    var url = './brandEdit.html?brand=' + str;
    layer.open({
        type: 2 //此处以iframe举例
        , title: '编辑品牌'
        , area: ['350px', '160px']
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


//根据Id删除品牌
function delB(id) {
    $.ajax({
        type: "post",
        url: "/api/tncBrand/delete",
        //contentType: "application/json",
        data: {
            id: id
        },
        success: function (res) {
            if (res.code === 0) {
                layer.msg(res.msg, {
                        time: 1500
                    },
                    function () {
                        layui.table.reload("brand");
                    })
            }
        },
        fail: function (res) {
            console.log(res);
        }
    })
}
