package cn.strongme.dao.system;

import cn.strongme.entity.system.DictComplex;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * Created by 阿水 on 2017/11/8 下午2:20.
 */
@Mapper
@Repository
public interface DictComplexDao extends MySqlMapper<DictComplex> {

    int insert(DictComplex dictComplex);

    int update(DictComplex dictComplex);

    int delete(DictComplex dictComplex);

    DictComplex get(DictComplex dictComplex);

    List<DictComplex> findList(DictComplex dictComplex);

    List<String> findTypeList(DictComplex dictComplex);

    List<DictComplex> findByParentIdsLike(DictComplex dictComplex);

    int updateParentIds(DictComplex dictComplex);

}
