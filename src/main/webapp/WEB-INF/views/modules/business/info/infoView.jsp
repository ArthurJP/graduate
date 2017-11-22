<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<!-- Default box -->
<div class="box">
    <div class="box-header with-border">
        <div class="row">
            <div class="col-md-8">
                <h4>${info.title}</h4>${fns:formateDate(info.createDate, 'yyyy-MM-dd hh:mm:ss')}
            </div>
            <div class="col-md-4">
                <div class="btn-group  pull-right">
                    <a type="button" class="btn btn-primary" href="${ctx}/business/info/form?id=${info.id}">
                        <i class="fa fa-pencil"></i>
                    </a>
                    <a type="button" class="btn btn-primary" href="${ctx}/business/info">
                        <i class="fa fa-mail-reply"></i>
                    </a>
                </div>

            </div>
        </div>

    </div>
    <div class="box-body table-responsive" style="padding: 30px;">
        ${info.content}
    </div>
    <!-- /.box-body -->
    <div class="box-footer">
        <div class="btn-group  pull-right">
            <a type="button" class="btn btn-primary" href="${ctx}/business/info/form?id=${info.id}">
                <i class="fa fa-pencil"></i>
            </a>
            <a type="button" class="btn btn-primary" href="${ctx}/business/info">
                <i class="fa fa-mail-reply"></i>
            </a>
        </div>
    </div>
    <!-- /.box-footer-->
</div>
<!-- /.box -->



