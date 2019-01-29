package com.chensi.yghy.util;

import com.chensi.yghy.model.Award;

import java.util.*;

public class LotteryUtil {
    /**
     * 抽奖，获取中奖奖品
     *
     * @param awardList 奖品及中奖概率列表
     * @return 中奖商品
     */
    public static Award lottery(List<Award> awardList) {
        if (awardList.isEmpty()) {
            //奖品没有了
            return null;
        } //奖品总数
        int size = awardList.size();
        //计算总概率
        double sumProbability = 0d;
        for (Award award : awardList) {
            sumProbability += award.getProbability();
        }
        //计算每个奖品的概率区间
        // 例如奖品A概率区间0-0.1  奖品B概率区间 0.1-0.5 奖品C概率区间0.5-1
        // 每个奖品的中奖率越大，所占的概率区间就越大
        List<Double> sortAwardProbabilityList = new ArrayList<Double>(size);
        Double tempSumProbability = 0d;
        for (Award award : awardList) {
            tempSumProbability += award.getProbability();
            sortAwardProbabilityList.add(tempSumProbability / sumProbability);
        }
        //产生0-1之间的随机数 //随机数在哪个概率区间内，则是哪个奖品
        double randomDouble = Math.random();
        //加入到概率区间中，排序后，返回的下标则是awardList中中奖的下标
        sortAwardProbabilityList.add(randomDouble);
        Collections.sort(sortAwardProbabilityList);
        int lotteryIndex = sortAwardProbabilityList.indexOf(randomDouble);
        return awardList.get(lotteryIndex);
    }

}
