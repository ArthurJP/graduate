<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<form:form id="infoForm" modelAttribute="info" action="${ctx}/business/info/save" method="post">
    <form:hidden path="id"/>
    <!-- Default box -->
    <div class="box">
        <div class="box-header with-border">
            <h3 class="box-title"></h3>
            <div class="btn-group  pull-right">
                <button type="submit" class="btn btn-primary">
                    <i class="fa fa-save"></i>
                </button>
                <a type="button" class="btn btn-primary" href="${ctx}/business/info">
                    <i class="fa fa-mail-reply"></i>
                </a>
            </div>
        </div>
        <div class="box-body">
            <div class="row">
                <div class="col-md-12">
                    <div class="row">
                        <div class="col-md-8">
                            <form:input  path="title" class="form-control input-lg" type="text" placeholder="资讯标题（5-50个字符）"/>
                            <form:input path="content" cssStyle="width: 0px;height: 0px;position: absolute;right: 50%;"/>
                            <div id="editor" style="margin-top: 10px;">
                                ${info.content}
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>资讯简介</label>
                                <form:textarea path="intro" class="form-control" rows="10" placeholder="资讯简介（10-500个字符）"/>
                            </div>
                            <div class="form-group">
                                <label for="datepicker">发布时间</label>
                                <div class="input-group date">
                                    <div class="input-group-addon">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                    <form:input path="createDate" type="text" class="form-control pull-right" id="datepicker" readonly="true" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="type">资讯类型</label>
                                <form:select path="type" cssClass="form-control">
                                    <form:option value="">请选择类型</form:option>
                                    <form:options items="${fns:getDictList('info-type')}" itemLabel="label" itemValue="value"/>
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
                <a type="button" class="btn btn-primary" href="${ctx}/business/info">
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
        $('#datepicker').datepicker({
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
        $("#infoForm").validate({
            ignore: '',
            rules: {
                title: {
                    required: true,
                    rangelength:[5,50]
                },
                intro: {
                    required: true,
                    rangelength:[10,500]
                },
                content: {
                    required: true
                },
                type: {
                    required: true
                }
            },
            messages: {
                title: {
                    required: '请填写标题'
                },
                intro: {
                    required: '请填写摘要'
                },
                content: {
                    required: '请填写内容'
                },
                type: {
                    required: '请选择类型'
                }
            },
            tooltip_options: {},
            submitHandler: function (form) {
                form.submit();
            }
        });
    }

</script>


