package com.ytoxl.module.uhome.uhomebase.service.impl;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import com.ytoxl.module.core.common.utils.DateUtil;
import com.ytoxl.module.core.common.utils.StringUtils;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.common.utils.WebUtils;
import com.ytoxl.module.uhome.uhomebase.dataobject.Express;
import com.ytoxl.module.uhome.uhomebase.dataobject.resultmap.DataItem;
import com.ytoxl.module.uhome.uhomebase.dataobject.resultmap.ExpressMessage;
import com.ytoxl.module.uhome.uhomebase.mapper.ExpressMapper;
import com.ytoxl.module.uhome.uhomebase.service.ExpressService;
@Service
public class ExpressServiceImpl implements ExpressService {
	@Autowired
	private ExpressMapper<Express> expressMapper;
	@Value("${express_query_condition}")
	private String queryCondition;
	@Value("${express_request_url}")
	private String requestUrl;
	@Value("${express_connect_timeout}")
	private String connectTimeout;
	@Value("${express_read_timeout}")
	private String readTimeout;
	@Value("${express_md5_constant}")
	private String md5Constant;
	
	@Value("${kuaidi100_api_url}")
	private String apiUrl;
	@Value("${kuaidi100_key}")
	private String key;
	
	/**
	 * 查询快递公司列表
	 */
	@Override
	public List<Express> listExpresses() throws UhomeStoreException{
		// TODO Auto-generated method stub
		return expressMapper.listExpresses();
	}
	
	/**
	 * 查询运单详细信息
	 * @return
	 * @throws UhomeStoreException 
	 */
	@Override
	public List<Map> getExpressDetailInfo(String mailNo) throws UhomeStoreException {
		// TODO Auto-generated method stub2806880758
		String condition="<order><mailNo>"+mailNo+"</mailNo></order>";
		String xml3=StringUtils.replace(queryCondition, "{1}", condition);
		Map<String,String>params=new HashMap<String, String>();
		params.put("logistics_interface", xml3);//UTF-8加密
		params.put("data_digest",new String(Base64.encodeBase64(DigestUtils.md5((xml3+md5Constant)))) );//通知内容（xml）+parternID，然后进行MD5，转换为Base64字符串
		try {
			String detail=WebUtils.doPost(requestUrl, params,Integer.parseInt(connectTimeout),Integer.parseInt(readTimeout));
			List detailList=new ArrayList();
			detailList.add(data(detail));
			return detailList;
		} catch (IOException e) {
			throw new UhomeStoreException(e.getMessage());
		} catch (DocumentException e) {
			throw new UhomeStoreException(e.getMessage());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw new UhomeStoreException(e.getMessage());
		}
	}
	
	private Map<Object,Object> data(String xmldata) throws DocumentException, ParseException{
 		InputSource in = new InputSource(new StringReader(xmldata));   
		SAXReader reader = new SAXReader();   
		Document document;
		Map<Object,Object> data=new HashMap<Object,Object>();
		document = reader.read(in);
		
		Element rootElt = document.getRootElement(); // 获取根节点
		Element rootjd = rootElt.element("orders");   
		if(rootjd !=null && rootjd.element("order")!=null){
			Iterator rootiter = rootjd.elementIterator("order"); // 获取根节点下的子节点mxCell
			while (rootiter.hasNext()) {                 
				Element recordEle = (Element) rootiter.next();
				//System.out.println(recordEle.getName());
				data.put("mailNo", recordEle.element("mailNo").getData());
				data.put("txLogisticID", recordEle.element("txLogisticID")==null?"":recordEle.element("txLogisticID").getData());
				data.put("mailType", recordEle.element("mailType").getData());
				data.put("orderStatus", recordEle.element("orderStatus").getData());
				if(recordEle.element("steps")!=null){
					List<Map<Object,Object>> stepMap=new ArrayList<Map<Object,Object>>(); 
					for (Iterator steps=recordEle.element("steps").elementIterator(); steps.hasNext();) {
						Element element = (Element) steps.next();
						Map<Object,Object> map=new HashMap<Object,Object>();
						//map.put("acceptTime", DateUtil.parseDate(element.element("acceptTime").getData().toString()));
						//System.out.println("~~~~~~~~~~~~~~~~~~~~~:"+DateUtil.toSeconds(DateUtil.valueof(element.element("acceptTime").getText(), "yyyy-MM-dd HH:mm:ss")));
						map.put("acceptTime", DateUtil.toSeconds(DateUtil.valueof(element.element("acceptTime").getText(), "yyyy-MM-dd HH:mm:ss")));
						map.put("acceptAddress", element.element("acceptAddress").getData());
						map.put("name", element.element("name")==null ?"":element.element("name").getData());
						map.put("status", element.element("status").getData());
						map.put("remark", element.element("remark").getData());
						stepMap.add(map);
					}
					data.put("steps", stepMap);
				}
			}	
		}
		
		return data;
	}
	
	/**
	 * 调用快递100API返回运单信息
	 * @param mailNo
	 * @return
	 */
	@Override
	public ExpressMessage getExpressDetailInfoFromKuaidi100(String companyCode, String mailNo) throws UhomeStoreException {
		if(StringUtils.isEmpty(companyCode) || StringUtils.isEmpty(mailNo)){
			return null;
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", key);
		params.put("com", companyCode);
		params.put("nu", mailNo);
		params.put("show", "0");
		params.put("muti", "1");
		params.put("order", "asc");
		
		String result = "";
		try {
			result = WebUtils.doGet(apiUrl, params);
		} catch (IOException e) {
			throw new UhomeStoreException(e.getMessage());
		}
		JSONObject jsonObject = JSONObject.fromObject(result);
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("data", DataItem.class);
		ExpressMessage message = (ExpressMessage)JSONObject.toBean(jsonObject, ExpressMessage.class, classMap);
		return message;
	}

}
