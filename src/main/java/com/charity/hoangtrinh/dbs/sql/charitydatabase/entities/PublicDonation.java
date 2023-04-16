package com.charity.hoangtrinh.dbs.sql.charitydatabase.entities;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "public_donation")
@Table(name = "public_donation")
public class PublicDonation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public PublicDonation(Integer donorId, Integer introPostId, String name, String status, Integer receivingOrganizationId, String targetAddress, String targetObject, String img) {
        this.donorId = donorId;
        this.introPostId = introPostId;
        this.name = name;
        this.status = status;
        this.receivingOrganizationId = receivingOrganizationId;
        this.targetAddress = targetAddress;
        this.targetObject = targetObject;
        this.img = img;
    }
}