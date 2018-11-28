package com.ytoxl.module.uhome.uhomebase.mapper;

import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import com.ytoxl.module.core.common.persistence.BaseSqlMapper;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Brand;
import com.ytoxl.module.uhome.uhomebase.dataobject.Seller;
import com.ytoxl.module.uhome.uhomebase.dataobject.UserInfo;
import com.ytoxl.module.uhome.uhomebase.dataobject.tbl.SellerTbl;
import com.ytoxl.module.user.dataobject.User;

/**
 * 用户管理
 * @author user
 *
 * @param <T>
 */
public interface UserInfoMapper<T extends UserInfo> extends BaseSqlMapper<T> {
	
	/**
	 * 分页查询商家
	 * @param sellerPage
	 * @throws UhomeStoreException
	 */
	public List<Seller> searchSellers(Map<String, Object> sellerPage) throws DataAccessException;
	public Integer searchSellersCount(Map<String, Object> sellerPage) throws DataAccessException;
	
	/**
	 * 分页查询买家
	 * @param buyerPage
	 * @throws UhomeStoreException
	 */
	public List<UserInfo> searchBuyers(Map<String, Object> buyerPage) throws DataAccessException;
	public Integer searchBuyersCount(Map<String, Object> buyerPage) throws DataAccessException;
	/**
	 * 添加卖家的时候判断邮箱是否存在
	 */
	public User validateEmailIsRepate(String email) throws DataAccessException ;
	
	/**
	 * 添加卖家
	 * @param seller
	 * @return
	 */
	public Integer addSeller(Seller seller) throws DataAccessException;
	
	/**
	 * 添加买家
	 * @param userInfo
	 * @return
	 */
	public Integer addBuyer(UserInfo userInfo) throws DataAccessException;
	/**
	 * 添加买家
	 * @param userInfo
	 * @return
	 */
	public Integer updateUserRegister(UserInfo userInfo) throws DataAccessException;
	
	/**
	 * 更新卖家
	 * @param seller
	 * @return
	 */
	public Integer updateSeller(Seller seller) throws DataAccessException;
	
	/**
	 * 更新买家
	 * @param userInfo
	 * @return
	 */
	public Integer updateBuyer(UserInfo userInfo) throws DataAccessException;
	
	/**
	 * 重置密码
	 * @param user
	 * @return
	 */
	public Integer resetPassword(User user) throws DataAccessException;
	
	/**
	 * 根据id查询商家信息
	 * @param user
	 * @return
	 */
	public Seller getSellerById(Integer userId) throws DataAccessException;
	
	/**
	 * 根据id查询买家信息
	 * @param user
	 * @return
	 */
	public UserInfo getBuyerById(Integer userId) throws DataAccessException;
	
	/**
	 * 查询用户订阅的品牌名称 
	 * @param userId
	 * @return
	 */
	public String listSubBrands(Integer userId) throws DataAccessException;
	
	/**
	 * 根据品牌ID查询卖这个品牌卖家
	 * @param brandId
	 * @return
	 */
	public List<Seller> listSellersByBrandId(Integer brandId) throws DataAccessException;
	
	/**
	 * 根据userId查询sellerId
	 * @param userId
	 * @return
	 */
	public Integer getSellerIdByUserId(Integer userId) throws DataAccessException;
	
	/**
	 * 更新用户的信息
	 * @param brandId
	 * @return
	 */
	public List<SellerTbl> listExportSellers() throws DataAccessException;
	 
	public void  updateUser(UserInfo userInfo) throws DataAccessException;
	/**
	 * 根据用户的id 查询用户的详细信息
	 * @param brandId
	 * @return
	 */
	public UserInfo getUserByUserId(Integer userId) throws DataAccessException;
	/**
	 * 删除用户选择的品牌
	 * @param seller
	 * @return
	 * @throws DataAccessException
	 */
	public Integer delSellerBrand(Seller seller )throws DataAccessException;
	
	/**
	 * 查询用户是否选择该品牌
	 * @param seller
	 * @return
	 * @throws DataAccessException
	 */
	public Integer listBrandsCount(Seller seller )throws DataAccessException;
	
	/**
	 * 添加用户选择的品牌
	 * @param seller
	 * @return
	 * @throws DataAccessException
	 */
	public Integer addSellerBrand(Seller seller )throws DataAccessException;
	
	/**
	 * 查询卖家可售品牌
	 * @param sellerid
	 * @return
	 */
	public List<Integer> listBrandsBySellerId(Integer sellerId) throws DataAccessException;
	
	/**
	 * 查询需要所有商家
	 * @return
	 */
	public List<Seller> getSellerList() throws DataAccessException;
	
	/**
	 * 给据userId查找用户信息
	 * @param userId
	 * @return
	 * @throws DataAccessException
	 */
	public UserInfo getMemberInfoByUserId(Integer userId) throws DataAccessException;
	/**
	 * 注册的时候根据user的信息填写userinfo的信息
	 * @param user
	 * @throws DataAccessException
	 */
	public void addUserInfo(User user)throws DataAccessException;
	
	/**
	 * 通过卖家id查询该卖家的基本信息  状态是否被禁用
	 * @param sellerId
	 * @return
	 * @throws DataAccessException
	 */
	public User getUserBySellerId(Integer sellerId) throws DataAccessException;
	
	
	/**
	 * 根据id查询商家信息
	 * @param user
	 * @return
	 */
	public Seller getSellerBySellerUserId(Integer userId) throws DataAccessException;
	
	/**
	 * 根据userId查询商家信息
	 * @param user
	 * @return
	 */
	public Seller getSellerByUserId(Integer userId) throws DataAccessException;
	
	/**
	 * 根据用户的email查询用户信息 更新用户check邮箱
	 * @param userInfo
	 * @return
	 * @throws DataAccessException
	 */
	public List<UserInfo> getUserInfoByEmail(UserInfo userInfo) throws DataAccessException;
	/**
	 * 卖家管理订单管理全部品牌只显示曾经卖过或者正在卖的品牌
	 * @param userInfo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Brand> getUserInfoBrandbyid(Integer id) throws DataAccessException;
	/**
	 * 根据userId获取user对象，
	 * @param userId
	 * @return
	 */
	public User getUserById(Integer userId) throws DataAccessException ;
	
}
