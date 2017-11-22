<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ attribute name="baseUrl" type="java.lang.String" required="true" description="相关查询链接" %>
<%@ attribute name="page" type="com.github.pagehelper.PageInfo" required="true" description="分页实体" %>
<c:if test="${fn:length(page.navigatepageNums)>0}">
    <ul class="pagination pagination-sm no-margin pull-right">
        <li><a href="javascript:page('${page.navigateFirstPage}','${page.pageSize}')">&laquo;</a></li>
        <c:forEach items="${page.navigatepageNums}" var="n">
            <c:if test="${page.pageNum eq n}"><li class="active"></c:if>
            <c:if test="${!(page.pageNum eq n)}"><li></c:if>
            <a href="javascript:page('${n}','${page.pageSize}')">${n}</a></li>
        </c:forEach>
        <li><a href="javascript:page('${page.navigateLastPage}','${page.pageSize}')">&raquo;</a></li>
    </ul>
    <script>
        function page(p, r) {
            if (p) $("#page").val(p);
            if (r) $("#rows").val(r);
            $("#searchForm").attr("action", "${baseUrl}");
            $("#searchForm").submit();
            return false;
        }
    </script>
</c:if>&nbsp;

