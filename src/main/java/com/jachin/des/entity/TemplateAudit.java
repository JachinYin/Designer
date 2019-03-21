package com.jachin.des.entity;

/**
 * 模板审核表对象
 * @author Jachin
 * @since 2019/3/8 15:02
 */
public class TemplateAudit implements AEntity{
    private int id;             // 主键自增
    private int aid;            // 账户ID
    private int tempId;         // 模板ID
    private String title;       // 模板标题
    private String designer;    // 设计师昵称
    private String time;        // 审核时间
    private int status;         // 审核状态
    private int price;          // 价格
    private String reason;      // 打回理由

    public TemplateAudit() {
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "TemplateAudit{" +
                "id=" + id +
                ", aid=" + aid +
                ", tempId=" + tempId +
                ", title='" + title + '\'' +
                ", designer='" + designer + '\'' +
                ", time='" + time + '\'' +
                ", status=" + status +
                ", price=" + price +
                ", reason='" + reason + '\'' +
                '}';
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

