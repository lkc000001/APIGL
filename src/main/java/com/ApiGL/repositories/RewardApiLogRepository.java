package com.ApiGL.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.ApiGL.entity.RewardAPILog;

public interface RewardApiLogRepository extends PagingAndSortingRepository<RewardAPILog, Long>{

	@Query("SELECT * " +
	 		"FROM RewardAPILog " +
	 		"WHERE ((:rewardIdnTpan IS NULL OR :rewardIdnTpan = '') OR RewardIdnTpan LIKE :rewardIdnTpan) " +
			"  AND ((:startDate IS NULL) OR CREATETIME BETWEEN :startDate AND :endDate) " + 
			"  AND ((:rewardData IS NULL OR :rewardData = '') OR RewardData LIKE :rewardData) "+ 
			"  AND ((:queryType IS NULL OR :queryType = '') OR RewardType LIKE :queryType) " + 
			"  AND ((:queryUrl IS NULL OR :queryUrl = '') OR RewardURL LIKE :queryUrl) " + 
			"  ORDER BY RewardId DESC " + 
			"  OFFSET (:PageIndex -1) * :PageSize ROWS " + 
			"  FETCH NEXT :PageSize ROWS ONLY")
	List<RewardAPILog> queryRewardAPILog(@Param("rewardIdnTpan") String rewardIdnTpan, @Param("startDate") Date startDate, 
								 @Param("endDate") Date endDate, @Param("rewardData") String rewardData,
								 @Param("queryType") String queryType, @Param("queryUrl") String queryUrl, 
								 @Param("PageIndex") Integer PageIndex, @Param("PageSize") Integer PageSize);
	long count(); 
}
