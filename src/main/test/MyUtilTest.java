import com.zj.lotteryAnalyze.dto.LotteryInfo;
import com.zj.lotteryAnalyze.utils.MyUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/31.
 */
public class MyUtilTest {

	public static void main(String[] args) {
		List<LotteryInfo> lotteryInfos = new ArrayList<>();

		for(int i=0; i<6; i++){
			LotteryInfo info = new LotteryInfo();
			String number = "";
			for(int j=0; j<5; j++){
				int random = (int)(Math.random()*10);
				number += String.valueOf(random) + " ";
			}
			number = number.substring(0, number.length() - 1);
			info.setNumber(number);
			info.setIssueNo("20180131");
			lotteryInfos.add(info);
		}

		MyUtil util = new MyUtil();
		String json = util.convertListToJSON(lotteryInfos);
		System.out.println(json);
	}
}
