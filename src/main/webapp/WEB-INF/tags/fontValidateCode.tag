<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ attribute name="callbackName" type="java.lang.String" required="true" description="回调操作名称" %>
<%@ attribute name="callback" type="java.lang.String" required="true" description="回调函数" %>

<table border="0" cellpadding="0" width="100%" class="tbtable"
       style="border-collapse:separate; border-spacing:2px 15px;">
    <tr>
        <td><input class="nametext" id="validateCode" style="width: 100%;"></td>
        <td><img style="width: 100%; text-align: left" src="${ctx}/validateCode?${fns:currentMiliSeconds()}"
                 onclick="$(this).attr('src','${ctx}/validateCode?'+new Date().getTime());">
        </td>
    </tr>
    <tr style="margin-top: 10px">
        <td colspan="2" style="text-align: center;">
            <button style="width: 100%;" type="button" class="hqbtn" onclick="${callback}();">${callbackName}</button>
        </td>
    </tr>
</table>