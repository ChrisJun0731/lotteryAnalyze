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

		for(int i=0; i<50; i++){
			LotteryInfo info = new LotteryInfo();
			String number = "";
			for(int j=0; j<5; j++){
				int random = (int)(Math.random()*10);
				number += String.valueOf(random) + " ";
			}
			number = number.substring(0, number.length() - 1);
			info.setNumber(number);
			lotteryInfos.add(info);
		}
		System.out.println("打印这组彩票：");
		for(LotteryInfo info: lotteryInfos){
			System.out.println(info.getNumber());
		}
//		int[] big = stat.countBigNumOfLastTwo(lotteryInfos);
//		System.out.println("该组中大号的个数为："+ big[0]+"/"+big[1]);
//		int[] odd = stat.countOddNumQuinaryOfLastTwo(lotteryInfos);
//		System.out.println("按五进制个位上为奇的个数为："+ odd[0]+ "/" + odd[1]);
//		int[] same = stat.countSameNumQuinaryLast(lotteryInfos);
//		System.out.println("按五进制个位上相同最大次数为: "+ same[0] + "/" + same[1]);
//		int[] diff = stat.countDiffQuinary(lotteryInfos);
//		System.out.println("按五进制个数出现不同数字的个数为："+ diff[0] + "/" + diff[1]);
//		int[] sameCon = stat.countSameContinueQuinary(lotteryInfos);
//		System.out.println("按五进制个位连续相同的最大次数为：" + sameCon[0] + "/" + sameCon[1]);
//		int[] result = stat.lastSumQuinary(lotteryInfos);
//		System.out.println("按五进制个位上的和为：" + result[0] + "/" + result[1]);

//		List<LotteryInfo> infos = stat.getHistoryData();
//		System.out.println(infos.size());

//		List<List<LotteryInfo>> list = stat.groupLotterys(lotteryInfos);

//		List<int[]> lastTwo = stat.getLastTwoNumber(lotteryInfos);

		int[][][] lastTwoQuinary = stat.getLastTwoQuinary(lotteryInfos);

	}
}
