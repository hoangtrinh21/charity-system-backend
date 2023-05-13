package com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.CampaignInfo;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;

import java.util.List;

public interface CampaignInfoRepository extends JpaRepository<CampaignInfo, Integer> {
    @Query("select c from CampaignInfo c where c.organization.id = ?1")
    List<CampaignInfo> findByOrganization_IdEquals(Integer id);
    List<CampaignInfo> findByCampaignNameLikeAndRegionLikeAndCampaignTypeLikeAndTargetObjectLikeAndStatusLikeAndIsActiveTrue(@Nullable String campaignName, @Nullable String region, @Nullable String campaignType, @Nullable String targetObject, @Nullable String status);
    List<CampaignInfo> findByCampaignNameLikeAndRegionLikeAndCampaignTypeLikeAndTargetObjectLikeAndStatusLike(@Nullable String campaignName, @Nullable String region, @Nullable String campaignType, @Nullable String targetObject, @Nullable String status);
    List<CampaignInfo> findByOrganization_Id(Integer id);
    List<CampaignInfo> findByOrganization_IdEqualsAndIsActiveEquals(Integer id, Boolean isActive);
    List<CampaignInfo> findByIdEqualsAndIsActiveEquals(Integer id, Boolean isActive);
    List<CampaignInfo> findByIsActiveEquals(Boolean isActive);
    List<CampaignInfo> findByIsActiveTrue();
}