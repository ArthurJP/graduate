<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<!-- Default box -->
<div class="box">
    <div class="box-header with-border">
        &nbsp;
        <!-- tools box -->
        <div class="pull-right box-tools">
            <a href="${ctx}/system/role/form" class="btn btn-primary btn-sm"><i class="fa fa-plus"></i></a>
        </div>
        <!-- /. tools -->
    </div>
    <div class="box-body table-responsive no-padding">
        <table class="table table-hover table-bordered">
            <tr>
                <th>序号</th>
                <th>汉语名称</th>
                <th>英文名称</th>
                <th>操作</th>
            </tr>
            <c:forEach items="${list}" var="role" varStatus="i">
                <tr>
                    <td>${i.index+1}</td>
                    <td><a href="${ctx}/system/role/form?id=${role.id}">${role.name}</a></td>
                    <td>${role.ename}</td>
                    <td>
                        <a href="${ctx}/system/role/form?id=${role.id}">修改</a>
                        <a href="${ctx}/system/role/delete?id=${role.id}" onclick="return del(this.href)">删除</a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${fn:length(list)<=0}">
                <tr>
                    <td colspan="7">暂无数据</td>
                </tr>
            </c:if>
        </table>
    </div>
    <!-- /.box-body -->
    <div class="box-footer">
        &nbsp;
    </div>
    <!-- /.box-footer-->
</div>
<!-- /.box -->


