package com.jachin.des.entity;

/**
 * 过滤通配对象
 * @author Jachin
 * @since 2019/3/16 15:27
 */
public class SearchArg {
    private int id;
    private int aid;
    private int tempId;
    private String title;
    private String designer;
    private String begTime;
    private String endTime;
    private int status;
    private String nickName;

    private boolean distinct;     // 是否去重
    private String columns;       // 排序列
    private boolean comp;         // 是否规则

    public SearchArg() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

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

    public String getDesigner() {
        return designer;
    }

    public void setDesigner(String designer) {
        this.designer = designer;
    }

    public String getBegTime() {
        return begTime;
    }

    public void setBegTime(String begTime) {
        this.begTime = begTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public boolean isComp() {
        return comp;
    }

    public void setComp(boolean comp) {
        this.comp = comp;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    // 设置排序规则
    public void setCompColumns(String column, boolean desc){
        this.columns = column;
        this.comp = desc;
    }
}
