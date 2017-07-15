layui.use(['element', 'form', 'jquery', 'layer'], function () {
    var form = layui.form();
    var element = layui.element;
    var $ = layui.jquery;
    var layer = layui.layer;

    $('.lookFinishRoom').click(function (e) {
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
        $('.lookFinishRoom').click(function () {
            $('.Message').show();
            $('#add-Table-button').hide();
        });
    });
});
