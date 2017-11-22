package cn.strongme.entity.common;

/**
 * Created by 阿水 on 2017/11/16 上午9:45.
 */
public class Title {

    private String title;
    private String subTitle;

    public Title(String title, String subTitle) {
        this.title = title;
        this.subTitle = subTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
}
