<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<form:form id="userForm" class="form-horizontal" action="${ctx}/system/user/save" modelAttribute="user"
           enctype="multipart/form-data">
    <form:hidden path="id"/>
    <form:hidden path="password"/>
    <div class="box">
        <div class="box-header with-border">
            <h3 class="box-title"></h3>
            <div class="btn-group  pull-right">
                <button type="submit" class="btn btn-primary">
                    <i class="fa fa-save"></i>
                </button>
                <a type="button" class="btn btn-primary" href="${ctx}/system/user/completeInfoView">
                    <i class="fa fa-mail-reply"></i>
                </a>
            </div>
        </div>
        <div class="box-body">
            <div class="row">
                <div class="col-md-8 col-md-offset-2">
                    <div class="form-group">
                        <label for="name" class="col-sm-2 control-label">所属部门</label>
                        <div class="col-sm-10">
                            <form:hidden path="office.id" cssClass="form-control"/>
                            <form:input path="office.name" cssClass="form-control" placeholder="部门名称" readonly="true"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="name" class="col-sm-2 control-label">姓名</label>
                        <div class="col-sm-10">
                            <form:input path="name" cssClass="form-control" placeholder="负责人姓名"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="mobile" class="col-sm-2 control-label">电话</label>
                        <div class="col-sm-10">
                            <form:input path="mobile" cssClass="form-control" placeholder="负责人电话" readonly="true"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="name" class="col-sm-2 control-label">角色</label>
                        <div class="col-sm-10">
                            <form:hidden path="role.id" cssClass="form-control"/>
                            <form:input path="role.name" cssClass="form-control" placeholder="角色名称" readonly="true"/>
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
                <a type="button" class="btn btn-primary" href="${ctx}/system/user/completeInfoView">
                    <i class="fa fa-mail-reply"></i>
                </a>
            </div>
        </div>
    </div>
</form:form>
<script>
    $(function () {
        initForm();
    });

    function initForm() {
        $("#userForm").validate({
            ignore: '',
            rules: {
                name: {
                    required: true,
                },
            },
            messages: {
                name: {
                    required: '请填写姓名'
                },
            },
            tooltip_options: {},
            submitHandler: function (form) {
                form.submit();
            }
        });
    }

</script>
