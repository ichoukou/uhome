package com.ytoxl.module.uhome.uhomebase.dataobject.tbl;

public class PartnerTbl {
	protected Integer partnerId;
	protected String name;
	protected String unionId;
	protected String pushLink;
	protected String hash;
	protected Short type;

	public Integer getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getPushLink() {
		return pushLink;
	}

	public void setPushLink(String pushLink) {
		this.pushLink = pushLink;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

}
