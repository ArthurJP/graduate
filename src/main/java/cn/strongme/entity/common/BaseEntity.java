package cn.strongme.entity.common;

import cn.strongme.common.utils.StringUtils;
import cn.strongme.common.utils.UUID15;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 阿水 on 2017/9/9 下午9:58.
 */
public abstract class BaseEntity<T> implements Serializable {
    private static final long serialVersionUID = 6669810101500114017L;

    protected String id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date createDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date updateDate;
    String remarks;

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

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
    public boolean isNewRecord() {
        if (StringUtils.isNotBlank(id)) {
            return false;
        } else {
            return true;
        }
    }

}
