package cn.strongme.dao.system;

import cn.strongme.dao.common.BaseMapper;
import cn.strongme.entity.system.Log;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Created by 阿水 on 2017/11/8 上午8:42.
 */
@Mapper
@Repository
public interface LogDao extends BaseMapper<Log> {


}
