package cn.strongme.entity.system;

import cn.strongme.entity.common.BaseEntity;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by 阿水 on 2017/9/19 下午4:04.
 */
public class Role extends BaseEntity<Role> {

    public static final String ADMIN = "ADMIN";
    public static final String NORMAL = "NORMAL";
    private static final long serialVersionUID = -236031557056704082L;

    private String name;
    private String ename;    // 英文名称

    private String oldName;    // 原角色名称
    private String oldEname;    // 原英文名称

    private List<Menu> menuList = Lists.newArrayList();

    private User user;//根据用户ID查询角色列表

    public Role() {
    }

    public Role(String id) {
        super(id);
    }

    public Role(String id, String name, Date createDate, Date updateDate) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Role(String name, String ename) {
        this.name = name;
        this.ename = ename;
    }

    public Role(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getOldEname() {
        return oldEname;
    }

    public void setOldEname(String oldEname) {
        this.oldEname = oldEname;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public List<String> getMenuIdList() {
        List<String> menuIdList = Lists.newArrayList();
        for (Menu menu : menuList) {
            menuIdList.add(menu.getId());
        }
        return menuIdList;
    }

    public void setMenuIdList(List<String> menuIdList) {
        menuList = Lists.newArrayList();
        for (String menuId : menuIdList) {
            Menu menu = new Menu();
            menu.setId(menuId);
            menuList.add(menu);
        }
    }

    public String getMenuIds() {
        return StringUtils.join(getMenuIdList(), ",");
    }

    public void setMenuIds(String menuIds) {
        menuList = Lists.newArrayList();
        if (menuIds != null) {
            String[] ids = StringUtils.split(menuIds, ",");
            setMenuIdList(Lists.newArrayList(ids));
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}