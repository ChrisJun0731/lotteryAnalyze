package com.zj.lotteryAnalyze.aliyunApi;

import com.zj.lotteryAnalyze.dto.LotteryInfo;
import com.zj.lotteryAnalyze.utils.HttpUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018/1/26.
 */
public class YiYuanHistory {

	public static void main(String[] args) {
		//test1
//		String result = getYiYuanHistory("2018-01-28 21:15:00");
//		System.out.println(result);

		//test2
//		List<LotteryInfo> lotteryInfos = getLotteryHistory(Calendar.getInstance());
//		for(LotteryInfo lotteryInfo: lotteryInfos){
//			System.out.println(lotteryInfo.getDate() + ": " + lotteryInfo.getIssueNo() + " number is: " + lotteryInfo.getNumber());
//		}

		//test3
//		List<List<LotteryInfo>> list = getLotteryHisOfDays(3);


	}

	protected String getYiYuanHistory(String date) {
		String host = "https://ali-lottery.showapi.com";
		String path = "/multi";
		String method = "GET";
		String appcode = "6e4a94d3fe7c46ddb7c3b93d4c4a97a7";
		Map<String, String> headers = new HashMap<String, String>();
		//最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		Map<String, String> querys = new HashMap<String, String>();

		//重庆时时彩cqssc
		querys.put("code", "cqssc");
		querys.put("count", "50");
		querys.put("endTime", date);
		String resBody = null;
		try {
			/**
			 * 重要提示如下:
			 * HttpUtils请从
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
			 * 下载
			 *
			 * 相应的依赖请参照
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
			 */
			HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
//			System.out.println(response.toString());
			//获取response的body
			//System.out.println(EntityUtils.toString(response.getEntity()));
			resBody = EntityUtils.toString(response.getEntity());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resBody;
	}

	/**
	 * 获取Calendar日期当日的历史彩票数据
	 * @param c 日期
	 * @return 彩票开奖历史
	 */
	protected List<LotteryInfo> getLotteryHistory(Calendar c){

		List<LotteryInfo> lotteryInfos = new ArrayList<>();

		String jsonStr = getYiYuanHistory(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(c.getTimeInMillis()));
		System.out.println("打印从api返回的数据:" + jsonStr);

		JSONObject obj = JSONObject.fromObject(jsonStr);
		JSONObject body = (JSONObject)obj.get("showapi_res_body");
		JSONArray list = (JSONArray)body.get("result");
		System.out.println("彩票的期数为:" + list.size());

		for(Object lottery: list){
			LotteryInfo info = new LotteryInfo();
			String expect = (String)((JSONObject) lottery).get("expect");
			String time = (String) ((JSONObject) lottery).get("time");
			String openCode = (String) ((JSONObject) lottery).get("openCode");
			info.setIssueNo(expect);
			info.setDate(time);
			info.setNumber(openCode);
			lotteryInfos.add(info);
		}

		return lotteryInfos;

	}

	/**
	 * 创建num天彩票历史数据
	 * @param num 天数
	 * @return 日期列表
	 */
	private List<Calendar> createCalendarList(int num){

		List<Calendar> list = new ArrayList();

		for(int i=0; i<num; i++){
			Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			c.set(year, month, day, 24, 0, 0);
			c.add(Calendar.DATE, -i);
			list.add(c);
		}

		return list;
	}

	public List<List<LotteryInfo>> getLotteryHisOfDays(int num){

		List<List<LotteryInfo>> list = new ArrayList<>();
		List<Calendar> cList = createCalendarList(num);

		for(Calendar c: cList){
			List<LotteryInfo> lotteryInfos = getLotteryHistory(c);
			list.add(lotteryInfos);
	}

		return list;
	}
}
