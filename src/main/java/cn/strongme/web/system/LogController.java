package cn.strongme.web.system;

import cn.strongme.annotation.MenuKey;
import cn.strongme.annotation.TitleInfo;
import cn.strongme.entity.system.Log;
import cn.strongme.service.system.LogService;
import cn.strongme.web.common.BaseController;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by 阿水 on 2017/11/8 上午9:35.
 */
@Controller
@RequestMapping("system/log")
@MenuKey("log")
public class LogController extends BaseController {

    @Autowired
    private LogService logService;

    @ModelAttribute
    public Log get(@RequestParam(required = false) String id) {
        return new Log();
    }

    @RequestMapping(value = {"", "list"})
    @TitleInfo(title = "日志数据", subTitle = "所有数据列表")

    public String list(Log log, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (log == null) {
            log = new Log();
        }
        if (log.getBeginDate() == null) {
            log.setBeginDate(new Date());
        }
        if (log.getEndDate() == null) {
            log.setEndDate(new Date());
        }

        PageInfo<Log> pageInfo = logService.findPage(log);
        model.addAttribute("page", pageInfo);
        model.addAttribute("log", log);
        return "system.log.logList";
    }

}
