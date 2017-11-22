package cn.strongme.service.system;

import cn.strongme.dao.system.AttachmentDao;
import cn.strongme.entity.system.Attachment;
import cn.strongme.exception.ServiceException;
import cn.strongme.utils.system.AttachmentUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by 阿水 on 2017/9/23 下午1:59.
 */
@Service
@Transactional(readOnly = true, rollbackFor = ServiceException.class)
public class AttachmentService {

    @Autowired
    private AttachmentDao attachmentDao;

    public PageInfo<Attachment> findListPage(Attachment attachment) {
        if (attachment.getPage() != null && attachment.getRows() != null) {
            PageHelper.startPage(attachment.getPage(), attachment.getRows());
        }
        return new PageInfo<>(this.attachmentDao.findList(attachment));
    }

    public Attachment get(Attachment attachment) {
        return this.attachmentDao.get(attachment);
    }

    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public void batchInsert(List<Attachment> list) {
        this.attachmentDao.batchInsert(list);
    }

    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public void delete(Attachment attachment, HttpServletRequest request) {
        this.attachmentDao.delete(attachment);
        AttachmentUtils.deleteAttachment(attachment.getRelativeFilePath(), request);
    }
}
