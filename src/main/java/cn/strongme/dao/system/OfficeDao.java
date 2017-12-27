package cn.strongme.dao.system;

import cn.strongme.dao.common.TreeMapper;
import cn.strongme.entity.system.Office;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 阿水 on 2017/11/7 下午3:18.
 */
@Mapper
@Repository
public interface OfficeDao extends TreeMapper<Office> {

    List<Office> findByParentIdsLike(Office office);

    int updateParentIds(Office office);

    int inUsing(Office office);


}
