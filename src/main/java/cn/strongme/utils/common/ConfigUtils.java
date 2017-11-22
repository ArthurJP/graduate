package cn.strongme.utils.common;

import cn.strongme.common.utils.SpringContextHolder;
import org.springframework.core.env.Environment;

/**
 * Created by 阿水 on 2017/9/19 上午11:31.
 */
public class ConfigUtils {

    private static Environment environment = SpringContextHolder.getBean(Environment.class);

    public static String getConfig(String key) {
        return environment.getProperty(key);
    }

}
