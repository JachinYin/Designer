package com.jachin.des.entity;

/**
 * 设计师审核表
 * @author Jachin
 * @since 2019/3/8 15:02
 */
public class DesignerAudit implements AEntity{
    private int id;             // 主键自增
    private int aid;            // 账户ID
    private String nickName;    // 设计师昵称
    private String time;        // 审核时间
    private int status;         // 审核状态
    private String reason;      // 打回理由

    public DesignerAudit() {}

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

