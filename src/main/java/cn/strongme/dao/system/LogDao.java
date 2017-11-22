package cn.strongme.dao.system;

import cn.strongme.entity.system.Log;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 阿水 on 2017/11/8 上午8:42.
 */
@Mapper
@Repository
public interface LogDao {

    int insert(Log log);

    List<Log> findList(Log log);


}
