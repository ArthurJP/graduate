<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<!-- Default box -->
<div class="box">
    <div class="box-header with-border">
        <form:form id="searchForm" class="form-inline" action="${ctx}/system/log" method="post" modelAttribute="log">
            <input type="hidden" id="page" name="page" value="${log.page}">
            <input type="hidden" id="rows" name="rows" value="${log.rows}">
            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <label for="title">操作菜单：</label>
                        <form:input path="title" class="form-control" placeholder="操作菜单"/>
                    </div>&nbsp;&nbsp;
                    <div class="form-group">
                        <label for="user.mobile">登陆用户：</label>
                        <form:input path="user.mobile" class="form-control" placeholder="标签"/>
                    </div>&nbsp;&nbsp;
                    <div class="form-group">
                        <label for="requestUri">链接地址：</label>
                        <form:input path="requestUri" class="form-control" placeholder="链接地址"/>
                    </div>&nbsp;&nbsp;
                </div>
            </div>
            <br>
            <div class="row">
                <div class="col-md-12">
                    <div class="form-group">
                        <label>开始时间：</label>
                        <div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input name="beginDate" type="text" class="form-control pull-right" id="datepickerStart"
                                   value="${fns:formateDate(log.beginDate, 'yyyy-MM-dd')}" readonly>
                        </div>
                        <!-- /.input group -->
                    </div>&nbsp;&nbsp;
                    <div class="form-group">
                        <label>结束时间：</label>
                        <div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input name="endDate" type="text" class="form-control pull-right" id="datepickerEnd"
                                   value="${fns:formateDate(log.endDate, 'yyyy-MM-dd')}" readonly>
                        </div>
                        <!-- /.input group -->
                    </div>&nbsp;&nbsp;
                    <button type="submit" class="btn btn-default">查询</button>
                </div>
            </div>
        </form:form>
        <!-- tools box -->
    </div>
    <div class="box-body table-responsive no-padding">
        <table class="table table-hover table-bordered">
            <thead>
            <tr>
                <th>操作菜单</th>
                <th>操作用户</th>
                <th>所在部门</th>
                <th>URI</th>
                <th>提交方式</th>
                <th>操作者IP</th>
                <th>操作时间</th>
            </thead>
            <c:forEach items="${page.list}" var="l" varStatus="i">
                <tr>
                    <td>${l.title}</td>
                    <td>${l.user.name}</td>
                    <td>${l.user.office.name}</td>
                    <td><strong>${l.requestUri}</strong></td>
                    <td>${l.method}</td>
                    <td>${l.remoteAddr}</td>
                    <td><fmt:formatDate value="${l.createDate}" type="both"/></td>
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
        <sys:page baseUrl="${ctx}/system/log/list" page="${page}"/>
    </div>
    <!-- /.box-footer-->
</div>
<!-- /.box -->
<script>
    $(function () {
        $('#datepickerStart').datepicker({
            autoclose: true,
            format: 'yyyy-mm-dd',
            language: 'zh-CN'
        });
        $('#datepickerEnd').datepicker({
            autoclose: true,
            format: 'yyyy-mm-dd',
            language: 'zh-CN'
        });
    });
</script>
