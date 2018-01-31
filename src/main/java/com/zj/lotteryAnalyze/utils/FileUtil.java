package com.zj.lotteryAnalyze.utils;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Administrator on 2018/1/31.
 */
@Component
public class FileUtil {

	public void writeJson(String filename, String json){
		File file = new File("D:\\project\\lotteryAnalyze\\src\\main\\resources\\data\\" + filename);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(json);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
