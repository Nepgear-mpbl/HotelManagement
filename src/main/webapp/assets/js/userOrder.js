layui.use(['element', 'jquery', 'layer'], function () {
    var element = layui.element;
    var $ = layui.jquery;
    var layer = layui.layer;

    $('.pay').click(function (e) {
        e.preventDefault();
        var $this = $(this);
        var url = $this.prop('href');
        $.post(url, {}, function (payJson) {
            if (payJson.status) {
                layer.open({
                    title: '消息'
                    , content: '支付订单成功!'
                    ,yes:function () {
                        location.reload();
                    }
                    ,cancel:function () {
                        location.reload();
                    }
                });
            } else {
                layer.open({
                    title: '消息'
                    , content: payJson.message
                });
            }
        });
    });
});