package com.charity.hoangtrinh.dbs.sql.charitydatabase.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "request")
@Table(name = "request")
public class Request {
    @Id
    @Column(name = "request_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "donation_id", nullable = false)
    private PublicDonation donation;

    @Column(name = "organization_id", nullable = false)
    private Integer organizationId;

    @Column(name = "status", nullable = false, length = 100)
    private String status;

}