package com.axis.octate.controller;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.axis.octate.constant.PermissionURLConstants;
import com.axis.octate.jwt.JwtAuthTokenFilter;
import com.axis.octate.model.Resource;
import com.axis.octate.model.ResourcePerm;
import com.axis.octate.model.Role;
import com.axis.octate.model.RolePermission;
import com.axis.octate.model.User;
import com.axis.octate.model.User_Role;
import com.axis.octate.repository.UserRepository;
import com.axis.octate.request.ChangeRoleRequest;
import com.axis.octate.request.CompleteDetails;
import com.axis.octate.request.CompletePermissions;
import com.axis.octate.request.EditPermissonsRequest;
import com.axis.octate.request.EmailRequestDto;
import com.axis.octate.request.ResourcePermRequest;
import com.axis.octate.request.RolePermissionRequest;
import com.axis.octate.request.SignUpForm;
import com.axis.octate.response.ResponseMessage;
import com.axis.octate.service.AccessServiceImpl;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AccessController {

	@Autowired
	private AccessServiceImpl accessService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	JdbcTemplate jdbcTemplate;

	EmailRequestDto email = null;

	@PostMapping("/register")
	@PreAuthorize("hasPermission('" + PermissionURLConstants.ADMIN_API_SERVICE + "','" + PermissionURLConstants.ADD
			+ "')")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {

		if (userRepository.existsByEmailId(signUpRequest.getEmailId())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
					HttpStatus.BAD_REQUEST);
		}
		Date date = new Date();

		String crunchifyUUID = UUID.randomUUID().toString();

		User user = new User(signUpRequest.getName(), signUpRequest.getEmailId(), signUpRequest.getMobile(),
				signUpRequest.getGender(), signUpRequest.getDepartment(), signUpRequest.getCity(), date,
				encoder.encode(crunchifyUUID));

		email = new EmailRequestDto("Octate-Login Credentials", signUpRequest.getEmailId(),
				"username : " + signUpRequest.getEmailId() + "   password : " + crunchifyUUID);
		System.out.println(signUpRequest.getEmailFrom() + "************************************************");
		userRepository.save(user);

		User details = userRepository.findUserByEmailId(signUpRequest.getEmailId());

		User_Role userRole = new User_Role(details.getId(), signUpRequest.getRole());
		accessService.setRole(userRole);

		return new ResponseEntity<>(new ResponseMessage("User registered successfully!", details.getId(),
				details.getName(), details.getEmailId(), details.getMobile(), details.getGender(),
				details.getDepartment(), details.getCity(), details.getHiredDate()), HttpStatus.OK);
	}

	@PostMapping("/email")
	@PreAuthorize("hasPermission('" + PermissionURLConstants.ADMIN_API_SERVICE + "','" + PermissionURLConstants.ADD
			+ "')")
	public String sendEmail(@Valid @RequestBody EmailRequestDto emailRequestDto) {

		emailRequestDto.setEmail(email.getEmail());
		emailRequestDto.setBody(email.getBody());
		emailRequestDto.setSubject(email.getSubject());

		System.out.println(emailRequestDto.getFrom());
		System.out.println(emailRequestDto.getEmail());
		System.out.println(emailRequestDto.getBody());

		HttpHeaders headers = new HttpHeaders();

		headers.setBearerAuth(JwtAuthTokenFilter.jwt.toString());
		headers.setAccept(Arrays.asList(org.springframework.http.MediaType.APPLICATION_JSON));
		HttpEntity<EmailRequestDto> entity = new HttpEntity<EmailRequestDto>(emailRequestDto, headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:8082/api/access/hr/email",
				HttpMethod.POST, entity, String.class);

		return responseEntity.getBody();
	}

	@PostMapping("/setRole")
	@PreAuthorize("hasPermission('" + PermissionURLConstants.ADMIN_API_SERVICE + "','" + PermissionURLConstants.ADD
			+ "')")
	public String setRoletoUser(@RequestBody User_Role userRole) {

		return accessService.setRole(userRole);
	}

	@PostMapping("/grantPerm")
	@PreAuthorize("hasPermission('" + PermissionURLConstants.ADMIN_API_SERVICE + "','" + PermissionURLConstants.ADD
			+ "')")
	// @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
	public RolePermission grantPermissions(@RequestBody RolePermission rolePermission) {

		return accessService.grantPermissions(rolePermission);
	}

	@PostMapping("/addResource")
	@PreAuthorize("hasPermission('" + PermissionURLConstants.ADMIN_API_SERVICE + "','" + PermissionURLConstants.ADD
			+ "')")
	public Resource addResource(@RequestBody Resource resource) {

		return accessService.addResource(resource);
	}

	@PostMapping("/addRole")
	@PreAuthorize("hasPermission('" + PermissionURLConstants.ADMIN_API_SERVICE + "','" + PermissionURLConstants.ADD
			+ "')")
	public Role addRole(@RequestBody Role role) {

		return accessService.addRole(role);

	}

	@GetMapping("/getroleByRoleId/{roleID}")
	@PreAuthorize("hasPermission('" + PermissionURLConstants.ADMIN_API_SERVICE + "','" + PermissionURLConstants.VIEW
			+ "')")
	public Role getRoleByRoleID(@PathVariable int roleID) {

		return accessService.getRoleByRoleID(roleID);
	}

	@GetMapping("/getByRoleName/{roleName}")
	@PreAuthorize("hasPermission('" + PermissionURLConstants.ADMIN_API_SERVICE + "','" + PermissionURLConstants.VIEW
			+ "')")
	public Role getByRoleName(@PathVariable String roleName) {

		return accessService.getByRoleName(roleName);
	}

	@PostMapping("/createRolewithPerm")
	@PreAuthorize("hasPermission('" + PermissionURLConstants.ADMIN_API_SERVICE + "','" + PermissionURLConstants.ADD
			+ "')")
	public String createRoleWithPermissions(@RequestBody ResourcePerm resourcePerm) {

		System.out.println("inside ");
		Role role = new Role();

		role.setRoleName(resourcePerm.getRoleName());

		Role roledata = accessService.addRole(role);

		System.out.println(roledata.getRoleID());
		System.out.println(resourcePerm.getPermissionList());

		for (RolePermission p : resourcePerm.getPermissionList()) {

			p.setRoleId(roledata.getRoleID());
		}

		System.out.println(resourcePerm.getPermissionList());
		for (RolePermission p : resourcePerm.getPermissionList()) {
			accessService.grantPermissions(p);
		}
		return "successFully created";

	}

	@GetMapping("/getpermissions/{roleID}")
	@PreAuthorize("hasPermission('" + PermissionURLConstants.ADMIN_API_SERVICE + "','" + PermissionURLConstants.VIEW
			+ "')")
	public List<RolePermission> getPermissionsByRoleId(@PathVariable int roleID) {

		return accessService.getPermissionsByRoleId(roleID);
	}

	@GetMapping("/getall")
	@PreAuthorize("hasPermission('" + PermissionURLConstants.ADMIN_API_SERVICE + "','" + PermissionURLConstants.VIEW
			+ "')")
	public List<User> getAllUser() {
		return accessService.findAllUser();
	}

	@GetMapping("/getAllRoles")
	@PreAuthorize("hasPermission('" + PermissionURLConstants.ADMIN_API_SERVICE + "','" + PermissionURLConstants.VIEW
			+ "')")
	public List<Role> getAllRoles() {
		return accessService.getAllRoles();
	}

	@GetMapping("/getAllResources")
	@PreAuthorize("hasPermission('" + PermissionURLConstants.ADMIN_API_SERVICE + "','" + PermissionURLConstants.VIEW
			+ "')")
	public List<Resource> getAllResources() {
		return accessService.getAllResources();
	}

	@GetMapping("/getEmployee/{id}")
	@PreAuthorize("hasPermission('" + PermissionURLConstants.ADMIN_API_SERVICE + "','" + PermissionURLConstants.VIEW
			+ "')")
	public CompleteDetails getEmp(@PathVariable("id") String id) {
		return accessService.getEmployeeById(id);
	}

	@GetMapping("/getuserrole/{userId}")
	@PreAuthorize("hasPermission('" + PermissionURLConstants.ADMIN_API_SERVICE + "','" + PermissionURLConstants.VIEW
			+ "')")
	public User_Role getRoleOfUser(@PathVariable("userId") String userId) {
		return accessService.finduserRoleByUserId(userId);
	}

	@PutMapping("/editrole/{userId}")
	@PreAuthorize("hasPermission('" + PermissionURLConstants.ADMIN_API_SERVICE + "','" + PermissionURLConstants.EDIT
			+ "')")
	public String updateUserRole(@PathVariable("userId") String userId,
			@RequestBody ChangeRoleRequest changeRoleRequest) throws Exception {
		return accessService.updateRole(userId, changeRoleRequest);

	}

	@GetMapping("/permissions")
	@PreAuthorize("hasPermission('" + PermissionURLConstants.ADMIN_API_SERVICE + "','" + PermissionURLConstants.VIEW
			+ "')")
	public List<CompletePermissions> getAllPermissions() {
		return accessService.getPermission();
	}

	@GetMapping("/permission/{id}")
	@PreAuthorize("hasPermission('" + PermissionURLConstants.ADMIN_API_SERVICE + "','" + PermissionURLConstants.VIEW
			+ "')")
	public Optional<RolePermission> getpermission(@PathVariable("id") int id) {
		return accessService.getPermissionsById(id);
	}

	@PutMapping("/editPermission/{id}")
	@PreAuthorize("hasPermission('" + PermissionURLConstants.ADMIN_API_SERVICE + "','" + PermissionURLConstants.EDIT
			+ "')")
	public String updatePermissionbyId(@PathVariable("id") int id,
			@RequestBody EditPermissonsRequest editPermissionRequest) throws Exception {
		return accessService.editPermissionById(id, editPermissionRequest);
	}

	@DeleteMapping("/deletePermission/{id}")
	@PreAuthorize("hasPermission('" + PermissionURLConstants.ADMIN_API_SERVICE + "','" + PermissionURLConstants.DELETE
			+ "')")
	public String deletePermission(@PathVariable("id") int id) throws Exception {
		return accessService.deletePermissionById(id);
	}

	@Transactional
	@DeleteMapping("/deleteUser/{userId}")
	@PreAuthorize("hasPermission('" + PermissionURLConstants.ADMIN_API_SERVICE + "','" + PermissionURLConstants.DELETE
			+ "')")
	public String deleteUserById(@PathVariable("userId") String userId) throws Exception {
		return accessService.deleteUserRole(userId);
	}

	@GetMapping("/getPermissionsByRoleName/{roleName}")
	@PreAuthorize("hasPermission('" + PermissionURLConstants.ADMIN_API_SERVICE + "','" + PermissionURLConstants.VIEW
			+ "')")
	public ResourcePermRequest getPermbyRoleName(@PathVariable("roleName") String roleName) {
		Role role = getByRoleName(roleName);
		List<RolePermissionRequest> list1 = jdbcTemplate.query(
				"select r_role.id,r_role.resource_id,r.resource_name,r.api_url,ro.ROLE_NAME,r_role.role_id, r_role.can_add, r_role.can_edit, r_role.can_view, r_role.can_delete from resource r \r\n"
						+ "inner join resource_role r_role on r.id=r_role.resource_id\r\n"
						+ "inner join role ro on ro.id=r_role.role_id\r\n"
						+ "inner join user_role ur on ro.id=ur.role_id\r\n" + "inner join user u on u.id=ur.user_id\r\n"
						+ "where ro.ROLE_NAME=?;",
				new String[] { roleName }, (ResultSet rs, int rowNum) -> {
					RolePermissionRequest data = new RolePermissionRequest();
					data.setId(rs.getInt("id"));
					data.setResourceId(rs.getInt("resource_id"));
					data.setResourceName(rs.getString("resource_name"));
					data.setRoleId(rs.getInt("role_id"));
					data.setCanAdd(rs.getBoolean("can_add"));
					data.setCanDelete(rs.getBoolean("can_delete"));
					data.setCanEdit(rs.getBoolean("can_edit"));
					data.setCanView(rs.getBoolean("can_view"));
					return data;
				});

		ResourcePermRequest resourcePerm = new ResourcePermRequest();
		resourcePerm.setRoleName(roleName);
		resourcePerm.setPermissionList(list1);

		return resourcePerm;
	}

	@PutMapping("/updatepermissionsByRoleID")
	@PreAuthorize("hasPermission('" + PermissionURLConstants.ADMIN_API_SERVICE + "','" + PermissionURLConstants.EDIT
			+ "')")
	public String updatepermissionsByRoleIDAndResourceId(@RequestBody ResourcePerm resourcePerm) {

		System.out.println("inside update ");

		return accessService.updatepermissionsByRoleIDAndResourceId(resourcePerm);

	}
	
	@GetMapping("/viewAllRolePermissions")
	public List<RolePermission> viewallRolePermissions(){
		return accessService.viewAllRolePermissions();
		}
	
	
	

}
