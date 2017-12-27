package cn.strongme.service.system;

import cn.strongme.dao.system.DictDao;
import cn.strongme.entity.system.Dict;
import cn.strongme.exception.ServiceException;
import cn.strongme.service.common.BaseService;
import cn.strongme.utils.common.CacheUtils;
import cn.strongme.utils.system.DictUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 阿水 on 2017/9/5 下午10:56.
 *
 * @author Walter
 */
@Service
@Transactional(readOnly = true, rollbackFor = ServiceException.class)
public class DictService extends BaseService<DictDao, Dict> {

    public List<String> findTypeList() {
        return dao.findTypeList(new Dict());
    }

    public PageInfo<Dict> findListPage(Dict dict) {
        if (dict.getPage() != null && dict.getRows() != null) {
            PageHelper.startPage(dict.getPage(), dict.getRows());
        }
        PageInfo<Dict> pageInfo = new PageInfo<>(this.dao.findList(dict));
        return pageInfo;
    }

    @Cacheable(value = CacheUtils.SYS_CACHE, key = DictUtils.DICT_CACHE_LIST)
    public List<Dict> findListForUtils(Dict dict) {
        return this.dao.findList(dict);
    }

    @CacheEvict(value = CacheUtils.SYS_CACHE, key = DictUtils.DICT_CACHE_LIST)
    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public void save(Dict dict) {
        if (StringUtils.isBlank(dict.getId())) {
            dict.preInsert();
            this.dao.insert(dict);
        } else {
            dict.preUpdate();
            this.dao.update(dict);
        }
    }

    @CacheEvict(value = CacheUtils.SYS_CACHE, key = DictUtils.DICT_CACHE_LIST)
    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public void delete(Dict dict) {
        this.dao.delete(dict);
    }


}
