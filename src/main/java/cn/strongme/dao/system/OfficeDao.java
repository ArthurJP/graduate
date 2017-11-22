package cn.strongme.dao.system;

import cn.strongme.entity.system.Office;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * Created by 阿水 on 2017/11/7 下午3:18.
 */
@Mapper
@Repository
public interface OfficeDao extends MySqlMapper<Office> {

    Office get(Office t);

    List<Office> findList(Office t);

    int insert(Office t);

    int update(Office t);

    int delete(Office t);

    List<Office> findByParentIdsLike(Office office);

    int updateParentIds(Office office);

    int inUsing(Office office);


}
