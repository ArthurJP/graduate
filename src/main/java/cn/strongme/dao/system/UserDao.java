package cn.strongme.dao.system;

import cn.strongme.entity.system.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * @author 阿水
 * @date 2017/7/14 上午8:59
 */
@Mapper
@Repository
public interface UserDao extends MySqlMapper<User> {

    User get(User t);

    List<User> findList(User t);

    int insert(User t);

    int update(User t);

    int delete(User t);

    User findByMobile(User user);

    int insertUserRole(User user);

    int deleteUserRole(User user);

    int insertUserOffice(User user);

    int deleteUserOffice(User user);

    int checkExistMobile(User user);

}
