<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<link href="${ctxStatic}/treeTable/themes/vsStyle/treeTable.min.css" rel="stylesheet" type="text/css"/>
<script src="${ctxStatic}/treeTable/jquery.treeTable.js" type="text/javascript"></script>

<div class="box">
    <div class="box-header with-border">
        &nbsp;
        <!-- tools box -->
        <div class="pull-right box-tools">
            <a href="${ctx}/system/dictComplex/form" class="btn btn-primary btn-sm"><i class="fa fa-plus"></i></a>
        </div>
        <!-- /. tools -->
    </div>
    <div class="box-body table-responsive no-padding">
        <table class="table table-hover table-bordered" id="treeTable">
            <tr>
                <th>标签</th>
                <th>键值</th>
                <th>类型</th>
                <th>描述</th>
                <th>排序</th>
                <th>操作</th>
            </tr>
            <tbody>
            <c:forEach items="${list}" var="dictComplex">
                <tr id="${dictComplex.id}" pId="${dictComplex.parent.id ne '1'?dictComplex.parent.id:'0'}">
                    <td nowrap style="text-align: left;"><a
                            href="${ctx}/system/dictComplex/form?id=${dictComplex.id}">${dictComplex.label}</a></td>
                    <td>${dictComplex.value}</td>
                    <td>${dictComplex.type}</td>
                    <td>${dictComplex.description}</td>
                    <td>${dictComplex.sort}</td>
                    <td nowrap class="text-center">
                        <a href="${ctx}/system/dictComplex/form?id=${dictComplex.id}" class="btn btn-primary btn-xs"> 修改</a>
                        <a href="${ctx}/system/dictComplex/delete?id=${dictComplex.id}"
                           onclick="return del(this.href)" class="btn btn-danger btn-xs"> 删除</a>
                        <a href="${ctx}/system/dictComplex/form?parent.id=${dictComplex.id}" class="btn btn-success btn-xs">
                            添加下级业务字典</a>
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