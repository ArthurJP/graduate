package cn.strongme.web.system;

import cn.strongme.annotation.MenuKey;
import cn.strongme.annotation.TitleInfo;
import cn.strongme.entity.system.Office;
import cn.strongme.service.system.OfficeService;
import cn.strongme.utils.system.OfficeUtils;
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
 * Created by 阿水 on 2017/11/7 下午3:29.
 */
@Controller
@RequestMapping("system/office")
@MenuKey("office")
public class OfficeController extends BaseController {

    @Autowired
    private OfficeService officeService;

    @ModelAttribute("office")
    public Office get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return officeService.get(new Office(id));
        } else {
            return new Office();
        }
    }

    @RequestMapping(value = {"list", ""})
    @TitleInfo(title = "部门数据", subTitle = "所有数据列表")
    public String list(Model model) {
        List<Office> list = Lists.newArrayList();
        List<Office> sourcelist = officeService.findList(new Office());
        Office.sortList(list, sourcelist, Office.getRootId(), true);
        model.addAttribute("list", list);
        return "system.office.officeList";
    }

    @RequestMapping(value = "form")
    @TitleInfo(title = "部门编辑", subTitle = "部门编辑")
    public String form(Office office, Model model) {
        Office parent = officeService.get(office.getParent());
        if (parent == null || parent.getId() == null) {
            parent = new Office(Office.getRootId());
        }
        office.setParent(parent);
        model.addAttribute("office", office);
        return "system.office.officeForm";
    }

    @RequestMapping(value = "save")
    public String save(Office office, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (!beanValidator(model, office)) {
            return form(office, model);
        }
        try {
            officeService.save(office);
            addMessage(redirectAttributes, "success", "保存部门'" + office.getName() + "'成功");
        } catch (Exception e) {
            addMessage(redirectAttributes, "danger", "保存部门'" + office.getName() + "'失败，" + e.getMessage());
        }
        return "redirect:/system/office";
    }

    @RequestMapping(value = "delete")
    public String delete(Office office, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        try {
            officeService.delete(office);
            addMessage(redirectAttributes, "'success'", "删除部门成功");
        } catch (Exception e) {
            addMessage(redirectAttributes, "danger", "删除部门失败，" + e.getMessage());
        }
        return "redirect:/system/office";
    }

    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(String defaultSelectedId, String defaultCheckedId) {
        return OfficeUtils.getTreeViewData(defaultSelectedId, defaultCheckedId);
    }


}
