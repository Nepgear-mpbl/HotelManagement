layui.use(['element', 'form', 'jquery', 'layer'], function () {
    var form = layui.form();
    var element = layui.element;
    var $ = layui.jquery;
    var layer = layui.layer;
    form.on('submit(changePwdSubmit)', function (data) {
        var postData = data.field;
        $.post('/login/changePwd', postData, function (changePwdData) {
            if(changePwdData.status){
                layer.open({
                    title: '消息'
                    , content: '修改成功!'
                    , yes: function () {
                        location.href = '/login';
                    }, cancel: function () {
                        location.href = '/login';
                    }
                });
            }else{
                layer.open({
                    title: '消息'
                    , content: changePwdData.message
                });
                $('#captcha-img').prop('src', '/login/captcha?v=' + Math.random());
            }
            $("#input-oldPassword").val("");
            $("#input-newPassword").val("");
            $("#input-repeatNewPassword").val("");
        });
        return false;
    });

    $('#captcha-img').click(function () {
        $('#captcha-img').prop('src', '/login/captcha?v=' + Math.random());
    });

});

