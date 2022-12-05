package com.ApiGL.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.ApiGL.entity.UserTrackLog;


public interface UserTrackLogRepository extends PagingAndSortingRepository<UserTrackLog, Long>{

	@Query("SELECT * " +
	 		"FROM USERTRACKLog " +
	 		"WHERE (:accountId IS NULL OR UserTrackAccountID LIKE :accountId) " +
			"  AND ((:startDate IS NULL) OR CREATETIME BETWEEN :startDate AND :endDate) " + 
			"  AND ((:userTrackData IS NULL OR :userTrackData = '') OR UserTrackData LIKE :userTrackData) " + 
			"  AND ((:queryUrl IS NULL OR :queryUrl = '') OR UserTrackUrl LIKE :queryUrl) " + 
			"  ORDER BY UserTrackId DESC " + 
			"  OFFSET (:PageIndex -1) * :PageSize ROWS " + 
			"  FETCH NEXT :PageSize ROWS ONLY")
	List<UserTrackLog> queryUserTrackLog(@Param("accountId") Integer accountId, @Param("startDate") Date startDate, 
								 @Param("endDate") Date endDate, @Param("userTrackData") String userTrackData,
								 @Param("queryUrl") String queryUrl, @Param("PageIndex") Integer PageIndex, @Param("PageSize") Integer PageSize);

	long count(); 
}
