package cn.strongme.service.system;

import cn.strongme.dao.system.DictComplexDao;
import cn.strongme.entity.common.TreeEntity;
import cn.strongme.entity.system.DictComplex;
import cn.strongme.exception.ServiceException;
import cn.strongme.service.common.TreeService;
import cn.strongme.utils.common.CacheUtils;
import cn.strongme.utils.common.Reflections;
import cn.strongme.utils.system.DictComplexUtils;
import org.apache.commons.lang3.StringUtils;
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
public class DictComplexService extends TreeService<DictComplexDao, DictComplex> {

    public DictComplex get(DictComplex dictComplex) {
        return this.dao.get(dictComplex);
    }

    public List<String> findTypeList() {
        return dao.findTypeList(new DictComplex());
    }

    @Override
    public List<DictComplex> findList(DictComplex dictComplex) {
        return this.dao.findList(dictComplex);
    }

    @Cacheable(value = CacheUtils.SYS_CACHE, key = DictComplexUtils.DICT_COMPLEX_CACHE_LIST)
    public List<DictComplex> findListForUtils(DictComplex dictComplex) {
        return this.dao.findList(dictComplex);
    }

    @CacheEvict(value = CacheUtils.SYS_CACHE, key = DictComplexUtils.DICT_COMPLEX_CACHE_LIST)
    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public void save(DictComplex dictComplex) {

        DictComplex p = this.dao.get((DictComplex) Reflections.getFieldValue(dictComplex.getParent(), "parent"));
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
            this.dao.save(dictComplex);
        } else {
            dictComplex.preUpdate();
            this.dao.update(dictComplex);
        }

        // 更新子节点 parentIds
        DictComplex m = new DictComplex();
        m.setParentIds("%," + dictComplex.getId() + ",%");
        List<DictComplex> list = dao.findByParentIdsLike(m);
        for (DictComplex e : list) {
            e.setParentIds(e.getParentIds().replace(oldParentIds, dictComplex.getParentIds()));
            dao.updateParentIds(e);
        }
        DictComplexUtils.clearCache();
    }

    @CacheEvict(value = CacheUtils.SYS_CACHE, key = DictComplexUtils.DICT_COMPLEX_CACHE_LIST)
    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public void delete(DictComplex dictComplex) {
        this.dao.delete(dictComplex);
    }

}
