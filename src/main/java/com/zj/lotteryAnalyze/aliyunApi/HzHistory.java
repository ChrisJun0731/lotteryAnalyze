package com.zj.lotteryAnalyze.aliyunApi;

import com.zj.lotteryAnalyze.dto.LotteryInfo;
import com.zj.lotteryAnalyze.utils.HttpUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018/1/26.
 */
@Service
public class HzHistory {

	/**
	 * 获取issueNo之前的20期历史数据
	 * @param issueNo
	 * @return 字符串
	 */
	public String getHzLotteryHistory(String issueNo) {
		String host = "http://jisucpkj.market.alicloudapi.com";
		String path = "/caipiao/history";
		String method = "GET";
		String appcode = "6e4a94d3fe7c46ddb7c3b93d4c4a97a7";
		Map<String, String> headers = new HashMap<String, String>();
		//最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		Map<String, String> querys = new HashMap<String, String>();
		//充实时时彩 73
		querys.put("caipiaoid", "73");
		querys.put("issueno", issueNo);
		querys.put("num", "20");

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
			System.out.println("打印从api返回的数据"+resBody);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resBody;
	}

	/**
	 * 获取issueNo之前的20期彩票数据
	 * @param issueNo
	 * @return List<LotteryInfo>
	 */
	public List<LotteryInfo> getLotteryHistory(String issueNo){

		List<LotteryInfo> lotteryInfos = new ArrayList<>();

		String jsonStr = getHzLotteryHistory(issueNo);
		JSONObject obj = JSONObject.fromObject(jsonStr);
		JSONObject result = (JSONObject)obj.get("result");
		JSONArray list = (JSONArray)result.get("list");
		for(Object lottery: list){
			LotteryInfo info = new LotteryInfo();

			String issueno = (String)((JSONObject) lottery).get("issueno");
			String opendate = (String) ((JSONObject) lottery).get("opendate");
			String number = (String) ((JSONObject) lottery).get("number");
			info.setDate(opendate);
			info.setIssueNo(issueno);
			info.setNumber(number);
			lotteryInfos.add(info);
		}

		return lotteryInfos;
	}

	/**
	 * 获取某一天的彩票历史数据
	 * 时时彩每天120期
	 * @param c
	 * @return
	 */
	public List<LotteryInfo> getLotteryOfDate(Calendar c){

		List<LotteryInfo> list = new ArrayList<>();
		String date = new SimpleDateFormat("yyyyMMdd").format(c.getTimeInMillis());
		String issueNo1 = date + "-021";
		String issueNo2 = date + "-041";
		String issueNo3 = date + "-061";
		String issueNo4 = date + "-081";
		String issueNo5 = date + "-101";
		c.add(Calendar.DATE, 1);
		date = new SimpleDateFormat("yyyyMMdd").format(c.getTimeInMillis());
		String issueNo6 = date + "-001";

		List<String> issueNoList = new ArrayList<>();
		issueNoList.add(issueNo1);
		issueNoList.add(issueNo2);
		issueNoList.add(issueNo3);
		issueNoList.add(issueNo4);
		issueNoList.add(issueNo5);
		issueNoList.add(issueNo6);

		for(String issueNo: issueNoList){
			List<LotteryInfo> lotteryInfos = getLotteryHistory(issueNo);
			list.addAll(lotteryInfos);
		}

		return list;
	}
}
