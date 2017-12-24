package cn.strongme.wechat.handler;

import cn.strongme.common.utils.JsonMapper;
import cn.strongme.common.utils.StringUtils;
import cn.strongme.utils.common.ConfigUtils;
import cn.strongme.utils.system.WerchatUtils;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 转发客户消息Handler
 * <p>
 * Created by FirenzesEagle on 2016/7/27 0027.
 * Email:liumingbo2008@gmail.com
 */
@Component
public class MsgFinalHandler extends AbstractHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        this.logger.info("\nIn Final Handler Msg：\n{} ", JsonMapper.toPrettyJsonStr(wxMessage));
        String finalMsg = ConfigUtils.getConfig(WerchatUtils.FINAL_MSG);
        if (StringUtils.isBlank(finalMsg)) {
            finalMsg = "您反馈的消息我们已经收到，谢谢。\n";
        }
        return WxMpXmlOutMessage
                .TEXT().fromUser(wxMessage.getToUser())
                .content(finalMsg)
                .toUser(wxMessage.getFromUser()).build();

    }
}
