<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<form:form id="inputForm" role="form" action="${ctx}/system/office/save" method="post" modelAttribute="office">
    <form:hidden path="id"/>

    <div class="box">
        <div class="box-header with-border">
            <h3 class="box-title"></h3>
            <div class="btn-group  pull-right">
                <button type="submit" class="btn btn-primary">
                    <i class="fa fa-save"></i>
                </button>
                <a type="button" class="btn btn-primary" href="${ctx}/system/office">
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
                        <label for="officeName">上级部门</label>
                        <input type="hidden" name="parent.id" value="${office.parent.id}" id="parentId">
                        <input type="text" class="form-control" placeholder="请选择上级部门" value="${office.parent.name}"
                               id="parentName" readonly>
                    </div>
                    <div class="form-group">
                        <label for="officeName">名称</label>
                        <form:input path="name" class="form-control" id="officeName" placeholder="菜单名称"/>
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
                <a type="button" class="btn btn-primary" href="${ctx}/system/office">
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
        initOfficeTree();
    });

    function initForm() {
        $("#inputForm").validate({
            rules: {
                name: {
                    required: true
                }
            },
            messages: {
                name: {
                    required: '请输入菜单名称'
                }
            },
            submitHandler: function (form) {
                form.submit();
            }
        });
    }

    function initOfficeTree() {

        $.ajax('${ctx}/system/office/treeData', {
            dataType: 'json',
            method: 'post',
            cache: false,
            data: {
                'defaultSelectedId': '${office.parent.id}'
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
                    });
                ;
            },
            error: function (jqXhr, status, error) {
                NProgress.done();
            }

        });

    }


</script>