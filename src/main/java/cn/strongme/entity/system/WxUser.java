package cn.strongme.entity.system;

import cn.strongme.common.utils.StringUtils;
import cn.strongme.entity.common.BaseEntity;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

import java.util.Date;

/**
 * Created by Walter on 16/9/20.
 * 微信用户实体
 */
public class WxUser extends BaseEntity<WxUser> {

    private static final long serialVersionUID = -2134674781090750526L;
    protected String subscribe;
    protected String openId;
    protected String nickname;
    protected String sex;
    protected String language;
    protected String city;
    protected String province;
    protected String country;
    protected String headImgUrl;
    protected Date subscribeTime;
    protected String unionId;
    protected String remark;
    protected String tagIdList;
    protected Integer groupId;

    public WxUser() {
    }

    public WxUser(String id) {
        super(id);
    }

    public WxUser(String id, String openId) {
        this.openId = openId;
    }

    public static WxUser buildWxUserFromWxMpUser(WxMpUser wxMpUser) {
        if (wxMpUser == null) return null;
        WxUser wxUser = new WxUser();
        wxUser.setOpenId(wxMpUser.getOpenId());
        wxUser.setNickname(wxMpUser.getNickname());
        wxUser.setCity(wxMpUser.getCity());
        wxUser.setCountry(wxMpUser.getCountry());
        wxUser.setProvince(wxMpUser.getProvince());
        wxUser.setSex(wxMpUser.getSex());
        wxUser.setHeadImgUrl(wxMpUser.getHeadImgUrl());
        wxUser.setLanguage(wxMpUser.getLanguage());
        wxUser.setGroupId(wxMpUser.getGroupId());
        wxUser.setRemark(wxMpUser.getRemark());
        wxUser.setSubscribe("1");
        if (wxMpUser.getSubscribeTime() != null) {
            wxUser.setSubscribeTime(new Date(wxMpUser.getSubscribeTime()));
        }
        return wxUser;
    }

    public String getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickname() {
        if (StringUtils.isNotBlank(nickname)) {
            nickname = StringUtils.filterEmoji(nickname, "[emoji]");
        }
        return nickname;
    }

    public void setNickname(String nickname) {
        if (StringUtils.isNotBlank(nickname)) {
            nickname = StringUtils.filterEmoji(nickname, "[emoji]");
        }
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public Date getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(Date subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTagIdList() {
        return tagIdList;
    }

    public void setTagIdList(String tagIdList) {
        this.tagIdList = tagIdList;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

}

