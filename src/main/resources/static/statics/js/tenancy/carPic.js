/*上传汽车图片*/
layui.use('upload', function(){
    var upload = layui.upload;

    //执行实例
    var uploadInst = upload.render({
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
                // console.log(index); //得到文件索引
                // console.log(file); //得到文件对象
                // console.log(result); //得到文件base64编码，比如图片
                var pit = '<img src="'+result+'" class="layui-upload-img" style="width: 120px;height: 80px;margin-right: 10px;"/>';
                $("#previewPicture").append(pit);
                //obj.resetFile(index, file, '123.jpg'); //重命名文件名，layui 2.3.0 开始新增

                //这里还可以做一些 append 文件列表 DOM 的操作

                //obj.upload(index, file); //对上传失败的单个文件重新上传，一般在某个事件中使用
                //delete files[index]; //删除列表中对应的文件，一般在某个事件中使用
            });
        }
        ,done: function(res){
            //上传完毕回调
            console.log(res);

            if (res.code ==0){
                doStoragePic(res.path);
            }
        }
        ,error: function(res){
            //请求异常回调
            console.log(res);

        }
    });
});



function doStoragePic(path) {
    var data = {
        carId:carId,
        path :path,
        isCover:0
    }

    $.ajax({
        url:"/api/carPic/storage",
        data:data,
        success:function (res) {
            if (res.code == 0){
                layer.msg("上传成功！");
            }else{
                layer.msg("上传失败！");
            }
        }
    })
}

