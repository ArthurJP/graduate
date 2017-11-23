package cn.strongme.config;

import cn.strongme.common.utils.Encodes;
import cn.strongme.common.utils.Servlets;
import cn.strongme.common.utils.StringUtils;
import cn.strongme.entity.system.Menu;
import cn.strongme.entity.system.Role;
import cn.strongme.entity.system.User;
import cn.strongme.service.system.UserService;
import cn.strongme.utils.common.PwdUtils;
import cn.strongme.utils.system.LogUtils;
import cn.strongme.utils.system.MenuUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.List;


/**
 * @author 阿水
 * @date 2017/7/14 上午11:22
 */
public class MyShiroRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 权限认证，为当前登录的Subject授予角色和权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("##################执行Shiro权限认证##################");
        Principal principal = (Principal) super.getAvailablePrincipal(principalCollection);
        //到数据库查是否有此对象
        // 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        User user = userService.findByMobile(principal.getUser().getMobile());
        if (user != null) {
            //权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            List<Menu> list = MenuUtils.findList();
            for (Menu menu : list) {
                if (StringUtils.isNotBlank(menu.getPermission())) {
                    // 添加基于Permission的权限信息
                    for (String permission : StringUtils.split(menu.getPermission(), ",")) {
                        info.addStringPermission(permission);
                    }
                }
            }
            //用户的角色集合
            for (Role role : user.getRoleList()) {
                info.addRole(role.getEname());
            }
            LogUtils.saveLog(Servlets.getRequest(), "系统登录");
            return info;
        }
        // 返回null的话，就会导致任何用户访问被拦截的请求时，都会自动跳转到unauthorizedUrl指定的地址
        return null;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authenticationToken) throws AuthenticationException {
        //UsernamePasswordToken对象用来存放提交的登录信息
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        logger.info("验证当前Subject时获取到token为：" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));

        //查出是否有此用户
        User user = userService.findByMobile(token.getUsername());
        if (user != null) {
            // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
            //return new SimpleAuthenticationInfo(new Princiapl(user), user.getPassword(), getName());
            byte[] salt = Encodes.decodeHex(user.getPassword().substring(0, 16));
            return new SimpleAuthenticationInfo(new Principal(user),
                    user.getPassword().substring(16), ByteSource.Util.bytes(salt), getName());
        }
        return null;
    }

    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(PwdUtils.HASH_ALGORITHM);
        matcher.setHashIterations(PwdUtils.HASH_INTERATIONS);
        setCredentialsMatcher(matcher);
    }


}
