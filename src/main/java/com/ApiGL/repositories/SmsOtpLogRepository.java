package com.ApiGL.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.ApiGL.entity.SmsOtpLog;



public interface SmsOtpLogRepository extends PagingAndSortingRepository<SmsOtpLog, Long>{

	@Query("SELECT * " +
	 		"FROM SmsOtpLog " +
	 		"WHERE ((:phone IS NULL OR :phone = '') OR PHONE LIKE :phone) " +
			"  AND ((:startDate IS NULL) OR CREATETIME BETWEEN :startDate AND :endDate) " + 
			"  AND ((:msg IS NULL OR :msg = '') OR MSG LIKE :msg) " + 
			"  AND ((:queryType IS NULL OR :queryType = '') OR STATUSCODE LIKE :queryType) " + 
			"  AND ((:queryUrl IS NULL OR :queryUrl = '') OR SYSFLAG LIKE :queryUrl) " + 
			"  ORDER BY SOLID DESC " + 
			"  OFFSET (:PageIndex -1) * :PageSize ROWS " + 
			"  FETCH NEXT :PageSize ROWS ONLY")
	List<SmsOtpLog> querySmsOtpLog(@Param("phone") String phone, @Param("startDate") Date startDate, 
								 @Param("endDate") Date endDate, @Param("msg") String msg,
								 @Param("queryType") String queryType, @Param("queryUrl") String queryUrl, 
								 @Param("PageIndex") Integer PageIndex, @Param("PageSize") Integer PageSize);
	long count(); 
}
