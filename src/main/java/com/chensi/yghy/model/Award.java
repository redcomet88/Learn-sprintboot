package com.chensi.yghy.model;

import javax.persistence.*;

/**
 * @Description: 抽奖类
 * @Author: redcomet
 * @Date: 2019-01-25-9:52
 */
@Entity
@Table(name = "t_award")
public class Award {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String userID;     //用户的ID

    private String name;       //用户的姓名，同时也是第一罐的内容

    private String contents;   //定制的内容

    private long awardId;      //奖品id

    private String telephone;  //电话

    private String address;    //地址

    private String note1;      //备用1

    private String note2;      //备用2

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public long getAwardId() {
        return awardId;
    }

    public void setAwardId(long awardId) {
        this.awardId = awardId;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote1() {
        return note1;
    }

    public void setNote1(String note1) {
        this.note1 = note1;
    }

    public String getNote2() {
        return note2;
    }

    public void setNote2(String note2) {
        this.note2 = note2;
    }
}
