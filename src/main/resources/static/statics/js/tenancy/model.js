    $(document).ready(function(){
        // 获取页面参数
        var id = $.getUrlParam('id');
        var title = $.getUrlParam('title');
        var url = $.getUrlParam('url');
        var imagePath = $.getUrlParam('imagePath');
        console.log(imagePath);
        if ($.getUrlParam('type') == 0){
            $($("#type").siblings().eq(0).children("div").children("input")[0]).attr('value', "轮播图");
            $("#sowing").attr("selected",true);
        } else{
            $($("#type").siblings().eq(0).children("div").children("input")[0]).attr('value', "其他");
            $("#others").attr("selected",true);
        }
        if(title==''&&url==''&&imagePath==''&&id=='')
            $('#save').text('添加'),
                $('#myForm').attr('method','get');
        else
            $('#save').text('保存'),
                $('#myForm').attr('method','post');
        // 回显
        $('#title').attr("value",title);
        $('#url').attr("value",url);
        $('#imagePath').attr("value",imagePath);
        $('#demo1').attr("src",imagePath);
        $('#id').attr("value",id);
    })
    layui.use(['form','layer','upload'], function(){
        var $ = layui.jquery,upload = layui.upload;
        var form = layui.form;
        form.on('submit(go)', function(data){
            submit(data.field);
        });
        //监听提交
        // form.on('submit(go)', function(data){
        //     layer.msg(JSON.stringify(data.field));
        //     return false;
        // });
        //普通图片上传
        var uploadInst = upload.render({
            elem: '#chooseImg'
            ,url: '/api/pic/upload'
            ,auto: false //选择文件后不自动上传
            ,bindAction: '#upload' //指向一个按钮触发上传
            // 选择文件后的回调
            ,choose: function(obj){
                //预读本地文件，如果是多文件，则会遍历。(不支持ie8/9)
                obj.preview(function(index, file, result){
                    $('#filename').text(file.name);
                    $('#demo1').attr({src:result,alt:file.name});
                });
            }
            // 执行上传请求后的回调
            ,done: function(res){
                //上传成功
                if(res.code == 0){
                    console.log(res);
                    var demoText = $('#demoText'),pathPre = 'D:/zuche/images';
                    demoText.html('<span style="color: #FF5722;">上传成功</span>');
                    $('#imagePath').val(pathPre+res.path);
                }
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

    });
    function submit(data){
        var method = $('#myForm').attr('method');
        $.ajax({
            type:method,
            url:"/api/Ads/save",
            data:data,
            async:false,
            success:function (res) {
                layer.msg(res.msg,{time:1500},function(){
                    parent.layui.table.reload("mytable");
                    closeIframe();
                });
            },
            fail:function (res) {
                layer.msg(res.msg,{time:1500});
            }
        })
    }
    function closeIframe(){
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    }
