$(function () {
    let nub, name, sex, cid, email, pname, pphone;
//修改车牌信息
    let nublist
    $.ajax({
        url:'/statics/order_change_nub.json',
        dataType:'json',
        success:function (data) {
            nublist=data;
        }
    })

    $('#changecar').click(function(){
        nub = $('nub').text();
        $myselect=$("<select id='selectcar'><option value='"+nub+"'>"+nub+"</option></select>")
        $('nub').after($myselect);
        $('nub').hide();
        $.each(nublist.data, function (index, item) {
            if ($.trim(nub) != $.trim(item.nub))
                $('#selectcar').append(new Option(item.nub, item.nub));//往下拉菜单里添加元素
        })
        $('#btncarsubmit').show(); $('#btncarcancel').show();
        $('#changecar').hide();
    })
    $('#btncarcancel').click(function() {
        $('#btncarcancel').hide();$('#btncarsubmit').hide();
        $('#changecar').show();
        $('#selectcar').remove();
        $('nub').show()
    })
    $('#btncarsubmit').click(function() {
        nub = $('#selectcar').val();
        $('#btncarcancel').hide();$('#btncarsubmit').hide();
        $('#changecar').show();
        $('#selectcar').remove();
        $('nub').text(nub);
        $('nub').show()

    })
//修改用户信息
    $('#changeuser').click(function(){
        // console.log($('.btt'))
        // name = orderchange_data.order.user_name;
        // sex = orderchange_data.order.user_sex;
        // cid = orderchange_data.order.user_id_card;
        // email = orderchange_data.order.user_email;
        // pname = orderchange_data.order.user_emergency_name;
        // pphone = orderchange_data.order.user_emergency_phone;
        $('#btnusersubmit').show(); $('#btnusercancel').show();$('#changeuser').hide();
        $('.beforeinput').attr("disabled",false);
        $('.beforeinput').attr("class","myinput");
    })
    $('#btnusersubmit').click(function() {
        $('#btnusercancel').hide();$('#btnusersubmit').hide();
        $('#changeuser').show();

        //console.log(orderchange_data.user.gender);
        // var data = {
        //     id:orderchange_data.user.id,
        //     idCard:orderchange_data.user.idCard,
        //     name:orderchange_data.user.name,
        //     email:orderchange_data.user.email,
        //     emergencyName:orderchange_data.user.emergencyName,
        //     emergencyPhone:orderchange_data.user.emergencyPhone,
        // }
        // $.ajax({
        //     type: "post",
        //     url: "/api/customer/change",
        //     //data:JSON.stringify(data),
        //     contentType:'application/json',
        //     data:JSON.stringify(data),
        //     success:function (res) {
        //         if (res.code === 0)
        //             layer.msg(res.msg, {
        //                     time: 1000
        //                 },
        //                 function () {
        //                     parent.layui.table.reload("orderstable")
        //                     const index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        //                     parent.layer.close(index); //再执行关闭
        //                     //layui.table.reload("orders");
        //                 })
        //     },
        //     fail: function (res) {
        //         console.log(res);
        //     }
        // })

        $('.myinput').attr("class","beforeinput");
        $('.beforeinput').attr("disabled",true);
        //要刷新vue的数据，不然先修改提交再修改取消会有bug
    })
    $('#btnusercancel').click(function() {
        $('#btnusercancel').hide();$('#btnusersubmit').hide();
        $('#changeuser').show();
        orderchange_data.order.user_name = name;orderchange_data.order.user_sex = sex;orderchange_data.order.user_id_card = cid;
        orderchange_data.order.user_email = email;orderchange_data.order.user_emergency_name = pname;orderchange_data.order.user_emergency_phone = pphone;
        $('.myinput').attr("class","beforeinput");
        $('.beforeinput').attr("disabled",true);
    })
//确认提车
    $('#change_get').click(function() {
        //console.log("last"+orderchange_data.order.car_number)
        if($("#changeuser").is(":hidden")||$("#changecar").is(":hidden")) alert("请先提交或取消修改！");
        else{
            var deposit = orderchange_data.order.deposit;
            //confirm("是否确认收取"+deposit+"元押金，完成提车？");
            layer.confirm("是否确认收取"+deposit+"元押金，完成提车？", function(index){
                //do something

                layer.close(index);

            var data = {
                id:orderchange_data.order.id,
                isDepositReturned:0,
                status:1,
                deposit:deposit
            }
            console.log();
            $.ajax({
                type: "post",
                url: "/api/order/updateStatus",
                //data:JSON.stringify(data),
                contentType:'application/json',
                data:JSON.stringify(data),
                success:function (res) {
                    if (res.code === 0)
                        layer.msg(res.msg, {
                                time: 1000
                            },
                            function () {
                                parent.layui.table.reload("orderstable")
                                const index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                                parent.layer.close(index); //再执行关闭
                                //layui.table.reload("orders");

                            })
                },
                fail: function (res) {
                    console.log(res);
                }
            })
            });

        }
    })
    //确认退款
    $('#change_money').click(function () {
        layer.confirm("是否确认退款？", function(index){
            //do something
            layer.close(index);
            var data = {
                id:orderchange_data.order.id,
                carItemId:orderchange_data.order.carItemId,
                status:3,
            }
            $.ajax({
                type: "post",
                url: "/api/order/updateStatus",
                //data:JSON.stringify(data),
                contentType:'application/json',
                data:JSON.stringify(data),
                success:function (res) {
                    if (res.code === 0)
                        layer.msg(res.msg, {
                                time: 1000
                            },
                            function () {
                                parent.layui.table.reload("orderstable")
                                const index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                                parent.layer.close(index); //再执行关闭
                                //layui.table.reload("orders");

                            })
                },
                fail: function (res) {
                    console.log(res);
                }
            })
        });
    })

//取消提车
    $('#change_cancel').click(function () {
        //confirm("是否确认收取"+deposit+"元押金，完成提车？");
        layer.confirm("是否取消租车？", function(index){
            //do something

            layer.close(index);

            var data = {
                id:orderchange_data.order.id,
                carItemId:orderchange_data.order.carItemId,
                status:3,
            }
            $.ajax({
                type: "post",
                url: "/api/order/updateStatus",
                //data:JSON.stringify(data),
                contentType:'application/json',
                data:JSON.stringify(data),
                success:function (res) {
                    if (res.code === 0)
                        layer.msg(res.msg, {
                                time: 1000
                            },
                            function () {
                                parent.layui.table.reload("orderstable")
                                const index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                                parent.layer.close(index); //再执行关闭
                                //layui.table.reload("orders");

                            })
                },
                fail: function (res) {
                    console.log(res);
                }
            })
        });


    })
//确认还车
    $('#change_back').click(function () {
        layer.confirm("是否确认还车？", function(index) {
            //do something
            var data = {
                id:orderchange_data.order.id,
                isDepositReturned:1,
                otherAmount:$('#zongshu').val(),
                description:$('#beizhu').val(),
                status:4,
            }
            $.ajax({
                type: "post",
                url: "/api/order/updateStatus",
                //data:JSON.stringify(data),
                contentType:'application/json',
                data:JSON.stringify(data),
                success:function (res) {
                    if (res.code === 0)
                        layer.msg(res.msg, {
                                time: 1000
                            },
                            function () {
                                parent.layui.table.reload("orderstable")
                                const index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                                parent.layer.close(index); //再执行关闭
                                //layui.table.reload("orders");

                            })
                },
                fail: function (res) {
                    console.log(res);
                }
            })
        });
    })
//添加费用
    $('#change_addmoney').click(function () {
        layui.use(['layer','form'], function(){
            var form = layui.form;
            layer.open({
                type: 1,
                content:$('#newdiv')
                ,title: '费用列表'
                ,btn: ['添加','确认', '关闭']
                ,area: ['400px', '400px'] //自定义文本域宽高
                ,success:function (layero,index) {
                    layero.addClass('layui-form');//添加form标识
                    layero.find('.layui-layer-btn1').attr('lay-filter', 'fromVerify').attr('lay-submit', '');//将按钮弄成能提交的
                    form.render(null, 'test1');
                }
                ,btn1: function(index, layero){
                    add();
                }
                ,btn2: function(index, layero){
                    //按钮【按钮三】的回调
                    form.on('submit(fromVerify)', function (data) {
                        sub(index);
                        // return false;
                    });
                    //sub();/
                    //$('#newdiv').hide();
                    return false; //开启该代码可禁止点击该按钮关闭
                }
                ,btn3: function(index, layero){
                    //按钮【按钮三】的回调

                    $('#newdiv').hide();layer.close(index);
                    //return false 开启该代码可禁止点击该按钮关闭
                }
                ,cancel: function(){
                    //右上角关闭回调
                    $('#newdiv').hide();
                    //return false 开启该代码可禁止点击该按钮关闭
                }
            });
        });
    })
})
function add() {
    var $tr = $("<tr>\n" +
        "            <td><input lay-verify=\"required\" autocomplete=\"off\" class=\"layui-input\" name=\"state\" type='text' style='height:30px;width: 160px'/></td>\n" +
        "            <td><input lay-verify=\"required|number\" autocomplete=\"off\" class=\"layui-input\" name=\"yuan\" type='text' style='height:30px;width: 100px'/></td>\n" +
        "            <td><a class=\"layui-icon layui-icon-delete\" style=\"font-size: 25px; color: red;\" onclick='del(this)'></a></td>\n" +
        "        </tr>");
     $("#mytable tbody").append($tr);
}
function del(obj) {
    $(obj).parent().parent().remove();
}
function sub(index) {
    // layui.use('form', function () {
    // var form = layui.form;
    var state,yuan;
    var length =  $("#mytable tbody tr").length;
    var data = "";
    var sum = 0;
    $("#mytable tbody tr").each(function (index) {
        state = $("#mytable tbody tr:eq("+index+") input[name='state']").val();
        yuan = $("#mytable tbody tr:eq("+index+") input[name='yuan']").val();
        sum = sum + parseFloat(yuan);
        data = data +";"+ state + ":" + yuan ;
    })
    $('#beizhu').val(data);
    $('#zongshu').val(sum);
    console.log("data-------"+$('#beizhu').val());
    console.log("sum-------"+$('#zongshu').val());
    $('#newdiv').hide();
    layer.close(index);
    // });
}
function checknub(obj) {
    if(isNaN($(obj).val())) obj.focus();
}