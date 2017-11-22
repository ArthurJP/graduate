<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<form:form id="inputForm" role="form" action="${ctx}/system/dictComplex/save" method="post"
           modelAttribute="dictComplex">
    <form:hidden path="id"/>

    <div class="box">
        <div class="box-header with-border">
            <h3 class="box-title"></h3>
            <div class="btn-group  pull-right">
                <button type="submit" class="btn btn-primary">
                    <i class="fa fa-save"></i>
                </button>
                <a type="button" class="btn btn-primary" href="${ctx}/system/dictComplex">
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
                        <label for="parentName">父级业务字典</label>
                        <input type="hidden" name="parent.id" value="${dictComplex.parent.id}" id="parentId">
                        <input type="text" class="form-control" placeholder="请选择父级业务字典"
                               value="${dictComplex.parent.label}" name="parent.label"
                               id="parentName" readonly>
                    </div>
                    <div class="form-group">
                        <label for="dictValue">键值</label>
                        <form:input path="value" class="form-control" id="dictValue" placeholder="键值"/>
                    </div>
                    <div class="form-group">
                        <label for="dictLabel">标签</label>
                        <form:input path="label" class="form-control" id="dictLabel" placeholder="标签"/>
                    </div>
                    <div class="form-group">
                        <label for="type">类型</label>
                        <form:input path="type" class="form-control" id="type" placeholder="标签"/>
                    </div>
                    <div class="form-group">
                        <label for="description">描述</label>
                        <form:input path="description" class="form-control" id="description" placeholder="描述"/>
                    </div>
                    <div class="form-group">
                        <label for="sort">排序</label>
                        <form:input path="sort" class="form-control" id="sort" placeholder="排序"/>
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
        initDictTree();
    });

    function initForm() {
        $("#inputForm").validate({
            rules: {
                value: {
                    required: true
                },
                label: {
                    required: true
                },
                type: {
                    required: true
                },
                description: {
                    required: true
                },
                sort: {
                    required: true,
                    digits: true
                }
            },
            messages: {
                value: {
                    required: '请输入字典值'
                },
                label: {
                    required: '请输入标签'
                },
                type: {
                    required: '请输入类型'
                },
                description: {
                    required: '请输入描述'
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

    function initDictTree() {

        $.ajax('${ctx}/system/dictComplex/treeData', {
            dataType: 'json',
            method: 'post',
            cache: false,
            data: {
                'defaultSelectedId': '${dictComplex.parent.id}'
            },
            beforeSend: function () {
                NProgress.start();
            },
            complete: function (jqXhr, status) {
                NProgress.done();
            },
            success: function (data, status, jqXhr) {
                console.log(data);
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