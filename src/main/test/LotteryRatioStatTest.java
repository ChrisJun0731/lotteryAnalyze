import com.zj.lotteryAnalyze.dto.LotteryInfo;
import com.zj.lotteryAnalyze.service.LotteryRatioStat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/2.
 */
public class LotteryRatioStatTest {

	public static void main(String[] args) {

//		LotteryRatioStat stat = new LotteryRatioStat();
//		List<LotteryInfo> lotteryInfos = new ArrayList<>();
//
//
//		for(int i=0; i<601; i++){
//			LotteryInfo info = new LotteryInfo();
//			String number = "";
//			for(int j=0; j<5; j++){
//				int random = (int)(Math.random()*10);
//				number += String.valueOf(random) + " ";
//			}
//			number = number.substring(0, number.length() - 1);
//			info.setNumber(number);
//			lotteryInfos.add(info);
//		}
//
//		for(LotteryInfo info: lotteryInfos){
//			System.out.println(info.getNumber());
//		}
//
//		stat.computeRate(lotteryInfos);

		float ratio = (float)Math.round((float)8/24*100)/100;
		System.out.println((float)Math.round((float)8/23*100)/100);
		System.out.println(ratio);

	}
}
