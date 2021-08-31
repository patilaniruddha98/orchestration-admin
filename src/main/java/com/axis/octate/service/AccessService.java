package com.axis.octate.service;

import java.util.List;
import java.util.Optional;

import com.axis.octate.model.Resource;
import com.axis.octate.model.ResourcePerm;
import com.axis.octate.model.Role;
import com.axis.octate.model.RolePermission;
import com.axis.octate.model.User_Role;
import com.axis.octate.request.ChangeRoleRequest;
import com.axis.octate.request.CompleteDetails;
import com.axis.octate.request.CompletePermissions;
import com.axis.octate.request.EditPermissonsRequest;

public interface AccessService {
	
	public String setRole(User_Role userRole);
	
	public Resource addResource(Resource resource);
	
	public Role addRole(Role role);
	
	public RolePermission grantPermissions(RolePermission rolePermission);
	
	public List<RolePermission>  getPermissionsByRoleId(int roleId);
	
	public Role getRoleByRoleID(int roleID);
	
	public Role getByRoleName(String roleName);
	
	public String updatepermissionsByRoleIDAndResourceId( ResourcePerm resourcePerm);
	
	public List<Role> getAllRoles();
	
	public List<Resource> getAllResources();
	
	public CompleteDetails getEmployeeById(String id);
	
	public User_Role finduserRoleByUserId(String userId);
	
	public List<CompletePermissions> getPermission();
	
	public String updateRole(String userId,ChangeRoleRequest changeRoleRequest) throws Exception;


	public Optional<RolePermission> getPermissionsById(int id);
	
	public String editPermissionById(int id, EditPermissonsRequest editPermissionRequest) throws Exception;
	
	public String deletePermissionById(int id) throws Exception;
	
	public String deleteUserRole(String userId) throws Exception;
	
	public List<RolePermission> viewAllRolePermissions();
	
}
