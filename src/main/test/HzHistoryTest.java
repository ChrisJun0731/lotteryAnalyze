import com.zj.lotteryAnalyze.aliyunApi.HzHistory;
import com.zj.lotteryAnalyze.dto.LotteryInfo;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2018/1/27.
 */
public class HzHistoryTest {
	public static void main(String[] args) {

		HzHistory history = new HzHistory();
//		List<LotteryInfo> lotteryInfoList = history.getLotteryHistory("20180130-020");
//		System.out.println(lotteryInfoList.toString());

//		history.getLotteryOfDate(Calendar.getInstance());
		List<LotteryInfo> lotteryInfos = history.getLotteryHistory("20180130-001");
	}
}
