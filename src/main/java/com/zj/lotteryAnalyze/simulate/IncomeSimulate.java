package com.zj.lotteryAnalyze.simulate;

import com.zj.lotteryAnalyze.dto.Income;
import com.zj.lotteryAnalyze.dto.LotteryInfo;
import com.zj.lotteryAnalyze.dto.LotteryPredict;
import com.zj.lotteryAnalyze.dto.PredictResult;
import com.zj.lotteryAnalyze.service.NextThreePredict;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2018/2/6.
 */
public class IncomeSimulate {

	private int price = 2;
	private int prize;
	private Accuracy accuracy = new Accuracy();

	public void computeAll(List<List<LotteryInfo>> groups){

		int tenInputAll = 0;
		int unitInputAll = 0;
		int tenOutputAll = 0;
		int unitOutputAll = 0;
		int tenPrizeRatio = 0;
		int unitPrizeRatio = 0;


		for(List<LotteryInfo> group: groups){
			Income income = computeIncome(group);
			tenInputAll += income.getTenInput();
			tenOutputAll += income.getTenOutput();
			unitInputAll += income.getUnitInput();
			unitOutputAll += income.getUnitOutput();
		}

		tenPrizeRatio = (tenOutputAll - tenInputAll)/tenInputAll;
		unitPrizeRatio = (unitOutputAll - unitInputAll)/unitInputAll;

		System.out.println("投资"+groups.size()+"组产生的十位胆拖收益为"+ tenOutputAll+",收益率为:"+ tenPrizeRatio);
		System.out.println("投资"+groups.size()+"组产生的个位胆拖收益为"+ unitOutputAll+",收益率为:"+ unitPrizeRatio);

	}

	/**
	 * 计算一组彩票中，胆注十位和各位的收益。
	 * @param lotteryInfos
	 * @return
	 */
	public Income computeIncome(List<LotteryInfo> lotteryInfos){

		Income income = new Income();
		List<LotteryInfo> existLotterys = new ArrayList<>();

		for(int i=3; i<lotteryInfos.size(); i++){
			existLotterys.add(lotteryInfos.get(i));
		}
		NextThreePredict predict = new NextThreePredict(existLotterys);
		LotteryPredict lotteryPredict = predict.getLotteryPredict();

		int inputTen = price * lotteryPredict.getTenPreSet().size();
		int inputUnit = price * lotteryPredict.getUnitPreSet().size();
		income.setTenOutput(inputTen);
		income.setUnitInput(inputUnit);
		PredictResult result = accuracy.predictCorrect(lotteryInfos);

		if(result.isTenCorrect()){
			income.setTenOutput(prize - inputTen);
		}else{
			income.setTenOutput(-inputTen);
		}
		if(result.isUnitCorrect()){
			income.setUnitOutput(prize - inputUnit);
		}else{
			income.setUnitOutput(-inputTen);
		}

		return income;
	}

}
