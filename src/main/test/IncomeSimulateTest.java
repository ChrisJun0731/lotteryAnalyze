import com.zj.lotteryAnalyze.dto.LotteryInfo;
import com.zj.lotteryAnalyze.service.LotteryStatOfLastTwo;
import com.zj.lotteryAnalyze.simulate.IncomeSimulate;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/6.
 */
public class IncomeSimulateTest {

	public static void main(String[] args) {
		File day28 = new File("E:\\project\\lotteryAnalyze\\src\\main\\resources\\data\\20180128.json");
		File day29 = new File("E:\\project\\lotteryAnalyze\\src\\main\\resources\\data\\20180129.json");
		File day30 = new File("E:\\project\\lotteryAnalyze\\src\\main\\resources\\data\\20180130.json");
		File day01 = new File("E:\\project\\lotteryAnalyze\\src\\main\\resources\\data\\20180201.json");
		File day02 = new File("E:\\project\\lotteryAnalyze\\src\\main\\resources\\data\\20180202.json");

		try {
			BufferedReader reader28 = new BufferedReader(new FileReader(day28));
			BufferedReader reader29 = new BufferedReader(new FileReader(day29));
			BufferedReader reader30 = new BufferedReader(new FileReader(day30));
			BufferedReader reader01 = new BufferedReader(new FileReader(day01));
			BufferedReader reader02 = new BufferedReader(new FileReader(day02));

			String json28 = reader28.readLine();
			String json29 = reader29.readLine();
			String json30 = reader30.readLine();
			String json01 = reader01.readLine();
			String json02 = reader02.readLine();

			JSONArray arr28 = JSONArray.fromObject(json28);
			JSONArray arr29 = JSONArray.fromObject(json29);
			JSONArray arr30 = JSONArray.fromObject(json30);
			JSONArray arr01 = JSONArray.fromObject(json01);
			JSONArray arr02 = JSONArray.fromObject(json02);

			List<LotteryInfo> list28 = new ArrayList<>();
			List<LotteryInfo> list29 = new ArrayList<>();
			List<LotteryInfo> list30 = new ArrayList<>();
			List<LotteryInfo> list01 = new ArrayList<>();
			List<LotteryInfo> list02 = new ArrayList<>();

			List<LotteryInfo> list = new ArrayList<>();
			LotteryStatOfLastTwo stat = new LotteryStatOfLastTwo();

			for (Object obj : arr28) {
				LotteryInfo info = new LotteryInfo();
				String number = (String) ((JSONObject) obj).get("number");
				String issueNo = (String) ((JSONObject) obj).get("issueNo");
				info.setNumber(number);
				info.setIssueNo(issueNo);
				list28.add(info);
			}
			for (Object obj : arr29) {
				LotteryInfo info = new LotteryInfo();
				String number = (String) ((JSONObject) obj).get("number");
				String issueNo = (String) ((JSONObject) obj).get("issueNo");
				info.setNumber(number);
				info.setIssueNo(issueNo);
				list29.add(info);
			}
			for (Object obj : arr30) {
				LotteryInfo info = new LotteryInfo();
				String number = (String) ((JSONObject) obj).get("number");
				String issueNo = (String) ((JSONObject) obj).get("issueNo");
				info.setNumber(number);
				info.setIssueNo(issueNo);
				list30.add(info);
			}
			for (Object obj : arr01) {
				LotteryInfo info = new LotteryInfo();
				String number = (String) ((JSONObject) obj).get("number");
				String issueNo = (String) ((JSONObject) obj).get("issueNo");
				info.setNumber(number);
				info.setIssueNo(issueNo);
				list01.add(info);
			}
			for (Object obj : arr02) {
				LotteryInfo info = new LotteryInfo();
				String number = (String) ((JSONObject) obj).get("number");
				String issueNo = (String) ((JSONObject) obj).get("issueNo");
				info.setNumber(number);
				info.setIssueNo(issueNo);
				list02.add(info);
			}

			list.addAll(list01);
			list.addAll(list02);
			list.addAll(list28);
			list.addAll(list29);
			list.addAll(list30);

			List<List<LotteryInfo>> groups = stat.groupLotterys(list);

			IncomeSimulate simulate = new IncomeSimulate();
			simulate.computeAll(groups);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
