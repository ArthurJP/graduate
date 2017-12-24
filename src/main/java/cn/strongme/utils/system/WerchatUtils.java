package cn.strongme.utils.system;

import cn.strongme.common.utils.StringUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Walter on 2016/11/14.
 */
public class WerchatUtils {

    /**
     * 最后未匹配任何路由时的回复消息
     */
    public static final String FINAL_MSG = "FINAL_MSG";

    /**
     * 记录绑定企业老板微信的场景ID
     */
    public static final Map<Integer, String> QRCODE_SENCE_ID_COMPANY_MAP = Maps.newHashMap();

    public static Random random = new Random();

    public static Integer nextRandom() {
        Integer id = random.nextInt(10000);
        while (QRCODE_SENCE_ID_COMPANY_MAP.containsKey(id)) {
            id = random.nextInt(10000);
        }
        return id;
    }

    public static Integer generateBindSenceId(String idStr) {
        Integer id = nextRandom();
        //先把这个业务Id的删除
        List<Integer> keys = Lists.newArrayList(QRCODE_SENCE_ID_COMPANY_MAP.keySet());
        for (Integer key : keys) {
            if (StringUtils.isNotBlank(idStr) && idStr.equals(QRCODE_SENCE_ID_COMPANY_MAP.get(key))) {
                QRCODE_SENCE_ID_COMPANY_MAP.remove(key);
            }
        }
        QRCODE_SENCE_ID_COMPANY_MAP.put(id, idStr);
        return id;
    }


}
