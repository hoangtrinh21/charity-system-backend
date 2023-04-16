package com.charity.hoangtrinh.dbs.sql.charitydatabase.entities;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "campaign_info")
@Table(name = "campaign_info")
public class CampaignInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "campaign_id", nullable = false)
    private Integer id;

    @Column(name = "organization_id")
    private Integer organizationId;

    @Column(name = "campaign_name", nullable = false)
    private String campaignName;

    @Column(name = "introduction", nullable = false, length = 3000)
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
    private Instant lastUpdateTime;

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

}