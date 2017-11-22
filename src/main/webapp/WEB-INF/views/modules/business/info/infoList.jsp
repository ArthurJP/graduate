<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<!-- Default box -->
<div class="box">
    <div class="box-header with-border">
        <form:form id="searchForm" class="form-inline" action="${ctx}/business/info/list" method="post"
                   modelAttribute="info">
            <input type="hidden" id="page" name="page" value="${info.page}">
            <input type="hidden" id="rows" name="rows" value="${info.rows}">
            <div class="form-group">
                <label for="type">类型：</label>
                <form:select path="type" cssClass="form-control">
                    <form:option value="" label="请选择类型"/>
                    <form:options items="${fns:getDictList('info-type')}" itemLabel="label" itemValue="value"/>
                </form:select>
            </div>
            &nbsp;&nbsp;
            <button type="submit" class="btn btn-default">查询</button>
            <div class="pull-right box-tools">
                <a href="${ctx}/business/info/form" class="btn btn-primary btn-sm"><i class="fa fa-plus"></i></a>
            </div>
        </form:form>
    </div>
    <div class="box-body table-responsive no-padding">
        <table class="table table-hover table-bordered">
            <tr>
                <th>序号</th>
                <th>标题</th>
                <th>类型</th>
                <th>创建时间</th>
                <th>操作</th>
            </tr>
            <c:forEach items="${page.list}" var="info" varStatus="i">
                <tr>
                    <td>${i.index+1}</td>
                    <td><a href="${ctx}/business/info/view?id=${info.id}">${info.title}</a></td>
                    <td>${fns:getDictLabel(info.type,'info-type', '')}</td>
                    <td>${fns:formateDate(info.createDate, 'yyyy-MM-dd hh:mm:ss')}</td>
                    <td>
                        <a href="${ctx}/business/info/form?id=${info.id}">
                            编辑
                        </a>
                        <a href="${ctx}/business/info/delete?id=${info.id}" title="删除" onclick="return del(this.href)">
                            删除
                        </a>
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
        <sys:page baseUrl="${ctx}/business/info/list" page="${page}"/>
    </div>
    <!-- /.box-footer-->
</div>
<!-- /.box -->



