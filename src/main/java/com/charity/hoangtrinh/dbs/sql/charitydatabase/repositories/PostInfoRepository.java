package com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.CampaignInfo;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.PostInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostInfoRepository extends JpaRepository<PostInfo, Integer> {
    boolean existsByCampaignEquals(CampaignInfo campaign);
    @Transactional
    long deleteByCampaignEquals(CampaignInfo campaign);
    List<PostInfo> findByCampaignEquals(CampaignInfo campaign);
    List<PostInfo> findByCampaign_IdEquals(Integer id);
}