# 问题解决啦

>是shiro与spring整合的问题


具体解决思路：
``` java
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
```

# 下一步尝试

> 尝试使用`jwt+spring security` 用全家桶里面的东西会放心一点

