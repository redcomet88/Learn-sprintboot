package com.chensi.yghy.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_access_token")
public class AccessToken {

    private String id;
 
    private String accessToken;
 
    private String expiresin;
 
    private Date createdate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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