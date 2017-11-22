package cn.strongme.utils.system;

import cn.strongme.common.utils.SpringContextHolder;
import cn.strongme.common.utils.StringUtils;
import cn.strongme.config.Principal;
import cn.strongme.dao.system.RoleDao;
import cn.strongme.dao.system.UserDao;
import cn.strongme.entity.system.Role;
import cn.strongme.entity.system.User;
import cn.strongme.utils.common.CacheUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.List;

/**
 * Created by 阿水 on 2017/9/5 下午10:27.
 */
public class UserUtils {

    public static final String CACHE_ROLE_LIST = "roleList";

    public static final String USER_CACHE = "userCache";
    public static final String USER_CACHE_ID_ = "id_";
    public static final String USER_CACHE_LOGIN_NAME_ = "ln";

    private static UserDao userDao = SpringContextHolder.getBean(UserDao.class);
    private static RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);

    public static User get(User u) {
        if(u!=null && StringUtils.isNotBlank(u.getId())) {
            return get(u.getId());
        }else {
            return new User();
        }
    }

    public static User get(String id){
        User user = (User) CacheUtils.get(USER_CACHE, USER_CACHE_ID_ + id);
        if (user ==  null){
            user = userDao.get(new User(id));
            if (user == null){
                return null;
            }
            user.setRoleList(roleDao.findList(new Role(user)));
            CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
            CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getMobile(), user);
        }
        return user;
    }

    public static User currentUser() {
        Principal principal = getPrincipal();
        if (principal!=null){
            User user = get(principal.getUser());
            if (user != null){
                return user;
            }
            return null;
        }
        // 如果没有登录，则返回实例化空的User对象。
        return null;
    }



    public static void updateCurrentUser(User user) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.getPrincipal() instanceof Principal) {
            ((Principal) subject.getPrincipal()).setUser(user);
        }
    }


    public static User getByMobile(String mobile) {
        User user = (User) CacheUtils.get(USER_CACHE, USER_CACHE_LOGIN_NAME_ + mobile);
        if (user == null) {
            user = userDao.findByMobile(mobile);
            if (user == null) {
                return null;
            }
            //设置角色
            List<Role> roleList = roleDao.findList(new Role(user));
            user.setRoleList(roleList);
            CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
            CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getMobile(), user);
        }
        return user;
    }

    /**
     * 清除当前用户缓存
     */
    public static void clearCache() {
        removeCache(MenuUtils.Menu_CACHE_LIST);
        removeCache(MenuUtils.Menu_CACHE_LIST_TREE);
        UserUtils.clearCache(currentUser());
    }

    /**
     * 清除指定用户缓存
     *
     * @param user
     */
    public static void clearCache(User user) {
        CacheUtils.remove(USER_CACHE, USER_CACHE_ID_ + user.getId());
        CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getMobile());
    }

    public static Principal getPrincipal(){
        try{
            Subject subject = SecurityUtils.getSubject();
            Principal principal = (Principal) subject.getPrincipal();
            if (principal != null){
                return principal;
            }
        }catch (UnavailableSecurityManagerException e) {

        }catch (InvalidSessionException e){

        }
        return null;
    }

    public static Session getSession() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);
            if (session == null) {
                session = subject.getSession();
            }
            if (session != null) {
                return session;
            }
        } catch (InvalidSessionException e) {
        }
        return null;
    }


    // ============== User Cache ==============

    public static Object getCache(String key) {
        return getCache(key, null);
    }

    public static Object getCache(String key, Object defaultValue) {
        Object obj = getSession().getAttribute(key);
        return obj == null ? defaultValue : obj;
    }

    public static void putCache(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static void removeCache(String key) {
        getSession().removeAttribute(key);
    }

}
