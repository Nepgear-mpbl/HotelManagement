layui.use(['element', 'jquery', 'layer'], function () {
    var element = layui.element;
    var $ = layui.jquery;
    var layer = layui.layer;
    $('.setOrderTable').click(function (e) {
        e.preventDefault();
        var $this = $(this);
        var url = $this.prop('href');
        $.post(url, {}, function (setOrderTableJson) {
            if (setOrderTableJson.status) {
                var url='/order/orderFinal/'+$('#orderId').text();
                console.log(url);
                layer.open({
                    title: '消息'
                    , content: '预订桌子成功!'
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
                    , content: setOrderTableJson.message
                });
            }
        });
    });
    $('#cancer').click(function () {
        location.pathname='/order';
    });
});
