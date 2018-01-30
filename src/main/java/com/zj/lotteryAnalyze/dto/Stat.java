package com.zj.lotteryAnalyze.dto;

/**
 * Created by Administrator on 2018/1/30.
 */
public class Stat {

	private int[] bigNumTimes;
	private int[] oddNumTimes;
	private int[] sameNumTimes;
	private int[] diffNums;
	private int[] sameConTimes;
	private int[] lastSum;

	public int[] getBigNumTimes() {
		return bigNumTimes;
	}

	public void setBigNumTimes(int[] bigNumTimes) {
		this.bigNumTimes = bigNumTimes;
	}

	public int[] getOddNumTimes() {
		return oddNumTimes;
	}

	public void setOddNumTimes(int[] oddNumTimes) {
		this.oddNumTimes = oddNumTimes;
	}

	public int[] getSameNumTimes() {
		return sameNumTimes;
	}

	public void setSameNumTimes(int[] sameNumTimes) {
		this.sameNumTimes = sameNumTimes;
	}

	public int[] getDiffNums() {
		return diffNums;
	}

	public void setDiffNums(int[] diffNums) {
		this.diffNums = diffNums;
	}

	public int[] getSameConTimes() {
		return sameConTimes;
	}

	public void setSameConTimes(int[] sameConTimes) {
		this.sameConTimes = sameConTimes;
	}

	public int[] getLastSum() {
		return lastSum;
	}

	public void setLastSum(int[] lastSum) {
		this.lastSum = lastSum;
	}
}
