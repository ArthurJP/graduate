package cn.strongme.entity.business;

import cn.strongme.entity.common.BaseEntity;
import com.google.common.collect.Lists;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by 阿水 on 2017/9/21 下午1:52.
 */
public class Info extends BaseEntity {

    private String title;
    private String intro;
    private String type;
    private String content;
    private List<String> typeList = Lists.newArrayList();

    public Info() {
    }

    public Info(String id) {
        this.id = id;
    }

    @NotNull
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotNull
    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    @NotNull
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @NotNull
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<String> typeList) {
        this.typeList = typeList;
    }
}
