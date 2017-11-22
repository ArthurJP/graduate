<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<div class="row" style="margin-top: 100px;">
    <div class="col-md-4 col-md-offset-4">
        <div class="small-box bg-red">
            <div class="inner">
                <h3>${error.status}</h3>
                <p>${error.error}</p>
                <p>${error.message}</p>
            </div>
            <div class="icon">
                <i class="ion ion-pie-graph"></i>
            </div>
            <a href="${ctx}/" class="small-box-footer">
                返回主页 <i class="fa fa-arrow-circle-right"></i>
            </a>
        </div>
    </div>
</div>

