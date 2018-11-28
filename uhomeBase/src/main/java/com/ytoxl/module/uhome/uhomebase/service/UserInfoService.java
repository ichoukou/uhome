package com.ytoxl.module.uhome.uhomebase.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Brand;
import com.ytoxl.module.uhome.uhomebase.dataobject.ReceiverAddress;
import com.ytoxl.module.uhome.uhomebase.dataobject.Seller;
import com.ytoxl.module.uhome.uhomebase.dataobject.UserInfo;
import com.ytoxl.module.uhome.uhomebase.dataobject.tbl.SellerTbl;
import com.ytoxl.module.user.common.exception.YtoxlUserException;
import com.ytoxl.module.user.dataobject.User;
import com.ytoxl.module.user.security.CustomUserDetails;
/**
 * 用户
 * @author guoxinzhi
 *
 */
public interface UserInfoService {
	/**
	 * 分页查询商家
	 * @param sellerPage
	 * @throws UhomeStoreException
	 */
	public void searchSellers(BasePagination<Seller> sellerPage) throws UhomeStoreException;
	
	/**
	 * 分页查询买家
	 * @param buyerPage
	 * @throws UhomeStoreException
	 */
	public void searchBuyers(BasePagination<UserInfo> buyerPage) throws UhomeStoreException;
	
	/**
	 * 根据ID查询卖家信息
	 * @param sellerId
	 * @return
	 */
	public Seller getSellerById(Integer sellerId,boolean isEdit) throws UhomeStoreException;
	
	/**
	 * 根据ID查询用户信息
	 * @param userId
	 * @return
	 * @throws UhomeStoreException
	 */
	public UserInfo getBuyerById(Integer userId) throws UhomeStoreException;
	
	/**
	 * 添加卖家的时候判断邮箱是否存在
	 */
	public boolean validateEmailIsRepate(String email, Integer userId) throws UhomeStoreException;
	
	/**
	 * 添加卖家
	 * @param seller
	 * @return
	 * @throws UhomeStoreException
	 */
	public Integer addSeller(Seller seller) throws UhomeStoreException;
	
	/**
	 * 更新卖家
	 * @param seller
	 * @throws UhomeStoreException
	 */
	public void updateSeller(Seller seller) throws UhomeStoreException;
	
	/**
	 * 添加用户信息
	 * @param userInfo
	 * @throws UhomeStoreException
	 */
	public Integer addBuyer(UserInfo userInfo) throws UhomeStoreException;
	
	/**
	 * 更新用户信息
	 * @param userInfo
	 * @throws UhomeStoreException
	 */
	public void updateBuyer(UserInfo userInfo) throws UhomeStoreException;
	
	/**
	 * 更新用户状态
	 * @param userId
	 * @param status
	 * @throws UhomeStoreException
	 */
	public void updateUserStauts(List<Integer> userIds,Integer status)throws UhomeStoreException;
	
	/**
	 * 重置用户密码
	 * @param userId
	 * @param status
	 * @throws UhomeStoreException
	 */
	public void resetPassword(Integer userId) throws UhomeStoreException;
	
	/**
	 * 根据用户id查询该用户所有的收货地址
	 * @param userId
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<ReceiverAddress> getReceiverAddressList(Integer userId) throws UhomeStoreException;
	
	/**
	 * 添加用户的地址，如果该地址被设置默认地址，则会查找该用户之前是否存在默认地址，并更改
	 * @param address
	 * @return 保存到数据库中的id
	 * @throws UhomeStoreException
	 */
	public Integer addReceiverAddress(ReceiverAddress address) throws UhomeStoreException;
	
	/**
	 * 根据id获取地址信息
	 * @param addressId
	 * @return
	 * @throws UhomeStoreException
	 */
	public ReceiverAddress getReceiverAddressById(Integer addressId) throws UhomeStoreException;
	
	/**
	 * 查询用户订阅的品牌名称 
	 * @param userId
	 * @return
	 */
	public String listSubBrands(Integer userId) throws UhomeStoreException;
	
	/**
	 * 获取当前的sellerId
	 * @param userId
	 * @return
	 */
	public Integer getCurrentSellerId() throws UhomeStoreException;
	
	/**
	 * 查询需要导出的商家
	 * @return
	 */
	public List<SellerTbl> listExportSellers()throws UhomeStoreException;
	
	
	/**
	 * 更新用户的信息
	 * @param brandId
	 * @return
	 */
	public void  updateUser(UserInfo userInfo) throws UhomeStoreException;
	/**
	 * 根据用户的id 查询用户的详细信息
	 * @param brandId
	 * @return
	 */
	public UserInfo getUserByUserId(Integer userId) throws UhomeStoreException;
	
	/**
	 * 删除用户选择的品牌
	 * @param seller
	 * @return
	 * @throws UhomeStoreException
	 */
	public Integer delSellerBrand(Seller seller )throws UhomeStoreException;
	
	/**
	 * 查询用户是否选择该品牌
	 * @param seller
	 * @return
	 * @throws UhomeStoreException
	 */
	public Integer listBrandsCount(Seller seller )throws UhomeStoreException;
	
	/**
	 * 添加用户选择的品牌
	 * @param seller
	 * @return
	 * @throws UhomeStoreException
	 */
	public Integer addSellerBrand(Seller seller )throws UhomeStoreException;
	
	/**
	 * 查询卖家可售品牌
	 * @param sellerid
	 * @return
	 */
	public List<Integer> listBrandsBySellerId(Integer sellerid) throws UhomeStoreException;
	
	/**
	 * 更新密码
	 * @param seller
	 * @return
	 * @throws UhomeStoreException
	 */
	public Integer savePassword(Seller seller)throws UhomeStoreException;
	
	/**
	 * 查询所有商家
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Seller> listSellers()throws UhomeStoreException;
	
	/**
	 * 给据userId查找用户信息
	 * @param userId
	 * @return
	 * @throws DataAccessException
	 */
	public UserInfo getMemberInfoByUserId(Integer userId) throws UhomeStoreException;

	/**
	 * 根据userInfo的信息填写对应的user的信息
	 * @param userInfo
	 * @throws UhomeStoreException
	 */
	 public void updateUserRegister(UserInfo userInfo)throws UhomeStoreException;
	 /***
	  * 注册的时候根据注册用户填写userInfo的信息
	  * @param user
	  * @throws UhomeStoreException
	  */
	 public void addUserInfo(User user)throws UhomeStoreException;
	 /**
	  * 根据用户的Id 差寻卖家信息
	  * @param userId
	  * @param isEdit
	  * @return
	  * @throws UhomeStoreException
	  */
	 public Seller getSellerBySellerUserId(Integer userId, boolean isEdit) throws UhomeStoreException ;

	 /**
	 * 根据userId查询卖家信息
	 * @param sellerId
	 * @return
	 */
	 public Seller getSellerByUserId(Integer userId) throws UhomeStoreException;
	 /***
	  * 前台用户的注册
	  * @param user
	  */
	 public void addRegister(User user)  throws UhomeStoreException,YtoxlUserException;
	 /***
	  * 更新当前的用户
	  * @param userInfo
	  * @throws UhomeStoreException
	  */
	 public void updateUserAndUserInfo(UserInfo userInfo) throws UhomeStoreException;
	 
	 /**
	  * 检测用户是否登录
	  * @return
	  * @throws UhomeStoreException
	  */
	 public boolean checkIsLogin() throws UhomeStoreException;
	 
	 /**
	  * 检测用户是否登录
	  * @return
	  * @throws UhomeStoreException
	  */
	 public CustomUserDetails checkUserIsLogin() throws UhomeStoreException;
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
		public User getUserById(Integer userId)  throws DataAccessException;
}