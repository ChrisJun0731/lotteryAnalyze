import com.zj.lotteryAnalyze.aliyunApi.HzHistory;
import com.zj.lotteryAnalyze.dto.LotteryInfo;

import java.util.List;

/**
 * Created by Administrator on 2018/1/27.
 */
public class HzHistoryTest {
	public static void main(String[] args) {

		HzHistory history = new HzHistory();
		List<LotteryInfo> lotteryInfoList = history.getLotteryHistory();
	}
}
