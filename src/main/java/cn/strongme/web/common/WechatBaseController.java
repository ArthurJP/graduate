package cn.strongme.web.common;

import cn.strongme.common.utils.JsonMapper;
import cn.strongme.common.utils.StringUtils;
import cn.strongme.entity.system.WxUser;
import cn.strongme.service.system.CoreService;
import cn.strongme.service.system.WxUserService;
import com.google.gson.Gson;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by 阿水 on 2017/12/24 下午4:57.
 */
public class WechatBaseController extends BaseController {

    public static final String WX_IN_SESSION = "wxUser";
    @Autowired
    protected CoreService coreService;
    @Autowired
    protected WxMpConfigStorage configStorage;
    @Autowired
    protected WxMpService wxMpService;
    @Autowired
    protected WxUserService wxUserService;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 客户端返回JSON字符串
     *
     * @param response
     * @param object
     * @return
     */
    protected String renderString(HttpServletResponse response, Object object) {
        return renderString(response, new Gson().toJson(object), "application/json");
    }

    /**
     * 客户端返回字符串
     *
     * @param response
     * @param string
     * @return
     */
    protected String renderString(HttpServletResponse response, String string, String type) {
        try {
            response.reset();
            response.setContentType(type);
            response.setCharacterEncoding("utf-8");
            //解决跨域问题
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.getWriter().print(string);
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 尝试各种方式获取当前微信用户信息
     *
     * @param code
     * @param openId
     * @param session
     * @return
     */
    protected WxUser getWxUserFromCodeOrSessionOrOpenIdOrStudentId(String code, String openId, HttpSession session) {
        WxUser result = null;
        //尝试Session
        if (result == null && session != null) {
            logger.info("Load Info From Session Server!");
            result = (WxUser) session.getAttribute(WX_IN_SESSION);
            //比对是不是目标微信用户
            if (result != null && StringUtils.isNotBlank(openId) && !openId.equals(result.getOpenId())) {
                result = null;
            }
        }
        if (result == null && StringUtils.isNotBlank(code)) {
            //通过微信菜单进入的请求，根据微信接口查询微信用户信息
            logger.info("Load Info From Wx Server By Code :" + code);
            try {
                WxMpOAuth2AccessToken accessToken;
                WxMpUser wxMpUser;
                accessToken = this.wxMpService.oauth2getAccessToken(code);
                wxMpUser = this.wxMpService.oauth2getUserInfo(accessToken, "zh_CN");
                if (wxMpUser != null) {
                    result = WxUser.buildWxUserFromWxMpUser(wxMpUser);
                    result = wxUserService.getByOpenId(result);
                }
            } catch (WxErrorException e) {
                result = null;
            }
        }
        //尝试openId
        if (result == null && StringUtils.isNotBlank(openId)) {
            logger.info("Load Info From Server By OpenId:" + openId);
            WxUser wuForQuery = new WxUser();
            wuForQuery.setOpenId(openId);
            result = wxUserService.getByOpenId(wuForQuery);
        }
        if (result != null && session.getAttribute(WX_IN_SESSION) == null) {
            logger.info("Get current Wx User info Success" + JsonMapper.toPrettyJsonStr(result));
            session.setAttribute(WX_IN_SESSION, result);
        }
        return result;
    }

    /**
     * 删除 WxUser Session
     *
     * @param session
     */
    protected void clearWxUserSession(HttpSession session) {
        session.removeAttribute(WX_IN_SESSION);
    }

    /**
     * 处理页面请求，设置微信用户信息
     *
     * @param code
     * @param openId
     * @param companyId
     * @param session
     * @param model
     * @throws Exception
     */
    protected WxUser handleWxInfo(String code, String openId, String companyId, HttpSession session, Model model) throws Exception {
        WxUser wxUser = getWxUserFromCodeOrSessionOrOpenIdOrStudentId(code, openId, session);
        if (wxUser != null) {
            model.addAttribute("wxUser", wxUser);
            return wxUser;
        } else {
            throw new Exception("请在微信中使用此功能");
        }
    }

}
