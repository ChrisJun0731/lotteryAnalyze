package com.zj.lotteryAnalyze.schedule;

import com.zj.lotteryAnalyze.dto.LotteryInfo;
import com.zj.lotteryAnalyze.service.LotteryHistory;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/25.
 */
@Component
public class PushLotteryInfo {

	@Autowired
	private LotteryHistory history;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@Scheduled(cron="0 3/10 * * * ?")
	@SendTo("/topic/lotteryInfo")
	public String pushLotteryInfo(){
		List<LotteryInfo> lotteryInfos = new ArrayList<LotteryInfo>();
		String jsonStr = history.getLotteryHistory();
		JSONObject jsonObj = JSONObject.fromObject(jsonStr);
		JSONObject result = (JSONObject)jsonObj.get("result");
		JSONArray list = (JSONArray) result.get("list");
		for(Object obj: list){
			LotteryInfo info = new LotteryInfo();
			info.setIssueNo((String)((JSONObject)obj).get("issueno"));
			info.setNumber((String)((JSONObject)obj).get("number"));
			info.setDate((String)((JSONObject)obj).get("opendate"));
			lotteryInfos.add(info);
		}
		messagingTemplate.convertAndSend("/topic/lotteryInfo", lotteryInfos);
		return "callback";
	}


}
