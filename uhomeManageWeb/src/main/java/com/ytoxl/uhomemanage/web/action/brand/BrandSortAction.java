package com.ytoxl.uhomemanage.web.action.brand;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Brand;
import com.ytoxl.module.uhome.uhomebase.dataobject.BrandSort;
import com.ytoxl.module.uhome.uhomebase.service.BrandService;
import com.ytoxl.module.uhome.uhomebase.service.BrandSortService;
import com.ytoxl.uhomemanage.web.action.BaseAction;

public class BrandSortAction extends BaseAction  {

	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(BrandSortAction.class);
	@Autowired
	private BrandService brandService;
	@Autowired
	private BrandSortService brandSortService;
	private List<Brand> allBrandList ;
	private List<Brand> brandSortList;
	private String brandIds;
	private BrandSort brandSort;
	private String nextAction;

	public String listBrandSort(){
		try {
			this.brandSortList =  brandService.listBrandOrderBySort(brandSort.getType());
			this.allBrandList = new ArrayList<Brand>();
			List<Brand> tempBrand = brandService.listBrandBySort(brandSort.getType());
 			for(int i=0;i<tempBrand.size();i++){
				Brand brand = tempBrand.get(i);
				for(int j=0;j<brandSortList.size();j++){
					Brand bs = brandSortList.get(j);
					if(bs.getBrandId().intValue() == brand.getBrandId().intValue()){
						brand.setIsCheck(true);
					}
				}
				allBrandList.add(brand);
			}
	 	} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
		return "listBrandSort";
	}
	
	public String sortBrands(){
		try {
			 if(!StringUtils.isBlank(brandIds)){
				 String[] brandId = brandIds.split(",");
				 brandSortService.updateBrandSort(brandId,brandSort.getType());
			 }
	 	} catch (UhomeStoreException e) {
			logger.error(e.getMessage());
		}
	 	this.nextAction = "brandSort-listBrandSort.htm?brandSort.type=" + brandSort.getType();
		return "sort";
	}

	public List<Brand> getAllBrandList() {
		return allBrandList;
	}

	public void setAllBrandList(List<Brand> allBrandList) {
		this.allBrandList = allBrandList;
	}

	public List<Brand> getBrandSortList() {
		return brandSortList;
	}

	public void setBrandSortList(List<Brand> brandSortList) {
		this.brandSortList = brandSortList;
	}

	public String getBrandIds() {
		return brandIds;
	}

	public void setBrandIds(String brandIds) {
		this.brandIds = brandIds;
	}

	public BrandSort getBrandSort() {
		return brandSort;
	}

	public void setBrandSort(BrandSort brandSort) {
		this.brandSort = brandSort;
	}

	public String getNextAction() {
		return nextAction;
	}

	public void setNextAction(String nextAction) {
		this.nextAction = nextAction;
	}	
}
