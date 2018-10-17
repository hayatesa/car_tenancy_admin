
//配置过滤器hash
class Filter {
    constructor() {
        this.filters = {};
    }
    set(key, filter) {
        this.filters[key] = filter;
    }
    getFilters() {
        return Object.keys(this.filters).map(key => this.filters[key]);
    }
}
//配置模糊查询
function query(arr, q) {
    return arr.filter(v => Object.values(v).some(v => new RegExp(q + '').test(v))
    )
}
layui.use(['table','form'], function () {
    var table = layui.table;
    var form = layui.form;
    var $ = layui.$;
    form.on('select(business)', function(data){
        // if($('#selectID_text').val()==6){
        //     var formatDateTime = function (date) {
        //         var y = date.getFullYear();
        //         var m = date.getMonth() + 1;
        //         m = m < 10 ? ('0' + m) : m;
        //         var d = date.getDate();
        //         d = d < 10 ? ('0' + d) : d;
        //         var h = date.getHours();
        //         h = h < 10 ? ('0' + h) : h;
        //         var minute = date.getMinutes();
        //         minute = minute < 10 ? ('0' + minute) : minute;
        //         var second = date.getSeconds();
        //         second = second < 10 ? ('0' + second) : second;
        //         return y + '-' + m + '-' + d + ' ' + h + ':' + minute + ':' + second;
        //     };
        //     var datetime = formatDateTime(new Date());
        //     //console.log(datetime);
        //     table.reload('orderstable', {
        //         url: '/api/order/selectByWord'
        //         ,where: {
        //             search:$('#word').val(),
        //             status:1,
        //             now:datetime
        //         } //设定异步数据接口的额外参数
        //         //,height: 300
        //     });
        // }else{
        table.reload('orderstable', {
            url: '/api/order/selectByWord'
            ,where: {
                search:$('#word').val(),
                status:$('#selectID_text').val()
            } //设定异步数据接口的额外参数
            //,height: 300
        });
    });
    $("body").keydown(function() {
        if (event.keyCode == "13") {//keyCode=13是回车键
            $('#selectbyword').click();
        }
    });
    $('#selectbyword').click(function () {
        table.reload('orderstable', {
            url: '/api/order/selectByWord'
            ,where: {
                search:$('#word').val(),
                status:$('#selectID_text').val()
            } //设定异步数据接口的额外参数
            //,height: 300
        });
    })
        table.render({
            elem:'#orders'
            ,url:'/api/order/list'
            ,page:true
            ,id:'orderstable'
            //,toolbar: '#ListToolbar'
            ,cols:[[
                {field:'id', width:130,title:'订单编号'}//订单编号
                ,{field:'name', width:100,title:'客户名' ,templet:'#userdetail'}//客户名
                ,{field:'phone',width:130,title:'电话'}//电话
                ,{field:'car_name', width:90,title:'车品牌名',templet:function (res) {
                        return res.carItem.brand;
                    }}//车品牌名
                ,{field:'car_series', width:90,title:'车系',templet:function (res) {
                        return res.carItem.series;
                    }}//车系
                ,{field:'car_number', width:120,title:'车牌号',templet:function (res) {
                        return res.carItem.nub;
                    }}//车牌号
                ,{field:'totalAmount', width:100,title:'订单总金额'}//订单总金额
                ,{field:'getStore_name',width:115,title:'借车店名',templet:function (res) {
                        return res.getStore.name;
                    }}//借车店名
                ,{field:'returnStore_name',width:115,title:'还车店名',templet:function (res) {
                        return res.returnStore.name;
                    }}//还车店名
                ,{field:'status',width:110,title:'订单状态',sort: true ,templet:'#statusdetail'}
                ,{field: '', title:'操作', align:'center',toolbar: '#barDemo'}
            ]]
        })

    //监听工具条
    table.on('tool(orderstool)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象
         if(layEvent === 'delete'){
            layer.confirm('真的删除行么', function (index) {
                dodelete(data.id);
                layer.close(index);
            });
        }else if(layEvent === 'edit'){
                doEdit(data.id);
        // }else if(layEvent === 'searchByWord'){
        //      alert("asdsda");
        //      console.log($('#searchText').val());
        //      table.reload('orderstable', {
        //          url: '/api/order/selectByWord'
        //          ,where: {
        //              search:$('#searchText').val()
        //          } //设定异步数据接口的额外参数
        //          //,height: 300
        //      });
         }
        else if(layEvent === 'detail'){ //查看
            let url = './orderManager_order.html?id='+data.id;
            layer.open({
                type: 2 //此处以iframe举例
                ,title: '订单详情'
                ,area: ['1000px', '580px']
                ,shade: 0
                ,resize:false
                ,id:"2"
                ,maxmin: true
                ,shade:0.3
                ,shadeClose:true
                ,offset: 'auto'
                ,content: url
                ,zIndex: layer.zIndex //重点1
                ,success: function(layero){
                    layer.setTop(layero); //重点2
                }
            });
        } else if(layEvent === 'checkget'){ //编辑
            //alert("审核提车");console.log(data);
            var obj = {"type":1,"orderid":data.id}
            open("审核提车",obj);
        }else if(layEvent === 'checkback'){ //编辑
            //alert("审核还车");
            var obj = {"type":2,"orderid":data.id}
            open("审核还车",obj);
        }else if(layEvent === 'checkpaymoney'){ //编辑
            //alert("支付");
            var obj = {"type":3,"orderid":data.id}
            open("支付",obj);
        }else{ //编辑
            //alert("审核退款");
            var obj = {"type":4,"orderid":data.id}
            open("退款",obj);
        }
    });
    //操作--还车提车退款
    function open(title,data) {
        //console.log("data="+data.orderid);
        let str = encodeURIComponent(JSON.stringify(data))//编码
        let url = './orderManager_change.html?pack='+str
        //console.log(url)
        let area = ['1000px', '475px'];
        if(data.type==4) area = ['1000px', '430px'];
        layer.open({
            type: 2 //此处以iframe举例
            ,title: title
            ,area: area
            ,shade: 0
            ,resize:false
            ,id:"2"
            ,maxmin: true
            ,shade:0.3
            ,shadeClose:true
            ,offset: 'auto'
            ,content: url
            ,zIndex: layer.zIndex //重点1
            ,success: function(layero){
                layer.setTop(layero); //重点2
            }
        });
    }
    //select的动态加载
    // $.ajax({
    //     url:'/statics/order_select_data.json',
    //     dataType:'json',
    //     success:function(data){
    //
    //         $.each(data.data,function(index,item){
    //             $('#selectID').append(new Option(item.province,item.province));//往下拉菜单里添加元素
    //         })
    //
    //         form.render('select');//菜单渲染 把内容加载进去
    //     }
    // })
    // $('#selectID').change(function () {
    //     $("#selectID_1").empty();
    //     $('#selectID_1').append(new Option("请选择", "0"));
    //     //alert($('#selectID').val());
    //     $.ajax({
    //         url: '/statics/order_select_data.json',
    //         dataType: 'json',
    //         success: function (data) {
    //             $.each(data.data, function (index, item) {
    //                 $('#selectID_1').append(new Option(item.province, item.province));//往下拉菜单里添加元素
    //             })
    //
    //             form.render('select');//菜单渲染 把内容加载进去
    //         }
    //     })
    // });
})

function dodelete(id) {
    $.ajax({
        url:'/api/order/delete?id='+id,
        type:'post',
        success:function (res) {
            if (res.code === 0)
                layer.msg(res.msg, {
                        time: 1500
                    },
                    function () {
                        layui.table.reload("orderstable");
                    })
        },
        fail: function (res) {
            console.log(res);
        }
    })
}
function doEdit(str) {
    var url = './orderManage_edit.html?id=' + str;
    layer.open({
        type: 2 //此处以iframe举例
        , title: '编辑订单'
        , area: ['800px', '460px']
        , shade: 0
        , maxmin: true
        , offset: 'auto'
        , content: url
        , zIndex: layer.zIndex //重点1
        , success: function (layero) {
            layer.setTop(layero); //重点2
        }
    });
}
//获取个人信息详情
function getuserdetail(gg){
    var $ = layui.jquery;
    //alert(gg);
    layer.open({
        type: 1 //此处以iframe举例
        , title: '个人信息'
        , area: ['650px', '300px']
        , shade: 0
        , id: "2"
        , maxmin: true
        ,shade:0.3
        , offset: 'auto'
        , content: $('#mydiv')
        , success: function () {
            $.ajax({
                url:'/api/order/selectUser?id='+gg,
                dataType:'json',
                success:function(data){
                    //console.log(data);
                    let a = data.data;
                    let b = data.data.tncAddress;
                    $("#name").text(a.name);
                    $("#sex").text(a.gender==""?"":(a.gender=="0"?"女":"男"));
                    $("#phone").text(a.phone);
                    $("#email").text(a.email);
                    $("#pname").text(a.emergencyName);
                    $("#pphone").text(a.emergencyPhone);
                    $("#addr").text(b==null?"":(b.province.name+b.city.name+b.area.name+b.detail));
                    $("#ltime").text(a.lastAccessTime);
                }
            });
        }
        ,cancel: function(){//关闭按钮的回调函数
            $("#mydiv").css({"display":"none"})
        }
    })
}
