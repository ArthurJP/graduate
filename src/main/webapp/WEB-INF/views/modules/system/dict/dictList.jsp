<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<!-- Default box -->
<div class="box">
    <div class="box-header with-border">
        <form:form id="searchForm" class="form-inline" action="${ctx}/system/dict/list" method="post"
                   modelAttribute="dict">
            <input type="hidden" id="page" name="page" value="${dict.page}">
            <input type="hidden" id="rows" name="rows" value="${dict.rows}">
            <div class="form-group">
                <label for="label">标签：</label>
                <form:input path="label" class="form-control" placeholder="标签"/>
            </div>
            &nbsp;&nbsp;
            <div class="form-group">
                <label for="type">类型：</label>
                <form:select path="type" class="form-control">
                    <form:option value="" label="请选择类型"/>
                    <form:options items="${typeList}"/>
                </form:select>
            </div>
            &nbsp;&nbsp;
            <button type="submit" class="btn btn-default">查询</button>
        </form:form>
        <!-- tools box -->
        <div class="pull-right box-tools">
            <a href="${ctx}/system/dict/form" class="btn btn-primary btn-sm"><i class="fa fa-plus"></i></a>
        </div>
        <!-- /. tools -->
    </div>
    <div class="box-body table-responsive no-padding">
        <table class="table table-hover table-bordered">
            <tr>
                <th>序号</th>
                <th>标签</th>
                <th>键值</th>
                <th>类型</th>
                <th>描述</th>
                <th>排序</th>
                <th>操作</th>
            </tr>
            <c:forEach items="${page.list}" var="dict" varStatus="i">
                <tr>
                    <td>${i.index+1}</td>
                    <td><a href="${ctx}/system/dict/form?id=${dict.id}">${dict.label}</a></td>
                    <td>${dict.value}</td>
                    <td>${dict.type}</td>
                    <td>${dict.description}</td>
                    <td>${dict.sort}</td>
                    <td>
                        <a href="${ctx}/system/dict/form?id=${dict.id}">修改</a>
                        <a href="${ctx}/system/dict/delete?id=${dict.id}&type=${dict.type}"
                           onclick="return del(this.href)">删除</a>
                        <a href="${ctx}/system/dict/form?type=${dict.type}&sort=${dict.sort+10}&description=${dict.description}">添加键值</a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${fn:length(page.list)<=0}">
                <tr>
                    <td colspan="7">暂无数据</td>
                </tr>
            </c:if>
        </table>
    </div>
    <!-- /.box-body -->
    <div class="box-footer">
        <sys:page baseUrl="${ctx}/system/dict/list" page="${page}"/>
    </div>
    <!-- /.box-footer-->
</div>
<!-- /.box -->
<script>
    function del(url) {
        //title, content,confirmButton, cancelButton, onConfirm, onCancel
        confirmWithJbox('警告', '确认删除这条记录？', '删除', '取消', function () {
            document.location = url;
        }, function () {
        });
        return false;
    }
</script>


