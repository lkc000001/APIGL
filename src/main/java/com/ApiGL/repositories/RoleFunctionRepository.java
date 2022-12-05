package com.ApiGL.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import com.ApiGL.entity.RoleFunction;
import com.ApiGL.entity.PermissionResponse;


public interface RoleFunctionRepository extends CrudRepository<RoleFunction, Long>{

	List<RoleFunction> findByApiglRoleId(Long apiglRoleId);
	
	@Query( "SELECT f.apiglfunctionid, f.apiglfunctionname, f.apiglfunctionshowname, " +
			"		f.apiglfunctionurl, f.apiglfunctionsort, f.enabled, " +
			"		f.type, p.permissionenabled " +
	 		"FROM APIGLFUNCTION f " +
	 		"LEFT JOIN (select rf.apiglfunctionid, rf.enabled as permissionenabled " +
	 		"  			from APIGLROLE r, ROLEFUNCTION rf " + 
			" 			where r.apiglroleid=:apiglRoleId  AND rf.apiglroleid=:apiglRoleId) as p " + 
			"ON p.apiglfunctionid = f.apiglfunctionid " + 
			"ORDER BY f.apiglfunctionid")
	Set<PermissionResponse> queryRolePermission(Long apiglRoleId);
	
	@Modifying
	@Query("DELETE FROM ROLEFUNCTION WHERE apiglroleid=:apiglRoleId")
	int deleteByapiglRoleId(Long apiglRoleId);
}
