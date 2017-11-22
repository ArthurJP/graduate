package cn.strongme.utils.system;

import cn.strongme.common.utils.SpringContextHolder;
import cn.strongme.common.utils.StringUtils;
import cn.strongme.entity.system.DictComplex;
import cn.strongme.service.system.DictComplexService;
import cn.strongme.utils.common.CacheUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * Created by 阿水 on 2017/11/8 下午2:36.
 */
public class DictComplexUtils {

    public static final String DICT_COMPLEX_CACHE_LIST = "\"dictComplexCacheList\"";
    public static final String DICT_COMPLEX_CACHE_LIST_MANAUL = "dictComplexCacheList";
    public static final String DICT_COMPLEX_CACHE_LIST_TREE = "dictComplexCacheListTree";

    private static DictComplexService dictComplexService = SpringContextHolder.getBean(DictComplexService.class);

    public static String getDictLabel(String value, String type, String defaultValue) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)) {
            for (DictComplex dict : getDictList(type)) {
                if (type.equals(dict.getType()) && value.equals(dict.getValue())) {
                    return dict.getLabel();
                }
            }
        }
        return defaultValue;
    }

    public static String getDictLabels(String values, String type, String defaultValue) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(values)) {
            List<String> valueList = Lists.newArrayList();
            for (String value : StringUtils.split(values, ",")) {
                valueList.add(getDictLabel(value, type, defaultValue));
            }
            return StringUtils.join(valueList, ",");
        }
        return defaultValue;
    }

    public static String getDictValue(String label, String type, String defaultLabel) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)) {
            for (DictComplex dict : getDictList(type)) {
                if (type.equals(dict.getType()) && label.equals(dict.getLabel())) {
                    return dict.getValue();
                }
            }
        }
        return defaultLabel;
    }


    public static List<DictComplex> getDictList(String type) {
        return getDictList(type, Lists.newArrayList());
    }

    public static List<DictComplex> getDictList(String type, List<String> disabledList) {
        Map<String, List<DictComplex>> dictMap = Maps.newHashMap();
        for (DictComplex dict : dictComplexService.findListForUtils(new DictComplex())) {
            List<DictComplex> dictList = dictMap.get(dict.getType());
            if (dictList != null) {
                dictList.add(dict);
            } else {
                dictMap.put(dict.getType(), Lists.newArrayList(dict));
            }
        }
        List<DictComplex> dictList = dictMap.get(type);
        if (dictList == null) {
            dictList = Lists.newArrayList();
        }
        for (DictComplex d : dictList) {
            d.setDisabled(false);
        }
        if (disabledList != null && !disabledList.isEmpty()) {
            String ids = StringUtils.join(disabledList, ",");
            for (DictComplex dict : dictList) {
                if (dict != null && StringUtils.isNotBlank(dict.getValue()) && StringUtils.contains(ids, dict.getValue())) {
                    dict.setDisabled(true);
                }
            }
        }
        return dictList;
    }

    public static List<DictComplex> findList() {
        List<DictComplex> result = (List<DictComplex>) CacheUtils.get(DICT_COMPLEX_CACHE_LIST_MANAUL);
        if (result == null) {
            result = dictComplexService.findList(new DictComplex());
            CacheUtils.put(DICT_COMPLEX_CACHE_LIST_MANAUL, result);
        }
        return result;
    }

    public static List<DictComplex> findListInTreeStructre() {
        List<DictComplex> result = (List<DictComplex>) CacheUtils.get(DICT_COMPLEX_CACHE_LIST_TREE);
        if (result == null) {
            result = Lists.newLinkedList();
            List<DictComplex> allOffice = findList();
            for (DictComplex m : allOffice) {
                if (m == null) {
                    continue;
                }
                if ("1".equals(m.getParentId())) {
                    result.add(m);
                    recurDict(m, allOffice);
                }
            }
            UserUtils.putCache(DICT_COMPLEX_CACHE_LIST_TREE, result);
        }
        return result;
    }

    private static void recurDict(DictComplex dictComplex, List<DictComplex> allOffice) {
        List<DictComplex> subOffice = Lists.newLinkedList();
        List<DictComplex> restOffices = Lists.newLinkedList();
        for (DictComplex m : allOffice) {
            if (cn.strongme.common.utils.StringUtils.isNotBlank(m.getParentId()) && m.getParentId().equals(dictComplex.getId())) {
                subOffice.add(m);
            } else {
                restOffices.add(m);
            }
        }
        dictComplex.setChildren(subOffice);
        if (dictComplex.getChildren() != null && !dictComplex.getChildren().isEmpty()) {
            for (DictComplex m : subOffice) {
                recurDict(m, restOffices);
            }
        }
    }

    public static void clearCache() {
        CacheUtils.remove(DICT_COMPLEX_CACHE_LIST_MANAUL);
        CacheUtils.remove(DICT_COMPLEX_CACHE_LIST_TREE);
    }

    public static List<Map<String, Object>> getTreeViewData(String defaultSelectedId, String defaultCheckedId) {
        return convertToTreeViewData(findListInTreeStructre(), defaultSelectedId, defaultCheckedId);
    }

    public static List<Map<String, Object>> convertToTreeViewData(List<DictComplex> data, String defaultSelectedId, String defaultCheckedId) {
        List<Map<String, Object>> result = Lists.newArrayList();
        if (data == null || data.isEmpty()) {
            return result;
        }
        for (DictComplex m : data) {
            Map<String, Object> mSingle = recurToTreeViewData(m, defaultSelectedId, defaultCheckedId);
            if (mSingle == null) {
                continue;
            }
            result.add(mSingle);
        }
        return result;
    }

    private static Map<String, Object> recurToTreeViewData(DictComplex dictComplex, String defaultSelectedId, String defaultCheckedId) {
        Map<String, Object> result = Maps.newHashMap();
        if (dictComplex == null || StringUtils.isBlank(dictComplex.getId())) {
            return null;
        }
        result.put("id", dictComplex.getId());
        result.put("parentId", dictComplex.getParentId());
        result.put("text", dictComplex.getName());

        if (cn.strongme.common.utils.StringUtils.isNotBlank(defaultSelectedId) && cn.strongme.common.utils.StringUtils.inString(dictComplex.getId(), defaultSelectedId.split(","))) {
            Map<String, Object> tmp = (Map<String, Object>) result.get("state");
            if (tmp == null) {
                tmp = Maps.newHashMap();
            }
            tmp.put("selected", true);
            result.put("state", tmp);
        }
        if (cn.strongme.common.utils.StringUtils.isNotBlank(defaultCheckedId) && cn.strongme.common.utils.StringUtils.inString(dictComplex.getId(), defaultCheckedId.split(","))) {
            Map<String, Object> tmp = (Map<String, Object>) result.get("state");
            if (tmp == null) {
                tmp = Maps.newHashMap();
            }
            tmp.put("checked", true);
            result.put("state", tmp);
        }
        if (dictComplex.getChildren() != null && !dictComplex.getChildren().isEmpty()) {
            List<Map<String, Object>> subList = Lists.newArrayList();
            for (DictComplex m : dictComplex.getChildren()) {
                Map<String, Object> subSingle = recurToTreeViewData(m, defaultSelectedId, defaultCheckedId);
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
