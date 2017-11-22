package cn.strongme.dao.system;

import cn.strongme.entity.system.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * Created by 阿水 on 2017/9/19 下午4:06.
 */
@Mapper
@Repository
public interface RoleDao extends MySqlMapper<Role> {

    Role get(Role t);

    List<Role> findList(Role t);

    int insert(Role t);

    int update(Role t);

    int delete(Role t);

    int deleteRoleMenu(Role role);

    int insertRoleMenu(Role role);

    int inUsing(Role role);

    Role getByEname(Role role);

    Role getByName(Role role);
}
