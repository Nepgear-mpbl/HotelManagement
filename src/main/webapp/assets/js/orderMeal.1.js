layui.use(['element', 'form', 'jquery', 'layer'], function () {
    var form = layui.form();
    var element = layui.element;
    var $ = layui.jquery;
    var layer = layui.layer;
    var editHallUrl=null;
    $('.addmeal').click(function (e) {
        e.preventDefault();
        var $this = $(this);
        var url = $this.prop('href');
        $.post(url, {}, function (addmealJson) {
            if (addmealJson.status) {
                layer.open({
                    title: '消息'
                    , content: '预订成功!'
                    ,yes:function () {
                        location.reload();
                    }
                    ,cancel:function () {
                        location.reload();
                    }
                });
                location.herf='/order/mealNextStep/'+addmealJson.orderId.toString();
            } else {
                layer.open({
                    title: '消息'
                    , content: addmealJson.message
                });
            }
        });
    });
});
