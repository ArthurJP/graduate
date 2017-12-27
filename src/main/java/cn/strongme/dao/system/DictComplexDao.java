package cn.strongme.dao.system;

import cn.strongme.dao.common.TreeMapper;
import cn.strongme.entity.system.DictComplex;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 阿水 on 2017/11/8 下午2:20.
 */
@Mapper
@Repository
public interface DictComplexDao extends TreeMapper<DictComplex> {

    List<String> findTypeList(DictComplex dictComplex);

    List<DictComplex> findByParentIdsLike(DictComplex dictComplex);

    int updateParentIds(DictComplex dictComplex);

}
