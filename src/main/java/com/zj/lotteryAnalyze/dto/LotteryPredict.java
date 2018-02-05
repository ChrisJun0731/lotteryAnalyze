package com.zj.lotteryAnalyze.dto;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2018/2/5.
 */
public class LotteryPredict {

	private Set<Integer> tenPreSet;
	private Set<Integer> unitPreSet;
	private Set<String> tenRuleSet = new HashSet<>();
	private Set<String> unitRuleSet = new HashSet<>();

	public Set<Integer> getTenPreSet() {
		return tenPreSet;
	}

	public void setTenPreSet(Set<Integer> tenPreSet) {
		this.tenPreSet = tenPreSet;
	}

	public Set<Integer> getUnitPreSet() {
		return unitPreSet;
	}

	public void setUnitPreSet(Set<Integer> unitPreSet) {
		this.unitPreSet = unitPreSet;
	}

	public Set<String> getTenRuleSet() {
		return tenRuleSet;
	}

	public void setTenRuleSet(Set<String> tenRuleSet) {
		this.tenRuleSet = tenRuleSet;
	}

	public Set<String> getUnitRuleSet() {
		return unitRuleSet;
	}

	public void setUnitRuleSet(Set<String> unitRuleSet) {
		this.unitRuleSet = unitRuleSet;
	}
}
