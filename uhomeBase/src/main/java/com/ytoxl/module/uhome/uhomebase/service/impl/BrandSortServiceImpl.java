package com.ytoxl.module.uhome.uhomebase.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.BrandSort;
import com.ytoxl.module.uhome.uhomebase.mapper.BrandSortMapper;
import com.ytoxl.module.uhome.uhomebase.service.BrandSortService;

/**
 * @author shengjianming
 *
 */

@Service
public class BrandSortServiceImpl implements BrandSortService {
	@Autowired
	private BrandSortMapper<BrandSort> brandSortMapper;
	
	@Override  
	public void updateBrandSort(String[] args,Integer type) throws UhomeStoreException {
		brandSortMapper.delAllBrandSort(type);
		for(int i=1;i<=args.length;i++){
			BrandSort bs = new BrandSort();
			bs.setBrandId(Integer.valueOf(args[i-1]));
			bs.setSort(i);
			bs.setType(type);
			brandSortMapper.add(bs);
		}	
	}

	@Override
	public List<BrandSort> getBrandListGroupByType() throws UhomeStoreException {
		List<BrandSort> brandSortList=brandSortMapper.getBrandListGroupByType();
		return brandSortList;
	}
}
