import com.zj.lotteryAnalyze.dto.LotteryInfo;
import com.zj.lotteryAnalyze.service.NextThreePredict;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/4.
 */
public class NextThreePredictTest {

	public static void main(String[] args) {

		List<LotteryInfo> lotteryInfos = new ArrayList<>();

		for(int i=0; i<3; i++){
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
		for(LotteryInfo info: lotteryInfos){
			System.out.println(info.getNumber());
		}

		NextThreePredict predict = new NextThreePredict(lotteryInfos);
	}
}
