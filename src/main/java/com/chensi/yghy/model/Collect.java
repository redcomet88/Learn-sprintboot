package com.chensi.yghy.model;

import javax.persistence.*;

/**
 * @Description: 收集祝福类
 * @Author: redcomet
 * @Date: 2019-01-27-17:29
 */

@Entity
@Table(name = "t_collect",uniqueConstraints = {@UniqueConstraint(columnNames={"userID","nIndex"})})
public class Collect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String userID;     //用户的ID
    private int nIndex;
    private int isCollect;
    private String helpNickName;
    private String helpID;
    private String message;

    public Collect(String userID, int nIndex, int isCollect, String helpNickName, String helpID, String message) {
        this.userID = userID;
        this.nIndex = nIndex;
        this.isCollect = isCollect;
        this.helpNickName = helpNickName;
        this.helpID = helpID;
        this.message = message;
    }

    public Collect(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getnIndex() {
        return nIndex;
    }

    public void setnIndex(int nIndex) {
        this.nIndex = nIndex;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(int isCollect) {
        this.isCollect = isCollect;
    }

    public String getHelpNickName() {
        return helpNickName;
    }

    public void setHelpNickName(String helpNickName) {
        this.helpNickName = helpNickName;
    }

    public String getHelpID() {
        return helpID;
    }

    public void setHelpID(String helpID) {
        this.helpID = helpID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
