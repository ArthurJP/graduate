<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<form:form id="adsForm" modelAttribute="ads" action="${ctx}/advertisement/ads/save" method="post">
    <form:hidden path="id"/>
    <!-- Default box -->
    <div class="box">
        <div class="box-header with-border">
            <h3 class="box-title"></h3>
            <div class="btn-group  pull-right">
                <button type="submit" class="btn btn-primary">
                    <i class="fa fa-save"></i>
                </button>
                <a type="button" class="btn btn-primary" href="${ctx}/advertisement/ads">
                    <i class="fa fa-mail-reply"></i>
                </a>
            </div>
        </div>
        <div class="box-body">
            <div class="row">
                <div class="col-md-12">
                    <div class="row">
                        <div class="col-md-8">
                            <div style="margin-top: 15px;" class="form-group">
                                <label for="alt">广告标题：</label>
                                <form:input path="alt" class="form-control input-lg" type="text"
                                            placeholder="广告标题（5-50个字符）"/>
                            </div>
                            <div style="margin-top: 15px;" class="form-group">
                                <label for="alt">链接地址：</label>
                                <form:input path="url" class="form-control input-lg" type="text"
                                            placeholder="广告链接地址（最多150个字符）"/>
                            </div>
                            <div style="margin-top: 15px;" class="form-group">
                                <label for="alt">图片地址：</label>
                                <form:input path="imgSrc" class="form-control input-lg" type="text"
                                            placeholder="广告图片（最多255个字符）"/>
                            </div>


                                <%--<form:input path="content" cssStyle="width: 0px;height: 0px;position: absolute;right: 50%;"/>--%>
                                <%--<div id="editor" style="margin-top: 10px;">--%>
                                <%--${info.content}--%>
                                <%--</div>--%>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>备注信息</label>
                                <form:textarea path="remark" class="form-control" rows="10"
                                               placeholder="备注信息（最多150个字符）"/>
                            </div>
                            <div class="form-group">
                                <label for="datepicker1">发布时间</label>
                                <div class="input-group date">
                                    <div class="input-group-addon">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                    <form:input path="createDate" type="text" class="form-control pull-right"
                                                id="datepicker1" readonly="true"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="datepicker2">截止时间</label>
                                <div class="input-group date">
                                    <div class="input-group-addon">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                    <form:input path="updateDate" type="text" class="form-control pull-right"
                                                id="datepicker2" readonly="true"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="type">广告位置</label>
                                <form:select path="type" cssClass="form-control">
                                    <form:option value="">请选择位置</form:option>
                                    <form:options items="${fns:getDictList('adsPosition')}" itemLabel="label"
                                                  itemValue="value"/>
                                </form:select>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- /.col-->
            </div>

        </div>
        <!-- /.box-body -->
        <div class="box-footer">
            <div class="btn-group  pull-right">
                <button type="submit" class="btn btn-primary">
                    <i class="fa fa-save"></i>
                </button>
                <a type="button" class="btn btn-primary" href="${ctx}/advertisement/ads">
                    <i class="fa fa-mail-reply"></i>
                </a>
            </div>
        </div>
        <!-- /.box-footer-->
    </div>
    <!-- /.box -->
</form:form>
<script>
    var eidotor;
    $(function () {
        initForm();
        initEditor();
        $('#datepicker1').datepicker({
            autoclose: true,
            language: 'zh-CN',
            format: 'yyyy-mm-dd',
        });
        $('#datepicker2').datepicker({
            autoclose: true,
            language: 'zh-CN',
            format: 'yyyy-mm-dd',
        })
    })

    function initEditor() {
        var E = window.wangEditor;
        eidotor = new E('#editor');
        eidotor.customConfig.uploadImgServer = '${ctx}/system/upload/uploadWang';
        eidotor.customConfig.menus = [
            'head',  // 标题
            'bold',  // 粗体
            'italic',  // 斜体
            'underline',  // 下划线
            'strikeThrough',  // 删除线
            'foreColor',  // 文字颜色
            'backColor',  // 背景颜色
            'link',  // 插入链接
            'list',  // 列表
            'justify',  // 对齐方式
            'quote',  // 引用
            'image',  // 插入图片
            'undo',  // 撤销
            'redo'  // 重复
        ];
        eidotor.customConfig.onchange = function (html) {
            // html 即变化之后的内容
            $('#content').val(html);
        }

        eidotor.create();
    }

    function initForm() {
        $("#adsForm").validate({
            ignore: '',
            rules: {
                alt: {
                    required: true,
                    rangelength: [5, 50]
                },
                url: {
                    required: true,
                    rangelength: [0, 150]
                },
                imgSrc: {
                    required: true,
                    rangelength: [0, 255]
                },
                remark: {
                    required: true,
                    rangelength: [0, 150]
                },
                place: {
                    required: true
                }
            },
            messages: {
                alt: {
                    required: '请填写名称'
                },
                url: {
                    required: '请填写链接地址'
                },
                imgSrc: {
                    required: '请填写图片链接'
                },
                place: {
                    required: '请选择显示位置'
                }
            },
            tooltip_options: {},
            submitHandler: function (form) {
                form.submit();
            }
        });
    }

</script>


