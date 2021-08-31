package com.axis.octate.service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.axis.octate.model.Resource;
import com.axis.octate.model.ResourcePerm;
import com.axis.octate.model.Role;
import com.axis.octate.model.RolePermission;
import com.axis.octate.model.User;
import com.axis.octate.model.User_Role;
import com.axis.octate.repository.AccessRepository;
import com.axis.octate.repository.PermissionRepository;
import com.axis.octate.repository.ResourceRepository;
import com.axis.octate.repository.RolePermissionRepository;
import com.axis.octate.repository.RoleRepository;
import com.axis.octate.repository.UserRepository;
import com.axis.octate.repository.User_RoleRepository;
import com.axis.octate.request.ChangeRoleRequest;
import com.axis.octate.request.CompleteDetails;
import com.axis.octate.request.CompletePermissions;
import com.axis.octate.request.EditPermissonsRequest;


@Service
public class AccessServiceImpl implements AccessService {
	
	@Autowired
	AccessRepository accessRepository;

	@Autowired
	PermissionRepository permissionRepository;

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	User_RoleRepository userRoleRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ResourceRepository resourceRepository;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	RolePermissionRepository rolePermissionRepository;
	
	CompleteDetails data=new CompleteDetails();
	List<CompleteDetails> listEmp = new ArrayList<>();

	@Override
	public String setRole(User_Role userRole) {
		// TODO Auto-generated method stub
		userRoleRepository.save(userRole);
		return "users role is set";
	}

	public Resource addResource(Resource resource) {

		return accessRepository.save(resource) ;
	}

	public RolePermission grantPermissions(RolePermission rolePermission) {

		return permissionRepository.save(rolePermission);

	}


	public Role addRole(Role role) {

		return roleRepository.save(role) ;
	}

	@Override
	public List<RolePermission> getPermissionsByRoleId(int roleId) {

		return permissionRepository.findByroleId( roleId);
	}


	@Override
	public Role getRoleByRoleID(int roleID) {

		return roleRepository.findByroleID(roleID);
	}


	@Override
	public Role getByRoleName(String roleName) {
		

		return roleRepository.findByroleName(roleName);
	}

	@Override
	public String updatepermissionsByRoleIDAndResourceId(ResourcePerm resourcePerm) {

		Role role=getByRoleName(resourcePerm.getRoleName());

		System.out.println(resourcePerm.getPermissionList());

		for(RolePermission p:resourcePerm.getPermissionList()) {

			p.setRoleId(role.getRoleID());
		}

		System.out.println(resourcePerm);

		for(RolePermission p:resourcePerm.getPermissionList()) {

			RolePermission rp=permissionRepository.findByRoleIdAndResourceId(p.getRoleId(),p.getResourceId());

			rp.setCanView(p.isCanView());
			rp.setCanEdit(p.isCanEdit());
			rp.setCanAdd(p.isCanAdd());
			rp.setCanDelete(p.isCanDelete());
			System.out.println(rp);
			permissionRepository.save(rp);

		}
		return "success";
	}
	
	List<CompleteDetails> data1=new ArrayList<CompleteDetails>();
	
	public List<User> findAllUser() {
		return userRepository.findAll();
	}

	@Override
	public List<Role> getAllRoles() {
		//List<Role> roles=roleRepository.findAll();
		return roleRepository.findAll() ;
	}

	@Override
	public List<Resource> getAllResources() {
		// TODO Auto-generated method stub
		return resourceRepository.findAll();
	}

	@Override
	public CompleteDetails getEmployeeById(String id) {
		
		
		
		List<CompleteDetails> list1 =jdbcTemplate.query("select user.id as userId, user.name, user.EMAIL_ID,user.MOBILE,user.GENDER,user.DEPARTMENT,user.CITY,user.HIRED_DATE,\r\n"
				+ "user_role.role_id,role.ROLE_NAME\r\n"
				+ " from user\r\n"
				+ "inner join user_role on user.id= user_role.user_id\r\n"
				+ "inner join role on user_role.role_id = role.id\r\n"
				+ "where user.id=?;"
				,  new String[] { id }
				,  (ResultSet rs, int rowNum) -> {
					//data = new CompleteDetails();
					data.setUserId(rs.getString("userId"));
					data.setName(rs.getString("name"));
					data.setEmail(rs.getString("email_id"));
					data.setMobile(rs.getString("mobile"));
					data.setGender(rs.getString("gender"));
					data.setDepartment(rs.getString("department"));
					data.setCity(rs.getString("city"));
					data.setHiredDate(rs.getString("hired_date"));
					data.setRoleId(rs.getString("role_id"));
					data.setRoleName(rs.getString("role_name"));
					return data;
				});
		
		/*
		 * List<CompleteDetails> list2 = jdbcTemplate.
		 * query("select resource_role.id as permissionId,resource_role.resource_id,resource.resource_name,resource_role.can_add,resource_role.can_delete,resource_role.can_edit,resource_role.can_view from user_role \r\n"
		 * + "inner join resource_role on resource_role.role_id = user_role.role_id\r\n"
		 * + "inner join resource on resource.id = resource_role.resource_id\r\n" +
		 * "where user_role.user_id = ?" , new String[] { id } ,(ResultSet rs, int
		 * rowNum) -> {
		 * 
		 * data.setResourceId(rs.getString("resource_id"));
		 * data.setResourceName(rs.getString("resource_name"));
		 * data.setCan_add(rs.getBoolean("can_add"));
		 * data.setCan_edit(rs.getBoolean("can_edit"));
		 * data.setCan_delete(rs.getBoolean("can_delete"));
		 * data.setCan_View(rs.getBoolean("can_view")); return data;
		 * 
		 * });
		 */
		
		
		
		
		/*
		 * List<CompleteDetails> list3 = jdbcTemplate.
		 * query("select user.id as userId ,user.NAME,user.EMAIL_ID,user.mobile,user.gender,user.department,user.city,user.hired_date,user_role.role_id,role.ROLE_NAME,resource_role.resource_id,resource.resource_name, resource_role.can_add,resource_role.can_edit,resource_role.can_delete,resource_role.can_view from resource\r\n"
		 * + "inner join resource_role on resource.id=resource_role.resource_id\r\n" +
		 * "inner join role on role.id=resource_role.role_id \r\n" +
		 * "inner join user_role on role.id=user_role.role_id\r\n" +
		 * "inner join user on user.id= user_role.user_id where user.id=?;" , new
		 * String[] { id } , (ResultSet rs, int rowNum) -> { data = new
		 * CompleteDetails(); data.setUserId(rs.getString("userId"));
		 * data.setName(rs.getString("name")); data.setEmail(rs.getString("email_id"));
		 * data.setMobile(rs.getString("mobile"));
		 * data.setGender(rs.getString("gender"));
		 * data.setDepartment(rs.getString("department"));
		 * data.setCity(rs.getString("city"));
		 * data.setHiredDate(rs.getString("hired_date"));
		 * data.setRoleId(rs.getString("role_id"));
		 * data.setRoleName(rs.getString("role_name"));
		 * data.setResourceId(rs.getString("resource_id"));
		 * data.setResourceName(rs.getString("resource_name"));
		 * data.setCan_add(rs.getBoolean("can_add"));
		 * data.setCan_edit(rs.getBoolean("can_edit"));
		 * data.setCan_delete(rs.getBoolean("can_delete"));
		 * data.setCan_View(rs.getBoolean("can_view")); return data;
		 * 
		 * 
		 * 
		 * });
		 */
		
		return data;
	}


	@Override
	public String updateRole(String userId,ChangeRoleRequest changeRoleRequest) throws Exception {
		// TODO Auto-generated method stub
	  User_Role	info = userRoleRepository.findByUserId(userId);
	  
	  if(info != null) {
		  
		  System.out.println(info.getId()+info.getUserId()+changeRoleRequest.getRoleId()+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		
		User_Role roleInfo = new User_Role(info.getId(),info.getUserId(),changeRoleRequest.getRoleId());
		userRoleRepository.save(roleInfo);
		return "Role is updated";
		  
	  }else {
		  throw new Exception("role is not found for user");
	  }
	  
	
	}

	@Override
	public User_Role finduserRoleByUserId(String userId) {
		// TODO Auto-generated method stub
		return userRoleRepository.findByUserId(userId);
	}

	@Override
	public List<CompletePermissions> getPermission() {
		// TODO Auto-generated method stub
		
		

		List<CompletePermissions> permissionList =jdbcTemplate.query("select resource_role.id ,role.ROLE_NAME,resource.resource_name,resource_role.can_add,\r\n"
				+ "	resource_role.can_delete,resource_role.can_edit,resource_role.can_view from role \r\n"
				+ "	inner join resource_role on resource_role.role_id = role.id\r\n"
				+ "	inner join resource on resource.id = resource_role.resource_id"
				,  (ResultSet rs, int rowNum) -> {
					CompletePermissions permissions = new CompletePermissions();
					permissions.setId(rs.getString("id"));
					permissions.setRoleName(rs.getString("role_name"));
					permissions.setResourceName(rs.getString("resource_name"));
					permissions.setCanAdd(rs.getBoolean("can_add"));
					permissions.setCanDelete(rs.getBoolean("can_delete"));
					permissions.setCanEdit(rs.getBoolean("can_edit"));
					permissions.setCanView(rs.getBoolean("can_view"));
					return permissions;
				});
		
		
		
		
		return permissionList;
	}


	@Override
	public Optional<RolePermission> getPermissionsById(int id) {
		Optional<RolePermission> permission = permissionRepository.findById(id);
		return permission;
	}


	@Override
	public String editPermissionById(int id,EditPermissonsRequest editPermissionRequest) throws Exception {
		// TODO Auto-generated method stub
		Optional<RolePermission> permission = permissionRepository.findById(id);
		if(permission.isPresent()) {
			editPermissionRequest.setResourceId(permission.get().getResourceId());
			editPermissionRequest.setRoleId(permission.get().getRoleId());
			RolePermission edited =new RolePermission(id,editPermissionRequest.getResourceId(),editPermissionRequest.getRoleId(),
					editPermissionRequest.isCanAdd(),editPermissionRequest.isCanEdit(),editPermissionRequest.isCanView(),editPermissionRequest.isCanDelete()
					);
			permissionRepository.save(edited);
			return "permissions updated successfully";
		}else {
			throw new Exception("data not found");
		}
		
	}

	@Override
	public String deletePermissionById(int id) throws Exception {
		if(permissionRepository.existsById(id)) {
			
			permissionRepository.deleteById(id);
			return "deleted";
		
		}else if(!permissionRepository.existsById(id)) {
			throw new Exception("data not found");
		}
		return "";
		
	}

	@Override
	public String deleteUserRole(String userId) throws Exception {
		// TODO Auto-generated method stub
		userRoleRepository.deleteByUserId(userId);
		userRepository.deleteById(userId);
		return "deleted";
	}
	
	
	@Override 
	public List<RolePermission> viewAllRolePermissions(){
	   return rolePermissionRepository.findAll();
	}

}
