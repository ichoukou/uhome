package com.ytoxl.module.uhome.uhomebase.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Postage;
import com.ytoxl.module.uhome.uhomebase.mapper.PostageMapper;
import com.ytoxl.module.uhome.uhomebase.service.PostageService;

@Service
public class PostageServiceImpl implements PostageService {
	
	@Value("${postage_value}")
	private BigDecimal postageValue;
	@Autowired
	private PostageMapper<Postage> postageMapper;
	
	/**
	 * 保存邮费信息
	 * @param postage
	 */
	@Override
	public void savePostage(Postage postage) throws UhomeStoreException {
		Short type = postage.getType();
		Short option = postage.getOption();
		Postage obj = postageMapper.getPostageByOutIdAndType(postage.getOutId(), postage.getType());
		if(Postage.TYPE_PRODUCT.equals(type)){
			if(Postage.OPTION_FREE.equals(option)){
				if(obj != null){
					postageMapper.del(obj.getPostageId());
				}
			}else if(obj == null && Postage.OPTION_TEN.equals(option)){
				postage.setPostage(postageValue);
				postageMapper.add(postage);
			}	
		} else if(Postage.TYPE_PLAN.equals(type)){
			if(obj != null && option == null){
				postageMapper.del(obj.getPostageId());
			}else if(obj == null && Postage.OPTION_FREE.equals(option)){
				postage.setPostage(new BigDecimal(0));
				postageMapper.add(postage);
			}
		}
		
	}

}
