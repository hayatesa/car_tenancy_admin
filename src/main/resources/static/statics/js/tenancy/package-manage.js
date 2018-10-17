layui.use('table', function(){
    var table = layui.table;

    table.render({
        elem: '#PackageScheme'
        ,height:564
        ,loading:true
        ,toolbar: '#toolbarPackageScheme'
        ,totalRow: true
        ,url: '/api/PackageScheme/list' //数据接口
        ,page: true //开启分页
        ,cols: [[ //表头
             //{type: 'checkbox',width:'5%',fixed: 'left'}
            {field: 'id', title: 'ID', width:'15%', sort: true, fixed: 'left',align:"center"}
            ,{field: 'name', title: '套餐名', width:'25%',align:"center"}
            ,{field: 'daysMin', title: '天数下限', width:"20%", sort: true,align:"center"}
            ,{field: 'daysMax', title: '天数上限', width:"20%",sort: true,align:"center"}
            // ,{field: 'discount', title: '折扣', width: "15%",sort: true,align:"center"}
            ,{fixed: 'right', title:'操作', toolbar: '#barPackageScheme', width:"20%",align:"center"}
        ]]
    });

    //操作列
    table.on('tool(PackageScheme)', function(obj){
        let data = obj.data;
        if(obj.event === 'del'){
            layer.confirm('真的删除行么', function(index){
                del(data.id);
                layer.close(index);
            });
        } else if(obj.event === 'edit'){
            op(JSON.stringify(data))
        }else if(obj.event === 'add'){
            op()
        }

    });
    //工具条
    table.on('toolbar(PackageScheme)', function(obj){
        if(obj.event === 'add') {
            op()
        }
    })

});

/**
 * 添加
 * 编辑
 * @param data PackageScheme
 */
function op(data){
    let str = encodeURIComponent(data)//编码
    let url =""
    if(data !=null){
        url = './packageAdd.html?pack='+str
    }else{
        url = './packageAdd.html'
    }
    layer.open({
        type: 2 //此处以iframe举例
        ,title: '套餐'
        ,area: ['700px', '225px']
        ,shade: 0
        ,id:"2"
        ,anim: 4
        ,maxmin: true
        ,offset: 'auto'
        ,content: url
        ,zIndex: layer.zIndex //重点1
        ,success: function(layero){
            layer.setTop(layero); //重点2
        }
    });
}

/**
 * 删除 套餐
 * @param id
 */
function del(id) {
    $.ajax({
        type:"POST",
        url:"/api/PackageScheme/delete",
        data:{
            pid:id
        },
        // contentType:"application/json",
        success:function (res) {
            //console.log(res)
            if(res.code == 0){
                layer.msg(res.msg,
                    {
                        time:1500
                    },
                    function () {
                 layui.table.reload("PackageScheme")
                })
            }else{
                handleAjax(res)
            }
        }
    })
}