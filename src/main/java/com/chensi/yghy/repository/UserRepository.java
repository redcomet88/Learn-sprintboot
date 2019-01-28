package com.chensi.yghy.repository;

import com.chensi.yghy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @Description: User Dao
 * @Author: redcomet
 * @Date: 2019-01-22-11:06
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("select t from User t where t.name = :name")
    User findByUserName(@Param("name") String name);
}
