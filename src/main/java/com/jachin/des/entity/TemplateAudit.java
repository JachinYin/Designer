package com.jachin.des.entity;

/**
 * @author Jachin
 * @since 2019/3/8 15:02
 */
public class TemplateAudit {
    private int id;
    private int aid;
    private int tempId;
    private String title;
    private String designer;
    private String time;
    private int status;
    private double price;
    private int type;
    private String reason;

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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
                ", type=" + type +
                ", reason='" + reason + '\'' +
                '}';
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    //    public String toString(){
//        JacParam param = new JacParam();
//        param.setInt("id", getId());
//        param.setInt("aid", getAid());
//        param.setInt("tempId", getTempId());
//        param.setString("name", getName());
//        param.setString("designer", getDesigner());
//        param.setString("time", getTime());
//        param.setInt("status", getStatus());
//        param.setDouble("price", getPrice());
//        return param.toJson();
//    }
}

