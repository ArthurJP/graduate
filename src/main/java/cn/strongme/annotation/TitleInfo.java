package cn.strongme.annotation;

import java.lang.annotation.*;

/**
 * Created by 阿水 on 2017/11/16 上午9:43.
 */
@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface TitleInfo {

    String title();
    String subTitle() default "";

}
