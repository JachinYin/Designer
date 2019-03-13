package com.jachin.des.entity;

/**
 * @author Jachin
 * @since 2019/3/13 22:33
 */
public class Designer {
    private int aid;
    private int status;
    private String intro;
    private String idNum;
    private String realName;
    private String country;
    private String province;
    private String city;
    private String phone;
    private String email;
    private String weChat;
    private String qq;
    private String address;
    private String bankAcct;
    private String openBank;
    private String cardHolder;
    private String isDel;
    private String photoImg;
    private String frontImg;
    private String reverseImg;

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

    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
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
                ", isDel='" + isDel + '\'' +
                ", photoImg='" + photoImg + '\'' +
                ", frontImg='" + frontImg + '\'' +
                ", reverseImg='" + reverseImg + '\'' +
                '}';
    }
}
