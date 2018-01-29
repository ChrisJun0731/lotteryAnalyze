package com.zj.lotteryAnalyze.utils;

/**
 * Created by Administrator on 2018/1/29.
 */
public class MyUtil {

	/**
	 * 将一个0~9的数，用五进制表示
	 * @param num
	 * @return
	 */
	public int[] conventNumToQuinary(int num){

		int[] result = new int[2];

		result[0] = num/5;
		result[1] = num%5;

		return result;
	}
}
