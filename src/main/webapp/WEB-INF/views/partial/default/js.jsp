<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!-- jQuery 3 -->
<script src="${ctxStatic}/jquery/dist/jquery.min.js"></script>
<!-- jQuery UI 1.11.4 -->
<script src="${ctxStatic}/jquery-ui/jquery-ui.min.js"></script>
<!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
<script>
    $.widget.bridge('uibutton', $.ui.button);
</script>
<!--bootstrap fileinput-->
<script src="${ctxStatic}/bootstrap-fileinput/js/fileinput.js"></script>
<script src="${ctxStatic}/bootstrap-fileinput/js/plugins/sortable.js"></script>
<script src="${ctxStatic}/bootstrap-fileinput/js/locales/zh.js"></script>
<script src="${ctxStatic}/bootstrap-fileinput/themes/explorer/theme.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="${ctxStatic}/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- daterangepicker -->
<script src="${ctxStatic}/moment/min/moment.min.js"></script>
<script src="${ctxStatic}/bootstrap-daterangepicker/daterangepicker.js"></script>
<!-- datepicker -->
<script src="${ctxStatic}/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script src="${ctxStatic}/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<!-- Slimscroll -->
<script src="${ctxStatic}/jquery-slimscroll/jquery.slimscroll.min.js"></script>
<%-- jquery validate --%>
<script src="${ctxStatic}/jquery-validate/jquery.validate.js"></script>
<script src="${ctxStatic}/jquery-validate/jquery.validate.method.js"></script>
<script src="${ctxStatic}/jquery-validate/jquery-validate.bootstrap-tooltip.min.js"></script>
<script src="${ctxStatic}/jquery-validate/additional-methods.js"></script>
<script src="${ctxStatic}/jquery-validate/localization/messages_zh.js"></script>
<!-- InputMask -->
<script src="${ctxStatic}/input-mask/jquery.inputmask.js"></script>
<script src="${ctxStatic}/input-mask/jquery.inputmask.date.extensions.js"></script>
<script src="${ctxStatic}/input-mask/jquery.inputmask.extensions.js"></script>
<!--jbox-->
<script src="${ctxStatic}/jbox/jBox.js"></script>
<script src="${ctxStatic}/jbox/plugins/Notice/jBox.Notice.js"></script>
<script src="${ctxStatic}/jbox/plugins/Confirm/jBox.Confirm.js"></script>
<!-- treeview -->
<script src="${ctxStatic}/treeview/bootstrap-treeview.min.js" type="text/javascript"></script>
<!-- wang editor -->
<script src="${ctxStatic}/wang-editor/wangEditor.min.js"></script>
<!-- AdminLTE App -->
<script src="${ctxStatic}/adminlte/js/adminlte.min.js"></script>
<!--Custom-->
<script src="${ctxStatic}/custom/custom.js"></script>