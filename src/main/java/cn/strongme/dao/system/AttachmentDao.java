package cn.strongme.dao.system;

import cn.strongme.dao.common.BaseMapper;
import cn.strongme.entity.common.AttachmentableEntity;
import cn.strongme.entity.system.Attachment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 阿水 on 2017/9/23 下午1:54.
 */
@Mapper
@Repository
public interface AttachmentDao extends BaseMapper<Attachment> {


    int batchInsert(List<Attachment> list);

    int insertBusinessAttachment(AttachmentableEntity entity);

}
