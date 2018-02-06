package com.zj.lotteryAnalyze.dto;

/**
 * Created by Administrator on 2018/2/6.
 */
public class PredictResult {

	private boolean tenCorrect = false;
	private boolean unitCorrect = false;

	public boolean isTenCorrect() {
		return tenCorrect;
	}

	public void setTenCorrect(boolean tenCorrect) {
		this.tenCorrect = tenCorrect;
	}

	public boolean isUnitCorrect() {
		return unitCorrect;
	}

	public void setUnitCorrect(boolean unitCorrect) {
		this.unitCorrect = unitCorrect;
	}
}
