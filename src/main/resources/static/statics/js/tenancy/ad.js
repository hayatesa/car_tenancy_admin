
    function del(id){
        $.ajax({
            type:'get',
            url:'/api/Ads/del',
            data: {
                id:id
            },
            success:function (res) {
                // window.location.reload();
                layer.msg(res.msg,{time:1500},function(){
                    layui.table.reload("mytable");
                });
            },
            error:function(res){
                layer.msg(res.msg,{time:1500});
            }
        })
    }
    function showForm(windowTitle,record){
        var id;
        if(record!=null) {
            id = record.id;
        }else{
            id = '';
        }
        layer.open({
            type: 2
            ,title: windowTitle
            ,area: ['630px', '460px']
            ,shade: 0
            ,resize:false
            ,id:1
            ,anim: 4
            ,maxmin: true
            ,shade: [0.8, '#393D49']
            ,offset: ['10px', '281px']
            ,content: 'model.html?id='+id
            ,zIndex: layer.zIndex
            ,success: function(layero){
                layer.setTop(layero);
            }
        });
    }
    layui.use('table', function(){
        var table = layui.table;
        var windowTitle = "";
        table.render({
            elem: '#mytable'
            ,url:'/api/Ads/all'
            ,toolbar: '#toolbarDemo'
            ,title: '广告表'
            ,cols: [
                [{field:'title', title:'标题', style:'height:100px;',width:200,fixed: 'left', unresize: true, align:'center'}
                    ,{field:'url', title:'跳转链接',align:'center'}
                    ,{field:'imagePath', style:'height:100px;',width:200,align:'center',title: '图片',templet:function (res) {
                        return '<img src="/api/pic/item?imagePath='+res.imagePath+'"/>';
                    }}
                    ,{field:'type', title:'类型',align:'center', totalRow: true,templet:function(d){
                        if(d.type==0)
                            return "轮播图";
                        else
                            return "其它"
                    }}
                    ,{field:'gmtCreate', title:'创建时间',align:'center', sort: true}
                    ,{field:'gmtModified', title:'修改时间',align:'center', sort: true}
                    ,{fixed: 'right', title:'操作',align:'center', toolbar: '#barDemo'}]
            ]
            ,page: true
        });


        table.on('toolbar(mytable)', function(obj){
            if(obj.event=='add'){
                windowTitle = "添加";
                showForm(windowTitle,obj.data);
            }
        });
        table.on('tool(mytable)', function(obj){
            if(obj.event=='edit'){
                windowTitle = "编辑";
                showForm(windowTitle,obj.data);
            }
            if(obj.event == 'del'){
                layer.confirm('确定删除？', function(index){
                    del(obj.data.id);
                    layer.close(index);
                });
            }
        });
        $('#search').on('click',function(){
            var search = $('#demoReload').val();
            table.reload('mytable', {
                url: '/api/Ads/all',
                where: {
                    search:search
                }
            });
        })
    });