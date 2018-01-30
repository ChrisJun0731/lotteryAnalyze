package com.zj.lotteryAnalyze.schedule;

import com.zj.lotteryAnalyze.dto.LotteryInfo;
import com.zj.lotteryAnalyze.dto.Stat;
import com.zj.lotteryAnalyze.service.LotteryHistory;
import com.zj.lotteryAnalyze.service.LotteryStatOfLastTwo;
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
	private LotteryStatOfLastTwo lotteryStat;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@Scheduled(cron="0 1/1 * * * ?")
	@SendTo("/topic/lotteryInfo")
	public String pushLotteryInfo(){
		System.out.println("开始推送数据");

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

		List<Stat> stats = pushStat();

		messagingTemplate.convertAndSend("/topic/lotteryInfo", lotteryInfos);
		messagingTemplate.convertAndSend("/topic/stat", stats);
		System.out.println("推送数据结束");
		return "callback";
	}

	public List<Stat> pushStat(){

		List<LotteryInfo> lotteryInfos = lotteryStat.getHistoryData();
		List<List<LotteryInfo>> groups = lotteryStat.groupLotterys(lotteryInfos);
		List<Stat> stats = new ArrayList<>();

		for(List<LotteryInfo> group: groups){
			Stat stat = new Stat();
			stat.setBigNumTimes(lotteryStat.countBigNumOfLastTwo(group));
			stat.setOddNumTimes(lotteryStat.countOddNumQuinaryOfLastTwo(group));
			stat.setDiffNums(lotteryStat.countDiffQuinary(group));
			stat.setSameNumTimes(lotteryStat.countSameNumQuinaryLast(group));
			stat.setSameConTimes(lotteryStat.countSameContinueQuinary(group));
			stat.setLastSum(lotteryStat.lastSumQuinary(group));
			stats.add(stat);
		}

		return stats;
	}

}
