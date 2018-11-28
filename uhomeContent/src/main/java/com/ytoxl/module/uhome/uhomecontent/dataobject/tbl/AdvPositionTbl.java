package com.ytoxl.module.uhome.uhomecontent.dataobject.tbl;

/**
 * 广告显示的位置
 * @author guoxinzhi
 *
 */
public class AdvPositionTbl {
	protected Integer advpositionId;
	protected Short advType;            // 广告类型： 1=单图，2=轮换，3=高级，4=多图平铺
	protected Integer num;                // 限制数量    
	protected String name;                // 广告位置名称
	protected String code;                // 广告名称对应的标识
	protected Integer height;             // 广告最小单位的高度
	protected Integer width;              // 广告最小单位的宽度
	
	public Integer getAdvpositionId() {
		return advpositionId;
	}
	public void setAdvpositionId(Integer advpositionId) {
		this.advpositionId = advpositionId;
	}
	public Short getAdvType() {
		return advType;
	}
	public void setAdvType(Short advType) {
		this.advType = advType;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	
}
