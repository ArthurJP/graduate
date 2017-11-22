<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<link href="${ctxStatic}/treeTable/themes/vsStyle/treeTable.min.css" rel="stylesheet" type="text/css"/>
<script src="${ctxStatic}/treeTable/jquery.treeTable.js" type="text/javascript"></script>

<div class="box">
    <div class="box-header with-border">
        &nbsp;
        <!-- tools box -->
        <div class="pull-right box-tools">
            <a href="${ctx}/system/office/form" class="btn btn-primary btn-sm"><i class="fa fa-plus"></i></a>
        </div>
        <!-- /. tools -->
    </div>
    <div class="box-body table-responsive no-padding">
        <table class="table table-hover table-bordered" id="treeTable">
            <tr>
                <th>名称</th>
                <th>上级部门</th>
                <th>操作</th>
            </tr>
            <tbody>
            <c:forEach items="${list}" var="office">
                <tr id="${office.id}" pId="${office.parent.id ne '1'?office.parent.id:'0'}">
                    <td nowrap style="text-align: left;"><a
                            href="${ctx}/system/office/form?id=${office.id}">${office.name}</a></td>
                    <td nowrap style="text-align: left;">${office.parent.name}</td>
                    <td nowrap class="text-center">
                        <a href="${ctx}/system/office/form?id=${office.id}" class="btn btn-primary btn-xs"> 修改</a>
                        <a href="${ctx}/system/office/delete?id=${office.id}"
                           onclick="return del(this.href)" class="btn btn-danger btn-xs"> 删除</a>
                        <a href="${ctx}/system/office/form?parent.id=${office.id}" class="btn btn-success btn-xs">
                            添加下级部门</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <!-- /.box-body -->
    <div class="box-footer">
        &nbsp;
    </div>
    <!-- /.box-footer-->
</div>
<script type="text/javascript">
    $(document).ready(function () {
        $("#treeTable").treeTable({expandLevel: 3}).show();
    });
</script>