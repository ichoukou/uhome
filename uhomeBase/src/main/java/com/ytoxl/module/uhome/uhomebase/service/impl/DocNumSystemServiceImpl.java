package com.ytoxl.module.uhome.uhomebase.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.common.utils.StringUtils;
import com.ytoxl.module.uhome.uhomebase.dataobject.Coupon;
import com.ytoxl.module.uhome.uhomebase.dataobject.Event;
import com.ytoxl.module.uhome.uhomebase.dataobject.FormNumber;
import com.ytoxl.module.uhome.uhomebase.mapper.CouponMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.EventMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.FormNumberMapper;
import com.ytoxl.module.uhome.uhomebase.service.DocNumSystemService;

@Service
public class DocNumSystemServiceImpl implements DocNumSystemService {

	@Autowired
	private FormNumberMapper<FormNumber> numberMapper;
	@Autowired
	private CouponMapper<Coupon> couponMapper;
	@Autowired
	private EventMapper<Event> eventMapper;
	/**
	 * 优惠券号长度
	 */
	private static final Integer COUPON_CODE_LEN = 10;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
	public String getOrderNum() throws UhomeStoreException {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
    	String formPrefix = df.format(date);
    	FormNumber number=  numberMapper.getFormNumberByPrefix(formPrefix);
    	String orderNum = null;
    	if(number!=null){
    		if(formPrefix.equals(number.getFormPrefix())){
    		    int curIndex=number.getFormIndex() + 1;
    		    int flag= numberMapper.updateIndex(number.getFormNumberId(), curIndex);
    		    if(flag != 1)
    		    	throw new UhomeStoreException("Fail to update FormNumIndex ! ");
    		    orderNum = formPrefix + getStrCode(curIndex,5);
    		}
    		else {
    			int flag=numberMapper.updateIndex(number.getFormNumberId(),1);
    			if(flag!=1)
     		    	throw new UhomeStoreException("Fail to update FormNumIndex ! ");
    			orderNum= formPrefix + getStrCode(1,5);
    		}
    	}
    	else {
    		number=new FormNumber();
    		number.setFormPrefix(formPrefix);
    		number.setFormIndex(1);
    		numberMapper.add(number);
    		orderNum = formPrefix + getStrCode(1, 5);
    	}
    	return orderNum;
   }
	
	
	/**
	 * 获取多位编码
	 * @param num
	 * @return
	 */
	private String getStrCode(Integer num,Integer bit){
		 if(num<1||bit<1)
    		 return null;
		String s=num.toString();
		int length=s.length();
		if(length>bit)
			return null;
		int add=bit-length;
		String prefix="";
		for(int i=0;i<add;i++){
			prefix+="0";
		}
		return prefix+s;
	}
	
	/**
	 * 生成优惠券号
	 * @param count
	 * @param prefix
	 * @throws UhomeStoreException
	 */
	@Override
	public List<String> getCouponNos(Integer count, String prefix) throws UhomeStoreException {
		//生成一定数量的优惠券号
		if(StringUtils.isEmpty(prefix)){
			prefix = "";
		}else{
			prefix = prefix.replaceAll("[012IOZ]", "");
		}
		int len = COUPON_CODE_LEN - prefix.length();
		Set<String> set = new HashSet<String>();
		while(set.size() < count){
			String couponNo = prefix + getRandomCode(len);
			set.add(couponNo);
		}
		List<String> list = new ArrayList<String>(set);
		Integer num = couponMapper.listCountByCouponNos(list);
		//判断生成的优惠券号在数据库中是否已存在
		if(num == 0){
			return list;
		}else{
			//重新生成
			return getCouponNos(count, prefix);	
		}
	}

	/**
	 * 获取多位数字、大写字母随机组合
	 * @return
	 */
	private String getRandomCode(int len) {
		String[] numArray = {"3", "4", "5" ,"6", "7", "8", "9"};
		String[] letterArray = {"A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", 
								"M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y"};
		StringBuffer strBuf = new StringBuffer();
		Random random = new Random();
    	while(strBuf.length() < len){
    		if(random.nextInt(2) % 2 == 0){
    			int randomIndex = random.nextInt(numArray.length);
    			strBuf.append(numArray[randomIndex]);
    		}else{
    			int randomIndex = random.nextInt(letterArray.length);
    			strBuf.append(letterArray[randomIndex]);
    		}
    	}
		return strBuf.toString();
	}


	/**
	 * 生成优惠券礼包激活码
	 * @return
	 * @throws UhomeStoreException
	 */
	@Override
	public String getCouponActiveCode() throws UhomeStoreException {
		String activeCode = getRandomCode(COUPON_CODE_LEN);
		Integer num = eventMapper.listCountByActiveCode(activeCode);
		if(num == 0){
			return activeCode;
		}
		return getCouponActiveCode();
	}
	
}
