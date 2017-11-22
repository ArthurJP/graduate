package cn.strongme.entity.system;

import cn.strongme.entity.common.TreeEntity;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * Created by 阿水 on 2017/9/19 下午2:52.
 */
public class Menu extends TreeEntity<Menu> {

    private String url;
    private String key;
    private String type;
    private String icon;
    private int sort;
    private String isShow;    // 是否在菜单中显示（1：显示；0：不显示）
    private String permission; // 权限标识

    private String userId;

    public Menu() {
    }

    public Menu(String id) {
        super(id);
    }

    public Menu(String id, String name, String url, String key, String type, String icon, int sort, Date createdate, Date updateDate) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.key = key;
        this.type = type;
        this.icon = icon;
        this.sort = sort;
        this.createDate = createdate;
        this.updateDate = updateDate;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean containThisMenuKey(String key) {
        boolean result = false;
        if (StringUtils.isBlank(key)) {
            return result;
        }
        for (Menu m : children) {
            if (key.equals(m.key)) {
                result = true;
                break;
            }
        }
        return result;
    }


}
