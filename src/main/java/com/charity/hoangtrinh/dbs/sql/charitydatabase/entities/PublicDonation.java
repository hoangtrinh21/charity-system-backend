package com.charity.hoangtrinh.dbs.sql.charitydatabase.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity(name = "public_donation")
@Table(name = "public_donation")
public class PublicDonation {
    @Id
    @Column(name = "donation_id", nullable = false)
    private Integer id;

    @Column(name = "donor_id", nullable = false)
    private Integer donorId;

    @Column(name = "intro_post_id", nullable = false)
    private Integer introPostId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "status", nullable = false, length = 100)
    private String status;

    @Column(name = "receiving_organization_id")
    private Integer receivingOrganizationId;

    @Column(name = "target_address")
    private String targetAddress;

    @Column(name = "target_object")
    private String targetObject;

    @Column(name = "img", length = 3000)
    private String img;

}