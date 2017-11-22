package cn.strongme.dao.system;

import cn.strongme.entity.system.Dict;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * Created by 阿水 on 2017/9/5 下午10:54.
 */
@Mapper
@Repository
public interface DictDao extends MySqlMapper<Dict> {

    int insert(Dict dict);

    int update(Dict dict);

    int delete(Dict dict);

    Dict get(Dict dict);

    List<Dict> findList(Dict dict);

    List<String> findTypeList(Dict dict);

}
