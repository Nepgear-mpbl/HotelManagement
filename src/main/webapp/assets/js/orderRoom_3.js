layui.use(['element', 'form', 'jquery', 'layer'], function () {
    var form = layui.form();
    var element = layui.element;
    var $ = layui.jquery;
    var layer = layui.layer;

    form.on('submit(addOrderMeal)', function (data) {
        var menuData =[];
        var menuLen=parseInt($('#menu-size').text());
        for(var i=0;i<menuLen;i++){
            var idStr='id-'+i.toString();
            var numStr='num-'+i.toString();
            var id=$('#'+idStr).text();
            var num=$('#'+numStr).val();
            if(num>0) {
                menuData.push({'id': id, 'num': num});
            }
        }
        console.log(menuData);
        if(menuData.length>0) {
            var postData = {
                'data': JSON.stringify(menuData),
                'roomOrderId': $('#roomOrderId').text(),
                'mealOrderId': $('#mealOrderId').text()
            };
            console.log(postData);

            $.post('/order/setRoomMeal', postData, function (addMealData) {
                if (addMealData.status) {
                    layer.open({
                        title: '消息'
                        , content: '提交成功!'
                        , yes: function () {
                            //location.reload();
                        }
                        , cancel: function () {
                            //location.reload();
                        }
                    });
                } else {
                    layer.open({
                        title: '消息'
                        , content: '未知错误'
                    });
                }
            });
        }else{layer.open({
            title: '消息'
            , content: '请选择菜品'
        });

        }
        return false;
    });
});
