package com.zj.lotteryAnalyze.dto;

/**
 * Created by Administrator on 2018/1/25.
 */
public class LotteryInfo {
	private String issueNo;
	private String number;
	private String date;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getIssueNo() {
		return issueNo;
	}

	public void setIssueNo(String issueNo) {
		this.issueNo = issueNo;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
}
