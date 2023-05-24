package com.charity.hoangtrinh.dbs.sql.charitydatabase.entities;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "post_info")
public class PostInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "type", nullable = false, length = 100)
    private String type;

    @Column(name = "submit_time", nullable = false)
    private Long submitTime;

    @Lob
    @Column(name = "images", nullable = false)
    private String images;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "campaign_id", nullable = false)
    private CampaignInfo campaign;

}