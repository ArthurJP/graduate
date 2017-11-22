<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>


<form:form id="inputForm" role="form" action="${ctx}/system/role/save" method="post" modelAttribute="role">
    <form:hidden path="id"/>
    <input type="hidden" name="menuIds" value="${role.menuIds}" id="menuIds">
    <div class="box">
        <div class="box-header with-border">
            <h3 class="box-title"></h3>
            <div class="btn-group  pull-right">
                <button type="button" class="btn btn-primary" onclick="doFormSubmit();">
                    <i class="fa fa-save"></i>
                </button>
                <a type="button" class="btn btn-primary" href="${ctx}/system/role">
                    <i class="fa fa-mail-reply"></i>
                </a>
            </div>
        </div>
        <div class="box-body">
            <div class="row">
                <div class="col-md-4">
                    <div id="tree"></div>
                </div>
                <div class="col-md-8">
                    <div class="form-group">
                        <label for="roleName">名称</label>
                        <input id="oldName" name="oldName" type="hidden" value="${role.name}">
                        <form:input path="name" class="form-control required" id="roleName" placeholder="角色名称"/>
                    </div>
                    <div class="form-group">
                        <label for="ename">英文名称</label>
                        <input id="oldEname" name="oldEname" type="hidden" value="${role.ename}">
                        <form:input path="ename" class="form-control required" id="ename" placeholder="角色英文名称"/>
                    </div>
                </div>
            </div>
        </div>
        <!-- /.box-body -->
        <div class="box-footer">
            <div class="btn-group  pull-right">
                <button type="button" class="btn btn-primary" onclick="doFormSubmit();">
                    <i class="fa fa-save"></i>
                </button>
                <a type="button" class="btn btn-primary" href="${ctx}/system/role">
                    <i class="fa fa-mail-reply"></i>
                </a>
            </div>
        </div>
        <!-- /.box-footer-->
    </div>
</form:form>

<script>

    var treeView;
    var menuDataDone = false;

    $(function () {
        initMenuTree();
        initForm();
    });

    function initForm() {
        $("#inputForm").validate({
            rules: {
                name: {
                    remote: "${ctx}/system/role/checkName?oldName=" + encodeURIComponent("${role.name}"),
                    required: true
                },
                ename: {
                    remote: "${ctx}/system/role/checkEname?oldEname=" + encodeURIComponent("${role.ename}"),
                    required: true
                }
            },
            messages: {
                name: {
                    remote: "角色名已存在",
                    required: "请输入角色名称"
                },
                ename: {
                    remote: "英文名已存在",
                    required: "请输入英文名称"
                }
            }
        });
    }

    function setMenuIds() {
        if (treeView) {
            var nodes = treeView.treeview('getChecked');
            var ids = [];
            for (var i = 0; i < nodes.length; i++) {
                ids.push(nodes[i].id);
            }
            $("#menuIds").val(ids);
        }
    }

    function initMenuTree() {

        $.ajax('${ctx}/system/menu/treeData', {
            dataType: 'json',
            method: 'post',
            cache: false,
            data: {
                'defaultCheckedId': '${role.menuIds}'
            },
            beforeSend: function () {
                NProgress.start();
            },
            complete: function (jqXhr, status) {
                menuDataDone = true;
                if (menuDataDone == true) {
                    NProgress.done();
                }
            },
            success: function (data, status, jqXhr) {
                treeView = $('#tree')
                    .treeview({data: data, showCheckbox: true})
                    .treeview('expandAll', {levels: 2, silent: true})
                    .on('nodeChecked', function (event, node) {
                        recurNodeCheckStatusUp(treeView, node, true);
                        recurNodeCheckStatusDown(treeView, node, true);
                    }).on('nodeUnchecked', function (event, node) {
                        recurNodeCheckStatusUp(treeView, node, false);
                        recurNodeCheckStatusDown(treeView, node, false);
                    });
            },
            error: function (jqXhr, status, error) {
                NProgress.done();
            }
        });

    }

    function recurNodeCheckStatusUp(treeView, node, checked) {
        var parentNode = treeView.treeview('getParent', node);
        var siblings = treeView.treeview('getSiblings', node);
        if (parentNode && parentNode.state) {
            if (checked) {
                treeView.treeview('checkNode', [parentNode.nodeId, {silent: true}]);
                recurNodeCheckStatusUp(treeView, parentNode, checked);
            } else {
                //判断兄弟节点是否还有勾选状态的，否则全部去掉勾选
                var doUncheck = true;
                if (siblings.length > 0) {
                    for (var i = 0; i < siblings.length; i++) {
                        if (siblings[i].state && siblings[i].state.checked && siblings[i].state.checked == true) {
                            doUncheck = false;
                            break;
                        }
                    }
                }
                if (doUncheck == true) {
                    treeView.treeview('uncheckNode', [parentNode.nodeId, {silent: true}]);
                    recurNodeCheckStatusUp(treeView, parentNode, checked);
                }
            }

        }
    }

    /**
     * 向下检测节点勾选状态
     * @param treeView
     * @param node
     * @param checked
     */
    function recurNodeCheckStatusDown(treeView, node, checked) {
        if (!node) return;
        if (node.nodes && node.nodes.length > 0) {
            var nodes = node.nodes;
            for (var i = 0; i < nodes.length; i++) {
                if (checked) {
                    treeView.treeview('checkNode', [nodes[i].nodeId, {silent: false}]);
                } else {
                    treeView.treeview('uncheckNode', [nodes[i].nodeId, {silent: false}]);
                }
            }
        }
    }

    function doFormSubmit() {
        var nodes = treeView.treeview('getChecked');
        var ids = [];
        for (var i = 0; i < nodes.length; i++) {
            ids.push(nodes[i].id);
        }
        $("#menuIds").val(ids);
        $('#inputForm').submit();
    }

</script>
