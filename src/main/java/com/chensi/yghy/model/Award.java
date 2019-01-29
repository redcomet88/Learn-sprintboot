package com.chensi.yghy.model;

import javax.persistence.*;

/**
 * @Description: 抽奖类
 * @Author: redcomet
 * @Date: 2019-01-25-9:52
 */
@Entity
@Table(name = "t_award")
public class Award {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String awardName;  //奖品名

    private double probability;//获奖概率

    private int    amount;     //奖品库存

    private String contents;   //定制的内容(Invalid)

    private String note1;      //备用1

    private String note2;      //备用2

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getNote1() {
        return note1;
    }

    public void setNote1(String note1) {
        this.note1 = note1;
    }

    public String getNote2() {
        return note2;
    }

    public void setNote2(String note2) {
        this.note2 = note2;
    }
}
