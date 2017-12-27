package cn.strongme.service.system;

import cn.strongme.dao.system.WxUserDao;
import cn.strongme.entity.system.WxUser;
import cn.strongme.service.common.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Walter on 16/9/20.
 */
@Service
@Transactional(readOnly = true)
public class WxUserService extends BaseService<WxUserDao, WxUser> {

    @Autowired
    private WxUserDao wxUserDao;

    /**
     * 根据微信openId坚持是否存在此微信用户
     *
     * @param wxUser
     * @return
     */
    public boolean exist(WxUser wxUser) {
        if (this.wxUserDao.exist(wxUser) > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 更新微信用户的关注状态
     *
     * @param wxUser
     * @return
     */
    @Transactional(readOnly = false)
    public int updateSubscribeStatus(WxUser wxUser) {
        return this.wxUserDao.updateSubscribeStatus(wxUser);
    }

    /**
     * 根据微信openid获取微信用户信息
     *
     * @param wxUser
     * @return
     */
    public WxUser getByOpenId(WxUser wxUser) {
        return this.wxUserDao.getByOpenId(wxUser);
    }

    @Override
    public List<WxUser> findList(WxUser wxUser) {
        return this.wxUserDao.findList(wxUser);
    }

}
