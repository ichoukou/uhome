package com.ytoxl.module.uhome.uhomecontent.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ytoxl.module.uhome.uhomebase.common.exception.UhomeStoreException;
import com.ytoxl.module.uhome.uhomecontent.dataobject.Help;
import com.ytoxl.module.uhome.uhomecontent.dataobject.HelpCategory;
import com.ytoxl.module.uhome.uhomecontent.mapper.HelpMapper;
import com.ytoxl.module.uhome.uhomecontent.service.HelpService;
@Service
public class HelpServiceImpl implements HelpService {
	@Autowired
	private HelpMapper<HelpCategory> helpMapper;
	@Override
	public List<HelpCategory> listHelpCategorys() throws UhomeStoreException {
		// TODO Auto-generated method stub
		List<HelpCategory> helpCategorys=helpMapper.listHelpCategorys();
		for (Iterator<HelpCategory> iterator = helpCategorys.iterator(); iterator.hasNext();) {
			HelpCategory helpCategory = (HelpCategory) iterator.next();
			helpCategory.setHelpCategorys(helpMapper.listHelps(helpCategory.getHelpCategoryId()));
		}
		return helpCategorys;
	}

	@Override
	public void updateContentById(Help help) throws UhomeStoreException {
		// TODO Auto-generated method stub
		helpMapper.updateContent(help);

	}

	@Override
	public Help getContentByHelpId(Integer helpId) throws UhomeStoreException {
		// TODO Auto-generated method stub
		return helpMapper.getContentByHelpId(helpId);
	}

	/* (non-Javadoc)
	 * @see com.ytoxl.module.uhome.uhomecontent.service.HelpService#getHelp4ProductDetail()
	 */
	@Override
	public Map<String,List<HelpCategory>> getHelp4ProductDetail()
			throws UhomeStoreException {
		List<String> productDetailHelps1 = new ArrayList<String>();
		List<String> productDetailHelps2 = new ArrayList<String>();
		
		productDetailHelps1.add("支付方式");
		productDetailHelps1.add("关于发票");
		
		productDetailHelps2.add("正品保障");
		productDetailHelps2.add("退换货政策");
		productDetailHelps2.add("退货细则");
		productDetailHelps2.add("退款流程");

		Map<String,List<String>> productDetailHelpsMap= new HashMap<String,List<String>>();
		productDetailHelpsMap.put(HelpCategory.PAYS, productDetailHelps1);
		productDetailHelpsMap.put(HelpCategory.GUARANTEE, productDetailHelps2);
		
		Map<String,List<HelpCategory>> map = new HashMap<String,List<HelpCategory>>();
		
		//查找所有的 菜单
		List<HelpCategory> helpCategorys = listHelpCategorys();
		
		//System.out.println("#################:helpCategorys"+helpCategorys);
		for(String s : HelpCategory.proDetails){
			List<HelpCategory> productDetailHelpCategorys = new ArrayList<HelpCategory> ();
			for(String str : productDetailHelpsMap.get(s) ){
				for(HelpCategory helpCategory : helpCategorys){
					for(HelpCategory hc : helpCategory.getHelpCategorys()){
						if(str.contains(hc.getName())){
							productDetailHelpCategorys.add(hc);
						}
					}
				}
				map.put(s, productDetailHelpCategorys);
			}
		}
		//System.out.println("@@@@@@@@@@@@@@@@productDetailHelpCategorys:"+map);
		return map;
	}

}
