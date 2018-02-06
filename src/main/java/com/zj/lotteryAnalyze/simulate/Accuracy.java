package com.zj.lotteryAnalyze.simulate;

import com.zj.lotteryAnalyze.dto.LotteryInfo;
import com.zj.lotteryAnalyze.dto.LotteryPredict;
import com.zj.lotteryAnalyze.dto.PredictResult;
import com.zj.lotteryAnalyze.service.LotteryStatOfLastTwo;
import com.zj.lotteryAnalyze.service.NextThreePredict;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2018/2/6.
 */
public class Accuracy {

	private LotteryStatOfLastTwo stat = new LotteryStatOfLastTwo();

	public PredictResult predictCorrect(List<LotteryInfo> lotteryInfos) {

		PredictResult result = new PredictResult();
		List<LotteryInfo> existLotterys = new ArrayList<>();
		List<LotteryInfo> predictLotterys = new ArrayList<>();

		for (int i = 0; i < lotteryInfos.size(); i++) {
			if (i < 3) {
				predictLotterys.add(lotteryInfos.get(i));
			} else {
				existLotterys.add(lotteryInfos.get(i));
			}
		}

		NextThreePredict predict = new NextThreePredict(existLotterys);
		LotteryPredict lotteryPredict = predict.getLotteryPredict();
		Set<Integer> tenPre = lotteryPredict.getTenPreSet();
		Set<Integer> unitPre = lotteryPredict.getUnitPreSet();

		List<int[]> lastTwo = stat.getLastTwoNumber(predictLotterys);
		Set<Integer> tenValid = new HashSet<>();
		Set<Integer> unitValid = new HashSet<>();

		for(int[] last: lastTwo){
			tenValid.add(last[0]);
			unitValid.add(last[1]);
		}

		for(Integer tPre: tenPre){
			if(tenValid.contains(tPre)){
				result.setTenCorrect(true);
				break;
			}
		}
		for(Integer uPre: unitPre){
			if(unitValid.contains(uPre)){
				result.setUnitCorrect(true);
				break;
			}
		}

		return result;
	}
}
