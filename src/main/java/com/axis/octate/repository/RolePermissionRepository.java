package com.axis.octate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.axis.octate.model.RolePermission;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {
}
