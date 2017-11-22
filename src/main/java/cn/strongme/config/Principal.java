package cn.strongme.config;


import cn.strongme.entity.system.User;

/**
 * Created by 阿水 on 2017/7/14 下午1:38.
 */
public class Principal {

    private User user;

    public Principal(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
