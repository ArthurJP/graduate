<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<!DOCTYPE html>
<html lang="zh-Hans">
<head>
    <meta charset="UTF-8">
    <meta name=viewport content="width=device-width,initial-scale=1,user-scalable=0">
    <title>
        <c:if test="${not empty title}">
            <tiles:getAsString name="title"/>
        </c:if>
        <c:if test="${empty title}">
            Wechat
        </c:if></title>

    <tiles:insertAttribute name="stylesheets"/>
    <tiles:insertAttribute name="js"/>
</head>
<body ontouchstart>
<tiles:insertAttribute name="content"/>
</body>
</html>
