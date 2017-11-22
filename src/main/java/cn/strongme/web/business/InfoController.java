package cn.strongme.web.business;

import cn.strongme.annotation.MenuKey;
import cn.strongme.annotation.TitleInfo;
import cn.strongme.entity.business.Info;
import cn.strongme.entity.system.Dict;
import cn.strongme.service.business.InfoService;
import cn.strongme.utils.system.DictUtils;
import cn.strongme.web.common.BaseController;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
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
 * Created by 阿水 on 2017/9/21 下午3:13.
 * 资讯管理
 */
@Controller
@RequestMapping("business/info")
@MenuKey("info")
public class InfoController extends BaseController {

    @Autowired
    private InfoService infoService;

    @ModelAttribute
    public Info get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return infoService.get(new Info(id));
        } else {
            return new Info();
        }
    }

    @RequestMapping(value = {"", "list"})
    @TitleInfo(title = "资讯数据", subTitle = "所有数据列表")
    public String list(Info info, Model model) {
        //设置type
        List<Dict> dictList = DictUtils.getDictList("info-type");
        List<String> types = Lists.newArrayList();
        for (Dict d : dictList) {
            types.add(d.getValue());
        }
        info.setTypeList(types);
        List<Info> infoList = infoService.findListPage(info);
        PageInfo<Info> pageInfo = new PageInfo<>(infoList);
        model.addAttribute("info", info);
        model.addAttribute("page", pageInfo);
        return "business.info.infoList";
    }

    @RequestMapping(value = "form")
    @TitleInfo(title = "资讯数据", subTitle = "资讯添加编辑")
    public String form(Info info, Model model) {
        model.addAttribute("info", info);
        return "business.info.infoForm";
    }

    @RequestMapping(value = "view")
    @TitleInfo(title = "资讯预览", subTitle = "资讯数据")
    public String view(Info info, Model model) {
        model.addAttribute("info", info);
        return "business.info.infoView";
    }

    @RequestMapping(value = "save")//@Valid
    public String save(Info info, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, info)) {
            return form(info, model);
        }
        infoService.save(info);
        addMessage(redirectAttributes, "success", "保存《" + info.getTitle() + "》成功");
        return "redirect:/business/info";
    }

    @RequestMapping(value = "delete")
    public String delete(Info info, RedirectAttributes redirectAttributes) {
        try {
            infoService.delete(info);
            addMessage(redirectAttributes, "success", "删除资讯成功");
        } catch (Exception e) {
            addMessage(redirectAttributes, "danger", "删除资讯失败");
        }
        return "redirect:/business/info";
    }


}
