layui.use(['element', 'form', 'jquery', 'layer'], function () {
    var form = layui.form();
    var element = layui.element;
    var $ = layui.jquery;
    var layer = layui.layer;

    form.on('submit(setOrderText)', function (data) {
        var menuData = [];
        var menuLen = parseInt($('#menu-size').text());
        for (var i = 0; i < menuLen; i++) {
            var idStr = 'id-' + i.toString();
            var numStr = 'num-' + i.toString();
            var id = $('#' + idStr).text();
            var num = $('#' + numStr).val();
            if (num > 0) {
                menuData.push({'id': id, 'num': num});
            }
        }
        console.log(menuData);
        if (menuData.length > 0) {
            var postData = {
                'data': JSON.stringify(menuData),
                'orderId': $('#orderId').text()
            };
            console.log(postData);

            $.post('/order/setOrderText', postData, function (setOrderTextData) {
                if (setOrderTextData.status) {
                    layer.open({
                        title: '消息'
                        , content: '提交成功!'
                        , yes: function () {
                            location.href = '/order/user';
                        }
                        , cancel: function () {
                            location.href = '/order/user';
                        }
                    });
                } else {
                    layer.open({
                        title: '消息'
                        , content: setOrderTextData.message
                    });
                }
            });
        } else {
            layer.open({
                title: '消息'
                , content: '请选择菜品'
            });
        }
        return false;
    });
    $(document).ready(function () {
        var mealTypeList = $('.meal-type');
        var mealRecList = $('.meal-rec');
        for (var i = 0; i < mealTypeList.length; i++)
            switch (mealTypeList[i].innerText) {
                case '0':
                    mealTypeList[i].innerText = '素菜';
                    break;
                case '1':
                    mealTypeList[i].innerText = '荤菜';
                    break;
                case '2':
                    mealTypeList[i].innerText = '凉菜';
                    break;
                case '3':
                    mealTypeList[i].innerText = '汤';
                    break;
                case '4':
                    mealTypeList[i].innerText = '主食';
                    break;
                default:
                    mealTypeList[i].innerText = '未知';
            }
        for (var i = 0; i < mealRecList.length; i++)
            switch (mealRecList[i].innerText) {
                case '0':
                    mealRecList[i].innerText = '热销';
                    break;
                case '1':
                    mealRecList[i].innerText = '火热';
                    break;
                case '2':
                    mealRecList[i].innerText = '非常火爆';
                    break;
                case '3':
                    mealRecList[i].innerText = '本店招牌';
                    mealRecList[i].style.color = 'red';
                    break;
                default:
                    mealRecList[i].innerText = '未知';
            }
    });
});
