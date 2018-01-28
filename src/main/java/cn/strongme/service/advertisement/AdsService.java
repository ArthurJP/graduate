package cn.strongme.service.advertisement;

import cn.strongme.dao.advertisement.AdsDao;
import cn.strongme.entity.advertisement.Ads;
import cn.strongme.service.common.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by 张俊鹏 on 2018/1/23.
 *
 */
@Service
@Transactional
public class AdsService extends BaseService<AdsDao,Ads> {
    @Resource
    private AdsDao dao;

    @Transactional(readOnly = false)
    public int deleteByPrimaryKey(String id){
        return dao.deleteByPrimaryKey( id );
    }

}
