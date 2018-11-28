package com.ytoxl.module.uhome.uhomebase.common.utils.excel;

import java.io.Serializable;
import java.util.Collection;

public class Person implements Serializable{
	@ExcelField(filedName="个人姓名",sort=4)
	private String name;
	@ExcelField(filedName="个人年龄",sort=6)
	private String age;
	@ExcelField(type=Type.COLLECTION)
	private Collection<Phone> phones;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public Collection<Phone> getPhones() {
		return phones;
	}
	public void setPhones(Collection<Phone> phones) {
		this.phones = phones;
	}
	
	
}
