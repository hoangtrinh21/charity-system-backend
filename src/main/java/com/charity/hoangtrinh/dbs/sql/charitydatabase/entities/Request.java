package com.charity.hoangtrinh.dbs.sql.charitydatabase.entities;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "request")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id", nullable = false)
    private Integer id;

    @Column(name = "donation_id", nullable = false)
    private Integer donationId;

    @Column(name = "organization_id", nullable = false)
    private Integer organizationId;

    @Column(name = "status", nullable = false, length = 100)
    private String status;

    public Request() {
    }

    public Request(Integer id, Integer organizationId, String status) {
        this.id = id;
        this.organizationId = organizationId;
        this.status = status;
    }

    public Request(Integer id, Integer donationId, Integer organizationId, String status) {
        this.id = id;
        this.donationId = donationId;
        this.organizationId = organizationId;
        this.status = status;
    }
}