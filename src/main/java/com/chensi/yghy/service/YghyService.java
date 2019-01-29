package com.chensi.yghy.service;

import com.chensi.yghy.model.Collect;
import com.chensi.yghy.model.Yghy;
import com.chensi.yghy.model.vo.YghyVO;
import com.chensi.yghy.repository.CollectRepository;
import com.chensi.yghy.repository.YghyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 一罐好运Service
 * @Author: redcomet
 * @Date: 2019-01-25-10:44
 */
@Service
public class YghyService {
    @Autowired
    private YghyRepository yghyRepository;
    @Autowired
    private CollectRepository collectRepository;

    @Transactional
    public YghyVO save(Yghy yghy){
        Yghy y = yghyRepository.save(yghy);
        Collect c = new Collect();
        List<Collect> list = new ArrayList<Collect>();
        //初始化6条祝福数据
        for(int i=0;i<6;i++) {
            c = collectRepository.save(new Collect(y.getUserID(), i, 0, "", "", ""));
            list.add(c);
        }
        return new YghyVO(y,list);
    }

    @Transactional
    public void save(String userID,String formname){
        Yghy y = yghyRepository.findByUserID(userID);
        y.setFormName(formname);
        yghyRepository.save(y);
    }

    @Transactional
    public void save(String userID,String address,String phone,String email,String contacts){
        Yghy y = yghyRepository.findByUserID(userID);
        y.setAddress(address);
        y.setPhone(phone);
        y.setEmail(email);
        y.setContacts(contacts);
        yghyRepository.save(y);
    }

    public YghyVO isNew(String userID){
        Yghy y = yghyRepository.findByUserID(userID);
        if(null == y)
            return null;
        List<Collect> list = collectRepository.findByUserID(userID);
        return new YghyVO(y,list);
    }

    @Transactional
    public String wish(String userID, int nIndex, String message,String helpID,String helpNickName){
        //先查询是否已完成
        Collect collect = collectRepository.findByUserIDAndNIndex(userID,nIndex);

        if(collect.getIsCollect() == 1)
        {
            return "THUMB_AGAIN";
        }

        collect.setMessage(message);
        collect.setHelpID(helpID);
        collect.setIsCollect(1);
        collect.setHelpNickName(helpNickName);
        collectRepository.save(collect);

        //查询一下是否已经被赞6次了,更新主表内的收集字段
        long count = collectRepository.countByUserIDAndIsCollect(userID,1);
        if( count>=6 ){
            Yghy y = yghyRepository.findByUserID(userID);
            y.setIsCollected(1);
            yghyRepository.save(y);
        }

        return "success";
    }

    public String preDraw(String userID){
        Yghy y = yghyRepository.findByUserID(userID);
       if(y.getIsCollected() != 1 )
       {
           return "NOT_FINISHED";
       }
        if(y.getIsDraw() == 1 )
        {
            return "ALREADY_DRAW";
        }

        return "OK";
    }


    public List<Collect> getCollects(String userID){
        List<Collect> list = collectRepository.findByUserID(userID);
        return list;
    }
}
