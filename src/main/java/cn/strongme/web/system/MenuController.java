package cn.strongme.web.system;

import cn.strongme.annotation.MenuKey;
import cn.strongme.annotation.TitleInfo;
import cn.strongme.entity.system.Menu;
import cn.strongme.service.system.MenuService;
import cn.strongme.utils.system.MenuUtils;
import cn.strongme.web.common.BaseController;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by 阿水 on 2017/11/2 上午11:34.
 */
@Controller
@RequestMapping("system/menu")
@MenuKey("menu")
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuService;

    @ModelAttribute("menu")
    public Menu get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return menuService.get(new Menu(id));
        } else {
            return new Menu();
        }
    }

    @RequestMapping(value = {"list", ""})
    @TitleInfo(title = "菜单数据", subTitle = "所有数据列表")
    public String list(Model model) {
        List<Menu> list = Lists.newArrayList();
        List<Menu> sourcelist = menuService.findList(new Menu());
        Menu.sortList(list, sourcelist, Menu.getRootId(), true);
        model.addAttribute("list", list);
        return "system.menu.menuList";
    }

    @RequestMapping(value = "form")
    @TitleInfo(title = "菜单编辑", subTitle = "所有数据列表")
    public String form(Menu menu, Model model) {
        Menu parent = menuService.get(menu.getParent());
        if (parent == null || parent.getId() == null) {
            parent = new Menu(Menu.getRootId());
        }
        menu.setParent(parent);
        // 获取排序号，最末节点排序号+30
        if (StringUtils.isBlank(menu.getId())) {
            Menu pForQ = new Menu();
            pForQ.setParent(parent);
            List<Menu> list = menuService.findList(pForQ);
            if (list.size() > 0) {
                menu.setSort(list.get(list.size() - 1).getSort() + 30);
            }
        }
        if (StringUtils.isBlank(menu.getId())) {
            Menu p = menuService.get(menu.getParent());
            if (p != null) {
                menu.setType(p.getType());
            }
        }
        model.addAttribute("menu", menu);
        return "system.menu.menuForm";
    }

    @RequestMapping(value = "save")
    public String save(Menu menu, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (!beanValidator(model, menu)) {
            return form(menu, model);
        }
        try {
            menuService.save(menu);
            addMessage(redirectAttributes, "success", "保存菜单'" + menu.getName() + "'成功");
        } catch (Exception e) {
            addMessage(redirectAttributes, "danger", "保存菜单'" + menu.getName() + "'失败，" + e.getMessage());
        }

        return "redirect:/system/menu";
    }

    @RequestMapping(value = "delete")
    public String delete(Menu menu, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {
            menuService.delete(menu);
            addMessage(redirectAttributes, "success", "删除菜单成功");
        } catch (Exception e) {
            addMessage(redirectAttributes, "danger", "删除菜单失败，" + e.getMessage());
        }
        return "redirect:/system/menu";
    }

    @RequestMapping(value = "updateSort")
    public String updateSort(String[] ids, Integer[] sorts, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        for (int i = 0; i < ids.length; i++) {
            Menu menu = new Menu(ids[i]);
            menu.setSort(sorts[i]);
            menuService.updateMenuSort(menu);
        }
        addMessage(redirectAttributes, "success", "保存菜单排序成功");
        return "redirect:/system/menu";
    }

    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(String defaultSelectedId, String defaultCheckedId) {
        return MenuUtils.getTreeViewData(defaultSelectedId, defaultCheckedId);
    }

}
