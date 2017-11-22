<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ attribute name="o" type="cn.strongme.entity.system.Office" required="true" %>
<%@ attribute name="prepend" type="java.lang.String" required="true" %>
<c:if test="${o.children.size()>0}">
    <c:forEach items="${o.children}" var="oo">
        <option value="${oo.id}">${prepend}${oo.name}</option>
        <c:if test="${oo.children.size()>0}">
            <sys:recurseTree o="${oo}" prepend="${prepend}&nbsp;&nbsp;"/>
        </c:if>
    </c:forEach>
</c:if>