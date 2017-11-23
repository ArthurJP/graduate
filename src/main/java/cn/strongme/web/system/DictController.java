package cn.strongme.web.system;

import cn.strongme.annotation.MenuKey;
import cn.strongme.annotation.TitleInfo;
import cn.strongme.entity.system.Dict;
import cn.strongme.service.system.DictService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 字典数据
 */

@Controller
@RequestMapping(value = "system/dict")
@MenuKey("dict")
public class DictController extends BaseController {

    @Autowired
    private DictService dictService;

    @ModelAttribute
    public Dict get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return dictService.get(new Dict(id));
        } else {
            return new Dict();
        }
    }

    @RequestMapping(value = {"", "list"})
    @TitleInfo(title = "字典数据", subTitle = "所有数据列表")
    public String list(Dict dict, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<String> typeList = dictService.findTypeList();
        model.addAttribute("typeList", typeList);
        PageInfo<Dict> pageInfo = dictService.findListPage(dict);
        model.addAttribute("page", pageInfo);
        model.addAttribute("dict", dict);
        return "system.dict.dictList";
    }

    @RequestMapping(value = "form")
    @TitleInfo(title = "字典编辑", subTitle = "字典编辑")
    public String form(Dict dict, Model model) {
        model.addAttribute("dict", dict);
        return "system.dict.dictForm";
    }

    @RequestMapping(value = "save")//@Valid
    public String save(Dict dict, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, dict)) {
            return form(dict, model);
        }
        try {
            dictService.save(dict);
            addMessage(redirectAttributes, "success", "保存字典'" + dict.getLabel() + "'成功");
        } catch (Exception e) {
            e.getMessage();
            addMessage(redirectAttributes, "danger", "保存字典'" + dict.getLabel() + "'失败");
        }

        return "redirect:/system/dict";
    }

    @RequestMapping(value = "delete")
    public String delete(Dict dict, RedirectAttributes redirectAttributes) {
        try {
            dictService.delete(dict);
            addMessage(redirectAttributes, "success", "删除字典成功");
        } catch (Exception e) {
            addMessage(redirectAttributes, "danger", "删除字典失败");
        }
        return "redirect:/system/dict";
    }


}
