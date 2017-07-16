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
        $('#add-button').hide();
        $('#edit-menu-div').show();
        $('#edit-menu-lable').text('当前修改菜名:'+$this.parent().parent().find('td:first').text());
    });
    $(document).ready(function(){
        var mealTypeList=$('.meal-type');
        var mealRecList=$('.meal-rec');
        for(var i=0;i<mealTypeList.length;i++)
            switch (mealTypeList[i].innerText){
                case '0':
                    mealTypeList[i].innerText='素菜';
                    break;
                case '1':
                    mealTypeList[i].innerText='荤菜';
                    break;
                case '2':
                    mealTypeList[i].innerText='凉菜';
                    break;
                case '3':
                    mealTypeList[i].innerText='汤';
                    break;
                case '4':
                    mealTypeList[i].innerText='主食';
                    break;
                default:
                    mealTypeList[i].innerText='未知';
            }
        for(var i=0;i<mealRecList.length;i++)
            switch (mealRecList[i].innerText){
                case '0':
                    mealRecList[i].innerText='热销';
                    break;
                case '1':
                    mealRecList[i].innerText='火热';
                    break;
                case '2':
                    mealRecList[i].innerText='非常火爆';
                    break;
                case '3':
                    mealRecList[i].innerText='本店招牌';
                    mealRecList[i].style.color='red';
                    break;
                default:
                    mealRecList[i].innerText='未知';
            }
        $('#add-button').click(function () {
            $('#add-menu-div').show();
            $('#add-button').hide();
        });
        $('#cancerAdd').click(function () {
            $('#add-menu-div').hide();
            $('#add-button').show();
        });
        $('#cancerEdit').click(function () {
            $('#edit-menu-div').hide();
            $('#add-button').show();
        });
    });
});

