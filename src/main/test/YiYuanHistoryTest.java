import com.zj.lotteryAnalyze.aliyunApi.YiYuanHistory;
import com.zj.lotteryAnalyze.dto.LotteryInfo;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2018/1/27.
 */
public class YiYuanHistoryTest {

	public static void main(String[] args){
		YiYuanHistory history = new YiYuanHistory();
		List<List<LotteryInfo>> list = history.getLotteryHisOfDays(10);

//		List<LotteryInfo> lotteryInfos = history.getLotteryHistory(list);
//		System.out.println(lotteryInfos.size());
	}


}
