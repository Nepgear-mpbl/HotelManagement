layui.use(['element', 'form', 'jquery', 'layer'], function () {
    var form = layui.form();
    var element = layui.element;
    var $ = layui.jquery;
    var layer = layui.layer;

    form.on('submit(order1_hall)', function (data) {
        var postData=data.field ;
        $.post('/order/addOrder', postData, function (addOrderMessageJson) {
            if(addOrderMessageJson.status){
                var url='/order/orderNextHall/'+addOrderMessageJson.orderId.toString();
                console.log(url);
                layer.open({
                    title: '消息'
                    , content: '预订大厅成功!'
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
                    , content: addOrderMessageJson.message
                });
            }
        });
        return false;
    });
    form.on('submit(order1_room)', function (data) {
        var postData=data.field ;
        $.post('/order/addOrder', postData, function (addOrderMessageJson) {
            if(addOrderMessageJson.status){
                var url='/order/orderNextRoom/'+addOrderMessageJson.orderId.toString();
                console.log(url);
                layer.open({
                    title: '消息'
                    , content: '预订包间成功!'
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
                    , content: addOrderMessageJson.message
                });
            }
        });
        return false;
    });
});