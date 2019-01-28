package com.chensi.yghy.model;

import javax.persistence.*;

/**
 * @Description: 一罐好运模型类
 * @Author: redcomet
 * @Date: 2019-01-23-9:31
 */

@Entity
@Table(name = "t_yghy",uniqueConstraints = {@UniqueConstraint(columnNames="userID")})
public class Yghy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String userID;         //用户名

    private String nickName;       //用户的昵称

    private String formName;       //填写的姓名

    private int isCollected;

    private int isDraw;

    private int prizeType;

    private String address;

    private String phone;

    private String email;

    private String contacts;

    public Yghy(long id,String userID, String nickName, String formName, int isCollected, int isDraw, int prizeType, String address, String phone, String email, String contacts) {
        this.id = id;
        this.userID = userID;
        this.nickName = nickName;
        this.formName = formName;
        this.isCollected = isCollected;
        this.isDraw = isDraw;
        this.prizeType = prizeType;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.contacts = contacts;
    }

    public Yghy(String userID, String nickName){
        this.userID = userID;
        this.nickName = nickName;
    }
    public Yghy(){}



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public int getIsCollected() {
        return isCollected;
    }

    public void setIsCollected(int isCollected) {
        this.isCollected = isCollected;
    }

    public int getIsDraw() {
        return isDraw;
    }

    public void setIsDraw(int isDraw) {
        this.isDraw = isDraw;
    }

    public int getPrizeType() {
        return prizeType;
    }

    public void setPrizeType(int prizeType) {
        this.prizeType = prizeType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }
}
