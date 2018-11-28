package com.ytoxl.module.uhome.uhomebase.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.core.common.utils.StringUtils;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.Brand;
import com.ytoxl.module.uhome.uhomebase.dataobject.Point;
import com.ytoxl.module.uhome.uhomebase.dataobject.PointDetail;
import com.ytoxl.module.uhome.uhomebase.dataobject.ReceiverAddress;
import com.ytoxl.module.uhome.uhomebase.dataobject.Region;
import com.ytoxl.module.uhome.uhomebase.dataobject.Seller;
import com.ytoxl.module.uhome.uhomebase.dataobject.UserInfo;
import com.ytoxl.module.uhome.uhomebase.dataobject.resultmap.RegionModel;
import com.ytoxl.module.uhome.uhomebase.dataobject.tbl.SellerTbl;
import com.ytoxl.module.uhome.uhomebase.mapper.PointDetailMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.PointMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.ReceiverAddressMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.RegionMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.UserInfoMapper;
import com.ytoxl.module.uhome.uhomebase.service.UserInfoService;
import com.ytoxl.module.user.common.exception.YtoxlUserException;
import com.ytoxl.module.user.dataobject.User;
import com.ytoxl.module.user.mapper.UserMapper;
import com.ytoxl.module.user.security.CustomUserDetails;
import com.ytoxl.module.user.service.UserService;
/**
 * 用户
 * @author guoxinzhi
 *
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
	private static Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);
	
	@Autowired
	private UserInfoMapper<UserInfo> userInfoMapper;
	@Autowired
	private UserMapper<User> userMapper;
	@Autowired
	private ReceiverAddressMapper<ReceiverAddress> receiverAddressMapper;
	@Autowired
	private RegionMapper<Region> regionMapper;
	@Autowired
	private UserService userService;
	@Value("${defaultPass}")
	private String defaultPass;
	@Autowired
	private PointMapper<Point> pointMapper;
	@Autowired
	private PointDetailMapper<PointDetail> pointDetailMapper;

	/**
	 * 分页查询商家
	 * @param sellerPage
	 * @throws UhomeStoreException
	 */
	@Override
	public void searchSellers(BasePagination<Seller> sellerPage)
			throws UhomeStoreException {
		Map<String, Object> searchParams = sellerPage.getSearchParamsMap();
		if (sellerPage.isNeedSetTotal()) {
			Integer total = userInfoMapper.searchSellersCount(searchParams);
			sellerPage.setTotal(total);
		}
		Collection<Seller> result = userInfoMapper.searchSellers(searchParams);
		sellerPage.setResult(result);
	}
	
	/**
	 * 分页查询买家
	 * @param buyerPage
	 * @throws UhomeStoreException
	 */
	@Override
	public void searchBuyers(BasePagination<UserInfo> buyerPage)
			throws UhomeStoreException {
		Map<String, Object> searchParams = buyerPage.getSearchParamsMap();
		if (buyerPage.isNeedSetTotal()) {
			Integer total = userInfoMapper.searchBuyersCount(searchParams);
			buyerPage.setTotal(total);
		}
		Collection<UserInfo> result = userInfoMapper.searchBuyers(searchParams);
		for (Iterator iterator = result.iterator(); iterator.hasNext();) {
			UserInfo userInfo = (UserInfo) iterator.next();
			//查询用户所在地
			if(userInfo.getRegionId()!=null && !userInfo.getRegionId().equals("")){
				Region region=regionMapper.getDetailInfoByRegionId(userInfo.getRegionId());
				StringBuffer sb=new StringBuffer();
				sb.append(region.getProvince());
				sb.append(" "+region.getCity());
				sb.append(" "+region.getCounty());
				sb.append(" "+(userInfo.getAddress()==null ? "":userInfo.getAddress()));
				userInfo.setAddress(sb.toString());
				userInfo.setProvince(region.getProvince());
			}
			//查询用户订阅的品牌名称
			userInfo.setBrandNames(userInfoMapper.listSubBrands(userInfo.getUserId()));
			
			//查询用户收货地址
			userInfo.setReceAddress(receiverAddressMapper.getRAddressDetailList(userInfo.getUserId()));
			
		}
		
		buyerPage.setResult(result);

	}
	
	/**
	 * 根据ID查询卖家信息
	 * @param sellerId
	 * @return
	 */
	@Override
	public Seller getSellerById(Integer sellerId, boolean isEdit) throws UhomeStoreException {
		// TODO Auto-generated method stub
		Seller seller=userInfoMapper.getSellerById(sellerId);
		if(! isEdit){
			//查询用户所在地
			if(seller.getCompanyRegionId()!=null && !seller.getCompanyRegionId().equals("")){
				Region region=regionMapper.getDetailInfoByRegionId(seller.getCompanyRegionId());
				StringBuffer sb=new StringBuffer();
				if(region!=null){
					sb.append(region.getProvince());
					sb.append(" "+region.getCity());
					sb.append(" "+region.getCounty());
				}
				sb.append(" "+seller.getCompanyAddress());
				seller.setCompanyAddress(sb.toString());
			}
			//查询退货地址
			if(seller.getReceiverRegionId()!=null && !seller.getReceiverRegionId().equals("")){
				Region region=regionMapper.getDetailInfoByRegionId(seller.getReceiverRegionId());
				StringBuffer sb=new StringBuffer();
				sb.append(region.getProvince());
				sb.append(" "+region.getCity());
				sb.append(" "+region.getCounty());
				sb.append(" "+seller.getReceiverAddress());
				seller.setReceiverAddress(sb.toString());
			}
			//查询用户所在地
			if(seller.getShiperRegionId()!=null && !seller.getShiperRegionId().equals("")){
				Region region=regionMapper.getDetailInfoByRegionId(seller.getShiperRegionId());
				StringBuffer sb=new StringBuffer();
				sb.append(region.getProvince());
				sb.append(" "+region.getCity());
				sb.append(" "+region.getCounty());
				sb.append(" "+seller.getShiperAddress());
				seller.setShiperAddress(sb.toString());
			}
		}else{
			//查询地址code
			if(seller.getShiperRegionId()!=null){
				RegionModel shiperModel=regionMapper.getRegionIdsByRegionId(seller.getShiperRegionId());
				if(shiperModel!=null){
					seller.setShiperModel(shiperModel);
				}
			}
			
			if(seller.getReceiverRegionId()!=null){
				RegionModel receiverModel=regionMapper.getRegionIdsByRegionId(seller.getReceiverRegionId());
				if(receiverModel!=null){
					seller.setReceiverModel(receiverModel);
				}
			}

			if(seller.getCompanyRegionId()!=null){
				RegionModel companyModel=regionMapper.getRegionIdsByRegionId(seller.getCompanyRegionId());
				if(companyModel!=null){
					seller.setCompanyModel(companyModel);
				}
			}
		}
		return seller;
	}

	/**
	 * 根据USERID查询卖家信息
	 * @param sellerId
	 * @return
	 */
	@Override
	public Seller getSellerBySellerUserId(Integer userId, boolean isEdit) throws UhomeStoreException {
		// TODO Auto-generated method stub
		Seller seller=userInfoMapper.getSellerBySellerUserId(userId);
		if(!isEdit){
			//查询用户所在地
			if(seller.getCompanyRegionId()!=null && !seller.getCompanyRegionId().equals("")){
				Region region=regionMapper.getDetailInfoByRegionId(seller.getCompanyRegionId());
				StringBuffer sb=new StringBuffer();
				if(region!=null){
					sb.append(region.getProvince());
					sb.append(" "+region.getCity());
					sb.append(" "+region.getCounty());
				}
				sb.append(" "+seller.getCompanyAddress());
				seller.setCompanyAddress(sb.toString());
			}
			//查询退货地址
			if(seller.getReceiverRegionId()!=null && !seller.getReceiverRegionId().equals("")){
				Region region=regionMapper.getDetailInfoByRegionId(seller.getReceiverRegionId());
				StringBuffer sb=new StringBuffer();
				sb.append(region.getProvince());
				sb.append(" "+region.getCity());
				sb.append(" "+region.getCounty());
				sb.append(" "+seller.getReceiverAddress());
				seller.setReceiverAddress(sb.toString());
			}
			//查询用户所在地
			if(seller.getShiperRegionId()!=null && !seller.getShiperRegionId().equals("")){
				Region region=regionMapper.getDetailInfoByRegionId(seller.getShiperRegionId());
				StringBuffer sb=new StringBuffer();
				sb.append(region.getProvince());
				sb.append(" "+region.getCity());
				sb.append(" "+region.getCounty());
				sb.append(" "+seller.getShiperAddress());
				seller.setShiperAddress(sb.toString());
			}
		}else{
			//查询地址code
			if(seller.getShiperRegionId()!=null){
				Region shiperCodes=regionMapper.getRegionCodesByRegionId(seller.getShiperRegionId());
				if(shiperCodes!=null){
					seller.setShiperRegionCodes(shiperCodes.getProvince()+","+shiperCodes.getCity()+","+shiperCodes.getCounty());
				}
			}
			
			if(seller.getReceiverRegionId()!=null){
				Region receiverCodes=regionMapper.getRegionCodesByRegionId(seller.getReceiverRegionId());
				if(receiverCodes!=null){
					seller.setReceiverRegionCodes(receiverCodes.getProvince()+","+receiverCodes.getCity()+","+receiverCodes.getCounty());
				}
			}

			if(seller.getCompanyRegionId()!=null){
				Region addressCodes=regionMapper.getRegionCodesByRegionId(seller.getCompanyRegionId());
				if(addressCodes!=null){
					seller.setCompanyRegionCodes(addressCodes.getProvince()+","+addressCodes.getCity()+","+addressCodes.getCounty());
				}
			}
		}
		return seller;
	}
	/**
	 * 根据ID查询用户信息
	 * @param userId
	 * @return
	 * @throws UhomeStoreException
	 */
	@Override
	public UserInfo getBuyerById(Integer userId) throws UhomeStoreException {
		// TODO Auto-generated method stub
		return userInfoMapper.getBuyerById(userId);
	}
	
	/**
	 * 添加卖家
	 * @param seller
	 * @return
	 * @throws UhomeStoreException
	 */
	@Override
	public Integer addSeller(Seller seller) throws UhomeStoreException {
		seller.setTel(seller.getUser().getTel());
		seller.setEmail(seller.getUser().getEmail());
		seller.getUser().setStatus(User.STATUS_ABLE);
		seller.getUser().setPassword(defaultPass);
		
		//添加user
		User user=seller.getUser();
		try {
			user.setCreateByUserId(userService.getCurrentUser().getUserId());
			userService.saveUser(user);
		} catch (YtoxlUserException e) {
			// TODO Auto-generated catch block
			throw new UhomeStoreException(e.getMessage());
		}
		//需要根据最后一个regionCode查询到regionId
		if(StringUtils.isNotEmpty(seller.getShiperRegionCodes()) && seller.getShiperRegionCodes().split(",").length==3){
			Integer shiperId=regionMapper.getRegionByCode(seller.getShiperRegionCodes().substring(seller.getShiperRegionCodes().lastIndexOf(",")+1)).getRegionId();    //发货地址
			seller.setShiperRegionId(shiperId);
		}
		if(StringUtils.isNotEmpty(seller.getReceiverRegionCodes()) && seller.getReceiverRegionCodes().split(",").length==3){
			Integer receiverId=regionMapper.getRegionByCode(seller.getReceiverRegionCodes().substring(seller.getReceiverRegionCodes().lastIndexOf(",")+1)).getRegionId();  //退货地址
			seller.setReceiverRegionId(receiverId);
		}
		if(StringUtils.isNotEmpty(seller.getCompanyRegionCodes()) && seller.getCompanyRegionCodes().split(",").length==3){
			Integer companyId=regionMapper.getRegionByCode(seller.getCompanyRegionCodes().substring(seller.getCompanyRegionCodes().lastIndexOf(",")+1)).getRegionId();   //公司地址
			seller.setCompanyRegionId(companyId);
		}
		
		//添加商家
		seller.setUserId(user.getUserId());
		userInfoMapper.addSeller(seller);
		
		//添加角色
		List<Integer> uroleIds=new ArrayList<Integer>();
		uroleIds.add(UserInfo.USER_ROLE_SELLER);
		userMapper.addUserUroles(user.getUserId(), uroleIds);
		
		//添加该商家要卖的品牌
		List listBrandIds=seller.getListBrandIds();
		if(listBrandIds!=null && listBrandIds.size()>0){
			for (Iterator iterator = listBrandIds.iterator(); iterator.hasNext();) {
				Integer brandId = (Integer) iterator.next();
				seller.setBrandId(brandId);
					this.addSellerBrand(seller);
			}
		}
		return seller.getSellerId();
	}

	/**
	 * 更新卖家
	 * @param seller
	 * @throws UhomeStoreException
	 */
	@Override
	public void updateSeller(Seller seller) throws UhomeStoreException {
		seller.setTel(seller.getUser().getTel());
		seller.setEmail(seller.getUser().getEmail());
		
		//需要根据最后一个regionCode查询到regionId
		if(StringUtils.isNotEmpty(seller.getShiperRegionCodes()) && seller.getShiperRegionCodes().split(",").length==3){
			Integer shiperId=regionMapper.getRegionByCode(seller.getShiperRegionCodes().substring(seller.getShiperRegionCodes().lastIndexOf(",")+1)).getRegionId();    //发货地址
			seller.setShiperRegionId(shiperId);
		}
		if(StringUtils.isNotEmpty(seller.getReceiverRegionCodes()) && seller.getReceiverRegionCodes().split(",").length==3){
			Integer receiverId=regionMapper.getRegionByCode(seller.getReceiverRegionCodes().substring(seller.getReceiverRegionCodes().lastIndexOf(",")+1)).getRegionId();  //退货地址
			seller.setReceiverRegionId(receiverId);
		}
		if(StringUtils.isNotEmpty(seller.getCompanyRegionCodes()) && seller.getCompanyRegionCodes().split(",").length==3){
			Integer companyId=regionMapper.getRegionByCode(seller.getCompanyRegionCodes().substring(seller.getCompanyRegionCodes().lastIndexOf(",")+1)).getRegionId();   //公司地址
			seller.setCompanyRegionId(companyId);
		}
		
		try {
			userService.saveUser(seller.getUser());
		} catch (YtoxlUserException e) {
			throw new UhomeStoreException(e.getMessage());
		}
		userInfoMapper.updateSeller(seller);
		
		//添加该商家要卖的品牌
		List listBrandIds=seller.getListBrandIds();
		if(listBrandIds==null || listBrandIds.size()==0){
			//删除所有用户选择的品牌
			this.delSellerBrand(seller);
		}else{
			//选删除用户取消选择的品牌
//			StringBuffer sb=new StringBuffer();
//			Iterator iterator1 = listBrandIds.iterator();
//			sb.append(iterator1.next());
//			for (; iterator1
//					.hasNext();) {
//				Integer object = (Integer) iterator1.next();
//				sb.append(","+object);
//			}
			seller.setBrandIds(listBrandIds.toString().substring(1,listBrandIds.toString().length()-1));
			this.delSellerBrand(seller);
			
			
			for (Iterator iterator = listBrandIds.iterator(); iterator.hasNext();) {
				Integer brandId = (Integer) iterator.next();
				seller.setBrandId(brandId);
				if(this.listBrandsCount(seller)==0){
					this.addSellerBrand(seller);
				}
			}
		}
	}
	
	/**
	 * 添加用户信息
	 * @param userInfo
	 * @throws UhomeStoreException
	 */
	@Override
	public Integer addBuyer(UserInfo userInfo) throws UhomeStoreException {
		// TODO Auto-generated method stub
		User user = userInfo.getUser();
		user.setPassword(defaultPass);
		userMapper.add(user);
		userInfo.setUserId(userInfo.getUser().getUserId());
		userInfoMapper.addBuyer(userInfo);
		return userInfo.getUser().getUserId();
	}

	/**
	 * 更新用户信息
	 * @param userInfo
	 * @throws UhomeStoreException
	 */
	@Override
	public void updateBuyer(UserInfo userInfo) throws UhomeStoreException {
		// TODO Auto-generated method stub
		userMapper.update(userInfo.getUser());
		userInfoMapper.updateBuyer(userInfo);
	}

	/**
	 * 更新用户状态
	 * @param userId
	 * @param status
	 * @throws UhomeStoreException
	 */
	@Override
	public void updateUserStauts(List<Integer> userIds, Integer status)
			throws UhomeStoreException {
		userMapper.updateStatusByUserIds(userIds, status);
	}
	
	/**
	 * 查询用户订阅的品牌名称 
	 * @param userId
	 * @return
	 */
	@Override
	public String listSubBrands(Integer userId) throws UhomeStoreException {
		// TODO Auto-generated method stub
		return userInfoMapper.listSubBrands(userId);
	}

	@Override
	public List<ReceiverAddress> getReceiverAddressList(Integer userId) throws UhomeStoreException {
		List<ReceiverAddress> address = receiverAddressMapper.getReceiverAddressList(userId);
		//查找省市区信息
		for(ReceiverAddress addr : address){
			Region region = regionMapper.getDetailInfoByRegionId(addr.getRegionId());
			addr.setRegion(region);
		}
		return address;
	}

	@Override
	public Integer addReceiverAddress(ReceiverAddress address) throws UhomeStoreException {

		//如果是第一条地址,不管有木有设置默认地址都设置为默认地址
		Integer count = receiverAddressMapper.myAddress(address.getUserId());
		if(count == 0){
			address.setIsDefault(ReceiverAddress.ISDEFAULT_TRUE);
		}else{
			//如果该用户已经存在默认地址，则先更改
			if(ReceiverAddress.ISDEFAULT_TRUE.equals(address.getIsDefault())){
				ReceiverAddress addr = receiverAddressMapper.getDefaultAddress(address.getUserId());
				if(addr != null) {
					addr.setIsDefault(ReceiverAddress.ISDEFAULT_FALSE);
					receiverAddressMapper.update(addr);
				}
			}
		}
		//判断是新增还是修改
		Integer row = null;
		if(address.getReceiverAddressId() == null){
			row = receiverAddressMapper.add(address);
		} else {
			row = receiverAddressMapper.update(address);
		}
		return row;
	}

	@Override
	public ReceiverAddress getReceiverAddressById(Integer addressId) throws UhomeStoreException {
		ReceiverAddress ra = receiverAddressMapper.get(addressId);
		RegionModel rm = regionMapper.getRegionIdsByRegionId(ra.getRegionId());
		ra.setRegionModel(rm);
		return ra;
	}
	
	/**
	 * 根据userId查询sellerId
	 * @param userId
	 * @return
	 */
	@Override
	public Integer getCurrentSellerId() throws UhomeStoreException{
		Integer sellerId = null;
		try {
			Integer userId = userService.getCurrentUser().getUserId();
			sellerId = userInfoMapper.getSellerIdByUserId(userId);
		} catch (YtoxlUserException e) {
			e.printStackTrace();
			throw new UhomeStoreException(e.getMessage());
		}
		return sellerId;
	}
	
	/**
	 * 查询需要导出的商家
	 */
	@Override
	public List<SellerTbl> listExportSellers() throws UhomeStoreException{
		return userInfoMapper.listExportSellers();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void resetPassword(Integer userId) throws UhomeStoreException {
		userService.updateDefaultPassword(userId);
	}

	/**
	 * 删除用户选择的品牌
	 * @param seller
	 * @return
	 * @throws UhomeStoreException
	 */
	@Override
	public Integer delSellerBrand(Seller seller) throws UhomeStoreException {
		return userInfoMapper.delSellerBrand(seller);
	}

	/**
	 * 查询用户是否选择该品牌
	 * @param seller
	 * @return
	 * @throws UhomeStoreException
	 */
	@Override
	public Integer listBrandsCount(Seller seller) throws UhomeStoreException {
		return userInfoMapper.listBrandsCount(seller);
	}

	/**
	 * 添加用户选择的品牌
	 * @param seller
	 * @return
	 * @throws UhomeStoreException
	 */
	@Override
	public Integer addSellerBrand(Seller seller) throws UhomeStoreException {
		return userInfoMapper.addSellerBrand(seller);
	}
	/**
	 * 查询卖家可售品牌
	 * @param sellerid
	 * @return
	 */
	@Override
	public List<Integer> listBrandsBySellerId(Integer sellerid)
			throws UhomeStoreException {
		return userInfoMapper.listBrandsBySellerId(sellerid);
	}
	
	/**
	 * 更新密码
	 * @param seller
	 * @return
	 * @throws UhomeStoreException
	 */
	@Override
	public Integer savePassword(Seller seller) throws UhomeStoreException {
		try {
			if(userService.checkPassword(userService.getCurrentUser().getUsername(), seller.getUser().getPassword())){
				if(StringUtils.isNotEmpty(seller.getNewPassword()) && StringUtils.isNotEmpty(seller.getConfirmNewPassword())
						&& seller.getNewPassword().equals(seller.getConfirmNewPassword())){
					User user=seller.getUser();
					user.setUserId(userService.getCurrentUser().getUserId());
					user.setPassword(seller.getNewPassword());
					userService.saveUser(user);
				}else{
					throw new UhomeStoreException("与新密码不一致");
				}
				
			}else{
				throw new UhomeStoreException("密码错误");
			}
		} catch (YtoxlUserException e) {
			throw new UhomeStoreException(e.getMessage());
		}
		return null;
	}
	/**
	 * 查询所有商家
	 * @return
	 * @throws UhomeStoreException
	 */
	@Override
	public List<Seller> listSellers() throws UhomeStoreException {
		return userInfoMapper.getSellerList();
	}

	@Override
	public void updateUser(UserInfo userInfo) throws UhomeStoreException {
		userInfoMapper.updateUser(userInfo);
	}

	@Override
	public UserInfo getUserByUserId(Integer userId) throws UhomeStoreException {
		UserInfo userInfo = userInfoMapper.getUserByUserId(userId);
		if(null != userInfo.getRegionId()){
			//Region region = regionMapper.getRegionCodesByRegionId(userInfo.getRegionId());
			//userInfo.setRegion(region);
			RegionModel regionModel = regionMapper.getRegionIdsByRegionId(userInfo.getRegionId());
			userInfo.setRegionModel(regionModel);
		}
		return userInfo;
	}

	@Override
	public UserInfo getMemberInfoByUserId(Integer userId) throws UhomeStoreException {
		UserInfo user = userInfoMapper.getMemberInfoByUserId(userId);
		Region region = regionMapper.getDetailInfoByRegionId(user.getRegionId());
		user.setRegion(region);
		return user;
	}

	@Override
	public void updateUserRegister(UserInfo userInfo)
			throws UhomeStoreException {
		userInfoMapper.updateUserRegister(userInfo);
	}

	@Override
	public void addUserInfo(User user) throws UhomeStoreException {
		userInfoMapper.addUserInfo(user);
	}
	
	/**
	 * 根据userId查询卖家信息
	 * @param sellerId
	 * @return
	 */
	@Override
	public Seller getSellerByUserId(Integer userId) throws UhomeStoreException {
		Seller seller=userInfoMapper.getSellerByUserId(userId);
		return seller;
	}

	@Override
	public void addRegister(User user) throws UhomeStoreException, YtoxlUserException {
			List list = new ArrayList();//注册新用户并且为其分配权限
			list.add(UserInfo.USER_ROLE_BUYER);
			user.setStatus(User.STATUS_ABLE);//设置其为激活状态
			user.setCreateByUserId(0);//设置其为激活状态
			userMapper.add(user);
			userMapper.addUserUroles(user.getUserId(),list);
			addUserInfo(user);//添加用户
			//送20积分
			Integer point = new Integer(20);
			Point p = new Point();
			p.setTotal(new Integer(point));
			p.setUserId(user.getUserId());
			
			pointMapper.add(p);
			
			PointDetail pd = new PointDetail();
			pd.setPointSource(Point.POINTSOURCE_REGISTER);
			pd.setPoint(point);
			pd.setPointId(p.getPointId());
			
			pointDetailMapper.add(pd);
			
	}

	@Override
	public void updateUserAndUserInfo(UserInfo userInfo) throws UhomeStoreException {
		try{
			//查询email 是否已经有了 有了给提示
			List<UserInfo> infos = userInfoMapper.getUserInfoByEmail(userInfo);
			//可以有一个  email是自己
			if(null == infos || infos.size() < 2){
				//通过regionCode查询到regionId
				Region region = regionMapper.getRegionByCode(userInfo.getRegionId()+"");
				if(null != region){
					userInfo.setRegionId(region.getRegionId());
				}
				updateUser(userInfo);//更新userInfo表中的数据
				updateUserRegister(userInfo);//更新user表中的数据
				return;
			}
		}catch(DataAccessException d){
			//抛出异常提示action 其他选项长度超出异常 故注释email重复! 提示
			throw new UhomeStoreException("email重复!");
//			throw new UhomeStoreException();
		}
		//抛出异常提示action
		//throw new UhomeStoreException("email重复!");
		throw new UhomeStoreException("");
	}

	@Override
	public boolean checkIsLogin() throws UhomeStoreException {
		// 找到当前 登录的用户
		CustomUserDetails customUserDetail = null;
		try {
			customUserDetail = userService.getCurrentUser();
			if(null != customUserDetail){
				return true;
			}
		} catch (YtoxlUserException e) {
			logger.error("当前用户没有登录:"+e.getMessage());
			throw new UhomeStoreException("当前用户没有登录");
		}
		return false;
	}

	@Override
	public CustomUserDetails checkUserIsLogin() throws UhomeStoreException {
		// 找到当前 登录的用户
		CustomUserDetails customUserDetail = null;
		try {
			customUserDetail = userService.getCurrentUser();
			if(null != customUserDetail){
				return customUserDetail;
			}
		} catch (YtoxlUserException e) {
			logger.error("当前用户没有登录:"+e.getMessage());
			throw new UhomeStoreException("当前用户没有登录");
		}
		return null;
	}

	@Override
	public List<Brand> getUserInfoBrandbyid(Integer id)
			throws DataAccessException {
		List<Brand> list=userInfoMapper.getUserInfoBrandbyid( id);
		return list;
	}
	
	/**
	 * 添加卖家的时候判断邮箱是否存在
	 * @param email
	 * @param userId
	 * @return boolean true :非重复 false:重复
	 */
	@Override
	public boolean validateEmailIsRepate(String email, Integer userId) {
		User user = userInfoMapper.validateEmailIsRepate(email);
		if(userId == null){
			if(user != null){
				return false;
			}
			return true;
		}
		if(user != null){
			if(user.getUserId().equals(userId)){
				return true;
			}
			return false;
		}
		return true;
	}
	
	/**
	 * 根据userId获取user对象，
	 * @param userId
	 * @return
	 */
	@Override
	public User getUserById(Integer userId) {
		return userInfoMapper.getUserById(userId);
	}
}
