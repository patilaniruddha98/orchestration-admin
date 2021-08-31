package com.axis.octate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.axis.octate.model.Resource;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, String> {

}
