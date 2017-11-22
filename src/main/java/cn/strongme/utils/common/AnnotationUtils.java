package cn.strongme.utils.common;

import cn.strongme.annotation.MenuKey;
import cn.strongme.annotation.TitleInfo;
import cn.strongme.entity.common.Title;
import com.google.common.collect.Lists;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author 阿水
 * @date 2017/11/15 上午9:27
 */
public class AnnotationUtils {


    public static String retriveMenuKey(Class cls) {
        String result = null;
        try {
            MenuKey mk = AnnotationUtils.class.getClassLoader().loadClass(cls.getName()).getAnnotation(MenuKey.class);
            if (mk != null) {
                result = mk.value();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Title retriveMethodTitle(Class cls, String methodName) {
        Title result = null;
        try {
            Class c = AnnotationUtils.class.getClassLoader().loadClass(cls.getName());
            if (c == null) {
                return null;
            }

            List<Method> mList = Lists.newArrayList(c.getDeclaredMethods());
            for (Method m : mList) {
                if (m == null) {
                    continue;
                }
                if (methodName.equals(m.getName())) {
                    TitleInfo ti = m.getAnnotation(TitleInfo.class);
                    if (ti != null) {
                        result = new Title(ti.title(), ti.subTitle());
                        break;
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }


}
