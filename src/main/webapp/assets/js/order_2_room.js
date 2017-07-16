layui.use(['element', 'jquery', 'layer'], function () {
    var element = layui.element;
    var $ = layui.jquery;
    var layer = layui.layer;
    $('.setOrderRoom').click(function (e) {
        e.preventDefault();
        var $this = $(this);
        var url = $this.prop('href');
        $.post(url, {}, function (setOrderRoomJson) {
            if (setOrderRoomJson.status) {
                console.log(setOrderRoomJson);
                var url='/order/orderFinal/'+$('#orderId').text();
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
            } else {
                layer.open({
                    title: '消息'
                    , content: setOrderRoomJson.message
                });
            }
        });
    });
    $(document).ready(function(){
        var roomTypeList=$('.room-type');
        for(var i=0;i<roomTypeList.length;i++){
            switch (roomTypeList[i].innerText){
                case '0':
                    roomTypeList[i].innerText='小包间(10人)';
                    break;
                case '1':
                    roomTypeList[i].innerText='中包间(20人)';
                    break;
                case '2':
                    roomTypeList[i].innerText='大包间(30人)';
                    break;
                case '3':
                    roomTypeList[i].innerText='豪华包间(40人)';
                    break;
                case '4':
                    roomTypeList[i].innerText='总统套间(50人)';
                    break;
                default:
                    roomTypeList[i].innerText='未知';
            }
        }
    });
    $('#cancer').click(function () {
        location.pathname='/order';
    });
});
