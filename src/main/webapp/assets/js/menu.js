layui.use(['element', 'form', 'jquery', 'layer'], function () {
    var form = layui.form();
    var element = layui.element;
    var $ = layui.jquery;
    var layer = layui.layer;
    var editMealUrl=null;

    form.on('submit(addSubmit)', function (data) {
        console.log(data.field);
        var postData = data.field;
        $.post('menu/add', postData, function (addMealData) {
            if(addMealData.status){
                layer.open({
                    title: '消息'
                    , content: '添加菜品成功!'
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
                    , content: addMealData.message
                });
            }
        });
        return false;
    });
    form.on('submit(editSubmit)', function (data) {
        console.log(data.field);
        var postData = data.field;
        $.post(editMealUrl, postData, function (editMealData) {
            if(editMealData.status){
                layer.open({
                    title: '消息'
                    , content: '修改菜品信息成功!'
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
                    , content: editMealData.message
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
        editMealUrl = $this.prop('href');
        $('#edit-menu-div').show();
        $('#edit-menu-lable').text('当前修改菜名:'+$this.parent().parent().find('td:first').text());
    });
    $(document).ready(function(){
        $('#add-button').click(function () {
            $('#add-menu-div').show();
            $('#add-button').hide();
        });
    });
});

