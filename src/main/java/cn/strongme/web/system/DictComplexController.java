package cn.strongme.web.system;

import cn.strongme.annotation.MenuKey;
import cn.strongme.annotation.TitleInfo;
import cn.strongme.entity.common.TreeEntity;
import cn.strongme.entity.system.DictComplex;
import cn.strongme.service.system.DictComplexService;
import cn.strongme.utils.system.DictComplexUtils;
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
 * Created by 阿水 on 2017/11/8 下午2:46.
 */
@Controller
@RequestMapping("system/dictComplex")
@MenuKey("dictComplex")
public class DictComplexController extends BaseController {

    @Autowired
    private DictComplexService dictComplexService;

    @ModelAttribute
    public DictComplex get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return dictComplexService.get(new DictComplex(id));
        } else {
            return new DictComplex();
        }
    }

    @RequestMapping(value = {"list", ""})
    @TitleInfo(title = "业务字典数据", subTitle = "所有数据列表")
    public String list(Model model) {
        List<DictComplex> list = Lists.newArrayList();
        List<DictComplex> sourcelist = dictComplexService.findList(new DictComplex());
        DictComplex.sortList(list, sourcelist, DictComplex.getRootId(), true);
        model.addAttribute("list", list);
        return "system.dictComplex.dictComplexList";
    }

    @RequestMapping(value = "form")
    @TitleInfo(title = "业务字典编辑", subTitle = "业务字典编辑")
    public String form(DictComplex dictComplex, Model model) {
        DictComplex parent = dictComplexService.get(dictComplex.getParent());
        if (parent == null || parent.getId() == null) {
            parent = new DictComplex(TreeEntity.getRootId());
        }
        dictComplex.setParent(parent);
        // 获取排序号，最末节点排序号+30
        if (StringUtils.isBlank(dictComplex.getId())) {
            DictComplex dcForCurType = new DictComplex();
            dcForCurType.setParent(parent);
            List<DictComplex> list = dictComplexService.findList(dcForCurType);
            if (list.size() > 0) {
                dictComplex.setSort(list.get(list.size() - 1).getSort() + 10);
            } else {
                dictComplex.setSort(10);
            }
            DictComplex p = dictComplexService.get(dictComplex.getParent());
            if (p != null) {
                dictComplex.setType(p.getType());
                dictComplex.setDescription(p.getDescription());
            }
        }
        model.addAttribute("dictComplex", dictComplex);
        return "system.dictComplex.dictComplexForm";
    }

    @RequestMapping(value = "save")
    public String save(DictComplex dictComplex, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (!beanValidator(model, dictComplex)) {
            return form(dictComplex, model);
        }
        try {
            dictComplexService.save(dictComplex);
            addMessage(redirectAttributes, "success", "保存业务字典'" + dictComplex.getName() + "'成功");
        } catch (Exception e) {
            addMessage(redirectAttributes, "danger", "保存业务字典'" + dictComplex.getName() + "'失败，" + e.getMessage());
        }

        return "redirect:/system/dictComplex";
    }


    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(String defaultSelectedId, String defaultCheckedId) {
        List<Map<String, Object>> result = DictComplexUtils.getTreeViewData(defaultSelectedId, defaultCheckedId);
        return result;
    }

}
