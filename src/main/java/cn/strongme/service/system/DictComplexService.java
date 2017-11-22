package cn.strongme.service.system;

import cn.strongme.dao.system.DictComplexDao;
import cn.strongme.entity.common.TreeEntity;
import cn.strongme.entity.system.DictComplex;
import cn.strongme.exception.ServiceException;
import cn.strongme.service.common.BaseService;
import cn.strongme.utils.common.CacheUtils;
import cn.strongme.utils.system.DictComplexUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 阿水 on 2017/11/8 下午2:31.
 */
@Service
@Transactional(readOnly = true, rollbackFor = ServiceException.class)
public class DictComplexService extends BaseService<DictComplex> {

    @Autowired
    private DictComplexDao dictComplexDao;

    public DictComplex get(DictComplex dictComplex) {
        return this.dictComplexDao.get(dictComplex);
    }

    public List<String> findTypeList() {
        return dictComplexDao.findTypeList(new DictComplex());
    }

    @Override
    public List<DictComplex> findList(DictComplex dictComplex) {
        return this.dictComplexDao.findList(dictComplex);
    }

    @Cacheable(value = CacheUtils.SYS_CACHE, key = DictComplexUtils.DICT_COMPLEX_CACHE_LIST)
    public List<DictComplex> findListForUtils(DictComplex dictComplex) {
        return this.dictComplexDao.findList(dictComplex);
    }

    @CacheEvict(value = CacheUtils.SYS_CACHE, key = DictComplexUtils.DICT_COMPLEX_CACHE_LIST)
    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public void save(DictComplex dictComplex) {

        DictComplex p = this.dictComplexDao.get(dictComplex.getParent());
        if (p == null) {
            p = new DictComplex(TreeEntity.getRootId());
            p.setParentIds("");
        }
        dictComplex.setParent(p);

        // 获取修改前的parentIds，用于更新子节点的parentIds
        String oldParentIds = dictComplex.getParentIds();
        if (StringUtils.isBlank(oldParentIds)) {
            oldParentIds = "";
        }
        // 设置新的父节点串
        dictComplex.setParentIds(dictComplex.getParent().getParentIds() + dictComplex.getParent().getId() + ",");

        if (StringUtils.isBlank(dictComplex.getId())) {
            dictComplex.preInsert();
            this.dictComplexDao.insert(dictComplex);
        } else {
            dictComplex.preUpdate();
            this.dictComplexDao.update(dictComplex);
        }

        // 更新子节点 parentIds
        DictComplex m = new DictComplex();
        m.setParentIds("%," + dictComplex.getId() + ",%");
        List<DictComplex> list = dictComplexDao.findByParentIdsLike(m);
        for (DictComplex e : list) {
            e.setParentIds(e.getParentIds().replace(oldParentIds, dictComplex.getParentIds()));
            dictComplexDao.updateParentIds(e);
        }
        DictComplexUtils.clearCache();
    }

    @CacheEvict(value = CacheUtils.SYS_CACHE, key = DictComplexUtils.DICT_COMPLEX_CACHE_LIST)
    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public void delete(DictComplex dictComplex) {
        this.dictComplexDao.delete(dictComplex);
    }

}
