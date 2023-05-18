package com.charity.hoangtrinh.model;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.CampaignInfo;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.Charity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampaignInfoDTO {
    private Integer id;
    private Charity organization;
    private String campaignName;
    private String introduction;
    private String targetObject;
    private String region;
    private Long targetAmount;
    private Long receiveAmount;
    private Long donorAmount;
    private Long spentAmount;
    private Integer lastUpdateTime;
    private LocalDate startDate;
    private LocalDate stopDate;
    private String status;
    private Boolean isActive = true;
    private String images;
    private String introVideo;
    private Boolean star = false;
    private boolean isFollow;

    public CampaignInfoDTO(CampaignInfo campaignInfo, boolean isFollow) {
        this.isFollow       = isFollow;
        this.id             = campaignInfo.getId();
        this.organization   = campaignInfo.getOrganization();
        this.campaignName   = campaignInfo.getCampaignName();
        this.introduction   = campaignInfo.getIntroduction();
        this.targetObject   = campaignInfo.getTargetObject();
        this.region         = campaignInfo.getRegion();
        this.targetAmount   = campaignInfo.getTargetAmount();
        this.receiveAmount  = campaignInfo.getReceiveAmount();
        this.donorAmount    = campaignInfo.getDonorAmount();
        this.spentAmount    = campaignInfo.getSpentAmount();
        this.lastUpdateTime = campaignInfo.getLastUpdateTime();
        this.startDate      = campaignInfo.getStartDate();
        this.stopDate       = campaignInfo.getStopDate();
        this.status         = campaignInfo.getStatus();
        this.isActive       = campaignInfo.getIsActive();
        this.images         = campaignInfo.getImages();
        this.introVideo     = campaignInfo.getIntroVideo();
        this.star           = campaignInfo.getStar();
    }
}
