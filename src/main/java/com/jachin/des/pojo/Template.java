package com.jachin.des.pojo;

/**
 * @author Jachin
 * @since 2019/3/8 15:02
 */
public class Template {
    private int id;
    private int aid;
    private int tempId;
    private String name;
    private String designer;
    private String time;
    private int status;
    private double price;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "Template{" +
                "id=" + id +
                ", aid=" + aid +
                ", tempId=" + tempId +
                ", name='" + name + '\'' +
                ", designer='" + designer + '\'' +
                ", time='" + time + '\'' +
                ", status=" + status +
                ", price=" + price +
                '}';
    }

//    public String toString(){
//        Param param = new Param();
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

