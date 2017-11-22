$(function () {
    //给全选的复选框添加事件
    $("#all").click(function () {
        // this 全选的复选框
        var userids = this.checked;
        //获取name=box的复选框 遍历输出复选框
        $("input[name=checkedId]").each(function () {
            this.checked = userids;
        });
    });
    //给name=box的复选框绑定单击事件
    $("input[name=checkedId]").click(function () {
        var length = $("input[name=checkedId]:checked").length;
        var len = $("input[name=checkedId]").length;
        if (length == len) {
            $("#all").get(0).checked = true;
        } else {
            $("#all").get(0).checked = false;
        }
    });
});

//title, content,confirmButton, cancelButton, onConfirm, onCancel
function confirmWithJbox(title, content, confirmButton, cancelButton, onConfirm, onCancel) {
    new jBox('Confirm', {
        title: (title || ''),
        content: (content || ''),
        confirmButton: confirmButton,
        cancelButton: cancelButton,
        closeOnConfirm: true,
        confirm: function () {
            if (onConfirm) {
                onConfirm();
            }
        },
        cancel: function () {
            if (onCancel) {
                onCancel();
            }
        }
    }).open();
}

function del(url) {
    //title, content,confirmButton, cancelButton, onConfirm, onCancel
    confirmWithJbox('警告', '确认删除这条记录？', '删除', '取消', function () {
        document.location = url;
    }, function () {
    });
    return false;
}