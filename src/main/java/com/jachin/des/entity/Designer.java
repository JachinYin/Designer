package com.jachin.des.entity;

/**
 * @author Jachin
 * @since 2019/3/13 22:33
 */
public class Designer implements AEntity{
    // 22项
    private int aid;            // 账户ID
    private int status;         // 状态
    private int balance;        // 余额
    private int totalPrice;     // 总收入
    private String nickName;    // 昵称
    private String intro;       // 简介
    private String idNum;       // 身份证号码
    private String realName;    // 真实姓名
    private String country;     // 国家1
    private String province;    // 省份
    private String city;        // 城市
    private String phone;       // 联系手机
    private String email;       // 邮箱
    private String weChat;      // 微信号
    private String qq;          // QQ号
    private String address;     // 地址
    private String bankAcct;    // 银行账号
    private String openBank;    // 开户银行
    private String cardHolder;  // 持卡人
    private String photoImg;    // 头像
    private String frontImg;    // 身份证正面照
    private String reverseImg;  // 身份证反面照

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBankAcct() {
        return bankAcct;
    }

    public void setBankAcct(String bankAcct) {
        this.bankAcct = bankAcct;
    }

    public String getOpenBank() {
        return openBank;
    }

    public void setOpenBank(String openBank) {
        this.openBank = openBank;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getPhotoImg() {
        return photoImg;
    }

    public void setPhotoImg(String photoImg) {
        this.photoImg = photoImg;
    }

    public String getFrontImg() {
        return frontImg;
    }

    public void setFrontImg(String frontImg) {
        this.frontImg = frontImg;
    }

    public String getReverseImg() {
        return reverseImg;
    }

    public void setReverseImg(String reverseImg) {
        this.reverseImg = reverseImg;
    }

    @Override
    public String toString() {
        return "Designer{" +
                "aid=" + aid +
                ", status=" + status +
                ", intro='" + intro + '\'' +
                ", idNum='" + idNum + '\'' +
                ", realName='" + realName + '\'' +
                ", country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", weChat='" + weChat + '\'' +
                ", qq='" + qq + '\'' +
                ", address='" + address + '\'' +
                ", bankAcct='" + bankAcct + '\'' +
                ", openBank='" + openBank + '\'' +
                ", cardHolder='" + cardHolder + '\'' +
                ", photoImg='" + photoImg + '\'' +
                ", frontImg='" + frontImg + '\'' +
                ", reverseImg='" + reverseImg + '\'' +
                '}';
    }
}
