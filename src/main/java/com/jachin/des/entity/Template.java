package com.jachin.des.entity;

/**
 * @author Jachin
 * @since 2019/3/13 13:10
 */
public class Template implements AEntity{
    private int tempId;
    private String title;
    private String keyWd;
    private String info;
    private String imgUrl;
    private String content;

    public int getTempId() {
        return tempId;
    }

    public void setTempId(int tempId) {
        this.tempId = tempId;
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
}
