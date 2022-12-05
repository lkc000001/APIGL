package com.ApiGL.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.ApiGL.entity.LpmApiLog;

public interface LpmApiLogRepository extends PagingAndSortingRepository<LpmApiLog, Long>{
	
	@Query("SELECT * " +
	 		"FROM LPMAPILog " +
	 		"WHERE ((:lpmUserId IS NULL OR :lpmUserId = '') OR LPMUserId LIKE :lpmUserId) " +
			"  AND ((:startDate IS NULL) OR CREATETIME BETWEEN :startDate AND :endDate) " + 
			"  AND ((:lpmData IS NULL OR :lpmData = '') OR LPMData LIKE :lpmData) " + 
			"  AND ((:queryType IS NULL OR :queryType = '') OR LPMSendStatus LIKE :queryType) " + 
			"  AND ((:queryUrl IS NULL OR :queryUrl = '') OR LPMURL LIKE :queryUrl) " + 
			"  ORDER BY LPMId DESC " + 
			"  OFFSET (:PageIndex -1) * :PageSize ROWS " + 
			"  FETCH NEXT :PageSize ROWS ONLY")
	List<LpmApiLog> getLpmApiLog(@Param("lpmUserId") String lpmUserId, @Param("startDate") Date startDate, 
								 @Param("endDate") Date endDate, @Param("lpmData") String lpmData,
								 @Param("queryType") String queryType, @Param("queryUrl") String queryUrl, 
								 @Param("PageIndex") Integer PageIndex, @Param("PageSize") Integer PageSize);
	
	long count(); 

}
