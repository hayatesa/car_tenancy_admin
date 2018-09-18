layui.use('table', function(){
        var table = layui.table;
        var windowTitle = "";
        table.render({
            elem: '#tableDemo'
            ,url:'/api/Ads/all'
            ,toolbar: '#toolbarDemo'
            ,title: '广告表'
            ,cols: [
                [{field:'title', title:'标题', fixed: 'left', unresize: true, totalRowText: '合计'}
                    ,{field:'url', title:'跳转链接'}
                    ,{field:'imagePath', title:'图片路径'}
                    ,{field:'type', title:'类型', totalRow: true,templet:function(d){
                        if(d.type==0)
                            return "轮播图";
                        else
                            return "其它"
                    }}
                    ,{field:'gmtCreate', title:'创建时间', sort: true, templet:function(d){
                        var time = new Date(d.gmtCreate);
                        var times=time.getFullYear() + '-' + (time.getMonth() + 1) + '-' + time.getDate() + ' ' + time.getHours() + ':' + time.getMinutes() + ':' + time.getSeconds();
                        return times;
                    }}
                    ,{field:'gmtModified', title:'修改时间', sort: true, templet:function(d){
                        var time = new Date(d.gmtModified);
                        var times=time.getFullYear() + '-' + (time.getMonth() + 1) + '-' + time.getDate() + ' ' + time.getHours() + ':' + time.getMinutes() + ':' + time.getSeconds();
                        return times;
                    }}
                    ,{fixed: 'right', title:'操作', toolbar: '#barDemo'}]
            ]
            ,page: true
        });


        table.on('toolbar(tableDemo)', function(obj){
            if(obj.event=='add'){
                windowTitle = "添加";
                showForm(windowTitle,obj.data);
            }
        });
        table.on('tool(tableDemo)', function(obj){
            if(obj.event=='edit'){
                windowTitle = "编辑";
                showForm(windowTitle,obj.data);
            }
            if(obj.event == 'del'){
                del(obj.data.id);
            }
        });
        $('#search').on('click',function(){
            var search = $('#demoReload').val();
            table.reload('tableDemo', {
                url: '/api/Ads/all',
                where: {
                    search:search
                }
            });
        })
    });
    function del(id){
        $.ajax({
            type:'get',
            url:'/api/Ads/del',
            data: {
                id:id
            },
            success:function (result) {
                window.location.reload();
            },
            error:function(msg){

            }
        })
    }
    function showForm(windowTitle,record){
        if(record!=null) {
            var id = record.id;
            var title = record.title;
            var url = record.url;
            var image_path = record.imagePath;
            var type = record.type;
        }else{
            var id = '';
            var title = '';
            var url = '';
            var image_path = '';
            var type = '';
        }
        layer.open({
            type: 2
            ,title: windowTitle
            ,area: ['630px', '350px']
            ,shade: 0
            ,resize:false
            ,id:1
            ,isOutAnim: false
            ,shade: [0.8, '#393D49']
            ,offset: ['10px', '281px']
            ,content: 'model.html?title='+title+'&url='+url+'&image_path='+image_path+'&type='+type+'&id='+id
            ,zIndex: layer.zIndex
            ,success: function(layero){
                layer.setTop(layero);
            }
        });
    }