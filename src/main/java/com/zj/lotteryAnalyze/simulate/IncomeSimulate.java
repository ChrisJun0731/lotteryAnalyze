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

	private float price = 2.0f;
	private float prize = 19.2f;
	private Accuracy accuracy = new Accuracy();

	public void computeAll(List<List<LotteryInfo>> groups){

		float tenInputAll = 0;
		float unitInputAll = 0;
		float tenOutputAll = 0;
		float unitOutputAll = 0;
		float tenPrizeRatio = 0;
		float unitPrizeRatio = 0;


		for(List<LotteryInfo> group: groups){
			Income income = computeIncome(group);
			tenInputAll += income.getTenInput();
			tenOutputAll += income.getTenOutput();
			unitInputAll += income.getUnitInput();
			unitOutputAll += income.getUnitOutput();
		}

		tenPrizeRatio = (tenOutputAll - tenInputAll)/tenInputAll;
		unitPrizeRatio = (unitOutputAll - unitInputAll)/unitInputAll;

		System.out.println("投资"+groups.size()+"组产生的十位胆拖收益为"+ tenOutputAll+"总投入为："+tenInputAll+",收益率为:"+ tenPrizeRatio);
		System.out.println("投资"+groups.size()+"组产生的个位胆拖收益为"+ unitOutputAll+"总投入为："+unitInputAll+",收益率为:"+ unitPrizeRatio);

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

		float inputTen = price * lotteryPredict.getTenPreSet().size();
		float inputUnit = price * lotteryPredict.getUnitPreSet().size();
		income.setTenInput(inputTen);
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
