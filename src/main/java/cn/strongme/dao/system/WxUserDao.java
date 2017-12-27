package cn.strongme.dao.system;


import cn.strongme.dao.common.BaseMapper;
import cn.strongme.entity.system.WxUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Created by Walter on 16/9/20.
 */
@Mapper
@Repository
public interface WxUserDao extends BaseMapper<WxUser> {

    int exist(WxUser wxUser);

    WxUser getByOpenId(WxUser wxUser);

    /**
     * 更新微信用户的关注状态
     *
     * @param wxUser
     * @return
     */
    int updateSubscribeStatus(WxUser wxUser);

}
