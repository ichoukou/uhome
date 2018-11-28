package com.ytoxl.module.uhome.uhomebase.common.utils.excel;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class Company  extends CompanyFather implements Serializable{
	private static final long serialVersionUID = 1L;
	@ExcelField(filedName="date",sort=2,formatType=FormatType.DATE,pattern="yyyy-MM-dd")
	private Date now =new Date();
	@ExcelField(filedName="公司名称",sort=2)
	private String companyName;

	@ExcelField(type=Type.OBJECT)
	private Address address;
	@ExcelField(type=Type.COLLECTION)
	private List<Person> persons;	
	
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Collection<Person> getPersons() {
		return persons;
	}
	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
	
	
}
