package com.jachin.des.entity;

/**
 * @author Jachin
 * @since 2019/3/8 15:02
 */
public class DesignerAudit implements AEntity{
    private int id;
    private int aid;
    private String nickName;
    private String time;
    private int status;
    private String reason;

    public DesignerAudit() {
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "DesignerAudit{" +
                "id=" + id +
                ", aid=" + aid +
                ", nickName='" + nickName + '\'' +
                ", time='" + time + '\'' +
                ", status=" + status +
                ", reason='" + reason + '\'' +
                '}';
    }
}

