package cn.strongme.utils.system;

import cn.strongme.common.utils.SpringContextHolder;
import cn.strongme.entity.system.Dict;
import cn.strongme.service.system.DictService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 字典工具类
 */
public class DictUtils {

    private static Logger logger = LoggerFactory.getLogger(DictUtils.class);

    private static DictService dictService = SpringContextHolder.getBean(DictService.class);

    public static final String DICT_CACHE_LIST = "\"dictCacheList\"";

    public static String getDictLabel(String value, String type, String defaultValue) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)) {
            for (Dict dict : getDictList(type)) {
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
            for (Dict dict : getDictList(type)) {
                if (type.equals(dict.getType()) && label.equals(dict.getLabel())) {
                    return dict.getValue();
                }
            }
        }
        return defaultLabel;
    }


    public static List<Dict> getDictList(String type) {
        return getDictList(type, Lists.newArrayList());
    }

    public static List<Dict> getDictList(String type, List<String> disabledList) {
        Map<String, List<Dict>> dictMap = Maps.newHashMap();
        for (Dict dict : dictService.findListForUtils(new Dict())) {
            List<Dict> dictList = dictMap.get(dict.getType());
            if (dictList != null) {
                dictList.add(dict);
            } else {
                dictMap.put(dict.getType(), Lists.newArrayList(dict));
            }
        }
        List<Dict> dictList = dictMap.get(type);
        if (dictList == null) {
            dictList = Lists.newArrayList();
        }
        for (Dict d : dictList) {
            d.setDisabled(false);
        }
        if (disabledList != null && !disabledList.isEmpty()) {
            String ids = StringUtils.join(disabledList, ",");
            for (Dict dict : dictList) {
                if (dict != null && StringUtils.isNotBlank(dict.getValue()) && StringUtils.contains(ids, dict.getValue())) {
                    dict.setDisabled(true);
                }
            }
        }
        return dictList;
    }
}
