package com.chensi.yghy.repository;

import com.chensi.yghy.model.Award;
import com.chensi.yghy.model.Yghy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 一罐好运DAO
 * @Author: redcomet
 * @Date: 2019-01-25-10:43
 */
@Repository
public interface AwardRepository extends JpaRepository<Award,Long> {
    @Override
    List<Award> findAll();

    List<Award> findByAmountGreaterThan(int amount);
}
