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
    public AccessToken findTokenByID(String id){
        AccessToken token = null;
        token = tokenRepository.findAccessTokenById(id);
        return token;
    }

    @Cacheable(key = "#p0")
    public void save(AccessToken token){
        tokenRepository.save(token);
    }

}
