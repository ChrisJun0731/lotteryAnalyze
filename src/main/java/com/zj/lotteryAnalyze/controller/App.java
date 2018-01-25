package com.zj.lotteryAnalyze.controller;

import com.zj.lotteryAnalyze.dto.SocketMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/1/24.
 */
@Controller
public class App {
	@Autowired
	private SimpMessagingTemplate messagingTemplate;


	@GetMapping("/websocket")
	public String websocket() {
		return "websocket";
	}

	@MessageMapping("/send")
	@SendTo("/topic/send")
	public SocketMessage send(SocketMessage message) throws Exception {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		message.date = format.format(new Date());
		return message;
	}

	@Scheduled(fixedRate = 5000)
	@SendTo("/topic/callback")
	public Object callback() throws Exception {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		messagingTemplate.convertAndSend("/topic/callback", format.format(new Date()));
		return "callback";
	}
}
