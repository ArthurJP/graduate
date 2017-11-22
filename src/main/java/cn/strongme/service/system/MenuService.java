package cn.strongme.service.system;

import cn.strongme.dao.system.MenuDao;
import cn.strongme.entity.system.Menu;
import cn.strongme.exception.ServiceException;
import cn.strongme.service.common.BaseService;
import cn.strongme.utils.system.MenuUtils;
import cn.strongme.utils.system.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 阿水 on 2017/9/19 下午2:56.
 */
@Service
@Transactional(readOnly = true, rollbackFor = ServiceException.class)
public class MenuService extends BaseService<Menu> {

    @Autowired
    private MenuDao menuDao;

    public Menu get(Menu menu) {
        return this.menuDao.get(menu);
    }

    @Override
    public List<Menu> findList(Menu menu) {
        return this.menuDao.findList(menu);
    }

    public List<Menu> findListByUserId(Menu menu) {
        return this.menuDao.findListByUserId(menu);
    }

    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public void save(Menu menu) {

        // 获取父节点实体
        Menu p = this.menuDao.get(menu.getParent());
        if (p == null) {
            p = new Menu(Menu.getRootId());
            p.setParentIds("");
        }
        menu.setParent(p);

        // 获取修改前的parentIds，用于更新子节点的parentIds
        String oldParentIds = menu.getParentIds();
        if (StringUtils.isBlank(oldParentIds)) {
            oldParentIds = "";
        }
        // 设置新的父节点串
        menu.setParentIds(menu.getParent().getParentIds() + menu.getParent().getId() + ",");

        if (StringUtils.isBlank(menu.getId())) {
            menu.preInsert();
            this.menuDao.insert(menu);
        } else {
            menu.preUpdate();
            this.menuDao.update(menu);
        }

        // 更新子节点 parentIds
        Menu m = new Menu();
        m.setParentIds("%," + menu.getId() + ",%");
        List<Menu> list = menuDao.findByParentIdsLike(m);
        for (Menu e : list) {
            e.setParentIds(e.getParentIds().replace(oldParentIds, menu.getParentIds()));
            menuDao.updateParentIds(e);
        }
        UserUtils.clearCache();
        MenuUtils.clearCache();
    }

    @Transactional(readOnly = false)
    public void updateMenuSort(Menu menu) {
        menuDao.updateSort(menu);
        UserUtils.clearCache();
        MenuUtils.clearCache();
    }

    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public void delete(Menu menu) {
        if (menuDao.inUsing(menu) > 0) {
            throw new ServiceException("使用中的菜单，无法删除");
        }
        menuDao.delete(menu);
        UserUtils.clearCache();
        MenuUtils.clearCache();
    }

}
