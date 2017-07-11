layui.use(['element', 'form', 'jquery', 'layer'], function () {
    var form = layui.form();
    var element = layui.element;
    var $ = layui.jquery;
    var layer = layui.layer;
    var editRoomUrl=null;

    form.on('submit(addSubmit)', function (data) {
        console.log(data.field);
        var postData = data.field;
        $.post('room/add', postData, function (addBookData) {
            if(addBookData.status){
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
                    , content: addBookData.message
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
        $('#add-button').click(function () {
            $('#add-room-div').show();
            $('#add-button').hide();
        });
    });
});
