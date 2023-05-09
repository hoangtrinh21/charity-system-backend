package com.charity.hoangtrinh.dbs.sql.charitydatabase.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "request")
public class Request {
    @Id
    @Column(name = "request_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "donation_id", nullable = false)
    private Donation donation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", nullable = false)
    private Charity organization;

    @Column(name = "status", nullable = false, length = 100)
    private String status;

    public Request() {
    }

    public Request(Donation donation, Charity organization, String status) {
        this.donation = donation;
        this.organization = organization;
        this.status = status;
    }

    public Request(Integer id, Donation donation, Charity organization, String status) {
        this.id = id;
        this.donation = donation;
        this.organization = organization;
        this.status = status;
    }
}
