package cn.strongme.web.advertisement;

import cn.strongme.annotation.MenuKey;
import cn.strongme.annotation.TitleInfo;
import cn.strongme.common.utils.StringUtils;
import cn.strongme.entity.advertisement.Ads;
import cn.strongme.service.advertisement.AdsService;
import cn.strongme.web.common.BaseController;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by 张俊鹏 on 2018/1/23.
 *
 */
@Controller
@RequestMapping("advertisement/ads")
@MenuKey( "ads" )
public class AdsController extends BaseController {

    @Autowired
    private AdsService adsService;

//    有待学习
    @ModelAttribute
    public Ads get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return adsService.get(new Ads(id));
        } else {
            return new Ads();
        }
    }

    @RequestMapping(value={"", "list"})
    @TitleInfo(title = "广告数据", subTitle = "数据列表")
    public String list(Ads ads, Model model) {
//        List<Dict> dictList = DictUtils.getDictList("adsPosition");
//        List<String> types = Lists.newArrayList();
//        for (Dict d : dictList) {
//            types.add(d.getValue());
//        }
//        ads.setTypeList(types);
        List<Ads> adsList = adsService.findList( ads );
        PageInfo pageInfo = new PageInfo( adsList );
        model.addAttribute( "ads" );
        model.addAttribute( "page", pageInfo );
//        System.out.println("测试连通性");
        return "advertisement.ads.adsList";
    }

    @RequestMapping(value="form")
    @TitleInfo(title="广告数据",subTitle="广告添加编辑")
    public String form(Ads ads, Model model) {
        model.addAttribute("ads", ads);
        return "advertisement.ads.adsForm";
    }

    @RequestMapping(value = "save")//@Valid
    public String save(Ads ads, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, ads)) {
            return form(ads, model);
        }
        adsService.save(ads);
        System.out.println( "测试连通性" );
        addMessage(redirectAttributes, "success", "保存《" + ads.getAlt() + "》成功");
        return "redirect:/advertisement/ads";
    }

    @RequestMapping(value = "delete")
    public String delete(String id, RedirectAttributes redirectAttributes) {
        try {
            adsService.deleteByPrimaryKey(id);
            addMessage(redirectAttributes, "success", "删除广告成功");
        } catch (Exception e) {
            System.out.println( "e = " + e );
            addMessage(redirectAttributes, "danger", "删除广告失败");
        }
        return "redirect:/advertisement/ads";
    }

    @RequestMapping(value="deleteAll")
    public String deleteAll(@RequestParam(value="ids",required = false,defaultValue = "") String ids, RedirectAttributes redirectAttributes){
        try {
            ids=ids.replaceAll( ",","','" );
            ids="'"+ids+"'";
            System.out.println( "ids = " + ids );
            adsService.deleteByKeys(ids);
            addMessage(redirectAttributes, "success", "批量删除广告成功");
        } catch (Exception e) {
            System.out.println( "e = " + e );
            addMessage(redirectAttributes, "danger", "批量删除广告失败");
        }
        return "redirect:/advertisement/ads";
    }

    @RequestMapping("resort")
    @ResponseBody//响应Ajax请求，会将响应对象转换成json
    public String resort(@RequestBody String id,@RequestBody String sort) throws IOException {
        System.out.println( "id = " + id );
        System.out.println( "sort = " + sort );
        return "success";
    }
}
