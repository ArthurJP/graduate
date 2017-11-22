<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<!-- Default box -->
<div class="box">
    <div class="box-header with-border">
        <form:form id="searchForm" class="form-inline" action="${ctx}/system/user/list" method="post"
                   modelAttribute="user">
            <input type="hidden" id="page" name="page" value="${user.page}">
            <input type="hidden" id="rows" name="rows" value="${user.rows}">
            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <label for="name">用户姓名：</label>
                        <form:input path="name" class="form-control" placeholder="姓名"/>
                    </div>&nbsp;&nbsp;
                    <div class="form-group">
                        <label for="mobile">登录名：</label>
                        <form:input path="mobile" class="form-control" placeholder="登录名"/>
                    </div>&nbsp;&nbsp;
                    <div class="form-group">
                        <label for="mobile">部门：</label>
                        <select class="form-control" name="office.id">
                            <c:forEach items="${officeData}" var="o">
                                <option value="${o.id}">${o.name}</option>
                                <sys:recurseTree o="${o}" prepend="&nbsp;&nbsp;"/>
                            </c:forEach>
                        </select>
                    </div>&nbsp;&nbsp;
                    <button type="submit" class="btn btn-default">查询</button>
                </div>
            </div>
        </form:form>
        &nbsp;
        <!-- tools box -->
        <div class="pull-right box-tools">
            <a href="${ctx}/system/user/form" class="btn btn-primary btn-sm"><i class="fa fa-plus"></i></a>
        </div>
        <!-- /. tools -->
    </div>
    <div class="box-body table-responsive no-padding">
        <table class="table table-hover table-bordered">
            <tr>
                <th>序号</th>
                <th>姓名</th>
                <th>手机号</th>
                <th>所属部门</th>
            </tr>
            <c:forEach items="${page.list}" var="user" varStatus="i">
                <tr>
                    <td>${i.index+1}</td>
                    <td><a href="${ctx}/system/user/form?id=${user.id}">${user.name}</a></td>
                    <td>${user.mobile}</td>
                    <td>${user.office.name}</td>
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
        <sys:page baseUrl="${ctx}/system/user/list" page="${page}"/>
    </div>
    <!-- /.box-footer-->
</div>
<!-- /.box -->



