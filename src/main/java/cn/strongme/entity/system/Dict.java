package cn.strongme.entity.system;

import cn.strongme.entity.common.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 字典Entity
 */
public class Dict extends BaseEntity {

    private static final long serialVersionUID = 1L;
    private String value;    // 数据值
    private String label;    // 标签名
    private String type;    // 类型
    private String description;// 描述
    private Integer sort;    // 排序
    private boolean disabled;//

    public Dict() {
        super();
    }

    public Dict(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public Dict(String id) {
        super(id);
    }

    @Length(min = 1, max = 100)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Length(min = 1, max = 100)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Length(min = 1, max = 100)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Length(min = 0, max = 100)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return label;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}