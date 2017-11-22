<%--
  Created by IntelliJ IDEA.
  User: Walter
  Date: 2017/7/14
  Time: 上午8:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<body class="hold-transition register-page">
<div class="register-box">
    <div class="register-logo">
        <a href="${ctx}/"><b>基础WEB</b>系统</a>
    </div>
    <c:if test="${!(empty msg)}">
        <div class="alert alert-${msgType} text-center">${msg}</div>
    </c:if>
    <div class="register-box-body">
        <p class="login-box-msg">注册</p>

        <form id="registForm" action="${ctx}/regist" method="post">
            <div class="form-group has-feedback">
                <input type="text" class="form-control" id="mobile" name="mobile" value="${requestScope.mobile}"
                       placeholder="手机号">
                <i class="fa fa-mobile-phone form-control-feedback"></i>
            </div>
            <div class="form-group has-feedback">
                <input type="password" name="password" id="password" class="form-control" placeholder="输入密码">
                <span class="fa fa-lock form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="password" name="confirmPassword" class="form-control" placeholder="确认密码">
                <span class="fa fa-lock form-control-feedback"></span>
            </div>
            <div class="form-group">
                <div class="input-group input-group-md">
                    <input type="text" class="form-control" name="code" data-inputmask='"mask": "999999"'
                           placeholder="验证码"
                           data-mask>
                    <span class="input-group-btn">
                      <button type="button" class="btn btn-default btn-flat"
                              onclick="openValidateCodeModal();">发送验证码</button>
                    </span>
                </div>
            </div>

            <div class="row">
                <div class="col-xs-12">
                    <button type="submit" class="btn btn-primary btn-block btn-flat">注册</button>
                </div>
                <!-- /.col -->
            </div>
        </form>
        <hr>
        <div class="row">
            <div class="col-md-12 text-center">
                <a href="${ctx}/login" class="text-center">我已有账号，前往登陆</a>
            </div>
        </div>
    </div>
    <!-- /.form-box -->
</div>
<!-- /.register-box -->

<script>
    var modal;
    $(function () {
        initForm();
    });

    function initForm() {
        $("#registForm").validate({
            rules: {
                mobile: {
                    mobile: true,
                    required: true,
                    remote: {
                        url: "${ctx}/checkMobile",     //后台处理程序
                        type: "post",               //数据发送方式
                        dataType: "json",           //接受数据格式
                        data: {                     //要传递的数据
                            mobile: function () {
                                return $("#mobile").val();
                            }
                        }
                    }
                },
                password: {
                    required: true
                },
                confirmPassword: {
                    required: true,
                    equalTo: "#password"
                },
                code: {
                    required: true
                }
            },
            messages: {
                mobile: {
                    mobile: '请输入正确手机号',
                    required: '请填写手机号',
                    remote: '此号码已经注册'
                },
                password: {
                    required: '请填写密码'
                },
                confirmPassword: {
                    equalTo: '两次输入密码不一致'
                },
                code: {
                    required: '请填写手机验证码'
                }
            },
            tooltip_options: {},
            submitHandler: function (form) {
                form.submit();
            }
        });
    }

    function openValidateCodeModal() {
        if (!$('#mobile').val()) {
            alert('请填写手机号');
            return;
        }
        if (modal) {
            modal.destroy();
        }
        modal = new jBox('Modal', {
            width: '20%',
            maxHeight: '60%',
            animation: 'tada',
            fixed: true,
            adjustPosition: true,
            reposition: true,
            spinnerDelay: 2000,
            spinnerReposition: true,
            ajax: {
                url: '${ctx}/toValidateCode',
                reload: 'strict'
            }
        }).open();
    }

    function verifyValidateCode() {
        var validateCode = $('#validateCode').val();
        if (!validateCode) {
            alert('请填写验证码');
            return;
        }
        $.post(
            '${ctx}/doVerifyValidateCode',
            {'code': validateCode},
            function (data, status) {
                if (data && data.status && data.status == 'success') {
                    if (modal) modal.close();
                    sendMobileCode();
                } else {
                    alert('验证码错误');
                }
            }
        );
    }

    /**
     * 发送验证码
     */
    function sendMobileCode() {
        var mobile = $('#mobile').val();
        if (!mobile) {
            alert("请填写手机号码");
            return;
        }
        $.post(
            '${ctx}/doSendMobileValidateCode',
            {'mobile': mobile},
            function (data, status) {
                console.log(data);
                if (data && data.status && data.status == 'success') {
                    //@todo 开始倒计时
                }
            }
        );

    }

</script>
</body>