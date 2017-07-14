layui.use(['element', 'form', 'jquery', 'layer'], function () {
    var form = layui.form();
    var element = layui.element;
    var $ = layui.jquery;
    var layer = layui.layer;

    form.on('submit(addOrderPeople)', function (data) {
        var postData=data.field ;
        $.post('/order/addRoom', postData, function (addOrderMessageJson) {
            if(addOrderMessageJson.status){
                var url='/order/roomNextStep/'+addOrderMessageJson.roomOrderId.toString();
                console.log(url);
                layer.open({
                    title: '消息'
                    , content: '预订成功!'
                    ,yes:function () {
                        location.pathname=url;
                    }
                    ,cancel:function () {
                        location.pathname=url;
                    }
                });
            }else{
                layer.open({
                    title: '消息'
                    , content: addOrderMessage.message
                });
            }
        });
        return false;
    });
});