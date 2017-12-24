package cn.strongme.dao.system;


import cn.strongme.entity.system.WxUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * Created by Walter on 16/9/20.
 */
@Mapper
@Repository
public interface WxUserDao extends MySqlMapper<WxUser> {

    int exist(WxUser wxUser);

    WxUser getByOpenId(WxUser wxUser);

    WxUser get(WxUser t);

    List<WxUser> findList(WxUser t);

    int insert(WxUser t);

    int update(WxUser t);

    /**
     * 更新微信用户的关注状态
     *
     * @param wxUser
     * @return
     */
    int updateSubscribeStatus(WxUser wxUser);

}
