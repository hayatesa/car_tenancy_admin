
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
                        return '<img  src="/api/pic/item?imagePath='+res.imagePath+'"/>';
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
            ,done:function(){
                hoverOpenImg();//显示大图
            }
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
    function hoverOpenImg(){
        var img_show = null; // tips提示
        $('td img').hover(function(){
            //alert($(this).attr('src'));
            var img = "<img class='img_msg' src='"+$(this).attr('src')+"' style='width:330px;' />";
            img_show = layer.tips(img, this,{
                tips:[2, 'rgba(41,41,41,.5)']
                ,area: ['360px']
            });
        },function(){
            layer.close(img_show);
        });
        $('td img').attr('style','max-width:250px;height:80px;');
    }