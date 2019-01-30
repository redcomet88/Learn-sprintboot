package com.chensi.yghy.repository;

import com.chensi.yghy.model.AccessToken;
import com.chensi.yghy.model.Collect;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: redcomet
 * @Date: 2019-01-27-21:19
 */

@Repository
public interface TokenRepository extends JpaRepository<AccessToken,Long> {


    AccessToken findAccessTokenByUserID(String userID);

}
