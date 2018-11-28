package com.ytoxl.module.uhome.uhomebase.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomebase.dataobject.ProductCategory;

/**
 * 产品类别
 * @author user
 *
 * @param <T>
 */
public interface ProductCategoryMapper<T extends ProductCategory> extends BaseSqlMapper<T> {
	
	/**
	 * 查询所有产品类别信息
	 * @return
	 */
	public List<ProductCategory> listProductCategory() throws DataAccessException;
	
	/**
	 * 查询所有产品子类别信息
	 * @return
	 */
	public List<ProductCategory> listChildProductCategory() throws DataAccessException;
	
	/**
	 * 根据目录的名字查找目录
	 * @param categoryName
	 * @return
	 * @throws DataAccessException
	 */
	public List<ProductCategory> listProductCategroyByCategoryName(String categoryName) throws DataAccessException;
	
	/**
	 * 根据目录的id查询目录
	 * @param productCategoryId
	 * @return
	 * @throws DataAccessException
	 */
	public List<ProductCategory> listProductCategroyByCategoryId(Integer productCategoryId)throws DataAccessException;

	
	/**
	 * 根据目录的名字查找目录 精确查询
	 * @param categoryName
	 * @return
	 * @throws DataAccessException
	 */
	public ProductCategory getProductCategoryByName(String categoryName) throws DataAccessException;

	/**
	 * 根据cateId查询父类的cate
	 * @param pcId
	 * @return
	 * @throws DataAccessException
	 */
	public ProductCategory getParentProdcutCategoryById(Integer pcId) throws DataAccessException;

}
