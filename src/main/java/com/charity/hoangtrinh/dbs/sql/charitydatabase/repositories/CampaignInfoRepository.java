package com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.CampaignInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface CampaignInfoRepository extends JpaRepository<CampaignInfo, Integer> {
    List<CampaignInfo> findByIdEqualsAndOrganization_IdEqualsAndOrganization_UserNameLikeAndCampaignNameLikeAndTargetObjectLikeAndRegionLikeAndCampaignTypeLikeAndStatusLikeAndIsActiveTrue(
            @Nullable Integer id, @Nullable Integer id1, @Nullable String userName, @Nullable String campaignName,
            @Nullable String targetObject, @Nullable String region, @Nullable String campaignType, @Nullable String status);
    List<CampaignInfo> findByIdEqualsAndOrganization_IdEqualsAndOrganization_UserNameLikeAndCampaignNameLikeAndTargetObjectLikeAndRegionLikeAndCampaignTypeLikeAndStatusLike(
            @Nullable Integer id, @Nullable Integer id1, @Nullable String userName, @Nullable String campaignName,
            @Nullable String targetObject, @Nullable String region, @Nullable String campaignType, @Nullable String status);
    List<CampaignInfo> findByIsActiveEquals(Boolean isActive);


}