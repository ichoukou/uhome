package com.ytoxl.module.uhome.uhomebase.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomebase.dataobject.ReceiverAddress;
import com.ytoxl.module.uhome.uhomebase.dataobject.Region;
import com.ytoxl.module.uhome.uhomebase.dataobject.resultmap.RegionModel;
import com.ytoxl.module.uhome.uhomebase.mapper.ReceiverAddressMapper;
import com.ytoxl.module.uhome.uhomebase.mapper.RegionMapper;
import com.ytoxl.module.uhome.uhomebase.service.ReceiverAddressService;
import com.ytoxl.module.user.common.exception.YtoxlUserException;
import com.ytoxl.module.user.security.CustomUserDetails;
import com.ytoxl.module.user.service.UserService;
@Service
public class ReceiverAddressServiceImpl implements ReceiverAddressService {
	@Autowired
	private ReceiverAddressMapper<ReceiverAddress> receiverAddressMapper;
	@Autowired
	private UserService userService;
	
	@Autowired
	private RegionMapper<Region> regionMapper;
	

	/**
	 * 根据userID查询地址详细信息
	 * @param userId
	 * @return
	 */
	@Override
	public List<ReceiverAddress> getRAddressDetailList(Integer userId)
			throws UhomeStoreException {
		return receiverAddressMapper.getReceiverAddressList(userId);
	}

	@Override
	public ReceiverAddress getDefaultAddress(Integer userId)
			throws UhomeStoreException {
		return receiverAddressMapper.getDefaultAddress(userId);
	}

	@Override
	public List<ReceiverAddress> getOtherAddress(Integer userId)
			throws UhomeStoreException {
		return receiverAddressMapper.getOtherAddress(userId);
		}

	@Override
	public Integer delAddress(Integer id)
			throws UhomeStoreException {
		return receiverAddressMapper.del(id);
	}

	@Override
	public Integer addAddress(ReceiverAddress receiverAddress)
			throws UhomeStoreException {
		/*Region region  = regionMapper.getRegionByCode(receiverAddress.getRegionId().toString());
		if(region !=null){
			receiverAddress.setRegionId(region.getRegionId());
		}*/
		if(receiverAddress.getIsDefault().intValue() == 1){
			updatesNotDefaultAddress(receiverAddress.getUserId());
		}
		//如果是第一条地址,不管有木有设置默认地址都设置为默认地址
		Integer count = receiverAddressMapper.myAddress(receiverAddress.getUserId());
		if(count == 0){
			receiverAddress.setIsDefault(ReceiverAddress.ISDEFAULT_TRUE);
		}
		return receiverAddressMapper.add(receiverAddress);
	}

	@Override
	public Integer updateAddress(ReceiverAddress receiverAddress)
			throws UhomeStoreException {
		Region region  = regionMapper.getRegionByCode(receiverAddress.getRegionId().toString());
		if(region !=null){
			receiverAddress.setRegionId(region.getRegionId());
		}
		if(receiverAddress.getIsDefault().intValue() == 1){
			updatesNotDefaultAddress(receiverAddress.getUserId());
		}
		return receiverAddressMapper.update(receiverAddress) ;
	}

	@Override
	public void updateDefualtAddress(ReceiverAddress receiverAddress)
			throws UhomeStoreException {
		updatesNotDefaultAddress(receiverAddress.getUserId());
		receiverAddressMapper.updateDefaultAddress(receiverAddress.getReceiverAddressId());
	}

	@Override
	public ReceiverAddress getAddress(Integer receiverAddressId) throws UhomeStoreException {
		ReceiverAddress ra = receiverAddressMapper.get(receiverAddressId);
		RegionModel rm = regionMapper.getRegionIdsByRegionId(ra.getRegionId()); 
		ra.setRegionModel(rm);
		return ra;
	}

	@Override
	public void updatesNotDefaultAddress(Integer userId) {
		receiverAddressMapper.updatesNotDefaultAddress(userId);
	}

	@Override
	public int myAddress(Integer userId) throws UhomeStoreException {
		return receiverAddressMapper.myAddress(userId);
	}

	@Override
	public Boolean checkAddressIsMine(Integer addressId) throws UhomeStoreException {
		Boolean result = false;
		ReceiverAddress address = receiverAddressMapper.get(addressId);
		CustomUserDetails customUserDetail = null;
		try {
			customUserDetail = userService.getCurrentUser();
		} catch (YtoxlUserException e) {
			throw new UhomeStoreException("当前用户没有登录");
		}
		if(address != null && customUserDetail.getUserId().equals(address.getUserId())){
			result = true;
		} 
		return result;
	}

}
