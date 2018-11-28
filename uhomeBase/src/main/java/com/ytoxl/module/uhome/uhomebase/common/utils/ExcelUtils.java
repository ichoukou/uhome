package com.ytoxl.module.uhome.uhomebase.common.utils;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;


public class ExcelUtils<T> {
	
	private static Log log = LogFactory.getLog(ExcelUtils.class);
	
	private String sheetName;
	
	private  Workbook create(InputStream in) throws  IOException,InvalidFormatException {  
	    if (!in.markSupported()) {  
	        in = new PushbackInputStream(in, 8);  
	    }  
	    if (POIFSFileSystem.hasPOIFSHeader(in)) {  
	        return new HSSFWorkbook(in);  
	    }  
	    if (POIXMLDocument.hasOOXMLHeader(in)) {  
	        return new XSSFWorkbook(OPCPackage.open(in));  
	    }  
	    throw 
	    	new IllegalArgumentException("你的excel版本目前poi解析不了"); 
	}
	
	public List<String[]> read(InputStream inputStream) throws UhomeStoreException{
		List<String[]> result=new ArrayList<String[]>();
		try {
			Workbook	wb =create(inputStream);
			for(int sheetCount=0;sheetCount<wb.getNumberOfSheets();sheetCount++){
				Sheet sheet = wb.getSheetAt(sheetCount);
				if(sheetName==null)
					throw new UhomeStoreException("Excel表单名为空!");
				if(sheet.getSheetName()!=null&&!"".equals(sheet.getSheetName().trim())){
					if(sheet.getSheetName().startsWith(sheetName)){
						int rows = sheet.getPhysicalNumberOfRows();
						if(rows<=1)
							throw new UhomeStoreException("Excel表单中没数据!");
						Row tabHeaderInExcel=sheet.getRow(0);
						if(tabHeaderInExcel==null)
							throw new UhomeStoreException("Excel表单中没有找到表头!");
						int cells = tabHeaderInExcel.getPhysicalNumberOfCells();
						//校验表头
						//					for(int col=0;col<cells;col++){
						//						
						//						break;
						//					}
						for (int r = 1; r < rows; r++) {
							Row row = sheet.getRow(r);
							if (row == null) {
								 continue;
							}
							String []excelCols=new String[cells];
							for (int c = 0; c < cells; c++) {
								Cell cell = row.getCell(c);
								if(cell==null)
									continue;
								Object value=null;
								int type= cell.getCellType();
								switch (type) {
									case Cell.CELL_TYPE_NUMERIC:
										value=cell.getNumericCellValue();
										break;
									case Cell.CELL_TYPE_STRING:
										value=cell.getStringCellValue();
										break;
									case Cell.CELL_TYPE_BOOLEAN:
										value=cell.getBooleanCellValue();
										break;
									default:
										continue;
									}
								if(value==null)
									continue;
								excelCols[c]=value.toString();
							}
							result.add(excelCols);
						}
					}
				}
			}
			return result;
		} catch (IOException e) {
			throw new UhomeStoreException(e.getMessage());
		}
		catch (InvalidFormatException e){
			throw new UhomeStoreException(e.getMessage());
		}
	
	}
	public String WriteExcel(List<T> list,OutputStream os,String title,Class<T> tClass,String head){
		String [] titles = null;
		if(title.contains(",")){
			titles = title.split(",");
		}else{
			titles = new String[]{title};
		}
		String fileName = null;
		 Workbook wb = new HSSFWorkbook();
		 Sheet sheet = wb.createSheet("sheet1");
		 String [] heads = head.split(",");
		 Row headRow = sheet.createRow(0);
		 for(int i=0;i<heads.length;i++){
			 Cell cell = headRow.createCell(i);
			 cell.setCellValue(heads[i]);
		 }
		 for(int i=0;i<list.size();i++){
			 Row row = sheet.createRow((short)i+1);
			 for(int j=0;j<titles.length;j++){
				 Method method;
				try {
					method = tClass.getMethod(titles[j]);
					Object ov = method.invoke(list.get(i));
					String value = "";
					if(ov!=null){
						value = ov.toString();
					}
					Cell cell = row.createCell(j);
					cell.setCellValue(value);
				} catch (SecurityException e) {
					log.error(e);
				} catch (NoSuchMethodException e) {
					log.error(e);
				} catch (IllegalArgumentException e) {
					log.error(e);
				} catch (IllegalAccessException e) {
					log.error(e);
				} catch (InvocationTargetException e) {
					log.error(e);
				}
			 }
		 }
		 try {
			wb.write(os);
		} catch (IOException e) {
			log.error(e);
		}
		 return fileName;
	}
	/**
	 * 
	 * @param list  导出的数据
	 * @param os
	 * @param methods  
	 * @param templetFile  index_colspan_rowspan_content,第二列;
	 * @throws Exception
	 */
	public void WriteExcel(List<T> list,OutputStream os,String methods,String tHeader)throws Exception{
		if(list==null||list.size()==0)
			throw new Exception("没有数据需要导出!");
		Class c=list.get(0).getClass();
		String [] ms = null;
		if(methods==null)
			throw new Exception("methods格式不正确!");
		if(methods.contains(",")){
			ms = methods.split(",");
		}else{
			ms = new String[]{methods};
		}
		if(ms==null||ms.length==0)
			throw new Exception("methods格式不正确!");
		if(tHeader==null||"".equals(tHeader.trim()))
			throw new Exception("没有初始化表头!");
		String[] rows=tHeader.split(";");
		if(rows==null ||rows.length==0)
			throw new Exception("初始化表头错误!");
		
		 Workbook wb = new HSSFWorkbook();
		 Sheet sheet = wb.createSheet("报表");
		 for(int i=0;i<rows.length;i++){
			 Row row = sheet.createRow(i);
			 String[] cols =rows[i].split(",");
			 for(int j=0;j<cols.length;j++){
				 String [] p=cols[j].split("_");
				 int  firstCol=Integer.valueOf(p[0]);
				 int  lastCol=firstCol+Integer.valueOf(p[1])-1;
				 sheet.addMergedRegion(new CellRangeAddress(i, i+Integer.valueOf(p[2])-1, firstCol,lastCol));
				 Cell cell=   row.createCell(firstCol);
				 cell.setCellValue(p[3]);
			 }
		 }
		 int start=rows.length;
		 for(int i=0;i<list.size();i++){
			 Row row = sheet.createRow(start+i);
			 for(int j=0;j<ms.length;j++){
				 Method method;
				try {
					method = c.getMethod(ms[j]);
					Object ov = method.invoke(list.get(i));
					String value = "";
					if(ov!=null){
						value = ov.toString();
					}
					Cell cell = row.createCell(j);
					cell.setCellValue(value);
				} catch (SecurityException e) {
					log.error(e);
				} catch (NoSuchMethodException e) {
					log.error(e);
				} catch (IllegalArgumentException e) {
					log.error(e);
				} catch (IllegalAccessException e) {
					log.error(e);
				} catch (InvocationTargetException e) {
					log.error(e);
				}
			 }
		 }
		 try {
			wb.write(os);
		} catch (IOException e) {
			log.error(e);
		}
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	
	
}
