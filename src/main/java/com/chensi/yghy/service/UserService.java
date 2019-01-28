package com.chensi.yghy.service;

import com.chensi.yghy.repository.UserRepository;
import com.chensi.yghy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: redcomet
 * @Date: 2019-01-22-11:18
 */

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findUserByName(String name){
        User user = null;
        user = userRepository.findByUserName(name);
        return user;
    }
}
