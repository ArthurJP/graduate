package cn.strongme.dao.system;

import cn.strongme.entity.system.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * Created by 阿水 on 2017/9/19 下午2:54.
 */
@Mapper
@Repository
public interface MenuDao extends MySqlMapper<Menu> {

    Menu get(Menu t);

    List<Menu> findList(Menu t);

    int insert(Menu t);

    int update(Menu t);

    int delete(Menu t);

    List<Menu> findListByUserId(Menu menu);

    List<Menu> findByParentIdsLike(Menu menu);

    int updateParentIds(Menu menu);

    int inUsing(Menu menu);

    int updateSort(Menu menu);

}
