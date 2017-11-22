<%--文件上传:fileinput--%>
<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ attribute name="id" type="java.lang.String" required="true" description="上传附件ID" %>
<%@ attribute name="maxFileCount" type="java.lang.Integer" required="true" description="最大文件数" %>
<%@ attribute name="theme" type="java.lang.String" required="false" description="主题" %>
<%@ attribute name="browseLabel" type="java.lang.String" required="false" description="按钮名称" %>
<%@ attribute name="relativePath" type="java.lang.String" required="true" description="相对目录" %>
<%@ attribute name="imgUrl" type="java.lang.String" required="false" description="原因图片链接" %>
<%@ attribute name="imgUrlsConfig" type="java.lang.String" required="false" description="原因图片详情" %>
<%@ attribute name="singleUploadedCallback" type="java.lang.String" required="false" description="单个文件上传成功回调函数" %>
<%@ attribute name="singleRemoveCallback" type="java.lang.String" required="false" description="单个文件删除成功回调函数" %>
<%@ attribute name="singleDeleteCallback" type="java.lang.String" required="false" description="单个文件Preview删除成功回调函数" %>
<%@ attribute name="clearCallback" type="java.lang.String" required="false" description="清空文件回调" %>

<%@ attribute name="showUpload" type="java.lang.Boolean" required="false" %>
<c:if test="${empty showUpload}"><c:set var="showUpload" value="true"/></c:if>
<%@ attribute name="showRemove" type="java.lang.Boolean" required="false" %>
<c:if test="${empty showRemove}"><c:set var="showRemove" value="true"/></c:if>
<%@ attribute name="showBrowse" type="java.lang.Boolean" required="false" %>
<c:if test="${empty showBrowse}"><c:set var="showBrowse" value="true"/></c:if>
<%@ attribute name="initialPreviewShowDelete" type="java.lang.Boolean" required="false" %>
<c:if test="${empty initialPreviewShowDelete}"><c:set var="initialPreviewShowDelete" value="true"/></c:if>

<c:set var="multiple" value=""/>
<c:if test="${maxFileCount>1}"><c:set var="multiple" value="multiple"/></c:if>
<c:if test="${(empty browseLabel)}"><c:set var="browseLabel" value="选择文件"/></c:if>
<input id="fileinput${id}" class="file-loading" ${multiple} type="file" name="file">
<script>

    $(function () {

        var inputConfig = {
            browseLabel: '${browseLabel}',
            theme: '${theme}',
            uploadAsync: true,
            uploadUrl: '${ctx}/system/upload',
            deleteUrl: '${ctx}/system/upload/delete',
            uploadExtraData: {'relativePath': '${relativePath}'},
            dropZoneEnabled: false,
            showUpload: ${showUpload},
            showRemove: ${showRemove},
            showCaption: false,
            showBrowse: ${showBrowse},
            language: 'zh',
            enctype: 'multipart/form-data',
            allowedPreviewTypes: ['image'],
            allowedFileTypes: ['image'],
            allowedFileExtensions: ['jpg', 'png', 'gif'],
            maxFileSize: 2000,
            maxFileCount: ${maxFileCount},
            overwriteInitial: false,
            validateInitialCount: true,
            removeLabel: '移除未上传'
        };
        if ('${imgUrl}') {
            var imgUrls = eval('(${imgUrl})');
            if (imgUrls && imgUrls.length > 0) {
                var realImgUrls = [];
                $.each(imgUrls, function (i, d) {
                    realImgUrls.push('${ctx}/userfiles/' + d);
                });
                inputConfig['initialPreview'] = realImgUrls;

            }
        }
        if ('${imgUrlsConfig}') {
            var imgUrlsConfig = eval('(${imgUrlsConfig})');
            if (imgUrlsConfig && imgUrlsConfig.length > 0) {
                var realImgUrlsConfig = [];
                $.each(imgUrlsConfig, function (i, d) {
                    d['url'] = '${ctx}' + d.url;
                    realImgUrlsConfig.push(d);
                });
                inputConfig['initialPreviewConfig'] = realImgUrlsConfig;
            }
        }
        inputConfig['initialPreviewAsData'] = true;
        inputConfig['initialPreviewShowDelete'] = ${initialPreviewShowDelete};

        $('#fileinput${id}').fileinput(inputConfig);

        $('#fileinput${id}').on('fileuploaded', function (event, data, previewId, index) {
            var form = data.form, files = data.files, extra = data.extra,
                response = data.response, reader = data.reader;
            if ('${singleUploadedCallback}') {
                var func = eval('${singleUploadedCallback}');
                func(response, previewId);
            }
        });
        //下方移除按钮
        $('#fileinput${id}').on('filecleared', function (event, id, index) {
            if ('${clearCallback}') {
                eval('${clearCallback}()');
            }
        });
        //未上传之前删除
        $('#fileinput${id}').on('filepreremove', function (event, id, index) {
            console.log('id = ' + id + ', index = ' + index);
        });
        $('#fileinput${id}').on('filedeleted', function (event, key, jqXHR, data) {
            if (data && data.id) {
                if ('${singleDeleteCallback}') {
                    eval('${singleDeleteCallback}("' + data.id + '")');
                }
            }
        });
        //上传之后立马删除
        $('#fileinput${id}').on('filesuccessremove', function (event, id) {
            if ('${singleRemoveCallback}') {
                eval('${singleRemoveCallback}("' + id + '")');
            }
        });
    });

    function getFileWaitToUpload() {
        return $('#fileinput${id}').fileinput('getFileStack');
    }

    function getFilesCount() {
        return $('.file-to-upload').length;
    }

    function unlockFileInput() {
        $('#fileinput${id}').fileinput('unlock');
    }

    function lockFileInput() {
        $('#fileinput${id}').fileinput('lock');
    }

    function executeFunc(funcName) {
        if (funcName) {
            $('#fileinput${id}').fileinput(funcName);
        }
    }

</script>