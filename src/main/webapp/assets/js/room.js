layui.use(['element', 'form', 'jquery', 'layer'], function () {
    var form = layui.form();
    var element = layui.element;
    var $ = layui.jquery;
    var layer = layui.layer;
    var editRoomUrl=null;

    form.on('submit(addSubmit)', function (data) {
        console.log(data.field);
        var postData = data.field;
        $.post('room/add', postData, function (addRoomData) {
            if(addRoomData.status){
                layer.open({
                    title: '消息'
                    , content: '添加包间成功!'
                    ,yes:function () {
                        location.reload();
                    }
                    ,cancel:function () {
                        location.reload();
                    }
                });
            }else{
                layer.open({
                    title: '消息'
                    , content: addRoomData.message
                });
            }
        });
        return false;
    });
    form.on('submit(editSubmit)', function (data) {
        console.log(data.field);
        var postData = data.field;
        $.post(editRoomUrl, postData, function (editRoomData) {
            if(editRoomData.status){
                layer.open({
                    title: '消息'
                    , content: '修改包间信息成功!'
                    ,yes:function () {
                        location.reload();
                    }
                    ,cancel:function () {
                        location.reload();
                    }
                });
            }else{
                layer.open({
                    title: '消息'
                    , content: editRoomData.message
                });
            }
        });
        return false;
    });
    $('.remove').click(function (e) {
        e.preventDefault();
        var $this = $(this);
        var url = $this.prop('href');
        $.post(url, {}, function (removeJson) {
            if (removeJson.status) {
                layer.open({
                    title: '消息'
                    , content: '删除成功!'
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
                    , content: removeJson.message
                });
            }
        });
    });
    $('.edit').click(function (e) {
        e.preventDefault();
        var $this = $(this);
        editRoomUrl = $this.prop('href');
        $('#edit-room-div').show();
        $('#edit-room-lable').text('当前修改包间名:'+$this.parent().parent().find('td:first').text());
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
        $('#add-button').click(function () {
            $('#add-room-div').show();
            $('#add-button').hide();
        });
    });
});
