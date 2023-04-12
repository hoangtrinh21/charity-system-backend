package com.charity.hoangtrinh.dbs.sql.charitydatabase.entities;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.CampaignInfo;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "post_info")
@Table(name = "post_info")
public class PostInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "tyoe", nullable = false, length = 100)
    private String tyoe;

    @Column(name = "submit_time", nullable = false)
    private Long submitTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "campaign_id", nullable = false)
    private CampaignInfo campaign;

}