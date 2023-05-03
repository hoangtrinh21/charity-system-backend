package com.charity.hoangtrinh.services;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.CampaignInfo;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.CampaignInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CampaignService {
    @Autowired
    private CampaignInfoRepository campaignInfoRepository;

    public List<CampaignInfo> getAllCampaigns(boolean isAdmin) {
        if (isAdmin) return campaignInfoRepository.findAll();
        return campaignInfoRepository.findByIsActiveEquals(true);
    }

    public List<CampaignInfo> getByCondition(Map<String, String> conditions, boolean isAdmin) {
        Integer campaignId      = conditions.get("campaign-id") == null ?
                null : Integer.parseInt(conditions.get("campaign-id"));
        Integer organizationId  = conditions.get("organization-id") == null ?
                null : Integer.parseInt(conditions.get("organization-id"));
        String organizationName = conditions.get("organization-name");
        String campaignName     = conditions.get("campaign-name");
        String region           = conditions.get("region");
        String campaignType     = conditions.get("campaign-type");
        String targerObject     = conditions.get("target-object");
        String status           = conditions.get("status");
        if (isAdmin)
            return campaignInfoRepository.findByIdEqualsAndOrganization_IdEqualsAndOrganization_UserNameLikeAndCampaignNameLikeAndTargetObjectLikeAndRegionLikeAndCampaignTypeLikeAndStatusLike(
                    campaignId, organizationId, organizationName, campaignName, targerObject, region, campaignType, status
            );
        return campaignInfoRepository.findByIdEqualsAndOrganization_IdEqualsAndOrganization_UserNameLikeAndCampaignNameLikeAndTargetObjectLikeAndRegionLikeAndCampaignTypeLikeAndStatusLikeAndIsActiveTrue(
                campaignId, organizationId, organizationName, campaignName, targerObject, region, campaignType, status);

    }
}
