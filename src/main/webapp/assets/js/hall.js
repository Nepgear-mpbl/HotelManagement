layui.use(['element', 'form', 'jquery', 'layer'], function () {
    var form = layui.form();
    var element = layui.element;
    var $ = layui.jquery;
    var layer = layui.layer;
    var editHallUrl=null;

    form.on('submit(addSubmit)', function (data) {
        console.log(data.field);
        var postData = data.field;
        $.post('hall/add', postData, function (addTableData) {
            if(addTableData.status){
                layer.open({
                    title: '消息'
                    , content: '添加桌子成功!'
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
                    , content: addTableData.message
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
    $(document).ready(function(){
        $('#add-Table-button').click(function () {
            $('#add-Table-div').show();
            $('#add-Table-button').hide();
        });
        $('#cancerAdd').click(function () {
            $('#add-Table-div').hide();
            $('#add-Table-button').show();
        });
    });
});
