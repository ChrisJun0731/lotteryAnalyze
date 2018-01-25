package com.zj.lotteryAnalyze.service;

import com.zj.lotteryAnalyze.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/25.
 */
@Service
public class LotteryHistory {

	public String getLotteryHistory(){
		String host = "http://jisucpkj.market.alicloudapi.com";
		String path = "/caipiao/history";
		String method = "GET";
		String appcode = "6e4a94d3fe7c46ddb7c3b93d4c4a97a7";
		Map<String, String> headers = new HashMap<String, String>();
		//最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		Map<String, String> querys = new HashMap<String, String>();
		//重庆时时彩查询 73
		querys.put("caipiaoid", "73");
		querys.put("num", "20");

		String result = null;
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
//			System.out.println(EntityUtils.toString(response.getEntity()));

			result = EntityUtils.toString(response.getEntity());
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			System.out.println(df.format(new Date()).toString() + ": 彩票查询任务执行完毕\n"+ result);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
