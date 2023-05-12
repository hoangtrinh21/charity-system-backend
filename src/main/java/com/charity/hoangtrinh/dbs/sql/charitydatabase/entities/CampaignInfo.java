package com.charity.hoangtrinh.dbs.sql.charitydatabase.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "campaign_info")
public class CampaignInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "campaign_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Charity organization;

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

    @Column(name = "images", nullable = false)
    private String images;

    @OneToMany(mappedBy = "campaign")
    private Set<CampaignInput> campaignInputs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "campaign")
    private Set<CampaignOutput> campaignOutputs = new LinkedHashSet<>();

}