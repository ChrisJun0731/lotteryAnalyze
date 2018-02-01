package com.zj.lotteryAnalyze.service;

import com.zj.lotteryAnalyze.aliyunApi.HzHistory;
import com.zj.lotteryAnalyze.aliyunApi.YiYuanHistory;
import com.zj.lotteryAnalyze.dto.LotteryInfo;
import com.zj.lotteryAnalyze.dto.RatioStat;
import com.zj.lotteryAnalyze.utils.FileUtil;
import com.zj.lotteryAnalyze.utils.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018/1/26.
 */
@Service
public class LotteryStatOfLastTwo {

	@Autowired
	private HzHistory history;

	@Autowired
	private MyUtil util;

	@Autowired
	private FileUtil fileUtil;

	/**
	 * 统计每期中最后两位数是大数的次数
	 * @param lotteryInfos
	 * @return
	 */
	public int[] countBigNumOfLastTwo(List<LotteryInfo> lotteryInfos){

		List<int[]> list = getLastTwoNumber(lotteryInfos);
		int[] bigNumTimes = new int[2];
		int tenBig = 0;
		int unitBig = 0;
		for(int[] group: list){
			if(group[0]>= 5){
				tenBig++;
			}
			if(group[1]>= 5){
				unitBig++;
			}
		}
		bigNumTimes[0] = tenBig;
		bigNumTimes[1] = unitBig;

		return bigNumTimes;
	}

	/**
	 * 统计以五进制表示的最后两位数，在五进制的个位上
	 * 出现奇数的次数
	 */
	public int[] countOddNumQuinaryOfLastTwo(List<LotteryInfo> lotteryInfos){

		int[][][] lotterys = getLastTwoQuinary(lotteryInfos);
		int[] oddTimes = new int[2];
		int tenOddTimes = 0;
		int unitOddTimes = 0;

		for(int i=0; i<6; i++){
			for(int j=0; j<2; j++){
				//j==0表示每期彩票中的倒数第二个开奖号码
				if(j == 0){
					if(lotterys[i][j][1]%2 != 0){
						tenOddTimes++;
					}
				}
				//j==1表示每期彩票中的最后一个开奖号码
				if(j == 1){
					if(lotterys[i][j][1]%2 != 0){
						unitOddTimes++;
					}
				}
			}
		}
		oddTimes[0] = tenOddTimes;
		oddTimes[1] = unitOddTimes;

		return oddTimes;
	}

	/**
	 * 统计以五进制表示的最后两位数，个位数相同的最大次数
	 * 例如8和3以五进制表示个位上都是3
	 * @param lotteryInfos
	 * @return
	 */
	public int[] countSameNumQuinaryLast(List<LotteryInfo> lotteryInfos){

		int[][][] lotterys = getLastTwoQuinary(lotteryInfos);
		Map<Integer, Integer> mapTen = new HashMap<>();
		Map<Integer, Integer> mapUnit = new HashMap<>();
		int[] result = new int[2];
		int sameTimesTen = 0;
		int sameTimesUnit = 0;

		for(int i=0; i<6; i++){
			for(int j=0; j<2; j++){
				if(j == 0){
					int key = lotterys[i][j][1];
					if(mapTen.containsKey(key)){
						mapTen.put(key, mapTen.get(key) + 1);
					}else{
						mapTen.put(key, 1);
					}
				}
				if(j == 1){
					int key = lotterys[i][j][1];
					if(mapUnit.containsKey(key)){
						mapUnit.put(key, mapUnit.get(key) + 1);
					}else{
						mapUnit.put(key, 1);
					}
				}
			}
		}

		for(Map.Entry<Integer, Integer> entry: mapTen.entrySet()){
			if(entry.getValue() > sameTimesTen){
				sameTimesTen = entry.getValue();
			}
		}

		for(Map.Entry<Integer, Integer> entry: mapUnit.entrySet()){
			if(entry.getValue() > sameTimesUnit){
				sameTimesUnit = entry.getValue();
			}
		}

		result[0] = sameTimesTen;
		result[1] = sameTimesUnit;

		return result;
	}

	/**
	 * 统计以五进制表示的最后两位数，不同的个位数的个数
	 * 比如，0,5,8,3,7 个位上不同的位数有3位
	 * 其中0和5,8和3以五进制表示个位数是相同的
	 * @param lotteryInfos
	 * @return
	 */
	public int[] countDiffQuinary(List<LotteryInfo> lotteryInfos){

		int[][][] lotterys = getLastTwoQuinary(lotteryInfos);
		Set<Integer> tenSet = new HashSet<>();
		Set<Integer> unitSet = new HashSet<>();
		int[] result = new int[2];

		for(int i=0; i<6; i++){
			for(int j=0; j<2; j++){
				if(j == 0){
					tenSet.add(lotterys[i][j][1]);
				}
				if(j == 1){
					unitSet.add(lotterys[i][j][1]);
				}
			}
		}

		result[0] = tenSet.size();
		result[1] = unitSet.size();

		return result;
	}

	/**
	 * 统计以五进制表示的最后两位数，个位连续相同的最大次数
	 * 比如，4,9,3,8,3那么最大连续次数为3,8,3以五进制表示个位是相同的
	 * @param lotteryInfos
	 * @return
	 */
	public int[] countSameContinueQuinary(List<LotteryInfo> lotteryInfos){

		int[][][] lotterys = getLastTwoQuinary(lotteryInfos);
		int[] conTimes = new int[2];
		int tenMaxCon = 1;
		int unitMaxCon = 1;
		int tenTemMaxCon = 1;
		int unitTemMaxCon = 1;

		for(int i=1; i<6; i++){
			for(int j=0; j<2; j++){
				if(j == 0){
					if(lotterys[i][j][1] == lotterys[i-1][j][1]){
						tenTemMaxCon++;
						if(tenTemMaxCon > tenMaxCon){
							tenMaxCon = tenTemMaxCon;
						}
					}else{
						tenTemMaxCon = 0;
					}
				}
				if(j == 1){
					if(lotterys[i][j][1] == lotterys[i-1][j][1]){
						unitMaxCon++;
						if(unitTemMaxCon > unitMaxCon){
							unitMaxCon = unitTemMaxCon;
						}
					}else{
						unitTemMaxCon = 0;
					}
				}
			}
		}
		conTimes[0] = tenMaxCon;
		conTimes[1] = unitMaxCon;

		return conTimes;
	}

	/**
	 * 以五进制表示的最后两位数，个位上的和计算
	 * 例如0,0,3,6,9个位上的和为0+0+3+1+4=8
	 * @param lotteryInfos
	 * @return
	 */
	public int[] lastSumQuinary(List<LotteryInfo> lotteryInfos){

		int[][][] lotterys = getLastTwoQuinary(lotteryInfos);
		int sumTen = 0;
		int unitTen = 0;
		int[] result = new int[2];

		for(int i=0; i<6; i++){
			for(int j=0; j<2; j++){
				if(j == 0){
					sumTen += lotterys[i][j][1];
				}
				if(j == 1){
					unitTen += lotterys[i][j][1];
				}
			}
		}

		result[0] = sumTen;
		result[1] = unitTen;

		return result;
	}


	/**
	 * 获取数据源
	 * 获取某天的历史数据，共120期
	 * @return
	 */
	public List<LotteryInfo> getHistoryData(List<Calendar> calendars){

		List<LotteryInfo> list = new ArrayList<>();

		for(Calendar calendar: calendars){

			List<LotteryInfo> info = history.getLotteryOfDate(calendar);

			//把该天的彩票历史数据写在文件中
			String filename = new SimpleDateFormat("yyyyMMdd").format(calendar.getTimeInMillis()) + ".json";
			String json = util.convertListToJSON(info);
			fileUtil.writeJson(filename, json);
			System.out.println("彩票历史数据写入到"+filename+"文件中成功");

			list.addAll(info);
		}

		return list;
	}

	/**
	 * 对数据源进行分组，每6期数据为一组
	 * @param list
	 * @return
	 */
	public List<List<LotteryInfo>> groupLotterys(List<LotteryInfo> list){

		List<List<LotteryInfo>> lotteryGroups = new ArrayList<>();
		int size = list.size();
		int groupNum = size/6;

		try{
			if(size< 6){
				throw new Exception("彩票的总期数小于6期，无法进行分组");
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		List<LotteryInfo> lotteryInfos = null;
		for(int i=0; i<6*groupNum; i++){
			if(i%6 == 0){
				lotteryInfos = new ArrayList<>();
				lotteryInfos.add(list.get(i));
			}else{
				lotteryInfos.add(list.get(i));
				if(i%6 == 5){
					lotteryGroups.add(lotteryInfos);
				}
			}
		}
		return lotteryGroups;
	}

	/**
	 * 获取每组中每期的最后两个数字
	 * @return
	 */
	public List<int[]> getLastTwoNumber(List<LotteryInfo> lotteryInfos){

		List<int[]> list = new ArrayList<>();

		for(LotteryInfo info: lotteryInfos){
			int[] lastTwo = new int[2];
			int[] perNum = info.convertToPerNum();
			lastTwo[0] = perNum[3];
			lastTwo[1] = perNum[4];
			list.add(lastTwo);
		}

		return list;
	}

	/**
	 * 获得每组中每期最后两位数字的五进制形式
	 * @param lotteryInfos
	 * @return
	 */
	public int[][][] getLastTwoQuinary(List<LotteryInfo> lotteryInfos){

		MyUtil util = new MyUtil();

		List<int[]> list = getLastTwoNumber(lotteryInfos);
		int[][][] lastTwoQuinary = new int[list.size()][2][2];

		for(int i=0; i<list.size(); i++){
			for(int j=0; j<list.get(i).length; j++){
				lastTwoQuinary[i][j] = util.conventNumToQuinary(list.get(i)[j]);
			}
		}

		return lastTwoQuinary;
	}

	/**
	 * 计算多期彩票中，分组后，按不同的统计方式，每个值在组出现的概率
	 * @param lotteryInfos
	 * @return
	 */
	public Map<String, RatioStat> computeRate(List<LotteryInfo> lotteryInfos){

		return null;
	}

	public RatioStat computeBigNumRate(List<LotteryInfo> lotteryInfos) {

		return null;
	}
}
