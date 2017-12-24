<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<div class="page__bd">
    <div class="weui-panel weui-panel_access">
        <div class="weui-panel__bd">
            <div class="weui-media-box weui-media-box_appmsg">
                <div class="weui-media-box__hd">
                    <img class="weui-media-box__thumb" src="${wxUser.headImgUrl}" alt="">
                </div>
                <div class="weui-media-box__bd">
                    <h4 class="weui-media-box__title">${wxUser.nickname}</h4>
                    <p class="weui-media-box__desc">${wxUser.sex}&nbsp;&nbsp;&nbsp;&nbsp;${wxUser.country}-${wxUser.province}-${wxUser.city}</p>
                </div>
            </div>

            <div class="weui-cells weui-cells_form">
                <div class="weui-cell">
                    <div class="weui-cell__hd"><label class="weui-label">手机号</label></div>
                    <div class="weui-cell__bd">
                        <input class="weui-input" type="number" pattern="[0-9]*" placeholder="请输入手机号">
                    </div>
                </div>
                <div class="weui-cell weui-cell_select weui-cell_select-after">
                    <div class="weui-cell__hd">
                        <label for="" class="weui-label">小区</label>
                    </div>
                    <div class="weui-cell__bd">
                        <select class="weui-select" name="select2">
                            <option value="">请选择小区</option>
                            <option value="1">小区1</option>
                            <option value="2">小区2</option>
                            <option value="3">小区3</option>
                        </select>
                    </div>
                </div>
                <div class="weui-cell weui-cell_select weui-cell_select-after">
                    <div class="weui-cell__hd">
                        <label for="" class="weui-label">楼号</label>
                    </div>
                    <div class="weui-cell__bd">
                        <select class="weui-select" name="select2">
                            <option value="">请选择楼号</option>
                            <option value="1">1栋</option>
                            <option value="2">2栋</option>
                            <option value="3">3栋</option>
                        </select>
                    </div>
                </div>
                <div class="weui-cell weui-cell_select weui-cell_select-after">
                    <div class="weui-cell__hd">
                        <label for="" class="weui-label">房号</label>
                    </div>
                    <div class="weui-cell__bd">
                        <select class="weui-select" name="select2">
                            <option value="">请选择房号</option>
                            <option value="1">1101</option>
                            <option value="2">1102</option>
                            <option value="3">1103</option>
                        </select>
                    </div>
                </div>
            </div>
        </div>
        <div class="weui-panel__ft">
            <div class="weui-btn-area">
                <a class="weui-btn weui-btn_primary" href="javascript:">提交</a>
            </div>
        </div>
    </div>
</div>