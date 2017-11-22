package cn.strongme.entity.system;

import cn.strongme.entity.common.BaseEntity;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by 阿水 on 2017/7/14 上午8:57.
 */
public class User extends BaseEntity {

    private String name;
    private String password;
    private String mobile;

    private Office office;
    private Role role;

    private List<Role> roleList = Lists.newArrayList(); // 拥有角色列表

    public User() {
    }

    public User(String id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isAdmin() {
        boolean result = false;
        if ("1".equals(id)) {
            result = true;
        }
        return result;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }
}
