package cn.strongme.web.common;

import cn.strongme.common.beanvalidator.BeanValidators;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.List;
import java.util.Map;

/**
 * @author 阿水
 * @date 2017/9/5 下午8:23
 */
public class BaseController {

    /**
     * 添加Model消息
     */
    protected void addMessage(Model model, String type, String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            sb.append(message).append(messages.length > 1 ? "<br/>" : "");
        }
        model.addAttribute("msg", sb.toString());
        String color = convertToColor(type);
        model.addAttribute("msgType", type);
        model.addAttribute("msgColor", color);
    }

    /**
     * 添加Flash消息
     */
    protected void addMessage(RedirectAttributes redirectAttributes, String type, String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            sb.append(message).append(messages.length > 1 ? "<br/>" : "");
        }
        redirectAttributes.addFlashAttribute("msg", sb.toString());
        String color = convertToColor(type);
        redirectAttributes.addFlashAttribute("msgType", type);
        redirectAttributes.addFlashAttribute("msgColor", color);
    }


    private String convertToColor(String type) {
        String color = "blue";
        switch (type) {
            case "success":
                color = "green";
                break;
            case "danger":
                color = "red";
                break;
            default:
                break;
        }
        return color;
    }

    /**
     * 验证Bean实例对象
     */
    @Autowired
    protected Validator validator;

    /**
     * 服务端参数有效性验证
     *
     * @param object 验证的实体对象
     * @param groups 验证组
     * @return 验证成功：返回true；严重失败：将错误信息添加到 message 中
     */
    protected boolean beanValidator(Model model, Object object, Class<?>... groups) {
        return bvCommon(model, null, object, groups);
    }

    /**
     * 服务端参数有效性验证
     *
     * @param object 验证的实体对象
     * @param groups 验证组
     * @return 验证成功：返回true；严重失败：将错误信息添加到 flash message 中
     */
    protected boolean beanValidator(RedirectAttributes redirectAttributes, Object object, Class<?>... groups) {
        return bvCommon(null, redirectAttributes, object, groups);
    }

    private boolean bvCommon(Model model, RedirectAttributes redirectAttributes, Object object, Class<?>... groups) {
        try {
            BeanValidators.validateWithException(validator, object, groups);
        } catch (ConstraintViolationException ex) {
            List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
            list.add(0, "数据验证失败：");
            if (model != null) {
                addMessage(model, "danger", list.toArray(new String[]{}));
            } else {
                addMessage(redirectAttributes, "danger", list.toArray(new String[]{}));
            }
            return false;
        }
        return true;
    }


    /**
     * 服务端参数有效性验证
     *
     * @param object 验证的实体对象
     * @param groups 验证组，不传入此参数时，同@Valid注解验证
     * @return 验证成功：继续执行；验证失败：抛出异常跳转400页面。
     */
    protected void beanValidator(Object object, Class<?>... groups) {
        BeanValidators.validateWithException(validator, object, groups);
    }

    protected Map<String, Object> renderResult(String status, String msg, Object data) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("status", status);
        result.put("message", msg);
        result.put("data", data);
        return result;
    }

}
