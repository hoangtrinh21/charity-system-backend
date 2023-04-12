package com.charity.hoangtrinh.dbs.sql.charitydatabase.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity(name = "campaign_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "campaign_info")
public class CampaignInfo {
    @Id
    @Column(name = "campaign_id")
    private int campaignId;
    @Column(name = "organization_id")
    private int organizationId;
    @Column(name = "campaign_name")
    private String campaignName;
    @Column(name = "introduction")
    private String introduction;
    @Column(name = "target_object")
    private String targetObject;
    @Column(name = "region")
    private String region;
    @Column(name = "campaign_type")
    private String campaignType;
    @Column(name = "target_amount")
    private long targetAmount;
    @Column(name = "receive_amount")
    private long receiveAmount;
    @Column(name = "donor_amount")
    private long donorAmount;
    @Column(name = "spent_amount")
    private long spentAmount;
    @Column(name = "last_update_time")
    private String lastUpadteTime;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "stop_receive_date")
    private Date stropReceiveDate;
    @Column(name = "start_active_date")
    private Date startActiveDate;
    @Column(name = "stop_active_date")
    private Date stopActiveDate;
    @Column(name = "stop_date")
    private Date stopDate;
    @Column(name = "status")
    private String status;

}
