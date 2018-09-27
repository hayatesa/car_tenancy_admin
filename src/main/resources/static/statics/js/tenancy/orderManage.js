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
    var tabledata = [];
    var tabledata_1;
    var tabledata_2;
    var callback = function(){
        tabledata_2 = tabledata_1 = tabledata;
        var f = new  Filter();
        //过滤器
        $('#search').click(function(){
            tabledata_1 = tabledata;
            var a = $('#selectID').val();//匹配value
            var a_1 = $('#selectID_1').val();var a_2 = $('#selectID_2').val();
            var a_3 = $('#selectID_3').val();var a_4 = $('#selectID_4').val();var a_5 = $('#selectID_5').val();
            f.set("a", m => {if(a!="") return m.get_store_province == a;else return true});
            f.set("a_1", m => {if(a_1!="") return m.get_store_city == a_1;else return true});
            f.set("a_2", m => {if(a_2!="") return m.get_store_area == a_2;else return true});
            f.set("a_3", m => {if(a_3!="") return m.return_store_province == a_3;else return true});
            f.set("a_4", m => {if(a_4!="") return m.return_store_city == a_4;else return true});
            f.set("a_5", m => {if(a_5!="") return m.return_store_area == a_5;else return true});
            let filters = f.getFilters();
            for (let k of filters) {
                //console.log("before---"+tabledata_1+"k----"+k);
                tabledata_1 = tabledata_1.filter(k);
                //console.log("after---"+tabledata_1+"k----"+k);
            }

            table.reload('orderstable', {
                    data: tabledata_1
                });
            $('#selectID_text').val(-1);
            //tabledata_2 = tabledata_1;
        });
        //状态过滤
        $('#selectID_text').change(function () {
            tabledata_2 = tabledata_1
            var xx = $('#selectID_text').val();
            if(xx==-1){tabledata_2 = tabledata_1;}
            else{
                tabledata_2 = tabledata_2.filter(function (e) {
                    return e.status==xx;
                })
            }
            table.reload('orderstable', {
                data: tabledata_2
            });
            //alert($('#selectID_text').val())
        })

        //文本框的模糊查询
        $("#word").bind("input propertychange",function(event){
            console.log($("#word").val())
            var tabledata_3 = query(tabledata_2,$("#word").val());
            table.reload('orderstable', {
                data: tabledata_3
            });
        });
        console.log("--------------"+tabledata_1);

        //2------用获取的数据加载table
        table.render({
            elem:'#orders'
            //,url:'/statics/order_data.json'
            ,data:tabledata_1
            ,page:true
            ,id:'orderstable'
            // ,toolbar: '#carListToolbar'
            ,cols:[[
                {field:'id',title:'订单编号'}//订单编号
                ,{field:'name',title:'客户名' ,templet:'#userdetail'}//客户名
                ,{field:'phone',title:'电话'}//电话
                ,{field:'car_name',title:'车品牌名'}//车品牌名
                ,{field:'car_series',title:'车系'}//车系
                ,{field:'car_number',title:'车牌号'}//车牌号
                ,{field:'total_amount',title:'订单总金额'}//订单总金额
                ,{field:'get_store_name',title:'借车店名'}//借车店名
                ,{field:'return_store_name',title:'还车店名'}//还车店名
                ,{field:'status',title:'订单状态',sort: true ,templet:'#statusdetail'}
                ,{field: '', title:'操作', width:150, align:'center',toolbar: '#barDemo'}
            ]]
        })
    }
    //1----获取到表格数据
    $.ajax({
        url:'/statics/order_data.json',
        dataType:"json",
    }).done(function (data) {
        tabledata = data.data;
        callback();
    })


    //监听工具条
    table.on('tool(orderstool)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象
        if(layEvent ==='wordfilter'){
            alert("wordfilter");
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
                url:'/statics/userdetail_data.json',
                dataType:'json',
                success:function(data){
                    let a = data.data;
                    $("#name").text(a.name);
                    $("#sex").text(a.gender);
                    $("#phone").text(a.phone);
                    $("#email").text(a.email);
                    $("#pname").text(a.emergency_name);
                    $("#pphone").text(a.emergency_phone);
                    $("#addr").text(a.province+a.city+a.area+a.detail);
                    $("#ltime").text(a.last_access_time);
                }
            });
        }
        ,cancel: function(){//关闭按钮的回调函数
            $("#mydiv").css({"display":"none"})
        }
    })
}
