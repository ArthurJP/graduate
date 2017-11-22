<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ attribute name="callbackName" type="java.lang.String" required="true" description="回调操作名称" %>
<%@ attribute name="callback" type="java.lang.String" required="true" description="回调函数" %>

<div class="input-group">
    <input type="text" class="form-control" id="validateCode">
    <div class="input-group-addon" style="padding: 0;">
        <img src="${ctx}/validateCode?${fns:currentMiliSeconds()}"
             onclick="$(this).attr('src','${ctx}/validateCode?'+new Date().getTime());">
    </div>
</div>
<br>

<div class="row">
    <div class="col-xs-12">
        <button type="button" class="btn btn-primary btn-block btn-flat" style="height: auto;margin-top: auto;"
                onclick="${callback}();">${callbackName}</button>
    </div>
</div>
