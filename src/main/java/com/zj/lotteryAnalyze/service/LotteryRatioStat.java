package com.zj.lotteryAnalyze.service;

import com.zj.lotteryAnalyze.dto.LotteryInfo;
import com.zj.lotteryAnalyze.dto.RatioStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/2/2.
 */
@Service
public class LotteryRatioStat {

	@Autowired
	private LotteryStatOfLastTwo lastStat;

	/**
	 * 计算多期彩票中，分组后，按不同的统计方式，每个值在组出现的概率
	 * @param lotteryInfos
	 * @return
	 */
	public Map<String, RatioStat> computeRate(List<LotteryInfo> lotteryInfos){

		Map<String, RatioStat> map = new HashMap<>();

		List<List<LotteryInfo>> groups = lastStat.groupLotterys(lotteryInfos);
		RatioStat bigNumRatio = computeBigNumRate(groups);
		RatioStat oddNumRatio = computeOddNumRate(groups);
		RatioStat sameNumRatio = computeSameNumRate(groups);
		RatioStat diffNumRatio = computeDiffNumRate(groups);
		RatioStat sameConRatio = computeSameConRate(groups);

		map.put("bigNumRatio", bigNumRatio);
		map.put("oddNumRatio", oddNumRatio);
		map.put("sameNumRatio", sameNumRatio);
		map.put("diffNumRatio", diffNumRatio);
		map.put("sameConRatio", sameConRatio);

		return map;
	}

	/**
	 * 计算每组中大数出现的不同次数的概率
	 * @param groups
	 * @return
	 */
	public RatioStat computeBigNumRate(List<List<LotteryInfo>> groups) {

		int groupSize = groups.size();
		List<int[]> groupResult = new ArrayList<>();

		for (List<LotteryInfo> lotteryInfos : groups) {
			int[] bigNum = lastStat.countBigNumOfLastTwo(lotteryInfos);
			groupResult.add(bigNum);
		}
		RatioStat ratio = computeFromResult(groupResult, groupSize);

		return ratio;
	}

	/**
	 * 计算分组中五进制个数出现不同奇数次数的概率
	 * @param groups
	 * @return
	 */
	public RatioStat computeOddNumRate(List<List<LotteryInfo>> groups) {

		int groupSize = groups.size();
		List<int[]> groupResult = new ArrayList<>();

		for(List<LotteryInfo> lotteryInfos: groups){
			int[] oddNum = lastStat.countOddNumQuinaryOfLastTwo(lotteryInfos);
			groupResult.add(oddNum);
		}
		RatioStat ratio = computeFromResult(groupResult, groupSize);

		return ratio;
	}

	/**
	 * 计算每组中五进制个位相同的最大次数的概率
	 * @param groups
	 * @return
	 */
	public RatioStat computeSameNumRate(List<List<LotteryInfo>> groups){

		int groupSize = groups.size();
		List<int[]> groupResult = new ArrayList<>();

		for(List<LotteryInfo> lotteryInfos: groups){
			int[] sameNum = lastStat.countSameNumQuinaryLast(lotteryInfos);
			groupResult.add(sameNum);
		}
		RatioStat ratio = computeFromResult(groupResult, groupSize);

		return ratio;
	}

	/**
	 * 计算分组中五进制个位不同的个数的概率
	 * @param groups
	 * @return
	 */
	public RatioStat computeDiffNumRate(List<List<LotteryInfo>> groups){

		int groupSize = groups.size();
		List<int[]> groupResult = new ArrayList<>();

		for(List<LotteryInfo> lotteryInfos: groups){
			int[] diffNum = lastStat.countDiffQuinary(lotteryInfos);
			groupResult.add(diffNum);
		}
		RatioStat ratio = computeFromResult(groupResult, groupSize);

		return ratio;
	}

	/**
	 * 计算分组中五进制个位连续相同的期数的概率
	 * @param groups
	 * @return
	 */
	public RatioStat computeSameConRate(List<List<LotteryInfo>> groups){

		int groupSize = groups.size();
		List<int[]> groupResult = new ArrayList<>();

		for(List<LotteryInfo> lotteryInfos: groups){
			int[] sameCon= lastStat.countSameContinueQuinary(lotteryInfos);
			groupResult.add(sameCon);
		}
		RatioStat ratio = computeFromResult(groupResult, groupSize);

		return ratio;
	}

	/**
	 * 根据每组的统计计算概率
 	 * @param groupResult
	 * @param groupSize
	 * @return
	 */
	public RatioStat computeFromResult(List<int[]> groupResult, int groupSize){

		Map<Integer, Integer> tenMap = new HashMap<>();
		Map<Integer, Integer> unitMap = new HashMap<>();
		RatioStat ratio = new RatioStat();

		for (int[] result : groupResult) {
			if (!tenMap.containsKey(result[0])) {
				tenMap.put(result[0], 1);
			} else {
				tenMap.put(result[0], tenMap.get(result[0]) + 1);
			}
			if (!unitMap.containsKey(result[1])) {
				unitMap.put(result[1], 1);
			} else {
				unitMap.put(result[1], unitMap.get(result[1]) + 1);
			}
		}
		for (int i = 0; i <= 6; i++) {
			if (tenMap.containsKey(i)) {
				switch (i) {
					case 0:
						ratio.setZeroRateTen((float)Math.round((float)tenMap.get(i) / groupSize * 100)/100);
						break;
					case 1:
						ratio.setOneRateTen((float)Math.round((float)tenMap.get(i) / groupSize * 100)/100);
						break;
					case 2:
						ratio.setTwoRateTen((float)Math.round((float)tenMap.get(i) / groupSize * 100)/100);
						break;
					case 3:
						ratio.setThreeRateTen((float)Math.round((float)tenMap.get(i) / groupSize * 100)/100);
						break;
					case 4:
						ratio.setFourRateTen((float)Math.round((float)tenMap.get(i) / groupSize * 100)/100);
						break;
					case 5:
						ratio.setFiveRateTen((float)Math.round((float)tenMap.get(i) / groupSize * 100)/100);
						break;
					case 6:
						ratio.setSixRateTen((float)Math.round((float)tenMap.get(i) / groupSize * 100)/100);
						break;
				}
			} else {
				switch (i) {
					case 0:
						ratio.setZeroRateTen(0);
						break;
					case 1:
						ratio.setOneRateTen(0);
						break;
					case 2:
						ratio.setTwoRateTen(0);
						break;
					case 3:
						ratio.setThreeRateTen(0);
						break;
					case 4:
						ratio.setFourRateTen(0);
						break;
					case 5:
						ratio.setFiveRateTen(0);
						break;
					case 6:
						ratio.setSixRateTen(0);
						break;
				}
			}
		}
		for (int i = 0; i <= 6; i++) {
			if (unitMap.containsKey(i)) {
				switch (i) {
					case 0:
						ratio.setZeroRateUnit((float)Math.round((float)unitMap.get(i) / groupSize *100)/100);
						break;
					case 1:
						ratio.setOneRateUnit((float)Math.round((float)unitMap.get(i) / groupSize * 100)/100);
						break;
					case 2:
						ratio.setTwoRateUnit((float)Math.round((float)unitMap.get(i) / groupSize * 100)/100);
						break;
					case 3:
						ratio.setThreeRateUnit((float)Math.round((float)unitMap.get(i) / groupSize * 100)/100);
						break;
					case 4:
						ratio.setFourRateUnit((float)Math.round((float)unitMap.get(i) / groupSize * 100)/100);
						break;
					case 5:
						ratio.setFiveRateUnit((float)Math.round((float)unitMap.get(i) / groupSize * 100)/100);
						break;
					case 6:
						ratio.setSixRateUnit((float)Math.round((float)unitMap.get(i) / groupSize * 100)/100);
						break;
				}
			} else {
				switch (i) {
					case 0:
						ratio.setZeroRateUnit(0);
						break;
					case 1:
						ratio.setOneRateUnit(0);
						break;
					case 2:
						ratio.setTwoRateUnit(0);
						break;
					case 3:
						ratio.setThreeRateUnit(0);
						break;
					case 4:
						ratio.setFourRateUnit(0);
						break;
					case 5:
						ratio.setFiveRateUnit(0);
						break;
					case 6:
						ratio.setSixRateUnit(0);
						break;
				}
			}
		}

		return ratio;
	}


}
