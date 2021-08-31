package com.axis.octate.request;

import java.util.List;



public class ResourcePermRequest {
	
	private String roleName;
	

	
	
    List<RolePermissionRequest>  permissionList;




	public String getRoleName() {
		return roleName;
	}




	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}




	public List<RolePermissionRequest> getPermissionList() {
		return permissionList;
	}




	public void setPermissionList(List<RolePermissionRequest> permissionList) {
		this.permissionList = permissionList;
	}




	public ResourcePermRequest() {
		super();
		// TODO Auto-generated constructor stub
	}




	public ResourcePermRequest(String roleName, List<RolePermissionRequest> permissionList) {
		super();
		this.roleName = roleName;
		this.permissionList = permissionList;
	}
    
    

}
