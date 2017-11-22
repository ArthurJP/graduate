<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!-- footer goes here -->
<footer class="main-footer">
    <div class="pull-right hidden-xs">
        <b>Version</b> ${fns:getConfig('version')}&nbsp; Front Theme By <a target="_blank" href="https://adminlte.io/"><strong>AdminLTE</strong></a>
    </div>
    <strong>
        <a href="${ctx}/" target="_blank">${fns:getConfig('name.chinese')} -
            [${fns:getConfig('spring.profiles.active')}]</a>
    </strong>
</footer>