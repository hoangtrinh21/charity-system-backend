package com.charity.hoangtrinh.dbs.sql.charitydatabase.entities;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "post_info")
@Table(name = "post_info")
public class PostInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "type", nullable = false, length = 100)
    private String type;

    @Column(name = "submit_time", nullable = false)
    private Long submitTime;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "campaign_id", nullable = false)
    private CampaignInfo campaign;

    public PostInfo(String content, String type, Long submitTime, CampaignInfo campaign) {
        this.content = content;
        this.type = type;
        this.submitTime = submitTime;
        this.campaign = campaign;
    }

    public PostInfo(Integer id, String content, String type, Long submitTime, CampaignInfo campaign) {
        this.id = id;
        this.content = content;
        this.type = type;
        this.submitTime = submitTime;
        this.campaign = campaign;
    }
}