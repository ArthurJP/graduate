package cn.strongme.wechat.handler;

import cn.strongme.common.utils.JsonMapper;
import cn.strongme.entity.system.WxUser;
import cn.strongme.service.system.CoreService;
import cn.strongme.service.system.WxUserService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户关注公众号Handler
 * <p>
 * Created by FirenzesEagle on 2016/7/27 0027.
 * Email:liumingbo2008@gmail.com
 */
@Component
public class SubscribeHandler extends AbstractHandler {

    @Autowired
    protected WxMpConfigStorage configStorage;
    @Autowired
    protected WxMpService wxMpService;
    @Autowired
    protected CoreService coreService;
    @Autowired
    private WxUserService wxUserService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        WxMpUser wxMpUser = coreService.getUserInfo(wxMessage.getFromUser(), "zh_CN");
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("openId", wxMpUser.getOpenId()));
        params.add(new BasicNameValuePair("nickname", wxMpUser.getNickname()));
        params.add(new BasicNameValuePair("headImgUrl", wxMpUser.getHeadImgUrl()));

        //TODO 在这里可以进行用户关注时对业务系统的相关操作（比如新增用户）

        //保存微信用户到数据库
        String openId = wxMpUser.getOpenId();
        WxUser wxUser = new WxUser();
        wxUser.setOpenId(openId);
        try {
            if (!wxUserService.exist(wxUser)) {
                logger.info("不存在此微信用户，进行数据库存储");
                //保存微信用户信息
                wxUser = WxUser.buildWxUserFromWxMpUser(wxMpUser);
                wxUser.setSubscribe("1");
                wxUserService.save(wxUser);
            } else {
                //更新关注状态
                logger.info("存在此微信用户，进行关注状态的更新");
                wxUser.setSubscribe("1");
                wxUserService.updateSubscribeStatus(wxUser);
            }
        } catch (Exception e) {
            logger.error("保存微信用户信息失败:" + e.getMessage());
        }


        WxMpXmlOutTextMessage m
                = WxMpXmlOutMessage.TEXT()
                .content("尊敬的" + wxMpUser.getNickname() + "，您好！")
                .fromUser(wxMessage.getToUser())
                .toUser(wxMessage.getFromUser())
                .build();

        logger.info("subscribeMessageHandler" + m.getContent());
        logger.info("subscribeMessageHandler关注用户的信息：" + JsonMapper.toPrettyJsonStr(wxMpUser));
        return m;
    }
};
