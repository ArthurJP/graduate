package cn.strongme.web.system;

import cn.strongme.annotation.MenuKey;
import cn.strongme.annotation.TitleInfo;
import cn.strongme.common.utils.StringUtils;
import cn.strongme.entity.system.Role;
import cn.strongme.service.system.RoleService;
import cn.strongme.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by 阿水 on 2017/11/6 下午4:37.
 */
@Controller
@RequestMapping("system/role")
@MenuKey("role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @ModelAttribute("role")
    public Role get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return roleService.get(new Role(id));
        } else {
            return new Role();
        }
    }

    @RequestMapping(value = {"list", ""})
    @TitleInfo(title = "角色数据", subTitle = "所有数据列表")
    public String list(Role role, Model model) {
        List<Role> list = roleService.findList(role);
        model.addAttribute("list", list);
        return "system.role.roleList";
    }

    @RequestMapping(value = "form")
    @TitleInfo(title = "角色编辑", subTitle = "角色编辑")
    public String form(Role role, Model model) {
        model.addAttribute("role", role);
        return "system.role.roleForm";
    }

    @RequestMapping(value = "save")
    public String save(Role role, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
        if (!beanValidator(model, role)) {
            return form(role, model);
        }
        if (!"true".equals(checkName(role.getOldName(), role.getName()))) {
            addMessage(model, "danger", "保存角色'" + role.getName() + "'失败, 角色名已存在");
            return form(role, model);
        }
        if (!"true".equals(checkEname(role.getOldEname(), role.getEname()))) {
            addMessage(model, "danger", "保存角色'" + role.getName() + "'失败, 英文名已存在");
            return form(role, model);
        }
        try {
            roleService.save(role);
            addMessage(redirectAttributes, "success", "保存角色'" + role.getName() + "'成功");
        } catch (Exception e) {
            addMessage(redirectAttributes, "danger", "保存角色'" + role.getName() + "'失败，" + e.getMessage());
        }
        return "redirect:/system/role";
    }

    @RequestMapping(value = "delete")
    public String delete(Role role, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {
            roleService.delete(role);
            addMessage(redirectAttributes, "'success'", "删除角色成功");
        } catch (Exception e) {
            addMessage(redirectAttributes, "danger", "删除角色失败，" + e.getMessage());
        }
        return "redirect:/system/role";
    }

    @ResponseBody
    @RequestMapping(value = "checkName")
    public String checkName(String oldName, String name) {
        if (name != null && name.equals(oldName)) {
            return "true";
        } else if (roleService.getByName(new Role(name, null)) == null) {
            return "true";
        }
        return "false";
    }

    /**
     * 验证角色英文名是否有效
     *
     * @param oldEname
     * @param ename
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "checkEname")
    public String checkEname(String oldEname, String ename) {
        if (ename != null && ename.equals(oldEname)) {
            return "true";
        } else if (roleService.getByEname(new Role(null, ename)) == null) {
            return "true";
        }
        return "false";
    }


}
