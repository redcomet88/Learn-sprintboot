package com.chensi.yghy.repository;

import com.chensi.yghy.model.Collect;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Description:
 * @Author: redcomet
 * @Date: 2019-01-27-21:19
 */

public interface CollectRepository  extends JpaRepository<Collect,Long> {
    List<Collect> findByUserID(String userID);

    Collect findByUserIDAndNIndex(String userID,int nIndex);

    long countByUserIDAndIsCollect(String userID,int isCollect);

    long countByUserIDAndHelpID(String userID, String helpID);
}
