<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<form:form id="userForm" class="form-horizontal" action="${ctx}/system/user/saveInForm" modelAttribute="user">
    <form:hidden path="id"/>
    <form:hidden path="password"/>
    <div class="box">
        <div class="box-header with-border">
            <h3 class="box-title"></h3>
            <div class="btn-group  pull-right">
                <button type="submit" class="btn btn-primary">
                    <i class="fa fa-save"></i>
                </button>
                <a type="button" class="btn btn-primary" href="${ctx}/system/user">
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
                        <label for="officeId" class="col-sm-2 control-label">所属部门</label>
                        <input type="hidden" name="office.id" value="${user.office.id}" id="officeId">
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="office.name" placeholder="请选择所属部门"
                                   value="${user.office.name}" id="officeName" readonly>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="name" class="col-sm-2 control-label">姓名</label>
                        <div class="col-sm-10">
                            <form:input path="name" cssClass="form-control" placeholder="真实姓名"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="mobile" class="col-sm-2 control-label">电话</label>
                        <div class="col-sm-10">
                            <form:input path="mobile" cssClass="form-control" placeholder="手机号"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="roleId" class="col-sm-2 control-label">角色</label>
                        <div class="col-sm-10">
                            <form:select path="role.id" cssClass="form-control" id="roleId">
                                <form:option value="">请选择角色</form:option>
                                <form:options items="${roleList}" itemLabel="name" itemValue="id"/>
                            </form:select>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="box-footer">
            <div class="btn-group  pull-right">
                <button type="submit" class="btn btn-primary">
                    <i class="fa fa-save"></i>
                </button>
                <a type="button" class="btn btn-primary" href="${ctx}/system/user">
                    <i class="fa fa-mail-reply"></i>
                </a>
            </div>
        </div>
    </div>
</form:form>
<script>
    $(function () {
        initForm();
        initOfficeTree();
    });

    function initForm() {
        $("#userForm").validate({
            ignore: '',
            rules: {
                name: {
                    required: true,
                },
                'office.name': {
                    required: true,
                },
                'role.id': {
                    required: true,
                }
            },
            messages: {
                name: {
                    required: '请填写姓名'
                },
                'office.name': {
                    required: '请选择部门'
                },
                'role.id': {
                    required: '请选择角色'
                }
            },
            tooltip_options: {},
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
                'defaultSelectedId': '${user.office.id}'
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
                        $('#officeId').val(data.id);
                        $('#officeName').val(data.text);
                    });
            },
            error: function (jqXhr, status, error) {
                NProgress.done();
            }

        });

    }


</script>
