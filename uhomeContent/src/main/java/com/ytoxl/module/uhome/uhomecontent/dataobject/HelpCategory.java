package com.ytoxl.module.uhome.uhomecontent.dataobject;

import java.util.List;

import com.ytoxl.module.uhome.uhomecontent.dataobject.tbl.HelpCategoryTbl;

public class HelpCategory extends HelpCategoryTbl {
	
	//商品详细页面  关于支付
	public static final String PAYS = "pays";
	//商品详细页面  正品保障
	public static final String GUARANTEE = "guarantee";
	public static final String[] proDetails = new String[]{PAYS,GUARANTEE};
	
	protected List<HelpCategory> helpCategorys;
	protected Help help;

	public List<HelpCategory> getHelpCategorys() {
		return helpCategorys;
	}

	public void setHelpCategorys(List<HelpCategory> helpCategorys) {
		this.helpCategorys = helpCategorys;
	}

	public Help getHelp() {
		return help;
	}

	public void setHelp(Help help) {
		this.help = help;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.name;
	}
}
