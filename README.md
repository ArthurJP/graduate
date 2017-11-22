+ db目录下有sql文件，导入即可，数据库名默认为web


# 目前的问题
> UserService事务不起作用，其他Service都没有问题，各位大牛帮忙看看！

``` java
     @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public void save(User user) {
        if (StringUtils.isBlank(user.getId())) {
            user.preInsert();
            try {
                this.userDao.insert(user);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException("保存用户失败");
            }
        } else {
            user.preUpdate();
            try {
                this.userDao.update(user);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException("更新用户失败");
            }
        }

        //这里应该是要导致事务回滚的，但是却没有，其他Service都可以的！WHY
        if(ConfigUtils.getConfig("a")==null) {
            throw new ServiceException("不存在此配置");
        }

        //插入用户角色
        if (user.getRole() == null || StringUtils.isBlank(user.getRole().getId())) {
            throw new ServiceException("缺少角色信息");
        }
        try {
            this.userDao.deleteUserRole(user);
            this.userDao.insertUserRole(user);
        } catch (Exception e) {
            throw new ServiceException("保存角色信息失败");
        }

        if (user.getOffice() == null || StringUtils.isBlank(user.getOffice().getId())) {
            throw new ServiceException("缺少部门信息");
        }
        try {
            this.userDao.deleteUserOffice(user);
            this.userDao.insertUserOffice(user);
        } catch (Exception e) {
            throw new ServiceException("保存部门信息失败");
        }
    }

```


![求助](https://o888cums1.qnssl.com/help.png)