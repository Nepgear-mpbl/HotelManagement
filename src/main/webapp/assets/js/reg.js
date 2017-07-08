layui.use(['element', 'form', 'jquery', 'layer'], function () {
    var form = layui.form();
    var element = layui.element;
    var $ = layui.jquery;
    var layer = layui.layer;
    form.on('submit(regSubmit)', function (data) {
        //console.log(data.field);
        var postData = data.field;
        $.post('reg', postData, function (regData) {
            if(regData.status){
                layer.open({
                    title: '消息'
                    , content: '注册成功!'
                    , yes: function () {
                        location.href = '/login';
                    }, cancel: function () {
                        location.href = '/login';
                    }
                });
            }else{
                layer.open({
                    title: '消息'
                    , content: regData.message
                });
                $("#password").val('');
                $("#repeatPassword").val('');
            }
        });
        return false;
    });
});