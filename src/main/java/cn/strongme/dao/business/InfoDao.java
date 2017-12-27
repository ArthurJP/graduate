package cn.strongme.dao.business;

import cn.strongme.dao.common.BaseMapper;
import cn.strongme.entity.business.Info;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 阿水 on 2017/9/21 下午1:54.
 */
@Mapper
@Repository
public interface InfoDao extends BaseMapper<Info> {

    List<Info> findListByType(Info t);

    Info findListByMaxDate(Info info);
}
