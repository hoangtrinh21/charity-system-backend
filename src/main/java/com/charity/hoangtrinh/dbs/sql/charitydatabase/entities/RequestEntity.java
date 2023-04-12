package com.charity.hoangtrinh.dbs.sql.charitydatabase.entities;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "request")
@Table(name = "request")
public class RequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "donation_id", nullable = false)
    private PublicDonationEntity donation;

    @Column(name = "organization_id", nullable = false)
    private Integer organizationId;

    @Column(name = "status", nullable = false, length = 100)
    private String status;

}