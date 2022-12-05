package com.ApiGL.repositories;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ApiGL.entity.ApiGLRole;

public interface ApiglRoleRepository extends CrudRepository<ApiGLRole, Long>{

	@Query("SELECT * " +
	 		"FROM APIGLROLE " +
	 		"WHERE ((:apiglRoleNumber IS NULL OR :apiglRoleNumber = '') OR apiglrolenumber=:apiglRoleNumber) " +
			"  AND ((:apiglRoleName IS NULL OR :apiglRoleName = '') OR apiglrolename=:apiglRoleName) " + 
			"  AND ((:apiglRoleType IS NULL OR :apiglRoleType = '') OR apiglroletype=:apiglRoleType) " + 
			"  AND ((:apiglRoleDirections IS NULL OR :apiglRoleDirections = '') OR apiglroledirections LIKE :apiglRoleDirections) " + 
			"  AND ((:enabled IS NULL OR :enabled = '') OR enabled=:enabled) " + 
			"  ORDER BY apiglroleid DESC " + 
			"  OFFSET (:PageIndex -1) * :PageSize ROWS " + 
			"  FETCH NEXT :PageSize ROWS ONLY")
	List<ApiGLRole> queryApiglRole(@Param("apiglRoleNumber") String apiglRoleNumber, @Param("apiglRoleName") String apiglRoleName, 
			 @Param("apiglRoleType") String apiglRoleType, @Param("apiglRoleDirections") String apiglRoleDirections, 
			 @Param("enabled") String enabled, @Param("PageIndex") Integer PageIndex, @Param("PageSize") Integer PageSize);
	
	long count();
	
	ApiGLRole findByApiglRoleNameAndApiglRoleNumberAndApiglRoleType(String apiglrolename, String apiglrolenumber, String apiglRoleType);

}
