var carId = window.location.search.slice(7);
var picData=null;
/*上传汽车图片*/
layui.use('upload', function(){
    var upload = layui.upload;
    //普通图片上传
    var uploadInst = upload.render({
        elem: '#cover'
        ,url: '/api/pic/upload'
        ,before: function(obj){
            //预读本地文件示例，不支持ie8
            obj.preview(function(index, file, result){
                $('#demo1').attr('src', result); //图片链接（base64）
            });
        }
        ,done: function(res){
            console.log(res);
            //如果上传失败
            if(res.code > 0){
                return layer.msg('上传失败');
            }
            var data={
                carId:carId,
                path:res.path,
                isCover:1
            }
            //上传成功
            doStoragePic(data);

        }
        ,error: function(){
            //演示失败状态，并实现重传
            var demoText = $('#demoText');
            demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
            demoText.find('.demo-reload').on('click', function(){
                uploadInst.upload();
            });
        }
    });

    //执行实例
    var uploadInst1 = upload.render({
        elem: '#uploadPhoto' //绑定元素
        ,url: '/api/pic/upload' //上传接口
        ,auto: false //选择文件后不自动上传
        ,multiple: true
        ,bindAction: '#uploadListAction' //指向一个按钮触发上传
        ,before: function(obj){
            //预读本地文件示例，不支持ie8
            obj.preview(function(index, file, result){
                $('#demo2').append('<img src="'+ result +'" alt="'+ file.name +'" class="layui-upload-img">')
            });
        }
        ,choose: function(obj){
            //将每次选择的文件追加到文件队列
            var files = obj.pushFile();
            //预读本地文件，如果是多文件，则会遍历。(不支持ie8/9)
            obj.preview(function(index, file, result){
                var pit = '<img src="'+result+'" class="layui-upload-img" style="width: 120px;height: 80px;margin-right: 10px;"/>';
                $("#previewPicture").append(pit);
  
            });
        }
        ,done: function(res){
            //上传完毕回调
            console.log(res)
            if (res.code ==0){
                var data={
                    carId:carId,
                    path:res.path,
                    isCover:0
                }
                doStoragePic(data);
            }
        }
        ,error: function(res){
            //请求异常回调
            // console.log(res);
            layer.msg("上传失败！");
        }
    });


});

function doStoragePic(data) {
    console.log("ICANTBELIEVE");
    console.log(data);
    var url ="/api/carPic/storage";
    if(picData != null){
        url ="/api/carPic/update";
        data.id=picData.id;
        data.isCover=picData.isCover;
    }
    console.log(picData);
    console.log(data);
    $.ajax({
        url:url,
        data:data,
        success:function (res) {
            if (res.code == 0){
                if (data.cover ==1){
                    showCoverUploadSuccess();
                }else{
                    layer.msg("上传成功！");
                }
                layui.table.reload('photoList', {
                    url: '/api/carPic/get/'+carId
                });

            }else{
                layer.msg("上传失败！");
            }
        }
    })
}
function showCoverUploadSuccess() {
    layer.msg('上传成功');
    var demoText = $('#demoText');
    demoText.html('<a class="layui-btn layui-btn-xs demo-reload" onclick="closeCoverUpload()">关闭</a>');
}

layui.use('table', function(){
    var table = layui.table;
    table.render({
        elem: '#photoList'
        ,url:'/api/carPic/get/'+carId
        // ,cellMinHeight: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        ,page:false
        ,toolbar: '#tnc_toolbar'
        ,cols: [[
            {field:'id',  title: 'ID', sort: true,align:'center'}
            ,{field:'path', style:'height:100px;',width:200,align:'center',title: '照片',templet:function (res) {
                    return '<img src="/api/pic/item?imagePath='+res.path+'"/>';
                }}
            ,{field:'isCover',  title: '是否封面', sort: true,align:'center'}
            ,{fixed: 'right', title:'操作', toolbar: '#barDemo',align:'center'}
        ]]
        ,done:function(res,curr,count){
            hoverOpenImg();//显示大图
            $('table tr').on('click',function(){
                $('table tr').css('background','');
                $(this).css('background','<%=PropKit.use("config.properties").get("table_color")%>');
            });
        }

    });

    /*侧边栏事件*/
    table.on('tool(photoList)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        console.log(data);
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象


        function doReplace(data) {
            picData = data;
            if(data.isCover ==1){
                openCoverUpload();
            }else{
                openNotCoverUpload();
            }
        }
        function doDelete(data) {
            delete data.gmtCreate;
            delete data.gmtModified;
            console.log(data);
            $.ajax({
                url:"/api/carPic/delete",
                data:data,
                success:function (res) {
                    if (res.code == 0){
                        layer.msg("删除成功！");
                        layui.table.reload('photoList', {
                            url: '/api/carPic/get/'+carId
                        });
                    }else{
                        layer.msg("删除失败！");
                    }
                }
            })
        }

        switch (layEvent) {

            case  'del':
                layer.confirm('真的删除行么', function(index){
                    doDelete(data);
                    layer.close(index);
                });
                break;
            case 'replace':
                doReplace(data);
                 break;
        }

    });

    //工具栏事件
    table.on('toolbar(photoList)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);

        switch(obj.event){
            case 'addPic':
                closeNotCoverUpload();
                openCoverUpload();
                break;
            case 'batchAddPic':
                closeCoverUpload();
                openNotCoverUpload();
                break;

        };
    });

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



function openCoverUpload() {
    $("#coverField").removeClass("layui-hide");
    $("#coverField").addClass("layui-show");
}

function closeCoverUpload() {
    picData =null;
    $("#coverField").removeClass("layui-show");
    $("#coverField").addClass("layui-hide");
}


function openNotCoverUpload() {
    $("#otherField").removeClass("layui-hide");
    $("#otherField").addClass("layui-show");
}

function closeNotCoverUpload() {
    picData =null;
    $("#otherField").removeClass("layui-show");
    $("#otherField").addClass("layui-hide");
}
