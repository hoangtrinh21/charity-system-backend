package com.charity.hoangtrinh.dbs.sql.charitydatabase.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "campaign_info")
@NoArgsConstructor
public class CampaignInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "campaign_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private UserAccount organization;

    @Column(name = "campaign_name", nullable = false)
    private String campaignName;

    @Lob
    @Column(name = "introduction", nullable = false)
    private String introduction;

    @Column(name = "target_object", nullable = false)
    private String targetObject;

    @Column(name = "region", nullable = false)
    private String region;

    @Column(name = "campaign_type", length = 100)
    private String campaignType;

    @Column(name = "target_amount", nullable = false)
    private Long targetAmount;

    @Column(name = "receive_amount", nullable = false)
    private Long receiveAmount;

    @Column(name = "donor_amount", nullable = false)
    private Long donorAmount;

    @Column(name = "spent_amount", nullable = false)
    private Long spentAmount;

    @Column(name = "last_update_time", nullable = false)
    private Integer lastUpdateTime;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "stop_receive_date", nullable = false)
    private LocalDate stopReceiveDate;

    @Column(name = "start_active_date", nullable = false)
    private LocalDate startActiveDate;

    @Column(name = "stop_active_date", nullable = false)
    private LocalDate stopActiveDate;

    @Column(name = "stop_date", nullable = false)
    private LocalDate stopDate;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

    public CampaignInfo(UserAccount organization, String campaignName, String introduction, String targetObject, String region, String campaignType, Long targetAmount, Long receiveAmount, Long donorAmount, Long spentAmount, Integer lastUpdateTime, LocalDate startDate, LocalDate stopReceiveDate, LocalDate startActiveDate, LocalDate stopActiveDate, LocalDate stopDate, String status, Boolean isActive) {
        this.organization = organization;
        this.campaignName = campaignName;
        this.introduction = introduction;
        this.targetObject = targetObject;
        this.region = region;
        this.campaignType = campaignType;
        this.targetAmount = targetAmount;
        this.receiveAmount = receiveAmount;
        this.donorAmount = donorAmount;
        this.spentAmount = spentAmount;
        this.lastUpdateTime = lastUpdateTime;
        this.startDate = startDate;
        this.stopReceiveDate = stopReceiveDate;
        this.startActiveDate = startActiveDate;
        this.stopActiveDate = stopActiveDate;
        this.stopDate = stopDate;
        this.status = status;
        this.isActive = isActive;
    }

    public CampaignInfo(UserAccount organization, String campaignName, String introduction, String targetObject, String region, String campaignType, Long targetAmount, Long receiveAmount, Long donorAmount, Long spentAmount, Integer lastUpdateTime, LocalDate startDate, LocalDate stopReceiveDate, LocalDate startActiveDate, LocalDate stopActiveDate, LocalDate stopDate, String status, boolean b) {
        this.organization = organization;
        this.campaignName = campaignName;
        this.introduction = introduction;
        this.targetObject = targetObject;
        this.region = region;
        this.campaignType = campaignType;
        this.targetAmount = targetAmount;
        this.receiveAmount = receiveAmount;
        this.donorAmount = donorAmount;
        this.spentAmount = spentAmount;
        this.lastUpdateTime = lastUpdateTime;
        this.startDate = startDate;
        this.stopReceiveDate = stopReceiveDate;
        this.startActiveDate = startActiveDate;
        this.stopActiveDate = stopActiveDate;
        this.stopDate = stopDate;
        this.status = status;
        this.isActive = b;
    }

    public CampaignInfo(int campaignId, UserAccount organization, String campaignName, String introduction, String targetObject, String region, String campaignType, Long targetAmount, Long receiveAmount, Long donorAmount, Long spentAmount, Integer lastUpdateTime, LocalDate startDate, LocalDate stopReceiveDate, LocalDate startActiveDate, LocalDate stopActiveDate, LocalDate stopDate, String status, boolean b) {
        this.id = campaignId;
        this.organization = organization;
        this.campaignName = campaignName;
        this.introduction = introduction;
        this.targetObject = targetObject;
        this.region = region;
        this.campaignType = campaignType;
        this.targetAmount = targetAmount;
        this.receiveAmount = receiveAmount;
        this.donorAmount = donorAmount;
        this.spentAmount = spentAmount;
        this.lastUpdateTime = lastUpdateTime;
        this.startDate = startDate;
        this.stopReceiveDate = stopReceiveDate;
        this.startActiveDate = startActiveDate;
        this.stopActiveDate = stopActiveDate;
        this.stopDate = stopDate;
        this.status = status;
        this.isActive = b;
    }

    @Override
    public String toString() {
        return "CampaignInfo{" +
                "id=" + id +
                ", organization=" + organization +
                ", campaignName='" + campaignName + '\'' +
                ", introduction='" + introduction + '\'' +
                ", targetObject='" + targetObject + '\'' +
                ", region='" + region + '\'' +
                ", campaignType='" + campaignType + '\'' +
                ", targetAmount=" + targetAmount +
                ", receiveAmount=" + receiveAmount +
                ", donorAmount=" + donorAmount +
                ", spentAmount=" + spentAmount +
                ", lastUpdateTime=" + lastUpdateTime +
                ", startDate=" + startDate +
                ", stopReceiveDate=" + stopReceiveDate +
                ", startActiveDate=" + startActiveDate +
                ", stopActiveDate=" + stopActiveDate +
                ", stopDate=" + stopDate +
                ", status='" + status + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}