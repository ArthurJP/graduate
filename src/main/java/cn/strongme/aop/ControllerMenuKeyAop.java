package cn.strongme.aop;

import cn.strongme.common.utils.StringUtils;
import cn.strongme.entity.common.Title;
import cn.strongme.utils.common.AnnotationUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;

/**
 * aop for menu key
 *
 * @author 阿水
 * @date 2017/11/16 上午8:59
 */
@Component
@Aspect
public class ControllerMenuKeyAop {

    /**
     * 定义拦截规则：拦截包下面的所有类中，有@RequestMapping注解的方法。
     */
    @Pointcut("execution(* cn.strongme.web..*.*(..)) @@annotation(org.springframework.web.bind.annotation.RequestMapping) && @within(cn.strongme.annotation.MenuKey)")
    public void controllerMethodPointcut() {
    }


    @Around("controllerMethodPointcut()")
    public Object controllerBeforeReturn(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
            //obj之前可以写目标方法执行前的逻辑
            String menuKey = AnnotationUtils.retriveMenuKey(proceedingJoinPoint.getSignature().getDeclaringType());
            if (StringUtils.isNotBlank(menuKey)) {
                request.setAttribute("menuKey", menuKey);
            }
            Title title = AnnotationUtils.retriveMethodTitle(proceedingJoinPoint.getSignature().getDeclaringType(), proceedingJoinPoint.getSignature().getName());
            if (title != null) {
                request.setAttribute("title", title.getTitle());
                request.setAttribute("subTitle", title.getSubTitle());
            }
            //调用执行目标方法
            Object obj = proceedingJoinPoint.proceed();
            return obj;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

}
