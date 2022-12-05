package com.ApiGL.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.ApiGL.entity.TwPayLineBindLog;

public interface TwPayLineBindLogRepository extends PagingAndSortingRepository<TwPayLineBindLog, Long>{
	
	@Query("SELECT * " +
	 		"FROM TWPayLineBindLog " +
	 		"WHERE ((:startDate IS NULL) OR CREATETIME BETWEEN :startDate AND :endDate) " + 
			"  AND ((:TwPayLineBindLogData IS NULL OR :TwPayLineBindLogData = '') OR TWPayLineBindLogData LIKE :TwPayLineBindLogData) "+ 
			"  AND ((:queryType IS NULL OR :queryType = '') OR twPayLineBindLogSendStatus LIKE :queryType) " + 
			"  AND ((:queryUrl IS NULL OR :queryUrl = '') OR twPayLineBindLogUrl LIKE :queryUrl) " + 
			"  ORDER BY TWPayLineBindLogId DESC " + 
			"  OFFSET (:PageIndex -1) * :PageSize ROWS " + 
			"  FETCH NEXT :PageSize ROWS ONLY")
	List<TwPayLineBindLog> querytwPayLineBindLog(@Param("TWPayLineBindLogLineUid") Long TWPayLineBindLogLineUid, @Param("startDate") Date startDate, 
								 @Param("endDate") Date endDate, @Param("TwPayLineBindLogData") String TwPayLineBindLogData,
								 @Param("queryType") String queryType, @Param("queryUrl") String queryUrl, 
								 @Param("PageIndex") Integer PageIndex, @Param("PageSize") Integer PageSize);
	long count();
}
