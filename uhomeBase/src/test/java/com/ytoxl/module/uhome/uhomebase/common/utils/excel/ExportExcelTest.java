package com.ytoxl.module.uhome.uhomebase.common.utils.excel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

public class ExportExcelTest {
	@Test
	public void testExportExcel() throws IOException {
		Company c1 = new Company();
		c1.setCompanyName("公司1");
		Address add = new Address();
		add.setAddr("ccccc");
		add.setNo("11hao");
		AddressSon addSon = new AddressSon();
		addSon.setAddSonName("addsoname1");
		addSon.setAddSonName2("addsoname2");
		add.setAddressSon(addSon);
		c1.setAddress(add);
		c1.setId("公司1id");
		List<Person> ps1 = new ArrayList<Person>();
		Person p11 = new Person();
		p11.setName("公司1姓名1");
		p11.setAge("公司1年龄1");
		Collection<Phone> ps1ps1=new ArrayList<Phone>();
		Phone p111 = new Phone();
		p111.setMobilePhone("公司1姓名1电话1");
		p111.setGudingPhone("公司1姓名1固定电话1");
		Phone p112 = new Phone();
		p112.setMobilePhone("公司1姓名1电话2");
		p112.setGudingPhone("公司1姓名1固定电话2");
		ps1ps1.add(p111);
		ps1ps1.add(p112);
		p11.setPhones(ps1ps1);
		ps1.add(p11);
		Person p12 = new Person();
		p12.setName("公司1姓名2");
		p12.setAge("公司1年龄2");
		ps1.add(p12);
		Person p13 = new Person();
		p13.setName("公司1姓名3");
		p13.setAge("公司1年龄3");
		ps1.add(p13);
		c1.setPersons(ps1);
		
		Company c2 = new Company();
		c2.setCompanyName("公司2");
		//c2.setAddress("公司1地址");
		c2.setId("公司2id");
		
		Collection<Company> cccs = new ArrayList<Company>();
		cccs.add(c1);
		cccs.add(c2);
		ExportExcel<Company> ee = new ExportExcel<Company>(cccs);
		ee.setMerge(true);
		ee.export("d:/cc.xls");
	}
}
