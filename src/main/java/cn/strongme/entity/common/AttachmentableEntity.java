package cn.strongme.entity.common;

import cn.strongme.common.utils.JsonMapper;
import cn.strongme.entity.system.Attachment;
import cn.strongme.utils.system.AttachmentUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

/**
 * @author 阿水
 * @date 2017/9/24 下午10:12
 */
public class AttachmentableEntity extends BaseEntity {

    private List<Attachment> attachmentList = Lists.newArrayList();
    private List<Attachment> attachmentListTmp = Lists.newArrayList();

    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }

    @JsonIgnore
    public List<Attachment> getAttachmentListTmp() {
        return attachmentListTmp;
    }

    public void setAttachmentListTmp(List<Attachment> attachmentListTmp) {
        this.attachmentListTmp = attachmentListTmp;
    }

    @JsonIgnore
    public List<String> getAttachmentUrlList() {
        return AttachmentUtils.convertToAttachmentUrlList(this.attachmentList);
    }

    @JsonIgnore
    public String getAttachmentUrlListInJsonFormat() {
        return JsonMapper.toJsonString(getAttachmentUrlList());
    }

    @JsonIgnore
    public List<Map<String, Object>> getAttachmentMapList() {
        return AttachmentUtils.convertToAttachmentMapList(this.attachmentList);
    }

    @JsonIgnore
    public String getAttachmentMapListInJsonFormat() {
        return JsonMapper.toJsonString(getAttachmentMapList());
    }


}
