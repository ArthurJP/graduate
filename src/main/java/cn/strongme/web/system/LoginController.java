package cn.strongme.web.system;

import cn.strongme.annotation.TitleInfo;
import cn.strongme.config.Principal;
import cn.strongme.entity.system.User;
import cn.strongme.service.system.UserService;
import cn.strongme.servlet.ValidateCodeServlet;
import cn.strongme.utils.common.CacheUtils;
import cn.strongme.utils.common.VerifyCodeUtils;
import cn.strongme.utils.system.UserUtils;
import cn.strongme.web.common.BaseController;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;


/**
 * @author 阿水
 * @date 2017/7/14 上午8:44
 * 首页及主要
 */
@Controller
public class LoginController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("system/home")
    @TitleInfo(title = "主页", subTitle = "系统主页")
    public String home(Model model) {
        model.addAttribute("info", "信息");
        return "system.main.home";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    @TitleInfo(title = "登陆", subTitle = "登陆信息")
    public String login(Model model) {
        Principal principal = UserUtils.getPrincipal();
        if (principal != null) {
            return "redirect:/system/home";
        }
        if (UserUtils.currentUser() != null) {
            return "redirect:/system/home";
        }
        return "system-main-login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String doLogin(String username, String password, RedirectAttributes redirectAttributes) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();
        try {
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            logger.info("对用户[" + username + "]进行登录验证..验证开始");
            currentUser.login(token);
            logger.info("对用户[" + username + "]进行登录验证..验证通过");
        } catch (UnknownAccountException uae) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
            redirectAttributes.addFlashAttribute("message", "未知账户");
            addMessage(redirectAttributes, "danger", "未知账户");
        } catch (IncorrectCredentialsException ice) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
            addMessage(redirectAttributes, "danger", "密码不正确");
        } catch (LockedAccountException lae) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
            addMessage(redirectAttributes, "danger", "账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
            addMessage(redirectAttributes, "danger", "用户名或密码错误次数过多");
        } catch (AuthenticationException ae) {
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
            ae.printStackTrace();
            redirectAttributes.addFlashAttribute("msg", "用户名或密码不正确");
            addMessage(redirectAttributes, "danger", "用户名或密码不正确");
        }
        //验证是否登录成功
        if (currentUser.isAuthenticated() && currentUser.getPrincipal() != null) {
            logger.info("用户[" + username + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
            redirectAttributes.addFlashAttribute("msg", null);
            return "redirect:/system/home";
        } else {
            token.clear();
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(RedirectAttributes redirectAttributes) {
        //使用权限管理工具进行用户的退出，跳出登录，给出提示信息
        SecurityUtils.getSubject().logout();
        redirectAttributes.addFlashAttribute("message", "您已安全退出");
        return "redirect:/";
    }


    @RequestMapping(value = "regist", method = RequestMethod.GET)
    @TitleInfo(title = "注册", subTitle = "注册信息")
    public String regist(Model model) {
        return "system-main-regist";
    }

    @RequestMapping(value = "regist", method = RequestMethod.POST)
    public String regist(String mobile, String password, String code, Model model, RedirectAttributes redirectAttributes) {
        if (StringUtils.isBlank(mobile)) {
            addMessage(redirectAttributes, "danger", "请填写手机号");
            return "redirect:/regist";
        }
        if (StringUtils.isBlank(password)) {
            addMessage(redirectAttributes, "danger", "请填写密码");
            return "redirect:/regist";
        }
        if (StringUtils.isBlank(code)) {
            addMessage(redirectAttributes, "danger", "请填写验证码");
            return "redirect:/regist";
        }
        String verfiyCodeInCache = (String) CacheUtils.get(CacheUtils.MOBILE_VALIDATE_CACHE, CacheUtils.MOBILE_VALIDATE_CACHE + "_mobile_" + mobile);
        if (StringUtils.isBlank(verfiyCodeInCache)) {
            addMessage(redirectAttributes, "danger", "验证码不存在");
            return "redirect:/regist";
        }
        if (verfiyCodeInCache.toUpperCase().equals(code.toUpperCase())) {
            //注册成功
            User newUser = new User();
            newUser.setMobile(mobile);
            newUser.setPassword(password);
            try {
                userService.save(newUser);
            } catch (Exception e) {
                addMessage(redirectAttributes, "danger", e.getMessage());
                return "redirect:/regist";
            }
        } else {
            addMessage(redirectAttributes, "danger", "验证码错误");
            return "redirect:/regist";
        }
        addMessage(redirectAttributes, "success", "注册成功，请登陆完善信息");
        return "redirect:/login";
    }

    @RequestMapping("toValidateCode")
    @TitleInfo(title = "验证码", subTitle = "验证人机")
    public String toValidateCode(Model model) {
        return "system_main_toValidateCode";
    }

    @RequestMapping("doVerifyValidateCode")
    @ResponseBody
    public Map<String, Object> doVerifyValidateCode(String code) {
        Map<String, Object> result = Maps.newHashMap();
        Session session = UserUtils.getSession();
        String codeInSession = (String) session.getAttribute(ValidateCodeServlet.VALIDATE_CODE);
        if (StringUtils.isNotBlank(code) && StringUtils.isNotBlank(codeInSession) && code.toUpperCase().equals(codeInSession.toUpperCase())) {
            result.put("status", "success");
        } else {
            result.put("status", "fail");
        }
        return result;
    }

    @RequestMapping("doSendMobileValidateCode")
    @ResponseBody
    public Map<String, Object> doSendMobileValidateCode(String mobile) {
        Map<String, Object> result = Maps.newHashMap();
        String verifyCode = VerifyCodeUtils.createRandom(true, 6);
        System.out.println("手机：" + mobile + " 验证码：" + verifyCode);
        CacheUtils.put(CacheUtils.MOBILE_VALIDATE_CACHE, CacheUtils.MOBILE_VALIDATE_CACHE + "_mobile_" + mobile, verifyCode);
        result.put("status", "success");
        result.put("code", verifyCode);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "checkMobile")
    public String checkLoginName(String mobile) {
        User u = new User();
        u.setMobile(mobile);
        if (userService.checkExistMobile(u)) {
            return "false";
        }
        return "true";
    }

    @RequestMapping("/403")
    public String unauthorizedRole() {
        logger.info("------没有权限-------");
        return "403";
    }

}
