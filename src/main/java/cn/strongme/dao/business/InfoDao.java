package cn.strongme.dao.business;

import cn.strongme.entity.business.Info;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * Created by 阿水 on 2017/9/21 下午1:54.
 */
@Mapper
@Repository
public interface InfoDao extends MySqlMapper<Info> {

    Info get(Info t);

    List<Info> findList(Info t);

    List<Info> findListByType(Info t);

    int insert(Info t);

    int update(Info t);

    int delete(Info t);

    Info findListByMaxDate(Info info);
}
