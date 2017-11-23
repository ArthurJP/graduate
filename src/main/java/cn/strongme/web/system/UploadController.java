package cn.strongme.web.system;

import cn.strongme.annotation.MenuKey;
import cn.strongme.annotation.TitleInfo;
import cn.strongme.entity.system.Attachment;
import cn.strongme.service.system.AttachmentService;
import cn.strongme.utils.system.AttachmentUtils;
import cn.strongme.web.common.BaseController;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by 阿水 on 2017/9/20 下午3:37.
 * 上传
 */
@Controller
@RequestMapping("system/upload")
@MenuKey("upload")
public class UploadController extends BaseController {

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

    @RequestMapping("toUpload")
    @TitleInfo(title = "文件上传", subTitle = "文件上传")
    public String uploadInner(Model model) {
        return "upload_uploadPage";
    }

    @RequestMapping("uploadWang")
    @ResponseBody
    public Map<String, Object> uploadWang(MultipartHttpServletRequest requestFile, HttpServletRequest request) {
        Map<String, Object> dataMap = Maps.newHashMap();
        try {
            List<Attachment> data = AttachmentUtils.retrieveAttachmentsFromRequest(requestFile, "info", true, false);
            dataMap = Attachment.convertAttachmentListToWangEditorNeeds(data, request);
        } catch (Exception e) {
            e.printStackTrace();
            dataMap.put("errno", 1);
        }
        return dataMap;
    }


    @RequestMapping("")
    @ResponseBody
    public Map<String, Object> doUpload(@RequestParam String relativePath, MultipartHttpServletRequest request) {
        List<Attachment> data;
        Map<String, Object> result = Maps.newHashMap();
        try {
            data = AttachmentUtils.retrieveAttachmentsFromRequest(request, relativePath);
            result.put("data", data);
            result.put("msg", "上传附件成功");
        } catch (Exception e) {
            e.printStackTrace();
            data = Lists.newArrayList();
            result.put("data", data);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    @RequestMapping("delete")
    @ResponseBody
    public Map<String, Object> doUploadedDelete(Attachment attachment, HttpServletRequest request) {
        Map<String, Object> result = Maps.newHashMap();
        try {
            if (attachment != null) {
                //attachmentService.delete(attachment, request); 这里的删除不做具体数据与文件的删除，只是返回目标文件ID,用来完成前端交互
                result.put("attachmentId", attachment.getId());
                result.put("status", "success");
            } else {
                result.put("status", "fail");
                result.put("msg", "不存在此文件");
            }
        } catch (Exception e) {
            result.put("status", "fail");
            result.put("msg", e.getMessage());
        }
        return result;
    }

}
