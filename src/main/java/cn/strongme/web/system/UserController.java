package cn.strongme.web.system;

import cn.strongme.annotation.MenuKey;
import cn.strongme.annotation.TitleInfo;
import cn.strongme.entity.system.Role;
import cn.strongme.entity.system.User;
import cn.strongme.service.system.JustMeService;
import cn.strongme.service.system.RoleService;
import cn.strongme.service.system.UserAltService;
import cn.strongme.utils.system.OfficeUtils;
import cn.strongme.utils.system.UserUtils;
import cn.strongme.web.common.BaseController;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * @author 阿水
 * @date 2017/7/14 上午9:42
 * 用户
 */
@Controller
@RequestMapping("system/user")
@MenuKey("user")
public class UserController extends BaseController {

    @Autowired
    private UserAltService userAltService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private JustMeService justMeService;



    @ModelAttribute
    public User get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return justMeService.get(new User(id));
        } else {
            return new User();
        }
    }

    @RequestMapping(value = {"", "list"})
    @TitleInfo(title = "用户数据", subTitle = "用户数据")
    public String list(User user, Model model) {
        PageInfo<User> pageInfo = justMeService.findListPage(user);
        model.addAttribute("user", user);
        model.addAttribute("page", pageInfo);
        model.addAttribute("officeData", OfficeUtils.findListInTreeStructre());
        return "system.user.userList";
    }

    @RequestMapping("completeInfoView")
    @TitleInfo(title = "用户数据", subTitle = "用户数据")
    public String completeInfoView(User user, Model model) {
        if (user == null || StringUtils.isBlank(user.getId())) {
            user = justMeService.get(UserUtils.currentUser());
        }
        model.addAttribute("user", user);
        return "system.user.userInfoView";
    }

    @RequestMapping("completeInfo")
    @TitleInfo(title = "个人中心", subTitle = "用户数据")
    public String completeInfo(User user, Model model) {
        model.addAttribute("self", true);
        if (user == null || StringUtils.isBlank(user.getId())) {
            user = justMeService.get(UserUtils.currentUser());
        }
        model.addAttribute("user", user);
        return "system.user.userInfo";
    }

    @RequestMapping(value = "save")
    public String save(User user, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, user)) {
            return completeInfo(user, model);
        }
        try {
            justMeService.saveBasicInfo(user);
            UserUtils.updateCurrentUser(user);
            addMessage(redirectAttributes, "success", "保存用户信息成功");
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(redirectAttributes, "danger", "保存用户信息失败:" + e.getMessage());
        }
        return "redirect:/system/user/completeInfoView";
    }

    @RequestMapping(value = "form")
    @TitleInfo(title = "用户信息", subTitle = "用户数据")
    public String form(User user, Model model) {
        model.addAttribute("user", user);
        List<Role> allRole = roleService.findList(new Role());
        model.addAttribute("roleList", allRole);
        return "system.user.userForm";
    }

    @RequestMapping(value = "saveInForm")
    public String saveInForm(User user, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, user)) {
            return completeInfo(user, model);
        }
        try {
            justMeService.save(user);
            if (user.getId().equals(UserUtils.currentUser().getId())) {
                UserUtils.updateCurrentUser(user);
            }
            addMessage(redirectAttributes, "success", "保存用户信息成功");
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(redirectAttributes, "danger", "保存用户信息失败:" + e.getMessage());
        }

        return "redirect:/system/user";
    }


    @RequestMapping(value = "delete")
    public String delete(User user, RedirectAttributes redirectAttributes) {
        try {
            justMeService.delete(user);
            addMessage(redirectAttributes, "success", "删除用户成功");
        } catch (Exception e) {
            addMessage(redirectAttributes, "danger", "删除用户失败");
        }
        return "redirect:/system/user";
    }


}
