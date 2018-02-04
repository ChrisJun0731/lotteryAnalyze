package com.zj.lotteryAnalyze.schedule;

import com.zj.lotteryAnalyze.aliyunApi.HzHistory;
import com.zj.lotteryAnalyze.dto.LotteryInfo;
import com.zj.lotteryAnalyze.dto.RatioStat;
import com.zj.lotteryAnalyze.dto.Stat;
import com.zj.lotteryAnalyze.service.LotteryRatioStat;
import com.zj.lotteryAnalyze.service.LotteryStatOfLastTwo;
import com.zj.lotteryAnalyze.utils.FileUtil;
import com.zj.lotteryAnalyze.utils.MyUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/25.
 */
@Component
public class PushLotteryInfo {

	@Autowired
	private HzHistory history;

	@Autowired
	private MyUtil util;

	@Autowired
	private FileUtil fileUtil;

	@Autowired
	private LotteryStatOfLastTwo lotteryStat;

	@Autowired
	private LotteryRatioStat ratioStat;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@Scheduled(cron="0 1/1 * * * ?")
	@SendTo("/topic/lotteryInfo")
	public String pushLotteryInfo(){
		System.out.println("开始推送数据");

		//推送近20期开奖号码
		List<LotteryInfo> lotteryInfos = new ArrayList<LotteryInfo>();
		lotteryInfos = history.getLotteryHistory(null);

		//根据过去的数据，推送统计数据
		List<Calendar> calendars = util.createCalendars(5);
		List<LotteryInfo> lotteryOfDays = lotteryStat.getHistoryData(calendars);
		List<Stat> stats = pushStat(lotteryOfDays);
		Map<String, RatioStat> ratioMap = pushRatioState(lotteryOfDays);

		messagingTemplate.convertAndSend("/topic/lotteryInfo", lotteryInfos);
		messagingTemplate.convertAndSend("/topic/stat", stats);
		messagingTemplate.convertAndSend("/topic/ratio", ratioMap);
		System.out.println("推送数据结束");
		return "callback";
	}

	public List<Stat> pushStat(List<LotteryInfo> lotteryInfos){
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

	public Map<String, RatioStat> pushRatioState(List<LotteryInfo> lotteryInfos){

		Map<String, RatioStat> map = ratioStat.computeRate(lotteryInfos);

		return map;
	}

}
