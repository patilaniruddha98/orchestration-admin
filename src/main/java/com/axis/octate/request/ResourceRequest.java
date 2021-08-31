package com.axis.octate.request;

public class ResourceRequest {
	
	private String roleName;
	
	
	private int id ;

	public String getRoleName() {
		return roleName;
	}


	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getResourceId() {
		return resourceId;
	}


	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}


	public String getResourceName() {
		return resourceName;
	}


	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}


	public int getRoleId() {
		return roleId;
	}


	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}


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


	private int resourceId;
	
	private String resourceName;
	

	private int roleId;
	
	private boolean canAdd;
	

	private boolean canEdit;
	
	
	private boolean canView;
	
	
	private boolean canDelete;
	

	
    
    
	

}
