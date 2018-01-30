import com.zj.lotteryAnalyze.dto.LotteryInfo;
import com.zj.lotteryAnalyze.service.LotteryStatOfLastTwo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 */
public class LotteryStatTest {

	public static void main(String[] args) {
		LotteryStatOfLastTwo stat = new LotteryStatOfLastTwo();
		List<LotteryInfo> lotteryInfos = new ArrayList<>();

		for(int i=0; i<6; i++){
			LotteryInfo info = new LotteryInfo();
//			String number = "";
//			for(int j=0; j<5; j++){
//				int random = (int)(Math.random()*10);
//				number += String.valueOf(random) + ",";
//			}
//			number = number.substring(0, number.length() - 1);
//			info.setNumber(number);
			info.setNumber("1,2,3,4,5");
			lotteryInfos.add(info);
		}

		int[] result = stat.lastSumQuinary(lotteryInfos);
		System.out.println(result[0] + "  " + result[1]);
	}
}
