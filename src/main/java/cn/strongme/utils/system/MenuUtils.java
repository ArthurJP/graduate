package cn.strongme.utils.system;

import cn.strongme.common.utils.SpringContextHolder;
import cn.strongme.common.utils.StringUtils;
import cn.strongme.entity.system.Menu;
import cn.strongme.entity.system.User;
import cn.strongme.service.system.MenuService;
import cn.strongme.utils.common.CacheUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * Created by 阿水 on 2017/9/19 下午2:57.
 */
public class MenuUtils {

    public static final String Menu_CACHE_LIST = "menuCacheList";
    public static final String Menu_CACHE_LIST_TREE = "menuCacheListTree";

    private static MenuService menuService = SpringContextHolder.getBean(MenuService.class);

    public static List<Menu> findList() {
        List<Menu> result = (List<Menu>) UserUtils.getCache(Menu_CACHE_LIST);
        if (result == null) {
            User currentUser = UserUtils.currentUser();
            if (currentUser == null) {
                return Lists.newArrayList();
            }
            if (currentUser.isAdmin()) {
                result = menuService.findList(new Menu());
            } else {
                Menu menu = new Menu();
                menu.setUserId(currentUser.getId());
                result = menuService.findListByUserId(menu);
            }
            UserUtils.putCache(Menu_CACHE_LIST, result);
        }
        return result;
    }

    public static List<Menu> findListInTreeStructre() {
        List<Menu> result = (List<Menu>) UserUtils.getCache(Menu_CACHE_LIST_TREE);
        if (result == null) {
            result = Lists.newLinkedList();
            List<Menu> allMenu = findList();
            for (Menu m : allMenu) {
                if (m == null) {
                    continue;
                }
                if ("1".equals(m.getParentId())) {
                    result.add(m);
                    recurMenus(m, allMenu);
                }
            }
            UserUtils.putCache(Menu_CACHE_LIST_TREE, result);
        }
        return result;
    }

    public static void clearCache() {
        CacheUtils.remove(Menu_CACHE_LIST);
        CacheUtils.remove(Menu_CACHE_LIST_TREE);
    }

    private static void recurMenus(Menu menu, List<Menu> allMenu) {
        List<Menu> subMenu = Lists.newLinkedList();
        List<Menu> restMenus = Lists.newLinkedList();
        for (Menu m : allMenu) {
            if (StringUtils.isNotBlank(m.getParentId()) && m.getParentId().equals(menu.getId())) {
                subMenu.add(m);
            } else {
                restMenus.add(m);
            }
        }
        menu.setChildren(subMenu);
        if (menu.getChildren() != null && !menu.getChildren().isEmpty()) {
            for (Menu m : subMenu) {
                recurMenus(m, restMenus);
            }
        }
    }

    public static List<Map<String, Object>> getTreeViewData(String defaultSelectedId, String defaultCheckedId) {
        return convertToMenuTreeViewData(findListInTreeStructre(), defaultSelectedId, defaultCheckedId);
    }

    public static List<Map<String, Object>> convertToMenuTreeViewData(List<Menu> data, String defaultSelectedId, String defaultCheckedId) {
        List<Map<String, Object>> result = Lists.newArrayList();
        if (data == null || data.isEmpty()) {
            return result;
        }
        for (Menu m : data) {
            Map<String, Object> mSingle = recurToMenuTreeViewData(m, defaultSelectedId, defaultCheckedId);
            if (mSingle == null) {
                continue;
            }
            result.add(mSingle);
        }
        return result;
    }

    private static Map<String, Object> recurToMenuTreeViewData(Menu menu, String defaultSelectedId, String defaultCheckedId) {
        Map<String, Object> result = Maps.newHashMap();
        if (menu == null || StringUtils.isBlank(menu.getId())) {
            return null;
        }
        result.put("id", menu.getId());
        result.put("parentId", menu.getParentId());
        result.put("text", menu.getName());
        result.put("isShow", menu.getIsShow());
        result.put("type", menu.getType());
        result.put("key", menu.getKey());
        if ("0".equals(menu.getIsShow())) {
            result.put("icon", "glyphicon glyphicon-eye-close");
        }
        if (StringUtils.isNotBlank(defaultSelectedId) && StringUtils.inString(menu.getId(), defaultSelectedId.split(","))) {
            Map<String, Object> tmp = (Map<String, Object>) result.get("state");
            if (tmp == null) {
                tmp = Maps.newHashMap();
            }
            tmp.put("selected", true);
            result.put("state", tmp);
        }
        if (StringUtils.isNotBlank(defaultCheckedId) && StringUtils.inString(menu.getId(), defaultCheckedId.split(","))) {
            Map<String, Object> tmp = (Map<String, Object>) result.get("state");
            if (tmp == null) {
                tmp = Maps.newHashMap();
            }
            tmp.put("checked", true);
            result.put("state", tmp);
        }
        if (menu.getChildren() != null && !menu.getChildren().isEmpty()) {
            List<Map<String, Object>> subList = Lists.newArrayList();
            for (Menu m : menu.getChildren()) {
                Map<String, Object> subSingle = recurToMenuTreeViewData(m, defaultSelectedId, defaultCheckedId);
                if (m == null) {
                    continue;
                }
                subList.add(subSingle);
            }
            result.put("nodes", subList);
        }
        return result;
    }

}
