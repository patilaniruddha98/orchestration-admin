package com.axis.octate.request;

import java.util.Date;

public class SignUpForm {
	
	private String emailFrom;
	  
    private String name;

    private String emailId;
    
    private String mobile;
    
    private String gender;
    
    private String department;
    
    private String city;
    
    private Date hiredDate;

    private String password;
    
    private String role;
    
    

	



	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public Date getHiredDate() {
		return hiredDate;
	}

	public void setHiredDate(Date hiredDate) {
		this.hiredDate = hiredDate;
	}

	public SignUpForm(String name, String emailId,String mobile, String gender, String department, String city,Date hiredDate, String password) {
		super();
		this.name = name;
		this.emailId = emailId;
		this.mobile=mobile;
		this.gender = gender;
		this.department = department;
		this.city=city;
		this.hiredDate = hiredDate;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailFrom() {
		return emailFrom;
	}

	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}

	public SignUpForm() {
		super();
		// TODO Auto-generated constructor stub
	}


    
  
}
