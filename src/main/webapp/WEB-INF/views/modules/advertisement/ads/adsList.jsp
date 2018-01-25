<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<!-- Default box -->
<div class="box">
    <div class="box-header with-border">
        <form:form id="searchForm" class="form-inline" action="${ctx}/advertisement/ads" method="post"
                   modelAttribute="ads">
            <input type="hidden" id="page" name="page" value="${ads.page}">
            <input type="hidden" id="rows" name="rows" value="${ads.rows}">
            <div class="btn-group btn-group-sm col-lg-2" role="group" aria-label="...">
                <button id="selectAll" type="button" class="btn btn-default">全选</button>
                <button id="selectNull" type="button" class="btn btn-default">清空</button>
                <button id="selectReverser" type="button" class="btn btn-default">反选</button>
            </div>
            <div class="form-group col-lg-4">
                <label for="type">位置：</label>
                <form:select path="type" cssClass="form-control">
                    <form:option value="" label="全部位置"/>
                    <form:options items="${fns:getDictList('adsPosition')}" itemLabel="label" itemValue="value"/>
                </form:select>
                &nbsp;&nbsp;
                <button type="submit" class="btn btn-default">查询</button>
            </div>
            <div class="col-lg-1">
                <button id="deleteAll" class="btn btn-danger">批量删除</button>
            </div>

            <div class="pull-right box-tools">
                <a href="${ctx}/advertisement/ads/form" class="btn btn-primary btn-sm"><i class="fa fa-plus"></i></a>
            </div>
        </form:form>
    </div>
    <div class="box-body table-responsive no-padding">
        <table class="table table-hover table-bordered">
            <tr>
                <th>序号</th>
                <th>标题</th>
                <th>预览</th>
                <th>位置</th>
                <th>排序</th>
                <th>开始时间</th>
                <th>截止时间</th>
                <th>操作</th>
            </tr>
            <c:forEach items="${page.list}" var="ads" varStatus="i">
                <tr>
                    <td><input type="checkbox" name="ids[]" id="${ids.id}">${i.index+1}</td>
                    <td>${ads.alt}</td>
                    <td><a href="${ads.url}"><img src="${ads.imgSrc}" alt="${ads.alt}" style="height:80px;"></a></td>
                    <td>${fns:getDictLabel(ads.type,'adsPosition', '')}</td>
                    <td>${ads.sort}</td>
                    <td>${fns:formateDate(ads.createDate, 'yyyy-MM-dd hh:mm:ss')}</td>
                    <td>${fns:formateDate(ads.updateDate, 'yyyy-MM-dd hh:mm:ss')}</td>
                    <td>
                        <a href="${ctx}/advertisement/ads/form?id=${ads.id}">
                            编辑
                        </a>
                        <a href="${ctx}/advertisement/ads/delete?id=${ads.id}" title="删除"
                           onclick="return del(this.href)">
                            删除
                        </a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${fn:length(page.list)<=0}">
                <tr>
                    <td colspan="7">暂无数据</td>
                </tr>
            </c:if>
        </table>
    </div>
    <!-- /.box-body -->
    <div class="box-footer">
        <sys:page baseUrl="${ctx}/advertisement/ads/list" page="${page}"/>
    </div>
    <!-- /.box-footer-->
</div>
<!-- /.box -->

<script>
    $('#selectAll').click(function () {
        $(':checkbox').attr({'checked': true});
    });
    $('#selectNull').click(function () {
        $(':checkbox').attr({'checked': false});
    });
    $('#selectReverser').click(function () {
        $(':checkbox').each(function () {
            this.checked = !this.checked;
        });
    });

    $('#deleteAll').click(function(){
        if(confirm('确定要删除所选吗?')){
            var checks = $("input[name='ids[]']:checked");
            if(checks.length == 0){ alert('未选中任何项！');return false;}
            //将获取的值存入数组
            var checkData = new Array();
            checks.each(function(){
                checkData.push($(this).val());
            });
            $.get("<{spUrl c=order a=delete}>",{Check:checkData.toString()},function(result){ if(result = true){ window.location.reload();}});
        }
    });
</script>



