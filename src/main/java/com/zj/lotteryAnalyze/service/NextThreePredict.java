package com.zj.lotteryAnalyze.service;

import com.zj.lotteryAnalyze.dto.LotteryInfo;
import com.zj.lotteryAnalyze.dto.LotteryPredict;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2018/2/4.
 */
public class NextThreePredict {

	private LotteryStatOfLastTwo stat = new LotteryStatOfLastTwo();
	private int[] bigNums;
	private int[] oddNums;
	private int[] maxSame;
	private int[] diffNum;
	private int[] sameCon;
	private int[][][] quainary;
	private Set<Integer> bigNumSetTen;
	private Set<Integer> bigNumSetUnit;
	private Set<Integer> oddNumSetTen;
	private Set<Integer> oddNumSetUnit;
	private Set<Integer> maxSameSetTen;
	private Set<Integer> maxSameSetUnit;
	private Set<Integer> diffNumSetTen;
	private Set<Integer> diffNumSetUnit;
	private Set<Integer> sameConSetTen;
	private Set<Integer> sameConSetUnit;

	private Set<Integer> tenPre;
	private Set<Integer> unitPre;
	private LotteryPredict lotteryPredict = new LotteryPredict();

	public NextThreePredict(List<LotteryInfo> existLotteryInfos) {
		bigNums = stat.countBigNumOfLastTwo(existLotteryInfos);
		oddNums = stat.countOddNumQuinaryOfLastTwo(existLotteryInfos);
		maxSame = stat.countSameNumQuinaryLast(existLotteryInfos);
		diffNum = stat.countDiffQuinary(existLotteryInfos);
		sameCon = stat.countSameContinueQuinary(existLotteryInfos);
		quainary = stat.getLastTwoQuinary(existLotteryInfos);

		bigNumSetTen = new HashSet();
		bigNumSetUnit = new HashSet();
		oddNumSetTen = new HashSet<>();
		oddNumSetUnit = new HashSet<>();
		maxSameSetTen = new HashSet<>();
		maxSameSetUnit = new HashSet<>();
		diffNumSetTen = new HashSet<>();
		diffNumSetUnit = new HashSet<>();
		sameConSetTen = new HashSet<>();
		sameConSetUnit = new HashSet<>();



		predictByBigNumRule();
		predictByOddNumRule();
		predictByMaxSameRule(existLotteryInfos);
		predictByDiffNumRule(existLotteryInfos);
		predictBySameConRule();

		tenPre = intersect(intersect(intersect(intersect(bigNumSetTen, oddNumSetTen), maxSameSetTen), diffNumSetTen), sameConSetTen);
		unitPre = intersect(intersect(intersect(intersect(bigNumSetUnit, oddNumSetUnit), maxSameSetUnit), diffNumSetUnit), sameConSetUnit);

		lotteryPredict.setTenPreSet(tenPre);
		lotteryPredict.setUnitPreSet(unitPre);
	}

	/**
	 * 如果大数出现的次数小于1，那么接下来的三期中会有大数出现
	 */
	public void predictByBigNumRule() {

		if (bigNums[0] < 1) {
			bigNumSetTen.add(5);
			bigNumSetTen.add(6);
			bigNumSetTen.add(7);
			bigNumSetTen.add(8);
			bigNumSetTen.add(9);
			lotteryPredict.getTenRuleSet().add("bigNumRule");
		}
		if (bigNums[1] < 1) {
			bigNumSetUnit.add(5);
			bigNumSetUnit.add(6);
			bigNumSetUnit.add(7);
			bigNumSetUnit.add(8);
			bigNumSetUnit.add(9);
			lotteryPredict.getUnitRuleSet().add("bigNumRule");
		}
	}

	/**
	 * 如果五进制个位上奇数出现的次数小于1次，那么接下来的3期会有奇数出现
	 */
	public void predictByOddNumRule() {

		if (oddNums[0] < 1) {
			oddNumSetTen.add(1);
			oddNumSetTen.add(3);
			oddNumSetTen.add(6);
			oddNumSetTen.add(8);
			lotteryPredict.getTenRuleSet().add("oddNumRule");
		}
		if (oddNums[1] < 1) {
			oddNumSetUnit.add(1);
			oddNumSetUnit.add(3);
			oddNumSetUnit.add(6);
			oddNumSetUnit.add(8);
			lotteryPredict.getUnitRuleSet().add("oddNumRule");
		}
	}

	/**
	 * 如果最大重复次数小于2，即还没有重复的数，那么接下来的3期会出现重复的数。
	 */
	public void predictByMaxSameRule(List<LotteryInfo> existLotteryInfos) {
		if (maxSame[0] < 2) {
			for (int i = 0; i < existLotteryInfos.size(); i++) {
				int unit = quainary[i][0][1];
				maxSameSetTen.add(unit);
				maxSameSetTen.add(unit + 5);
			}
			lotteryPredict.getTenRuleSet().add("maxSameRule");
		}
		if (maxSame[1] < 2) {
			for (int i = 0; i < existLotteryInfos.size(); i++) {
				int unit = quainary[i][1][1];
				maxSameSetUnit.add(unit);
				maxSameSetUnit.add(unit + 5);
			}
			lotteryPredict.getUnitRuleSet().add("maxSameRule");
		}
	}

	/**
	 * 如果五进制个位上出现不同值的次数小于3，那么接下来的3期将会有未出现过的值出现。
	 *
	 * @param existLotteryInfos
	 */
	public void predictByDiffNumRule(List<LotteryInfo> existLotteryInfos) {
		if (diffNum[0] < 3) {
			Set<Integer> setTemp = new HashSet<>();
			for (int i = 0; i < existLotteryInfos.size(); i++) {
				int unit = quainary[i][0][1];
				setTemp.add(unit);
			}
			for (int i = 0; i <= 4; i++) {
				if (!setTemp.contains(i)) {
					diffNumSetTen.add(i);
					diffNumSetTen.add(i + 5);
				}
			}
			lotteryPredict.getTenRuleSet().add("diffNumRule");
		}
		if (diffNum[1] < 3) {
			Set<Integer> setTemp = new HashSet<>();
			for (int i = 0; i < existLotteryInfos.size(); i++) {
				int unit = quainary[i][1][1];
				setTemp.add(unit);
			}
			for (int i = 0; i <= 4; i++) {
				if (!setTemp.contains(i)) {
					diffNumSetUnit.add(i);
					diffNumSetUnit.add(i + 5);
				}
			}
			lotteryPredict.getUnitRuleSet().add("diffNumRule");
		}

	}

	/**
	 * 如果五进制个位连续相同的次数超过3次，那么下次个位不会出现该值。
	 */
	public void predictBySameConRule() {
		if (sameCon[0] == 3) {
			int unit = quainary[0][0][1];
			for (int i = 0; i <= 4; i++) {
				if (i != unit) {
					sameConSetTen.add(i);
					sameConSetTen.add(i + 5);
				}
			}
			lotteryPredict.getTenRuleSet().add("sameConRule");
		}
		if (sameCon[1] == 3) {
			int unit = quainary[0][1][1];
			for (int i = 0; i <= 4; i++) {
				if (i != unit) {
					sameConSetUnit.add(i);
					sameConSetUnit.add(i + 5);
				}
			}
			lotteryPredict.getUnitRuleSet().add("sameConRule");
		}
	}

	/**
	 * 求两个集合的交集
	 *
	 * @param set1
	 * @param set2
	 * @return
	 */
	public Set<Integer> intersect(Set<Integer> set1, Set<Integer> set2) {

		Set<Integer> result = new HashSet<>();

		if (set1 == null || set1.size() == 0) {
			return set2;
		}
		if (set2 == null || set2.size() == 0) {
			return set1;
		}
		for (Integer num : set1) {
			if (set2.contains(num)) {
				result.add(num);
			}
		}

		return result;
	}

	public Set<Integer> getTenPre() {
		return tenPre;
	}

	public LotteryPredict getLotteryPredict() {
		return lotteryPredict;
	}

	public void setLotteryPredict(LotteryPredict lotteryPredict) {
		this.lotteryPredict = lotteryPredict;
	}
}
