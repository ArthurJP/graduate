<%--
  Created by IntelliJ IDEA.
  User: Walter
  Date: 2017/7/14
  Time: 上午8:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<body>

<c:if test="${fn:length(infoList)>0}">
    <c:forEach items="${infoList}" var="info">
        <section class="invoice">
            <div class="row">
                <div class="col-xs-12">
                    <h2 class="page-header">
                            ${info.title}
                        <small class="pull-right">Date: ${fns:formateDate(info.createDate, 'yyyy-MM-dd')}</small>
                    </h2>
                </div>
            </div>
            <div class="row invoice-info">
                <div class="col-md-12 invoice-col">
                        ${info.intro}
                </div>
            </div>
        </section>
        <div class="clearfix"></div>
    </c:forEach>
</c:if>


</body>
