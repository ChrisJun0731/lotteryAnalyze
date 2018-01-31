package com.zj.lotteryAnalyze.utils;

import com.zj.lotteryAnalyze.dto.LotteryInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2018/1/29.
 */
@Component
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

	/**
	 * 将一个彩票List转换为
	 * @param lotteryInfos
	 */
	public String convertListToJSON(List<LotteryInfo> lotteryInfos){
		String json = "[";
		for(LotteryInfo info: lotteryInfos){
			json += "{\"issueNo\":\""+ info.getIssueNo()+ "\",\"number\":\""+ info.getNumber() + "\"},";
		}
		json = json.substring(0, json.length()-1);
		json += "]";
		return json;
	}
}
