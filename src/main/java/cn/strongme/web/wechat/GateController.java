package cn.strongme.web.wechat;

import cn.strongme.annotation.TitleInfo;
import cn.strongme.entity.system.WxUser;
import cn.strongme.web.common.WechatBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 阿水 on 2017/12/24 下午3:46.
 */
@Controller
@RequestMapping("wechat/gate")
public class GateController extends WechatBaseController {

    @RequestMapping("index")
    @TitleInfo(title = "门禁")
    public String index(String openId, @RequestParam(value = "code", required = false) String code, @RequestParam(value = "lang", defaultValue = "zh_CN", required = false) String lang, Model model, HttpServletRequest request) {
        try {
            WxUser wxUser = handleWxInfo(code, openId, null, request.getSession(), model);
        } catch (Exception e) {
            e.printStackTrace();
            return "wechat@common@error";
        }
        return "wechat@gate@gateIndex";
    }

}
