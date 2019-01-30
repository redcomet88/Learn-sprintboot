package com.chensi.yghy.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_access_token")
public class AccessToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String userID;

    private String accessToken;
 
    private String expiresin;
 
    private Date createdate;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }
 
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken == null ? null : accessToken.trim();
    }
 
    public String getExpiresin() {
        return expiresin;
    }
 
    public void setExpiresin(String expiresin) {
        this.expiresin = expiresin == null ? null : expiresin.trim();
    }
 
    public Date getCreatedate() {
        return createdate;
    }
 
    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }
}