package cn.strongme.annotation;

import java.lang.annotation.*;

/**
 * Created by 阿水 on 2017/11/16 上午9:07.
 */
@Documented
@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface MenuKey {

    /**
     * 菜单字符
     *
     * @return
     */
    String value();

}
