var carId = window.location.search.slice(7);

var picList = null;

/*请求图片数据*/
$.ajax({
    url:'/api/carPic/get/'+carId,
        success:function (res) {
            console.log(res);
            if (res.code == 0){
                picList = res.data;
                doSetImg(res.data);
            }else{
                layer.msg(res.msg);
            }
    }
})


//後面添加的元素無法綁定事件，需預加載


/*设置图片显示*/
function doSetImg(data) {
    if(data!=0){
        var pnum = 1;
        for (var i=0;i<data.length;i++)
        {
            if(data[i].isCover == 1){
                doSetValue(data[i]);
            }else{
                doSetNotValue(data[i],pnum);
                pnum++;
            }
        }
    }
}

/*设置图片地址，并显示*/
function doSetValue(data) {
    $("#imgCover").attr("src","/api/pic/item?imagePath="+data.path);
    $("#coveruploadId").addClass("layui-hide");
    $("#coverId").removeClass("layui-hide");
}
/*设置非封面*/
function doSetNotValue(datum,i) {

    switch (i) {
        case 1:
            $("#imgNotCover1").attr("src","/api/pic/item?imagePath="+datum.path);
            $("#notCover1Update").removeClass("layui-hide");
            $("#notCover1Upload").addClass("layui-hide");
            $("#notCover1Id").val(datum.id);
            break;
        case 2:
            $("#imgNotCover2").attr("src","/api/pic/item?imagePath="+datum.path);
            $("#notCover2Upload").addClass("layui-hide");
            $("#notCover2Update").removeClass("layui-hide");
            $("#notCover2Id").val(datum.id);

            break;
        case 3:
            $("#imgNotCover3").attr("src","/api/pic/item?imagePath="+datum.path);
            $("#notCover3Upload").addClass("layui-hide");
            $("#notCover3Update").removeClass("layui-hide");
            $("#notCover3Id").val(datum.id);
            break;
        case 4:
            $("#imgNotCover4").attr("src","/api/pic/item?imagePath="+datum.path);
            $("#notCover4Upload").addClass("layui-hide");
            $("#notCover4Update").removeClass("layui-hide");
            $("#notCover4Id").val(datum.id);
            break;
    }

}

/*点击上传封面*/
layui.use('upload', function(){
    var upload = layui.upload;
    //执行实例
    var uploadInst = upload.render({
        elem: '#coveruploadId' //绑定元素
        ,url: '/api/pic/upload' //上传接口
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
            var storageurl ="/api/carPic/storage";
            //上传成功
            doStoragePic(data,storageurl);

        }
        ,error: function(){
            //请求异常回调
        }
    });

})

/*点击更改封面*/
layui.use('upload', function(){
    var upload = layui.upload;
    //执行实例
    var uploadInst = upload.render({
        elem: '#coverId' //绑定元素
        ,url: '/api/pic/upload' //上传接口
        ,done: function(res){
            console.log(res);
            //如果上传失败
            if(res.code > 0){
                return layer.msg('上传失败');
            }
            var id =null;
            for (var i=0;i<picList.length;i++){
                if(picList[i].isCover ==1){
                    id = picList[i].id;
                }
            }
            var data={
                id:id,
                carId:carId,
                path:res.path,
                isCover:1
            }
            var storageurl ="/api/carPic/update";
            //上传成功
            doStoragePic(data,storageurl);

        }
        ,error: function(){
            //请求异常回调
        }
    });

})

/*存储图片*/
function doStoragePic(data,url) {
    $.ajax({
        url:url,
        data:data,
        success:function (res) {
            if (res.code == 0){
                layer.msg("上传成功！");
                window.location.reload();
            }else{
                layer.msg(res.msg);
            }
        }
    })
}

/*点击上传非封面*/
layui.use('upload', function(){
    var upload = layui.upload;
    //执行实例
    var uploadInst = upload.render({
        elem: '.notCover' //绑定元素
        ,url: '/api/pic/upload' //上传接口
        ,done: function(res){
            //上传完毕回调
            var data={
                carId:carId,
                path:res.path,
                isCover:0
            }
            var storageurl ="/api/carPic/storage";
            //上传成功
            doStoragePic(data,storageurl);
        }
        ,error: function(){
            //请求异常回调
        }
    });

})

layui.use('upload', function(){
    var upload = layui.upload;
    //执行实例
    var uploadInst = upload.render({
        elem: '#notCover1Update' //绑定元素
        ,url: '/api/pic/upload' //上传接口
        ,done: function(res){
            //上传完毕回调
            var id = $("#notCover1Id").val();
            var data={
                id:id,
                carId:carId,
                path:res.path,
                isCover:0
            }
            var storageurl ="/api/carPic/update";
            //上传成功
            doStoragePic(data,storageurl);
        }
        ,error: function(){
            //请求异常回调
        }
    });

})
layui.use('upload', function(){
    var upload = layui.upload;
    //执行实例
    var uploadInst = upload.render({
        elem: '#notCover2Update' //绑定元素
        ,url: '/api/pic/upload' //上传接口
        ,done: function(res){
            //上传完毕回调
            var id = $("#notCover2Id").val();
            var data={
                id:id,
                carId:carId,
                path:res.path,
                isCover:0
            }
            var storageurl ="/api/carPic/update";
            //上传成功
            doStoragePic(data,storageurl);
        }
        ,error: function(){
            //请求异常回调
        }
    });

})
layui.use('upload', function(){
    var upload = layui.upload;
    //执行实例
    var uploadInst = upload.render({
        elem: '#notCover3Update' //绑定元素
        ,url: '/api/pic/upload' //上传接口
        ,done: function(res){
            //上传完毕回调
            var id = $("#notCover3Id").val();
            var data={
                id:id,
                carId:carId,
                path:res.path,
                isCover:0
            }
            var storageurl ="/api/carPic/update";
            //上传成功
            doStoragePic(data,storageurl);
        }
        ,error: function(){
            //请求异常回调
        }
    });

})
layui.use('upload', function(){
    var upload = layui.upload;
    //执行实例
    var uploadInst = upload.render({
        elem: '#notCover4Update' //绑定元素
        ,url: '/api/pic/upload' //上传接口
        ,done: function(res){
            //上传完毕回调
            var id = $("#notCover4Id").val();
            var data={
                id:id,
                carId:carId,
                path:res.path,
                isCover:0
            }
            var storageurl ="/api/carPic/update";
            //上传成功
            doStoragePic(data,storageurl);
        }
        ,error: function(){
            //请求异常回调
        }
    });

})
