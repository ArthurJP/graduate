package cn.strongme.dao.system;

import cn.strongme.dao.common.BaseMapper;
import cn.strongme.entity.system.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 阿水
 * @date 2017/7/14 上午8:59
 */
@Mapper
@Repository
public interface UserDao extends BaseMapper<User> {

    User findByMobile(User user);

    int insertUserRole(User user);

    int deleteUserRole(User user);

    int insertUserOffice(User user);

    int deleteUserOffice(User user);

    int checkExistMobile(User user);

}
