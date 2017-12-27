package cn.strongme.service.system;

import cn.strongme.dao.system.OfficeDao;
import cn.strongme.entity.system.Office;
import cn.strongme.exception.ServiceException;
import cn.strongme.service.common.TreeService;
import cn.strongme.utils.system.OfficeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 阿水
 * @date 2017/11/7 下午3:19
 */
@Service
@Transactional(readOnly = true, rollbackFor = ServiceException.class)
public class OfficeService extends TreeService<OfficeDao, Office> {


    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public void save(Office office) {

        // 获取父节点实体
        Office p = this.dao.get(office.getParent());
        if (p == null) {
            p = new Office(Office.getRootId());
            p.setParentIds("");
        }
        office.setParent(p);

        // 获取修改前的parentIds，用于更新子节点的parentIds
        String oldParentIds = office.getParentIds();
        if (StringUtils.isBlank(oldParentIds)) {
            oldParentIds = "";
        }
        // 设置新的父节点串
        office.setParentIds(office.getParent().getParentIds() + office.getParent().getId() + ",");

        if (StringUtils.isBlank(office.getId())) {
            office.preInsert();
            this.dao.save(office);
        } else {
            office.preUpdate();
            this.dao.update(office);
        }

        // 更新子节点 parentIds
        Office m = new Office();
        m.setParentIds("%," + office.getId() + ",%");
        List<Office> list = dao.findByParentIdsLike(m);
        for (Office e : list) {
            e.setParentIds(e.getParentIds().replace(oldParentIds, office.getParentIds()));
            dao.updateParentIds(e);
        }
        OfficeUtils.clearCache();
    }

    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public void delete(Office office) {
        if (dao.inUsing(office) > 0) {
            throw new ServiceException("使用中的部门，无法删除");
        }
        dao.delete(office);
        OfficeUtils.clearCache();
    }


}
