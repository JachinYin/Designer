package com.jachin.des.entity;

/**
 * 收入/提现表
 * @author Jachin
 * @since 2019/3/13 22:33
 */
public class CashFlowWithTitle implements AEntity{
    // 22项
    private String title;
    private int tempId;         // 模板ID
    private String time;        // 操作时间
    private int price;          // 金额

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTempId() {
        return tempId;
    }

    public void setTempId(int tempId) {
        this.tempId = tempId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

