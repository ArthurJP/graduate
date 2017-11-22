package cn.strongme.entity.system;

import cn.strongme.entity.common.TreeEntity;

/**
 * Created by 阿水 on 2017/11/8 下午2:16.
 */
public class DictComplex extends TreeEntity<DictComplex> {

    private String value;    // 数据值
    private String label;    // 标签名
    private String type;    // 类型
    private String description;// 描述
    private Integer sort;    // 排序
    private boolean disabled;//

    public DictComplex() {
    }

    public DictComplex(String id) {
        super(id);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    @Override
    public String getName() {
        return label;
    }
}
