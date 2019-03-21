package com.jachin.des.entity;

/**
 * 模板表
 * @author Jachin
 * @since 2019/3/13 13:10
 */
public class Template implements AEntity{
    private int tempId;     // 模板id
    private int aid;        // 账户id
    private String title;   // 标题
    private String keyWd;   // 关键词
    private String info;    // 行业信息
    private String imgUrl;  // 封面URL
    private String content; // 内容
    private String time;    // 更新时间

    public int getTempId() {
        return tempId;
    }

    public void setTempId(int tempId) {
        this.tempId = tempId;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeyWd() {
        return keyWd;
    }

    public void setKeyWd(String keyWd) {
        this.keyWd = keyWd;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Template{" +
                "tempId=" + tempId +
                ", aid=" + aid +
                ", title='" + title + '\'' +
                ", keyWd='" + keyWd + '\'' +
                ", info='" + info + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
