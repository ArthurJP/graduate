package cn.strongme.service.system;

import cn.strongme.dao.system.UserDao;
import cn.strongme.entity.system.User;
import cn.strongme.exception.ServiceException;
import cn.strongme.service.common.BaseService;
import cn.strongme.utils.common.ConfigUtils;
import cn.strongme.utils.system.UserUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 阿水 on 2017/7/14 上午9:04.
 */
@Service
@Transactional(readOnly = true, rollbackFor = ServiceException.class)
public class UserService extends BaseService<User> {

    @Autowired
    private UserDao userDao;

    public User get(User user) {
        return this.userDao.get(user);
    }

    @Override
    public List<User> findList(User user) {
        return this.userDao.findList(user);
    }

    public PageInfo<User> findListPage(User user) {
        if (user.getPage() != null && user.getRows() != null) {
            PageHelper.startPage(user.getPage(), user.getRows());
        }
        PageInfo<User> pageInfo = new PageInfo<>(this.userDao.findList(user));
        return pageInfo;
    }

    public User findByMobile(String mobile) {
        return UserUtils.getByMobile(mobile);
    }

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
    public void saveBasicInfo(User user) {
        if (StringUtils.isNotBlank(user.getId())) {
            user.preUpdate();
            try {
                this.userDao.update(user);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException("更新用户失败");
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

}
