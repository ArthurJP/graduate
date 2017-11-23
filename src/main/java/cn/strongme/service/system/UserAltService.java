package cn.strongme.service.system;

import cn.strongme.dao.system.UserDao;
import cn.strongme.entity.system.User;
import cn.strongme.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 阿水
 * @date 2017/11/23 上午9:17
 */
@Service
@Transactional(readOnly = true, rollbackFor = ServiceException.class)
public class UserAltService {

    @Autowired
    private UserDao userDao;

    public User findByMobile(String mobile) {
        User u = new User();
        u.setMobile(mobile);
        return userDao.findByMobile(u);
    }

}
