package com.ytoxl.module.uhome.uhomebase.common.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author wangguoqing
 *
 */
public class UncompressZip {
	
	public static void unzip(ZipInputStream zipInputStream,String outputDir) throws FileNotFoundException, IOException{
		ZipEntry zipEntry = null;
		FileOutputStream fileOutputStream = null;
		while((zipEntry = zipInputStream.getNextEntry()) != null){
			//如果是目录 复制目录(不复制文件)
			if(zipEntry.isDirectory()){
				String name = zipEntry.getName();
				name = name.substring(0, name.length() - 1);
				File file = new File(outputDir+File.separator+name);
//				file.mkdir();
				//多级目录
				file.mkdirs();
			}else{
				//不是目录 复制文件
				File file = new File(outputDir+File.separator+zipEntry.getName());
				file.createNewFile();
				fileOutputStream = new FileOutputStream(file);
				byte[] bb = new byte[8*1024];
				int b;
				while((b = zipInputStream.read(bb,0,bb.length)) != -1){
					fileOutputStream.write(bb,0,b);
				}
				fileOutputStream.flush();
				//fileOutputStream.close();
			}
		}
		zipInputStream.close();
		fileOutputStream.close();
	}

}
