
$('#save').on('click',function(){
        window.parent.location.reload();
    })
    $(document).ready(function(){
        var id = decodeURI(GetUrlParam('id'));
        var title = decodeURI(GetUrlParam('title'));
        var url = decodeURI(GetUrlParam('url'));
        var image_path = decodeURI(GetUrlParam('image_path'))
        if(title==''&&url==''&&image_path==''&&id=='')
            $('#save').text('添加'),
                $("form").attr("method","get");
        else
            $('#save').text('保存'),
                $("form").attr("method","post");
        $("#form #type").val('1');
        $('#title').attr("value",title);
        $('#url').attr("value",url);
        $('#imagePath').attr("value",image_path);
        $('#id').attr("value",id);
    })
    function GetUrlParam(name){
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = encodeURI(window.location.search).substr(1).match(reg);
            if (r != null) return unescape(r[2]); return null;
    }