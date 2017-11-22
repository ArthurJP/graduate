<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!-- Static navbar -->
<header class="main-header">
    <!-- Logo -->
    <a href="${ctx}/system/home" class="logo">
        <!-- mini logo for sidebar mini 50x50 pixels -->
        <span class="logo-mini"><b>Web</b></span>
        <!-- logo for regular state and mobile devices -->
        <span class="logo-lg">基础<b>WEB</b>系统</span>
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
        <!-- Sidebar toggle button-->
        <a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button">
            <span class="sr-only">Toggle navigation</span>
        </a>
        <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
                <li class="dropdown messages-menu">
                    <a href="${ctx}/system/user/completeInfoView">
                        <i class="fa fa-user"></i> &nbsp;${fns:currentUser().name}
                    </a>
                </li>
                <li class="dropdown messages-menu">
                    <a href=${ctx}/logout class="dropdown-toggle" title="登出">
                        <i class="fa fa-sign-out"></i>
                    </a>
                </li>
            </ul>
        </div>
    </nav>
</header>