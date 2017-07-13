layui.use(['element', 'jquery', 'layer'], function () {
    var element = layui.element;
    var $ = layui.jquery;
    var layer = layui.layer;
    $('.addMeal').click(function (e) {
        e.preventDefault();
        var $this = $(this);
        var url = $this.prop('href');
        $.post(url, {}, function (addmMealJson) {
            if (addmMealJson.status) {
                var url='/order/mealNextStep/'+addmMealJson.orderId.toString();
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
            } else {
                layer.open({
                    title: '消息'
                    , content: addmMealJson.message
                });
            }
        });
    });
});
