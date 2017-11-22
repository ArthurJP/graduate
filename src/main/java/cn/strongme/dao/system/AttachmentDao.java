package cn.strongme.dao.system;

import cn.strongme.entity.common.AttachmentableEntity;
import cn.strongme.entity.system.Attachment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * Created by 阿水 on 2017/9/23 下午1:54.
 */
@Mapper
@Repository
public interface AttachmentDao extends MySqlMapper<Attachment> {

    List<Attachment> findList(Attachment attachment);

    int batchInsert(List<Attachment> list);

    int delete(Attachment attachment);

    Attachment get(Attachment attachment);

    int insertBusinessAttachment(AttachmentableEntity entity);

}
