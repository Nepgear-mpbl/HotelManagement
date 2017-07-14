layui.use(['element', 'jquery', 'layer'], function () {
    var element = layui.element;
    var $ = layui.jquery;
    var layer = layui.layer;
    $('.orderRoom').click(function (e) {
        e.preventDefault();
        var $this = $(this);
        var url = $this.prop('href');
        $.post(url, {}, function (orderRoomJson) {
            if (orderRoomJson.status) {
                console.log(orderRoomJson);
                var url='/order/roomFinalStep/'+$('#roomOrderId').text()+'-'+orderRoomJson.mealOrderId.toString();
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
            } else {
                layer.open({
                    title: '消息'
                    , content: orderRoomJson.message
                });
            }
        });
    });
});
