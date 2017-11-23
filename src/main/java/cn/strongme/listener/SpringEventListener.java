package cn.strongme.listener;

import cn.strongme.config.MyShiroRealm;
import cn.strongme.service.system.UserService;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author 阿水
 * @date 2017/11/23 上午11:41
 */
@Component
public class SpringEventListener {

    @Autowired
    private UserService userService;


    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        DefaultWebSecurityManager manager = (DefaultWebSecurityManager) context.getBean("securityManager");
        MyShiroRealm realm = (MyShiroRealm) context.getBean("myShiroRealm");
        realm.setUserService(userService);
        manager.setRealm(realm);
    }

}
