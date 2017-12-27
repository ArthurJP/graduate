package cn.strongme.service.system;

import cn.strongme.common.utils.StringUtils;
import cn.strongme.dao.system.RoleDao;
import cn.strongme.entity.system.Role;
import cn.strongme.exception.ServiceException;
import cn.strongme.service.common.BaseService;
import cn.strongme.utils.system.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 阿水
 * @date 2017/9/19 下午4:07
 */
@Service
@Transactional(readOnly = true, rollbackFor = ServiceException.class)
public class RoleService extends BaseService<RoleDao, Role> {


    public Role getByEname(Role role) {
        return this.dao.getByEname(role);
    }

    public Role getByName(Role role) {
        return this.dao.getByName(role);
    }

    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public void save(Role role) {
        if (StringUtils.isBlank(role.getId())) {
            role.preInsert();
            dao.insert(role);
        } else {
            role.preUpdate();
            dao.update(role);
        }
        // 更新角色与菜单关联
        dao.deleteRoleMenu(role);
        if (role.getMenuList().size() > 0) {
            dao.insertRoleMenu(role);
        }
        // 清除用户角色缓存
        UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
    }

    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public void delete(Role role) {
        if (this.dao.inUsing(role) > 0) {
            throw new ServiceException("使用中的角色，无法删除");
        }
        dao.delete(role);
        // 清除用户角色缓存
        UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
    }

}
