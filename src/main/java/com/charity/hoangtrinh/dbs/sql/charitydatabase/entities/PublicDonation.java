package com.charity.hoangtrinh.dbs.sql.charitydatabase.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "public_donation")
@NoArgsConstructor
public class PublicDonation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donation_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "donor_id", nullable = false)
    private UserAccount donor;

    @Column(name = "intro_post_id", nullable = false)
    private Integer introPostId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "status", nullable = false, length = 100)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiving_organization_id")
    private UserAccount receivingOrganization;

    @Column(name = "target_address")
    private String targetAddress;

    @Column(name = "target_object")
    private String targetObject;

    @Column(name = "img", length = 3000)
    private String img;

    public PublicDonation(UserAccount donor, Integer introPostId, String name, String status, Integer receivingOrganizationId, String targetAddress, String targetObject, String img) {
    }
}