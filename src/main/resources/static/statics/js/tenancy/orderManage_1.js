layui.use(['layer', 'form'], function () {
    var layer = layui.layer
        , form = layui.form

    /*
    引用jquery
     */
    // var $ = layui.$;

    /*
    ajax获取弹出层内容
     */
    // $.post('url', {}, function(str){
    //     layer.open({
    //         type: 1,
    //         content: str //注意，如果str是object，那么需要字符拼接。
    //     });
    // });

    /*
    按钮组
     */
    // layer.open({
    //     content: 'test'
    //
    //     ,btn: ['按钮一', '按钮二', '按钮三']
    //     ,btn1: function(index, layero){
    //         layer.alert('酷毙了', {icon: 5});
    //         //按钮【按钮一】的回调
    //     }
    //     ,btn2: function(index, layero){
    //         //按钮【按钮二】的回调
    //
    //         return false
    //     }
    //     ,btn3: function(index, layero){
    //         //按钮【按钮三】的回调
    //
    //         //return false 开启该代码可禁止点击该按钮关闭
    //     }
    //     ,cancel: function(){
    //         //右上角关闭回调
    //
    //         //return false 开启该代码可禁止点击该按钮关闭
    //     }
    // });

    /*
    alert
     */
    //eg1
    //layer.alert('只想简单的提示');
    //eg2
    // layer.alert('加了个图标', {icon: 1,title: '哈哈傻逼'},function () {
    //     layer.alert('0.0  ')
    // }); //这时如果你也还想执行yes回调，可以放在第三个参数中。
    //eg3
    // layer.alert('有了回调', function (index) {
    //     //do something
    //
    //     layer.close(index);
    // });

    /*
    confirm(content, options, yes, cancel)
     */
    //eg1
    // layer.confirm('is not?', {icon: 3, title:'提示'}, function(index){
    //     //do something
    //
    //     layer.close(index);
    // });
    //eg2
    // layer.confirm('is not?', function(index){
    //     //do something
    //
    //     layer.close(index);
    // });

    /*
    tab
     */
    // layer.tab({
    //     area: ['600px', '300px'],
    //     tab: [{
    //         title: 'TAB1',
    //         content: '内容1'
    //     }, {
    //         title: 'TAB2',
    //         content: '内容2'
    //     }, {
    //         title: 'TAB3',
    //         content: '内容3'
    //     }]
    // });

    /*
    prompt(options, yes) - 输入层
    */
    //prompt层新定制的成员如下
    // {
    //     formType:1,//输入框类型，支持0（文本）默认1（密码）2（多行文本）
    //     value:555 //初始时的值，默认空字符
    //     maxlength:140 //可输入文本的最大长度，默认500
    // }
//例子1
//     layer.prompt(function(value, index, elem){
//         alert(value); //得到value
//         layer.close(index);
//     });
//例子2
//     layer.prompt({
//         formType: 2,
//         value: '初始值',
//         title: '请输入值',
//         area: ['800px', '350px'] //自定义文本域宽高
//     }, function(value, index, elem){
//         alert(value); //得到value
//         layer.close(index);
//     });

    // layer.open({
    //     type: 0//弹窗层类型
    //     , title: '我是林悦鸿'//弹出层标题
    //     , content: "asdasdasd"//弹出层内容
    //     , skin: 'layui-layer-molv'//弹窗层样式
    //     //,area: ['500px', '300px']//弹窗层宽高，默认是自适应
    //     //,offset:"lt"//弹出层位置，默认是居中
    //     //,closeBtn:0//弹出层关闭按钮
    //     , anim: 1//弹出层弹出动画
    //     //,resize:false //是否允许拉伸,默认是ture，在右下角拉伸
    //     , success: function (layero, index) {
    //         console.log(layero, index);
    //     }//层弹出后的成功回调方法,分别是当前层DOM当前层索引
    //     , yes: function (layero, index) {
    //         //dosmthing
    //         layer.close(index); //如果设定了yes回调，需进行手工关闭
    //     }//确定按钮回调方法
    //     , cancel: function (index, layero) {
    //         if (confirm('确定要关闭么')) { //只有当点击confirm框的确定时，该层才会关闭
    //             layer.close(index)
    //         }
    //         return false;
    //     }//右上角关闭按钮触发的回调
    // })
    //layer.alert('酷毙了', {icon: 5});
    //layer.msg("林悦鸿")

})