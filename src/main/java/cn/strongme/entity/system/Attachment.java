package cn.strongme.entity.system;

import cn.strongme.entity.common.BaseEntity;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by 阿水 on 2017/8/15 上午9:12.
 * 附件
 */
public class Attachment extends BaseEntity<Attachment> {

    private static final long serialVersionUID = -687711127354852766L;
    private String origin;
    private String subfix;
    private String relativePath;

    public Attachment() {
    }

    public Attachment(String id) {
        this.id = id;
    }


    public Attachment(String origin, String subfix, String relativePath) {
        this.origin = origin;
        this.subfix = subfix;
        this.relativePath = relativePath;
    }

    public String getSubfix() {
        return subfix;
    }

    public void setSubfix(String subfix) {
        this.subfix = subfix;
    }

    public String getFileName() {
        return this.id + "." + this.subfix;
    }

    public String getRelativeFilePath() {
        return this.relativePath + "/" + this.id + "." + this.subfix;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public static Map<String, Object> convertAttachmentListToWangEditorNeeds(List<Attachment> attachmentList, HttpServletRequest request) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("errno", 0);
        String contextPath = request.getContextPath();
        List<String> data = Lists.newArrayList();
        for (Attachment a : attachmentList) {
            data.add(contextPath + "/userfiles/" + a.getRelativeFilePath());
        }
        result.put("data", data);
        return result;
    }
}
