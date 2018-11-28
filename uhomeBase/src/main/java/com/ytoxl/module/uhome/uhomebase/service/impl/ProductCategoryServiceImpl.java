package com.ytoxl.module.uhome.uhomebase.service.impl;

import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductCategory;
import com.ytoxl.module.uhome.uhomebase.dataobject.resultmap.ProductCategoryModel;
import com.ytoxl.module.uhome.uhomebase.mapper.ProductCategoryMapper;
import com.ytoxl.module.uhome.uhomebase.service.ProductCategoryService;
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService{
	@Autowired
	private ProductCategoryMapper<ProductCategory> productCategoryMapper;

	public ProductCategoryMapper<ProductCategory> getProductCategoryMapper() {
		return productCategoryMapper;
	}

	public void setProductCategoryMapper(
			ProductCategoryMapper<ProductCategory> productCategoryMapper) {
		this.productCategoryMapper = productCategoryMapper;
	}

	@Override
	public List<ProductCategory> listProductCategory() {
		return productCategoryMapper.listProductCategory();
	}

	@Override
	public String[] getProductCategoryLink(Integer pcId) throws UhomeStoreException {
		if(null == pcId){
			return null;
		}
		String[] strArr = null;
		ProductCategory pc = productCategoryMapper.getParentProdcutCategoryById(pcId);
		for(Entry<String,Integer> entry:ProductCategoryModel.PCSMAP.entrySet()){
			if(entry.getValue().equals(pc.getProductCategoryId())){
				//如果cateId与map中那个值相等 就是那个目录
				strArr = new String[2];
				strArr[0] = entry.getKey();
				strArr[1] = pc.getName();
			}
		}
		return strArr;
	}

	@Override
	public List<ProductCategory> listChildProductCategory() throws UhomeStoreException {
		return productCategoryMapper.listChildProductCategory();
	}

}
