<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<!DOCTYPE html>
<html lang="zh-Hans">
<head>
    <title>
        <c:if test="${not empty title}">
            <tiles:getAsString name="title"/>
        </c:if>
        <c:if test="${empty title}">
            基础管理系统
        </c:if></title>
    <tiles:insertAttribute name="meta"/>
    <tiles:insertAttribute name="stylesheets"/>
    <tiles:insertAttribute name="js"/>
    <!-- The template to display files available for upload -->
</head>
<body class="hold-transition skin-blue fixed sidebar-mini">
<div class="wrapper">
    <tiles:insertAttribute name="nav"/>
    <!-- Left side column. contains the logo and sidebar -->
    <tiles:insertAttribute name="side"/>
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                ${title}
                <small>${subTitle}</small>
            </h1>
        </section>
        <section class="content">
            <c:if test="${!(empty msg)}">
                <div class="callout callout-${(empty msgType)? 'info':msgType}">
                    <h4>消息</h4>
                    <p>${msg}</p>
                </div>
            </c:if>
            <script>NProgress.start();</script>
            <tiles:insertAttribute name="content"/>
            <script>NProgress.done();</script>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <tiles:insertAttribute name="footer"/>
</div>
</body>
</html>
