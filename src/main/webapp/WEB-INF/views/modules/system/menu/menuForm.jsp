<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<form:form id="inputForm" role="form" action="${ctx}/system/menu/save" method="post" modelAttribute="menu">
    <form:hidden path="id"/>

    <div class="box">
        <div class="box-header with-border">
            <h3 class="box-title"></h3>
            <div class="btn-group  pull-right">
                <button type="submit" class="btn btn-primary">
                    <i class="fa fa-save"></i>
                </button>
                <a type="button" class="btn btn-primary" href="${ctx}/system/menu">
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
                        <label for="menuName">父级菜单</label>
                        <input type="hidden" name="parent.id" value="${menu.parent.id}" id="parentId">
                        <input type="text" class="form-control" placeholder="请选择父级菜单" value="${menu.parent.name}"
                               id="parentName" readonly>
                    </div>
                    <div class="form-group">
                        <label for="menuName">名称</label>
                        <form:input path="name" class="form-control" id="menuName" placeholder="菜单名称"/>
                    </div>
                    <div class="form-group">
                        <label for="url">链接</label>
                        <form:input path="url" class="form-control" id="url" placeholder="菜单链接"/>
                    </div>
                    <div class="form-group">
                        <label for="key">图标</label>
                        <form:input path="icon" class="form-control" id="icon" placeholder="图标"/>
                    </div>
                    <div class="form-group">
                        <label for="key">Key</label>
                        <form:input path="key" class="form-control" id="key" placeholder="菜单标识"/>
                    </div>
                    <div class="form-group">
                        <label for="type">类型</label>
                        <form:input path="type" class="form-control" id="type" placeholder="菜单分组类型"/>
                    </div>
                    <div class="form-group">
                        <label for="sort">排序</label>
                        <form:input path="sort" class="form-control" id="sort" placeholder="菜单排序" readonly="true"/>
                    </div>
                    <div class="form-group">
                        <label for="isShow">是否显示</label>
                        <form:select path="isShow" class="form-control">
                            <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value"/>
                        </form:select>
                    </div>
                    <div class="form-group">
                        <label for="permission">权限标识</label>
                        <form:input path="permission" class="form-control" id="permission" placeholder="权限标识"/>
                    </div>
                </div>
            </div>
        </div>
        <!-- /.box-body -->
        <div class="box-footer">
            <div class="btn-group  pull-right">
                <button type="submit" class="btn btn-primary">
                    <i class="fa fa-save"></i>
                </button>
                <a type="button" class="btn btn-primary" href="${ctx}/system/menu">
                    <i class="fa fa-mail-reply"></i>
                </a>
            </div>
        </div>
        <!-- /.box-footer-->
    </div>
</form:form>

<script>
    $(function () {
        initForm();
        initMenuTree();
    });

    function initForm() {
        $("#inputForm").validate({
            rules: {
                name: {
                    required: true
                },
                type: {
                    required: true
                },
                sort: {
                    required: true,
                    digits: true
                }
            },
            messages: {
                name: {
                    required: '请输入菜单名称'
                },
                type: {
                    required: '请输入菜单类型'
                },
                sort: {
                    required: '请输入排序号',
                    digits: '请输入整数'
                }
            },
            submitHandler: function (form) {
                form.submit();
            }
        });
    }

    function initMenuTree() {

        $.ajax('${ctx}/system/menu/treeData', {
            dataType: 'json',
            method: 'post',
            cache: false,
            data: {
                'defaultSelectedId': '${menu.parent.id}'
            },
            beforeSend: function () {
                NProgress.start();
            },
            complete: function (jqXhr, status) {
                NProgress.done();
            },
            success: function (data, status, jqXhr) {
                treeView = $('#tree')
                    .treeview({data: data})
                    .treeview('expandAll', {levels: 2, silent: true})
                    .on('nodeSelected', function (event, data) {
                        $('#parentId').val(data.id);
                        $('#parentName').val(data.text);
                        $('#type').val(data.type);
                    });
                ;
            },
            error: function (jqXhr, status, error) {
                NProgress.done();
            }

        });

    }


</script>