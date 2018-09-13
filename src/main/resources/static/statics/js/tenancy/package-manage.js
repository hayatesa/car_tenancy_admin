layui.use('table', function(){
    var table = layui.table;

    //第一个实例
    table.render({
        elem: '#demo'
        // ,height: 312
        ,toolbar: '#toolbarDemo'
        ,totalRow: true
        ,url: '/package_data.json' //数据接口
        ,page: true //开启分页
        ,cols: [[ //表头
             //{type: 'checkbox',width:'5%',fixed: 'left'}
            {field: 'id', title: 'ID', width:'15%', sort: true, fixed: 'left',align:"center"}
            ,{field: 'pName', title: '套餐名', width:'25%',align:"center"}
            ,{field: 'pMin', title: '天数下限', width:"15%", sort: true,align:"center"}
            ,{field: 'pMax', title: '天数上限', width:"15%",sort: true,align:"center"}
            ,{field: 'discount', title: '折扣', width: "15%",sort: true,align:"center"}
            ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:"15%",align:"center"}
        ]]
    });

    //工具条
    table.on('tool(demo)', function(obj){
        var data = obj.data;
        console.log(obj.event)
        if(obj.event === 'del'){
            layer.confirm('真的删除行么', function(index){
                obj.del();
                layer.close(index);
            });
        } else if(obj.event === 'edit'){
           // layer.alert('编辑行：<br>'+ JSON.stringify(data))
            op(JSON.stringify(data))
        }else if(obj.event === 'add'){
            op()
        }

    });
    //添加套餐
    $('#addPackage').on('click', function(){
      op()
    });

});

function op(data){
    console.log(data)
    var str = encodeURIComponent(data)//编码
    var url =""
    if(data !=null){
        url = './packageAdd.html?pack='+str
    }else{
        url = './packageAdd.html'
    }
    layer.open({
        type: 2 //此处以iframe举例
        ,title: '套餐'
        ,area: ['700px', '350px']
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

