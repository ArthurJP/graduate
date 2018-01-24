<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<!-- Default box -->
<div class="box">
    <div class="box-header with-border">
        <form:form id="searchForm" class="form-inline" action="${ctx}/advertisement/ads" method="post"
                   modelAttribute="ads">
            <%--<input type="hidden" id="page" name="page" value="${ads.page}">--%>
            <%--<input type="hidden" id="rows" name="rows" value="${ads.rows}">--%>
            <%--<div class="form-group">--%>
            <%--<label for="type">类型：</label>--%>
            <%--<form:select path="type" cssClass="form-control">--%>
            <%--<form:option value="" label="请选择类型"/>--%>
            <%--<form:options items="${fns:getDictList('ads-type')}" itemLabel="label" itemValue="value"/>--%>
            <%--</form:select>--%>
            <%--</div>--%>
            <%--&nbsp;&nbsp;--%>
            <%--<button type="submit" class="btn btn-default">查询</button>--%>
            <div class="pull-right box-tools">
                <a href="${ctx}/advertisement/ads/form" class="btn btn-primary btn-sm"><i class="fa fa-plus"></i></a>
            </div>
        </form:form>
    </div>
    <div class="box-body table-responsive no-padding">
        <table class="table table-hover table-bordered">
            <tr>
                <th>序号</th>
                <th>标题</th>
                <th>预览</th>
                <%--<th>类型</th>--%>
                <th>开始时间</th>
                <th>截止时间</th>
                <th>操作</th>
            </tr>
            <c:forEach items="${page.list}" var="ads" varStatus="i">
                <tr>
                    <td>${i.index+1}</td>
                    <td>${ads.alt}</td>
                    <td><a href="${ads.url}"><img src="${ads.imgSrc}" alt="${ads.alt}" style="height:80px;"></a></td>
                        <%--<td>${fns:getDictLabel(ads.type,'ads-type', '')}</td>--%>
                    <td>${fns:formateDate(ads.createDate, 'yyyy-MM-dd hh:mm:ss')}</td>
                    <td>${fns:formateDate(ads.updateDate, 'yyyy-MM-dd hh:mm:ss')}</td>
                    <td>
                        <a href="${ctx}/advertisement/ads/form?id=${ads.id}">
                            编辑
                        </a>
                        <a href="${ctx}/advertisement/ads/delete?id=${ads.id}" title="删除"
                           onclick="return del(this.href)">
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
        <sys:page baseUrl="${ctx}/advertisement/ads/list" page="${page}"/>
    </div>
    <!-- /.box-footer-->
</div>
<!-- /.box -->



