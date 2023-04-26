package com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.CampaignInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface CampaignInfoRepository extends JpaRepository<CampaignInfo, Integer> {
    List<CampaignInfo> findByIdEqualsAndOrganization_IdEqualsAndOrganization_UserNameLikeAndCampaignNameLikeAndTargetObjectLikeAndRegionLikeAndCampaignTypeLikeAndStatusLikeAndIsActiveTrue(Integer id, Integer id1, String userName, String campaignName, String targetObject, String region, String campaignType, String status);
    List<CampaignInfo> findByIdEqualsAndOrganization_IdEqualsAndOrganization_UserNameLikeAndCampaignNameLikeAndTargetObjectLikeAndRegionLikeAndCampaignTypeLikeAndStatusLike(Integer id, Integer id1, String userName, String campaignName, String targetObject, String region, String campaignType, String status);
    List<CampaignInfo> findByOrganization_IdEqualsAndOrganization_UserNameLikeAndCampaignNameLikeAndTargetObjectLikeAndRegionLikeAndCampaignTypeLikeAndStatusLike(Integer id, String userName, String campaignName, String targetObject, String region, String campaignType, String status);
    List<CampaignInfo> findByOrganization_IdEqualsAndOrganization_UserNameLikeAndCampaignNameLikeAndTargetObjectLikeAndRegionLikeAndCampaignTypeLikeAndStatusLikeAndIsActiveTrue(Integer id, String userName, String campaignName, String targetObject, String region, String campaignType, String status);
    List<CampaignInfo> findByIsActiveEquals(Boolean isActive);
    List<CampaignInfo> findByCampaignNameLike(String campaignName);
    List<CampaignInfo> findByOrganizationIdEquals(Integer organizationId);


}