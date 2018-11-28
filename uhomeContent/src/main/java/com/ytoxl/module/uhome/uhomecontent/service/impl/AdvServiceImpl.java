package com.ytoxl.module.uhome.uhomecontent.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ytoxl.module.core.common.pagination.BasePagination;
import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.AdvPosition;
import com.ytoxl.module.uhome.uhomecontent.dataobject.Advertisement;
import com.ytoxl.module.uhome.uhomecontent.mapper.AdvMapper;
import com.ytoxl.module.uhome.uhomecontent.service.AdvService;

/**
 * 广告service
 * @author guoxinzhi
 *
 */
@Service
public class AdvServiceImpl implements AdvService {
	
	@Autowired
	private AdvMapper<Advertisement> advMapper;
	
	public AdvMapper<Advertisement> getAdvMapper() {
		return advMapper;
	}

	public void setAdvMapper(AdvMapper<Advertisement> advMapper) {
		this.advMapper = advMapper;
	}

	/*
	 * 根据位置查询有效的广告
	 */
	@Override
	public List<Advertisement> listAvailableAdvsByPosition(
			Advertisement advertisement) throws UhomeStoreException {
		// TODO Auto-generated method stub
		
		return advMapper.listAvailableAdvsByPosition(advertisement);
	}
	
	/*
	 * 分页查询广告
	 */
	@Override
	public void searchAdvs(BasePagination<Advertisement> advertisementPage)
			throws UhomeStoreException {
		// TODO Auto-generated method stub
		Map<String, Object> searchParams = advertisementPage.getSearchParamsMap();
		if (advertisementPage.isNeedSetTotal()) {
			Integer total = advMapper.searchAdvsCount(searchParams);
			advertisementPage.setTotal(total);
		}
		Collection<Advertisement> result = advMapper.searchAdvs(searchParams);
		advertisementPage.setResult(result);

	}
	
	/*
	 * 添加广告
	 */
	@Override
	public void addAdv(Advertisement advertisement) throws UhomeStoreException{
		// TODO Auto-generated method stub
		if(advertisement.getIsDefault()==Advertisement.ISDEFAULT_TRUE){
			List<Advertisement> advs=advMapper.getAdvByPositionAndIsDefault(advertisement);
			if(advs.size()>0){
				for (Advertisement adver:advs) {
					adver.setIsDefault(Advertisement.ISDEFAULT_FALSE);
					//更新备用广告  用户添加备用广告  将之前的备用广告改成 非广告
					advMapper.updateIsDefault(adver);
				}
			}
		}
		advMapper.add(advertisement);
	}
	
	/*
	 * 更新广告
	 */
	@Override
	public void updateAdvById(Advertisement advertisement) throws UhomeStoreException{
		// TODO Auto-generated method stub
		if(advertisement.getIsDefault()==Advertisement.ISDEFAULT_TRUE){
			List<Advertisement> advs=advMapper.getAdvByPositionAndIsDefault(advertisement);
			if(advs.size()>0){
				for (Iterator iterator = advs.iterator(); iterator.hasNext();) {
					Advertisement adver = (Advertisement) iterator
							.next();
					if(adver.getAdvertisementId()!= advertisement.getAdvertisementId()){
						adver.setIsDefault(Advertisement.ISDEFAULT_FALSE);
						advMapper.updateIsDefaultValue(adver);
					}
				}
			}
		}
		advMapper.update(advertisement);
	}
	
	/*
	 * 根据id查询广告
	 */
	@Override
	public Advertisement getAdvById(Integer id) throws UhomeStoreException{
		// TODO Auto-generated method stub
		return advMapper.get(id);
	}
	

	/*
	 * 查询广告位信息
	 */
	@Override
	public List<AdvPosition> listAdvPositons() throws UhomeStoreException {
		// TODO Auto-generated method stub
		return advMapper.listAdvPositons();
	}

	/* (non-Javadoc)
	 * @see com.ytoxl.module.uhome.uhomecontent.service.AdvService#getHomeDefaultSecKillAdvList()
	 */
	@Override
	public List<Advertisement> getHomeDefaultSecKillAdvList()
			throws UhomeStoreException {
		List<String> codes = new ArrayList<String>();
		codes.add(AdvPosition.ADV_OPTION_001);
		codes.add(AdvPosition.ADV_OPTION_002);
		codes.add(AdvPosition.ADV_OPTION_003);
		codes.add(AdvPosition.ADV_OPTION_004);
		codes.add(AdvPosition.ADV_OPTION_005);
		codes.add(AdvPosition.ADV_OPTION_006);
		codes.add(AdvPosition.ADV_OPTION_007);
		//查询非默认广告 且时间的当前时间内  如果不够用 默认广告填充
		List<Advertisement> advs = advMapper.getHomeSecKillAdvs(codes);
		int size = advs.size();
		if(size < AdvPosition.ADV_NUM){
			List<Advertisement> advertisements = advMapper.getHomeSecKillAdvList(codes);
			List<Advertisement> ads = advertisements.subList(0, AdvPosition.ADV_NUM - size);
			advs.addAll(ads);
		}else if(size > AdvPosition.ADV_NUM){
			//大于7个截取7个
			advs = advs.subList(0, size - 1);
		}
		return advs;
	}

	@Override
	public List<Advertisement> listHomeHistorySpecialAdv()throws UhomeStoreException {
		List<Advertisement> advs = null;
		List<String> codes = new ArrayList<String>();
		codes.add(AdvPosition.ADV_OPTION_008);
		advs = advMapper.listAdvertisementByAdvCode(codes);
		return advs;
	}
	
	/**获得首页右边5个广告
	 * @return
	 * @throws UhomeStoreException
	 */
	public List<Advertisement> listHomeRightAdvertisement()throws UhomeStoreException{
		List<Advertisement> advs = null;
		List<String> codes = new ArrayList<String>();
		codes.add(AdvPosition.ADV_OPTION_010);//80
		codes.add(AdvPosition.ADV_OPTION_011);//300
		advs = advMapper.listAdvertisementRightByAdvCode(codes);
		//广告位排序
		if(advs!=null&&advs.size()>0){
			List<Advertisement> advContainer300 = new ArrayList<Advertisement>();
			List<Advertisement> advContainer80 = new ArrayList<Advertisement>();
			for(Advertisement adv : advs){
				if(adv.getAdvPosition().getCode().equals(AdvPosition.ADV_OPTION_010)){
					advContainer80.add(adv);
				}else{
					advContainer300.add(adv);
				}
			}
			List<Advertisement> advContainer = new ArrayList<Advertisement>();
			if(advContainer300!=null&&advContainer300.size()>0){//
				if(advContainer300.size()==1){
					advContainer.add(advContainer300.get(0));
					if(advContainer80!=null&&advContainer80.size()>0){
						if(advContainer80.size()>4){
							for(int i=0;i<4;i++){
								advContainer.add(advContainer80.get(i));
							}
						}else{
							for(int i=0;i<advContainer80.size();i++){
								advContainer.add(advContainer80.get(i));
							}
						}
					}
				}else if(advContainer300.size()==2){
					advContainer.addAll(advContainer300);
					if(advContainer80!=null&&advContainer80.size()>0){
						if(advContainer80.size()>3){
							for(int i=0;i<3;i++){
								advContainer.add(advContainer80.get(i));
							}
						}else{
							for(int i=0;i<advContainer80.size();i++){
								advContainer.add(advContainer80.get(i));
							}
						}
					}
				}else if(advContainer300.size()==3){
					advContainer.add(advContainer300.get(0));
					advContainer.add(advContainer300.get(1));
					if(advContainer80!=null&&advContainer80.size()>0){
						if(advContainer80.size()==1){//
							advContainer.add(advContainer80.get(0));
							advContainer.add(advContainer300.get(2));
						}else if(advContainer80.size()==2){
							advContainer.add(advContainer80.get(0));
							advContainer.add(advContainer300.get(2));
							advContainer.add(advContainer80.get(1));
						}
					}else{
						advContainer.add(advContainer300.get(2));
					}
				}else if(advContainer300.size()>3){//大于3张
					advContainer.add(advContainer300.get(0));
					advContainer.add(advContainer300.get(1));
					if(advContainer80!=null&&advContainer80.size()>0){
						if(advContainer80.size()==1){//
							advContainer.add(advContainer80.get(0));
							if(advContainer300.size()>5){
								for(int i=2;i<4;i++){
									advContainer.add(advContainer300.get(i));
								}
							}else if(advContainer300.size()==5){
								for(int i=2;i<4;i++){
									advContainer.add(advContainer300.get(i));
								}
							}else{
								for(int i=2;i<advContainer300.size();i++){
									advContainer.add(advContainer300.get(i));
								}
							}
						}else if(advContainer80.size()==2){
							advContainer.add(advContainer80.get(0));
							advContainer.add(advContainer300.get(2));
							advContainer.add(advContainer80.get(1));
						}
					}else{
						if(advContainer300.size()==5){
							for(int i=2;i<advContainer300.size();i++){
								advContainer.add(advContainer300.get(i));
							}
						}else if(advContainer300.size()>5){
							for(int i=2;i<5;i++){
								advContainer.add(advContainer300.get(i));
							}
						}else{
							for(int i=2;i<advContainer300.size();i++){
								advContainer.add(advContainer300.get(i));
							}
						}
						
					}
				}
			}else{
				if(advContainer80.size()>5){
					for(int i=0;i<5;i++){
						advContainer.add(advContainer80.get(i));
					}
				}else if(advContainer80.size()==5){
					advContainer.addAll(advContainer80);
				}else{
					advContainer.addAll(advContainer80);
				}
			}
			advs = advContainer;
		}
		return advs;
	}
	@Override
	public Advertisement getHomeTopAdv() throws UhomeStoreException {
		List<Advertisement> advs = null;
		List<String> codes = new ArrayList<String>();
		codes.add(AdvPosition.ADV_OPTION_009);
		advs = advMapper.listAdvertisementByAdvCode(codes);
		if(null != advs && advs.size() > 0){
			return advs.get(0);
		}
		return null;
	}

	@Override
	public List<Advertisement> listHomeBroadcastAdv()
			throws UhomeStoreException {
		List<Advertisement> advs = advMapper.listHomeBroadcastAdv();
		return advs;
	}
}
