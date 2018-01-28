package com.zj.lotteryAnalyze.aliyunApi;

import com.zj.lotteryAnalyze.dto.LotteryInfo;

import java.util.List;

/**
 * Created by Administrator on 2018/1/27.
 */
public class Test {
	public static void main(String[] args) {
		YiYuanHistory history = new YiYuanHistory();
		List<List<LotteryInfo>> list = history.getLotteryHisOfDays(10);
		System.out.println(list.size());
	}
}
