package com.zj.lotteryAnalyze.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2018/1/24.
 */
@Controller
public class  ViewController {
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest req, HttpServletResponse resp) {
		return new ModelAndView("index", "name", "welcome");
	}

	@RequestMapping("/lottery")
	public String lottery(HttpServletRequest req, HttpServletResponse resp){
		return "lottery";
	}
}
