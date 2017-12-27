package cn.strongme.dao.system;

import cn.strongme.dao.common.BaseMapper;
import cn.strongme.entity.system.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Created by 阿水 on 2017/9/19 下午4:06.
 */
@Mapper
@Repository
public interface RoleDao extends BaseMapper<Role> {

    int deleteRoleMenu(Role role);

    int insertRoleMenu(Role role);

    int inUsing(Role role);

    Role getByEname(Role role);

    Role getByName(Role role);
}
