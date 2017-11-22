package cn.strongme.utils.system;

import cn.strongme.common.utils.SpringContextHolder;
import cn.strongme.common.utils.StringUtils;
import cn.strongme.entity.system.Office;
import cn.strongme.service.system.OfficeService;
import cn.strongme.utils.common.CacheUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * Created by 阿水 on 2017/11/7 下午4:23.
 */
public class OfficeUtils {

    public static final String OFFICE_CACHE_LIST = "officeCacheList";
    public static final String OFFICE_CACHE_LIST_TREE = "officeCacheListTree";

    private static OfficeService officeService = SpringContextHolder.getBean(OfficeService.class);

    public static List<Office> findList() {
        List<Office> result = (List<Office>) CacheUtils.get(OFFICE_CACHE_LIST);
        if (result == null) {
            result = officeService.findList(new Office());
            CacheUtils.put(OFFICE_CACHE_LIST, result);
        }
        return result;
    }

    public static List<Office> findListInTreeStructre() {
        List<Office> result = (List<Office>) CacheUtils.get(OFFICE_CACHE_LIST_TREE);
        if (result == null) {
            result = Lists.newLinkedList();
            List<Office> allOffice = findList();
            for (Office m : allOffice) {
                if (m == null) {
                    continue;
                }
                if ("1".equals(m.getParentId())) {
                    result.add(m);
                    recurOffices(m, allOffice);
                }
            }
            UserUtils.putCache(OFFICE_CACHE_LIST_TREE, result);
        }
        return result;
    }

    public static void clearCache() {
        CacheUtils.remove(OFFICE_CACHE_LIST);
        CacheUtils.remove(OFFICE_CACHE_LIST_TREE);
    }

    private static void recurOffices(Office office, List<Office> allOffice) {
        List<Office> subOffice = Lists.newLinkedList();
        List<Office> restOffices = Lists.newLinkedList();
        for (Office m : allOffice) {
            if (StringUtils.isNotBlank(m.getParentId()) && m.getParentId().equals(office.getId())) {
                subOffice.add(m);
            } else {
                restOffices.add(m);
            }
        }
        office.setChildren(subOffice);
        if (office.getChildren() != null && !office.getChildren().isEmpty()) {
            for (Office m : subOffice) {
                recurOffices(m, restOffices);
            }
        }
    }


    public static List<Map<String, Object>> getTreeViewData(String defaultSelectedId, String defaultCheckedId) {
        return convertToOfficeTreeViewData(findListInTreeStructre(), defaultSelectedId, defaultCheckedId);
    }

    public static List<Map<String, Object>> convertToOfficeTreeViewData(List<Office> data, String defaultSelectedId, String defaultCheckedId) {
        List<Map<String, Object>> result = Lists.newArrayList();
        if (data == null || data.isEmpty()) {
            return result;
        }
        for (Office m : data) {
            Map<String, Object> mSingle = recurToOfficeTreeViewData(m, defaultSelectedId, defaultCheckedId);
            if (mSingle == null) {
                continue;
            }
            result.add(mSingle);
        }
        return result;
    }

    private static Map<String, Object> recurToOfficeTreeViewData(Office office, String defaultSelectedId, String defaultCheckedId) {
        Map<String, Object> result = Maps.newHashMap();
        if (office == null || StringUtils.isBlank(office.getId())) {
            return null;
        }
        result.put("id", office.getId());
        result.put("parentId", office.getParentId());
        result.put("text", office.getName());

        if (StringUtils.isNotBlank(defaultSelectedId) && StringUtils.inString(office.getId(), defaultSelectedId.split(","))) {
            Map<String, Object> tmp = (Map<String, Object>) result.get("state");
            if (tmp == null) {
                tmp = Maps.newHashMap();
            }
            tmp.put("selected", true);
            result.put("state", tmp);
        }
        if (StringUtils.isNotBlank(defaultCheckedId) && StringUtils.inString(office.getId(), defaultCheckedId.split(","))) {
            Map<String, Object> tmp = (Map<String, Object>) result.get("state");
            if (tmp == null) {
                tmp = Maps.newHashMap();
            }
            tmp.put("checked", true);
            result.put("state", tmp);
        }
        if (office.getChildren() != null && !office.getChildren().isEmpty()) {
            List<Map<String, Object>> subList = Lists.newArrayList();
            for (Office m : office.getChildren()) {
                Map<String, Object> subSingle = recurToOfficeTreeViewData(m, defaultSelectedId, defaultCheckedId);
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
