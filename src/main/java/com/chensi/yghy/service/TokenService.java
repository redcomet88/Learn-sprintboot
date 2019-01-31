package com.chensi.yghy.service;

import com.chensi.yghy.model.AccessToken;
import com.chensi.yghy.repository.TokenRepository;
import com.chensi.yghy.repository.YghyRepository;
import com.chensi.yghy.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
@CacheConfig(cacheNames = "TokenCache")
public class TokenService {
    @Autowired
    private TokenRepository tokenRepository;


    public String getaccessToken(String userID,String APPID,String APPSECRET,String LIVE_SECONDS) throws IOException {
        String access_token = null;
        AccessToken accessToken = this.findTokenByUserID(userID);

        //如果accessToken不存在，新建
        if (accessToken == null) {
            //去微信拿access_token
            access_token = AuthUtil.getAccessToken(APPID, APPSECRET);
            accessToken = new AccessToken();
            accessToken.setUserID(userID);
            accessToken.setAccessToken(access_token);
            accessToken.setExpiresin(LIVE_SECONDS);
            accessToken.setCreatedate(new Date());
            //
            this.save(accessToken);
        }//如果存在但超时，需要更新
        else if(accessToken.getCreatedate().getTime() + Long.parseLong(accessToken.getExpiresin()) * 1000 < new Date().getTime()) {
            //去微信拿access_token
            access_token = AuthUtil.getAccessToken(APPID, APPSECRET);
            accessToken.setAccessToken(access_token);
            accessToken.setExpiresin(LIVE_SECONDS);
            accessToken.setCreatedate(new Date());
            this.save(accessToken);
        }
        return access_token;
    }

    @Cacheable(key = "#userID")
    public AccessToken findTokenByUserID(String userID){
        AccessToken token = null;
        token = tokenRepository.findByUserID(userID);
        return token;
    }

    @CachePut(key = "#token.getUserID()")
    public AccessToken save(AccessToken token){
        AccessToken tk = tokenRepository.save(token);
        return tk;
    }

}
