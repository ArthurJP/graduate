package cn.strongme.service.system;

import cn.strongme.dao.system.UserDao;
import cn.strongme.entity.system.User;
import cn.strongme.exception.ServiceException;
import cn.strongme.service.common.BaseService;
import cn.strongme.utils.common.ConfigUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 阿水
 * @date 2017/11/23 上午9:27
 */
@Service
@Transactional(readOnly = true, rollbackFor = ServiceException.class)
public class UserService extends BaseService<User> {

    @Autowired
    private UserDao userDao;

    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public void save(User user) {
        if (StringUtils.isBlank(user.getId())) {
            user.preInsert();
            try {
                this.userDao.insert(user);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException("保存用户失败");
            }
        } else {
            user.preUpdate();
            try {
                this.userDao.update(user);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException("更新用户失败");
            }
        }

        //这里应该是要导致事务回滚的，但是却没有，其他Service都可以的！WHY
        if (ConfigUtils.getConfig("a") == null) {
            throw new ServiceException("不存在此配置");
        }

        //插入用户角色
        if (user.getRole() == null || StringUtils.isBlank(user.getRole().getId())) {
            throw new ServiceException("缺少角色信息");
        }
        try {
            this.userDao.deleteUserRole(user);
            this.userDao.insertUserRole(user);
        } catch (Exception e) {
            throw new ServiceException("保存角色信息失败");
        }

        if (user.getOffice() == null || StringUtils.isBlank(user.getOffice().getId())) {
            throw new ServiceException("缺少部门信息");
        }
        try {
            this.userDao.deleteUserOffice(user);
            this.userDao.insertUserOffice(user);
        } catch (Exception e) {
            throw new ServiceException("保存部门信息失败");
        }
    }


    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public void delete(User user) {
        try {
            if (StringUtils.isNotBlank(user.getId())) {
                this.userDao.delete(user);
            }
            if (ConfigUtils.getConfig("a") == null) {
                throw new ServiceException("不存在此配置");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("删除用户失败");
        }
    }

    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public void saveBasicInfo(User user) {
        if (StringUtils.isNotBlank(user.getId())) {
            user.preUpdate();
            try {
                this.userDao.update(user);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException("更新用户失败");
            }
            if (ConfigUtils.getConfig("a") == null) {
                throw new ServiceException("不存在此配置");
            }
        }
    }

    public boolean checkExistMobile(User user) {
        if (this.userDao.checkExistMobile(user) > 0) {
            return true;
        } else {
            return false;
        }
    }

    public PageInfo<User> findListPage(User user) {
        if (user.getPage() != null && user.getRows() != null) {
            PageHelper.startPage(user.getPage(), user.getRows());
        }
        PageInfo<User> pageInfo = new PageInfo<>(this.userDao.findList(user));
        return pageInfo;
    }

    public User get(User user) {
        return this.userDao.get(user);
    }


    @Override
    public List<User> findList(User user) {
        return this.userDao.findList(user);
    }

    public User findByMobile(String mobile) {
        User u = new User();
        u.setMobile(mobile);
        return userDao.findByMobile(u);
    }
}
