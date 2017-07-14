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
    $(document).ready(function(){
        var roomTypeList=$('.room-type');
        for(var i=0;i<roomTypeList.length;i++)
            switch (roomTypeList[i].innerText){
                case '0':
                    roomTypeList[i].innerText='小包间(10)';
                    break;
                case '1':
                    roomTypeList[i].innerText='中包间(20)';
                    break;
                case '2':
                    roomTypeList[i].innerText='大包间(30)';
                    break;
                case '3':
                    roomTypeList[i].innerText='豪华包间(40)';
                    break;
                case '4':
                    roomTypeList[i].innerText='总统套间(50)';
                    break;
                default:
                    roomTypeList[i].innerText='未知';
            }
    });
});
