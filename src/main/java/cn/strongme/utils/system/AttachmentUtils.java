package cn.strongme.utils.system;

import cn.strongme.common.utils.FileUtils;
import cn.strongme.common.utils.SpringContextHolder;
import cn.strongme.entity.system.Attachment;
import cn.strongme.exception.ServiceException;
import cn.strongme.service.system.AttachmentService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by 阿水 on 2017/8/15 上午9:18.
 * 附件工具方法
 */
public class AttachmentUtils {


    private static AttachmentService attachmentService = SpringContextHolder.getBean(AttachmentService.class);

    public static List<Attachment> retrieveAttachmentsFromRequest(MultipartHttpServletRequest request, String relativePath) {
        return retrieveAttachmentsFromRequest(request, relativePath, true, true);
    }

    /**
     * 提取MultipartHttpServletRequest 中上传的附件并做保存
     *
     * @param request
     * @param saveFileToFileSystem
     * @return
     */
    public static List<Attachment> retrieveAttachmentsFromRequest(MultipartHttpServletRequest request, String relativePath, boolean saveFileToFileSystem, boolean saveToDatabase) {
        List<Attachment> result = Lists.newArrayList();
        String fileBaseDir = null;
        //是否需要保存到文件系统
        if (saveFileToFileSystem) {
            //userfiles目录可做配置
            fileBaseDir = RequestContextUtils.findWebApplicationContext(request).getServletContext().getRealPath("/userfiles");
            if (StringUtils.isNotBlank(relativePath)) {
                fileBaseDir += File.separator + relativePath;
            }
            File dir = new File(fileBaseDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
        Iterator<String> itr = request.getFileNames();
        while (itr.hasNext()) {
            String uploadedFile = itr.next();
            MultipartFile file = request.getFile(uploadedFile);
            String filename = file.getOriginalFilename();
            if (StringUtils.isBlank(filename)) {
                continue;
            }
            String extension = FileUtils.getFileExtension(filename);
            Attachment attachment = new Attachment(filename, extension, relativePath);
            attachment.preInsert();
            if (saveFileToFileSystem) {
                String finalPath = fileBaseDir + File.separator + attachment.getFileName();
                File fTarget = new File(finalPath);
                try {
                    file.transferTo(fTarget);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("保存\"" + filename + "\"附件到文件系统失败");
                }
            }
            result.add(attachment);
        }

        //是否需要保存到数据库
        if (saveToDatabase) {
            try {
                attachmentService.batchInsert(result);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException("保存附件信息到数据库失败");
            }
        }

        return result;
    }

    public static void deleteAttachment(String fileName, HttpServletRequest request) {
        String fileBaseDir = RequestContextUtils.findWebApplicationContext(request).getServletContext().getRealPath("/userfiles");
        File fileToDelete = new File(fileBaseDir + File.separator + fileName);
        if (fileToDelete.exists()) {
            try {
                fileToDelete.delete();
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException("删除文件失败", e);
            }
        }
    }

    public static List<String> convertToAttachmentUrlList(List<Attachment> attachmentList) {
        List<String> result = Lists.newLinkedList();
        if (attachmentList != null && !attachmentList.isEmpty()) {
            for (Attachment attachment : attachmentList) {
                result.add(attachment.getRelativeFilePath());
            }
        }
        return result;
    }

    public static List<Map<String, Object>> convertToAttachmentMapList(List<Attachment> attachmentList) {
        List<Map<String, Object>> result = Lists.newLinkedList();
        for (Attachment attachment : attachmentList) {
            Map<String, Object> tmp = Maps.newHashMap();
            tmp.put("caption", attachment.getOrigin());
            tmp.put("url", "/upload/delete");
            Map<String, Object> tmpInner = Maps.newHashMap();
            tmpInner.put("id", attachment.getId());
            tmp.put("extra", tmpInner);
            result.add(tmp);
        }
        return result;
    }

}
