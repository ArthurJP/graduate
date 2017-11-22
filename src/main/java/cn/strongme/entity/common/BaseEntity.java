package cn.strongme.entity.common;

import cn.strongme.common.utils.UUID15;
import cn.strongme.entity.system.User;
import cn.strongme.utils.system.UserUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by 阿水 on 2017/9/9 下午9:58.
 */
public abstract class BaseEntity<T> {

    protected String id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    protected Date createDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    protected Date updateDate;
    protected User currentUser;

    protected Integer page = 1;

    protected Integer rows = 10;

    public BaseEntity() {
    }

    public BaseEntity(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonIgnore
    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    @JsonIgnore
    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public void preInsert() {
        // 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
        this.id = UUID15.generate();
        this.updateDate = new Date();
        if (this.createDate == null) {
            this.createDate = this.updateDate;
        }
    }

    /**
     * 更新之前执行方法，需要手动调用
     */
    public void preUpdate() {
        this.updateDate = new Date();
    }

    @JsonIgnore
    public User getCurrentUser() {
        if (currentUser == null) {
            currentUser = UserUtils.currentUser();
        }
        return currentUser;
    }

}
