package cn.strongme.entity.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author 阿水
 * @date 2017/11/8 下午2:49
 */
public class TreeEntity<T extends TreeEntity> extends BaseEntity<T> {

    protected String name;
    protected T parent;
    protected String parentIds;
    protected List<T> children = Lists.newArrayList();

    public TreeEntity() {
    }

    public TreeEntity(String id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public static <T extends TreeEntity> void sortList(List<T> list, List<T> sourcelist, String parentId, boolean cascade) {
        for (int i = 0; i < sourcelist.size(); i++) {
            T e = sourcelist.get(i);
            if (e.getParent() != null && e.getParent().getId() != null
                    && e.getParent().getId().equals(parentId)) {
                list.add(e);
                if (cascade) {
                    // 判断是否还有子节点, 有则继续获取子节点
                    for (int j = 0; j < sourcelist.size(); j++) {
                        T child = sourcelist.get(j);
                        if (child.getParent() != null && child.getParent().getId() != null
                                && child.getParent().getId().equals(e.getId())) {
                            sortList(list, sourcelist, e.getId(), true);
                            break;
                        }
                    }
                }
            }
        }
    }

    public String getParentId() {
        if (parent != null) {
            return parent.getId();
        }
        return null;
    }

    public T getParent() {
        return parent;
    }

    public void setParent(T parent) {
        this.parent = parent;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }

    @JsonIgnore
    public static String getRootId() {
        return "1";
    }
}
