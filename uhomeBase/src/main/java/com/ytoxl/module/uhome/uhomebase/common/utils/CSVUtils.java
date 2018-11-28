package com.ytoxl.module.uhome.uhomebase.common.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CSVUtils {
	
	private static Log log = LogFactory.getLog(CSVUtils.class);

	public List<String[]> read(FileReader in){
		BufferedReader br = new BufferedReader(in);
		List<String[]> contentList = new ArrayList<String[]>();
		try {
			String content = br.readLine();
			if(content!=null){
				while((content = br.readLine())!=null){
					String seperatedContent[] = content.split(",");
					if(seperatedContent!=null&&seperatedContent.length>0){
						contentList.add(seperatedContent);
					}
				}
			}
		} catch (IOException e) {
			log.error(e);
		}
		return contentList;
	}
}
