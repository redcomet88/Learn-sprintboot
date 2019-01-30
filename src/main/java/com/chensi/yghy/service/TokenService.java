package com.chensi.yghy.service;

import com.chensi.yghy.model.AccessToken;
import com.chensi.yghy.repository.TokenRepository;
import com.chensi.yghy.repository.YghyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    @Autowired
    private TokenRepository tokenRepository;

    @Cacheable(key = "#p0")
    public AccessToken findTokenByUserID(String userID){
        AccessToken token = null;
        token = tokenRepository.findAccessTokenByUserID(userID);
        return token;
    }

    @Cacheable(key = "#p0")
    public void save(AccessToken token){
        tokenRepository.save(token);
    }

}
