package com.axis.octate.controller;

import java.sql.ResultSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axis.octate.constant.PermissionURLConstants;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DashboardController {

	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@GetMapping("/numberofEmp")
	@PreAuthorize("hasPermission('" + PermissionURLConstants.ADMIN_API_SERVICE + "','" + PermissionURLConstants.VIEW
			+ "')")
	public String getNumberOfEmp() {
		
		List<String> totalEmployees = jdbcTemplate.query(
				"select count(ID) from user;"
				,(ResultSet rs, int rowNum) -> {
					
					return rs.getString("count(ID)");
				});
		
		return totalEmployees.get(0);
		
		
	}
	@GetMapping("/numberofRoles")
	@PreAuthorize("hasPermission('" + PermissionURLConstants.ADMIN_API_SERVICE + "','" + PermissionURLConstants.VIEW
			+ "')")
	public String getNumberOfRoles() {
		
		List<String> totalRoles = jdbcTemplate.query(
				"select count(ID) from role;"
				,(ResultSet rs, int rowNum) -> {
					
					return rs.getString("count(ID)");
				});
		
		return totalRoles.get(0);
		
		
	}
	
	
	@GetMapping("/numberofResources")
	@PreAuthorize("hasPermission('" + PermissionURLConstants.ADMIN_API_SERVICE + "','" + PermissionURLConstants.VIEW
			+ "')")
	public String getNumberOfResources() {
		
		List<String> totalResources = jdbcTemplate.query(
				"select count(ID) from resource;"
				,(ResultSet rs, int rowNum) -> {
					
					return rs.getString("count(ID)");
				});
		
		return totalResources.get(0);
		
		
	}
	
	

	
	
	@GetMapping("/numberofDocumentRequest")
	@PreAuthorize("hasPermission('" + PermissionURLConstants.ADMIN_API_SERVICE + "','" + PermissionURLConstants.VIEW
			+ "')")
	public String getNumberOfDocumentRequest() {
		
		List<String> totalNumberOfRequest = jdbcTemplate.query(
				"select count(ID) from onboarding_documents"
				,(ResultSet rs, int rowNum) -> {
					
					return rs.getString("count(ID)");
				});
		
		return totalNumberOfRequest.get(0);
		
		
	}
	@GetMapping("/numberofPendingDocumentSubmittedRequest")
	@PreAuthorize("hasPermission('" + PermissionURLConstants.ADMIN_API_SERVICE + "','" + PermissionURLConstants.VIEW
			+ "')")
	public int getNumberOfDocumentSubmittedRequest() {
		
		List<Integer> totalNumberOfRequest = jdbcTemplate.query(
				"select count(ID) from onboarding_documents;"
				,(ResultSet rs, int rowNum) -> {
					
					return rs.getInt("count(ID)");
				});
		
		List<Integer> totalNumberOfSubmittedDocuments = jdbcTemplate.query(
				"select count(educational_documents) from onboarding_documents where educational_documents is not null"
				,(ResultSet rs, int rowNum) -> {
					
					return rs.getInt("count(educational_documents)");
				});
		int submissionPending = totalNumberOfRequest.get(0) - totalNumberOfSubmittedDocuments.get(0);
		
		
		
		return submissionPending;
		
		
	}

	
	
	@GetMapping("/numberofDocumentVerificatonRequest")
	@PreAuthorize("hasPermission('" + PermissionURLConstants.ADMIN_API_SERVICE + "','" + PermissionURLConstants.VIEW
			+ "')")
	public String getNumberOfDocumentVerificationRequest() {
		
		List<String> totalNumberOfRequest = jdbcTemplate.query(
				"select count(verification_id) from onboarding_documents_verification_status;"
				,(ResultSet rs, int rowNum) -> {
					
					return rs.getString("count(verification_id)");
				});
		
		return totalNumberOfRequest.get(0);
		
		
	}
	
	@GetMapping("/numberofPendingDocumentVerificationRequest")
	@PreAuthorize("hasPermission('" + PermissionURLConstants.ADMIN_API_SERVICE + "','" + PermissionURLConstants.VIEW
			+ "')")
	public String getNumberOfPendingDocumentRequest() {
		
		List<String> pendingRequest = jdbcTemplate.query(
				"select count(verification_id) \r\n"
				+ "from onboarding_documents_verification_status \r\n"
				+ "where senior_manager_confirmation is not null\r\n"
				+ "\r\n"
				+ " ;"
				,(ResultSet rs, int rowNum) -> {
					
					return rs.getString("count(verification_id)");
				});
		
		
		
		
		
		
		return pendingRequest.get(0);
		
		
	}
	
	
	
	
	
	@GetMapping("/numberofInterviews")
	@PreAuthorize("hasPermission('" + PermissionURLConstants.ADMIN_API_SERVICE + "','" + PermissionURLConstants.VIEW
			+ "')")
	public String getNumberOfInterviews() {
		
		List<String> totalInterviews = jdbcTemplate.query(
				"select count(ID) from interviews;"
				,(ResultSet rs, int rowNum) -> {
					
					return rs.getString("count(ID)");
				});
		
		return totalInterviews.get(0);
		
		
	}
	
	
	@GetMapping("/numberofConfirmInterviews")
	@PreAuthorize("hasPermission('" + PermissionURLConstants.ADMIN_API_SERVICE + "','" + PermissionURLConstants.VIEW
			+ "')")
	public String getNumberOfConfirmInterviews() {
		
		List<String> totalConfirmInterviews = jdbcTemplate.query(
				"select count(confirmation) from interviews where confirmation = \"confirm\";"
				,(ResultSet rs, int rowNum) -> {
					
					return rs.getString("count(confirmation)");
				});
		
		return totalConfirmInterviews.get(0);
		
		
	}
	
	
	@GetMapping("/numberofDenyInterviews")
	@PreAuthorize("hasPermission('" + PermissionURLConstants.ADMIN_API_SERVICE + "','" + PermissionURLConstants.VIEW
			+ "')")
	public String getNumberOfDenyInterviews() {
		
		List<String> totalDenyInterviews = jdbcTemplate.query(
				"select count(confirmation) from interviews where confirmation = \"deny\";"
				,(ResultSet rs, int rowNum) -> {
					
					return rs.getString("count(confirmation)");
				});
		
		return totalDenyInterviews.get(0);
		
		
	}
	
	
	@GetMapping("/numberofPendingInterviews")
	@PreAuthorize("hasPermission('" + PermissionURLConstants.ADMIN_API_SERVICE + "','" + PermissionURLConstants.VIEW
			+ "')")
	public String getNumberOfPendingInterviews() {
		
		List<String> totalPendingInterviews = jdbcTemplate.query(
				"select count(confirmation) from interviews where confirmation = \"Pending...\";"
				,(ResultSet rs, int rowNum) -> {
					
					return rs.getString("count(confirmation)");
				});
		
		return totalPendingInterviews.get(0);
		
		
	}
	
	
	@GetMapping("/numberofAssetsRequest")
	@PreAuthorize("hasPermission('" + PermissionURLConstants.ADMIN_API_SERVICE + "','" + PermissionURLConstants.VIEW
			+ "')")
	public String getNumberOfAssetsRequest() {
		
		List<String> totalAssetsRequest = jdbcTemplate.query(
				"select count(assets_request_id) from assets_request;"
				,(ResultSet rs, int rowNum) -> {
					
					return rs.getString("count(assets_request_id)");
				});
		
		return totalAssetsRequest.get(0);
		
		
	}
	
	@GetMapping("/numberofVrifiedAssetsRequest")
	@PreAuthorize("hasPermission('" + PermissionURLConstants.ADMIN_API_SERVICE + "','" + PermissionURLConstants.VIEW
			+ "')")
	public String getNumberOfPendingAssetsVerification() {
		
		List<String> totalPendingAssetsVerification = jdbcTemplate.query(
				"select count(assets_verification_id) \r\n"
				+ "from assets_verification\r\n"
				+ "where senior_manager_confirmation is null"
				,(ResultSet rs, int rowNum) -> {
					
					return rs.getString("count(assets_verification_id)");
				});
		
		return totalPendingAssetsVerification.get(0);
		
		
	}
	
}
