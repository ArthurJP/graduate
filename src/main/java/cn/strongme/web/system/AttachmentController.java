package cn.strongme.web.system;

import cn.strongme.annotation.MenuKey;
import cn.strongme.annotation.TitleInfo;
import cn.strongme.common.utils.JsonMapper;
import cn.strongme.entity.system.Attachment;
import cn.strongme.service.system.AttachmentService;
import cn.strongme.utils.system.AttachmentUtils;
import cn.strongme.web.common.BaseController;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Created by 阿水 on 2017/9/25 下午2:20.
 * 附件
 */
@Controller
@RequestMapping("system/attachment")
@MenuKey("attachment")
public class AttachmentController extends BaseController {

    @Autowired
    private AttachmentService attachmentService;

    @ModelAttribute
    public Attachment get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return attachmentService.get(new Attachment(id));
        } else {
            return new Attachment();
        }
    }

    @RequestMapping(value = {"", "list"})
    @TitleInfo(title = "附件数据", subTitle = "所有数据列表")
    public String list(Attachment attachment, Model model) {
        PageInfo<Attachment> pageInfo = attachmentService.findListPage(attachment);
        model.addAttribute("page", pageInfo);
        //转换为inputfile可以接受的数据
        List<String> attUrls = AttachmentUtils.convertToAttachmentUrlList(pageInfo.getList());
        List<Map<String, Object>> attMaps = AttachmentUtils.convertToAttachmentMapList(pageInfo.getList());
        model.addAttribute("attachmentUrlListInJsonFormat", JsonMapper.toJsonString(attUrls));
        model.addAttribute("attachmentMapListInJsonFormat", JsonMapper.toJsonString(attMaps));
        return "system.attachment.attachmentList";
    }

}
