package com.chensi.yghy.model.vo;

import com.chensi.yghy.model.Collect;
import com.chensi.yghy.model.Yghy;

import java.util.List;

/**
 * @Description:
 * @Author: redcomet
 * @Date: 2019-01-27-21:23
 */

public class YghyVO  extends Yghy {
    private List<Collect> list;

    public YghyVO(long id,String userID, String nickName, String formName, int isCollected, int isDraw, int prizeType, String address, String phone, String email, String contacts, List<Collect> list) {
        super(id,userID, nickName, formName, isCollected, isDraw, prizeType, address, phone, email, contacts);
        this.list = list;
    }

    public YghyVO(Yghy y, List<Collect> list) {
        super(y.getId(),y.getUserID(), y.getNickName(), y.getFormName(),
                y.getIsCollected(),y.getIsDraw(), y.getPrizeType(), y.getAddress(),
                y.getPhone(),y.getEmail(),y.getContacts());
        this.list = list;
    }

    public YghyVO(String userID, String nickName, List<Collect> list) {
        super(userID, nickName);
        this.list = list;
    }

    public List<Collect> getList() {
        return list;
    }

    public void setList(List<Collect> list) {
        this.list = list;
    }
}
