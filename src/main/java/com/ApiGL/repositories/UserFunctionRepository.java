package com.ApiGL.repositories;

import java.util.Set;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import com.ApiGL.entity.PermissionResponse;
import com.ApiGL.entity.UserFunction;

public interface UserFunctionRepository extends CrudRepository<UserFunction, Long>{	
	@Query( "SELECT f.apiglfunctionid, f.apiglfunctionname, f.apiglfunctionshowname, " +
			"		f.apiglfunctionurl, f.apiglfunctionsort, f.enabled, " +
			"		f.type, p.permissionenabled " +
	 		"FROM APIGLFUNCTION f " +
	 		"LEFT JOIN (select uf.apiglfunctionid, uf.enabled as permissionenabled " +
	 		"  			from [USER] u, USERFUNCTION uf " + 
			" 			where u.userid=:userId  AND uf.userid=:userId ) as p " + 
			"ON p.apiglfunctionid = f.apiglfunctionid " + 
			"WHERE f.enabled=1 " + 
			"ORDER BY f.apiglfunctionid")
	Set<PermissionResponse> queryUserPermission(Long userId);
	
	@Modifying
	@Query("DELETE FROM USERFUNCTION WHERE userid=:userId")
	int deleteByUserId(Long userId);
}
