package cn.strongme.web.front;

import cn.strongme.annotation.MenuKey;
import cn.strongme.annotation.TitleInfo;
import cn.strongme.entity.business.Info;
import cn.strongme.service.business.InfoService;
import cn.strongme.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author 阿水
 * @date 2017/11/8 下午9:23
 */
@Controller
@MenuKey("home")
public class HomeController extends BaseController {

    @Autowired
    private InfoService infoService;

    @RequestMapping("")
    @TitleInfo(title = "主页", subTitle = "前端主页")
    public String index(Model model) {
        List<Info> infoList = infoService.findList(new Info());
        model.addAttribute("infoList", infoList);
        return "front-home-index";
    }

}
