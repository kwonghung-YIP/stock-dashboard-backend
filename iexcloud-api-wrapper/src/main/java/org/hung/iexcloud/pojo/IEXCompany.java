package org.hung.iexcloud.pojo;

import lombok.Data;

@Data
public class IEXCompany {
	
	private String symbol;
	private String companyName;
	private String exchange;
	private String industry;
	private String website;
	private String description;
	private String CEO;
	private String securityName;
	private String issueType;
	private String sector;
	private Integer primarySicCode;
	private Integer employees;
	private String[] tags;
	private String address;
	private String address2;
	private String state;
	private String city;
	private String zip;
	private String country;
	private String phone;
	
}
