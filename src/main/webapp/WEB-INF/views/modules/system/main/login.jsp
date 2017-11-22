<%--
  Created by IntelliJ IDEA.
  User: Walter
  Date: 2017/7/14
  Time: 上午8:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<body class="hold-transition login-page">

<div class="login-box">
    <div class="login-logo">
        <a href="${ctx}/"><b>基础WEB</b>系统</a>
    </div>
    <c:if test="${!(empty msg)}">
        <div class="alert alert-${msgType} text-center">${msg}</div>
    </c:if>
    <div class="login-box-body">
        <p class="login-box-msg">登陆发现更多功能</p>
        <form action="${ctx}/login" method="post">
            <div class="form-group has-feedback">
                <input type="text" class="form-control" name="username" placeholder="手机号" value="admin">
                <i class="fa fa-mobile-phone form-control-feedback"></i>
            </div>
            <div class="form-group has-feedback">
                <input type="password" class="form-control" placeholder="Password" name="password" value="123456">
                <span class="fa fa-lock form-control-feedback"></span>
            </div>
            <div class="row">
                <div class="col-xs-12">
                    <button type="submit" class="btn btn-primary btn-block btn-flat">登陆</button>
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-md-12 text-center">
                    <a href="${ctx}/regist" class="text-center">注册</a>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
