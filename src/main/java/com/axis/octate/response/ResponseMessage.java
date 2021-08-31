package com.axis.octate.response;

import java.util.Date;

public class ResponseMessage {
	private String message;
	
	private String id;
	
	private String name;
	
	private String email;
	
	private String mobile;
	
	private String gender;
	
	private String department;
	
	private String city;
	
	private Date hireDate;
	
	
	
	
	

	

	public ResponseMessage(String message) {
		super();
		this.message = message;
	}

	public ResponseMessage(String message, String id, String name, String email, String mobile, String gender,
			String department, String city, Date hireDate) {
		super();
		this.message = message;
		this.id = id;
		this.name = name;
		this.email = email;
		this.mobile = mobile;
		this.gender = gender;
		this.department = department;
		this.city = city;
		this.hireDate = hireDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
