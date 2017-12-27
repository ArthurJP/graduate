package cn.strongme.dao.system;

import cn.strongme.dao.common.BaseMapper;
import cn.strongme.entity.system.Dict;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 阿水 on 2017/9/5 下午10:54.
 */
@Mapper
@Repository
public interface DictDao extends BaseMapper<Dict> {

    List<String> findTypeList(Dict dict);

}
