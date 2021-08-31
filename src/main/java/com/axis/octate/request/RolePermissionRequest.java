package com.axis.octate.request;

import javax.persistence.Column;

public class RolePermissionRequest {
	
	private int id ;
	
	
	private int resourceId;
	
	private String resourceName;
	

	private int roleId;
	

	private boolean canAdd;
	

	private boolean canEdit;
	
	
	private boolean canView;
	
	
	private boolean canDelete;


	
	  public int getId() { return id; }
	  
	  
	  public void setId(int id) { this.id = id; }
	 


	public int getResourceId() {
		return resourceId;
	}


	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}


	
	  public String getResourceName() { return resourceName; }
	  
	  
	  public void setResourceName(String resourceName) { this.resourceName =
	  resourceName; }
	  
	  
	  public int getRoleId() { return roleId; }
	  
	  
	  public void setRoleId(int roleId) { this.roleId = roleId; }
	 

	public boolean isCanAdd() {
		return canAdd;
	}


	public void setCanAdd(boolean canAdd) {
		this.canAdd = canAdd;
	}


	public boolean isCanEdit() {
		return canEdit;
	}


	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}


	public boolean isCanView() {
		return canView;
	}


	public void setCanView(boolean canView) {
		this.canView = canView;
	}


	public boolean isCanDelete() {
		return canDelete;
	}


	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}


	public RolePermissionRequest(int id, int resourceId, String resourceName, int roleId, boolean canAdd,
			boolean canEdit, boolean canView, boolean canDelete) {
		super();
		this.id = id;
		this.resourceId = resourceId;
		this.resourceName = resourceName;
		this.roleId = roleId;
		this.canAdd = canAdd;
		this.canEdit = canEdit;
		this.canView = canView;
		this.canDelete = canDelete;
	}


	public RolePermissionRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
