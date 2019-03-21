package com.jachin.des.entity;

/**
 * 收入/提现表
 * @author Jachin
 * @since 2019/3/13 22:33
 */
public class CashFlow implements AEntity{
    // 22项
    private int id;             // 主键自增
    private int aid;            // 账户ID
    private int tempId;         // 模板ID
    private String time;        // 操作时间
    private int price;          // 金额
    private int balance;        // 余额
    private int type;           // 操作类型

    public CashFlow() {}

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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CashFlow{" +
                "id=" + id +
                ", aid=" + aid +
                ", tempId=" + tempId +
                ", time='" + time + '\'' +
                ", price=" + price +
                ", balance=" + balance +
                ", type=" + type +
                '}';
    }
}

