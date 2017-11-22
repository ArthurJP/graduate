<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<link href="${ctxStatic}/treeTable/themes/vsStyle/treeTable.min.css" rel="stylesheet" type="text/css"/>
<script src="${ctxStatic}/treeTable/jquery.treeTable.js" type="text/javascript"></script>
<form id="listForm" method="post" data-pjax action="${ctx}/system/menu/updateSort">
    <div class="box">
        <div class="box-header with-border">
            &nbsp;
            <!-- tools box -->
            <div class="pull-right box-tools">
                <a href="${ctx}/system/menu/form" class="btn btn-primary btn-sm"><i class="fa fa-plus"></i></a>
                <button type="submit" class="btn btn-primary btn-sm">保存排序</button>
            </div>
            <!-- /. tools -->
        </div>
        <div class="box-body table-responsive no-padding">
            <table class="table table-hover table-bordered" id="treeTable">
                <tr>
                    <th>名称</th>
                    <th>链接</th>
                    <th style="text-align:center;">排序</th>
                    <th>可见</th>
                    <th>权限标识</th>
                    <th>操作</th>
                </tr>
                <tbody>
                <c:forEach items="${list}" var="menu">
                    <tr id="${menu.id}" pId="${menu.parent.id ne '1'?menu.parent.id:'0'}">
                        <td nowrap style="text-align: left;"><a
                                href="${ctx}/system/menu/form?id=${menu.id}">${menu.name}</a></td>
                        <td title="${menu.url}">${fns:abbr(menu.url,30)}</td>
                        <td style="text-align:center;">
                            <input type="hidden" name="ids" value="${menu.id}"/>
                            <input name="sorts" type="text" value="${menu.sort}"
                                   style="width:50px;margin:0;padding:0;text-align:center;">
                        </td>
                        <td>${menu.isShow eq '1'?'显示':'隐藏'}</td>
                        <td title="${menu.permission}">${fns:abbr(menu.permission,30)}</td>
                        <td nowrap class="text-center">
                            <a href="${ctx}/system/menu/form?id=${menu.id}" class="btn btn-primary btn-xs"> 修改</a>
                            <a href="${ctx}/system/menu/delete?id=${menu.id}"
                               onclick="return del(this.href)" class="btn btn-danger btn-xs"> 删除</a>
                            <a href="${ctx}/system/menu/form?parent.id=${menu.id}" class="btn btn-success btn-xs">
                                添加下级菜单</a>
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
</form>
<script type="text/javascript">
    $(document).ready(function () {
        $("#treeTable").treeTable({expandLevel: 3}).show();
    });
</script>