<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
        <!-- sidebar menu: : style can be found in sidebar.less -->
        <ul class="sidebar-menu" data-widget="tree">
            <c:forEach items="${fns:getMenuList()}" var="m" varStatus="i">
                <c:if test="${i.index==0}">
                    <li class="header">${m.type}</li>
                </c:if>
                <li class="<c:if test="${m.containThisMenuKey(menuKey)}">active</c:if> treeview">
                    <a href="#">
                        <i class="fa fa-${m.icon}"></i> <span>${m.name}</span>
                        <span class="pull-right-container"><i class="fa fa-angle-left pull-right"></i></span>
                    </a>
                    <ul class="treeview-menu">
                        <c:forEach items="${m.children}" var="subM">
                            <li <c:if test="${menuKey eq subM.key}">class="active"</c:if>><a href="${ctx}${subM.url}"><i
                                    class="fa fa-circle-o"></i> ${subM.name}</a></li>
                        </c:forEach>
                    </ul>
                </li>
            </c:forEach>
        </ul>
    </section>
    <!-- /.sidebar -->
</aside>
