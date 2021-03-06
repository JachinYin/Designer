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
    private int status;
    private int type;
    private int year;
    private int month;
    private String title;
    private String designer;
    private String begTime;
    private String endTime;
    private String nickName;

    private String userName;
    private String password;
    private String token;

    private boolean distinct;     // 是否去重
    private String columns;       // 排序列
    private boolean comp;         // 是否规则

    private String extra;

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

    public int getStatus() {
        return status;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
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

    // 设置排序规则
    public void setCompColumns(String column, boolean desc){
        this.columns = column;
        this.comp = desc;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return "SearchArg{" +
                "id=" + id +
                ", aid=" + aid +
                ", tempId=" + tempId +
                ", status=" + status +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", designer='" + designer + '\'' +
                ", begTime='" + begTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", nickName='" + nickName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", distinct=" + distinct +
                ", columns='" + columns + '\'' +
                ", comp=" + comp +
                ", extra='" + extra + '\'' +
                '}';
    }
}
