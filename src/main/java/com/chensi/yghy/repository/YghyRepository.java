package com.chensi.yghy.repository;

import com.chensi.yghy.model.Yghy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description: 一罐好运DAO
 * @Author: redcomet
 * @Date: 2019-01-25-10:43
 */
@Repository
public interface YghyRepository extends JpaRepository<Yghy,Long> {
    Yghy findByUserID(String userID);
}
