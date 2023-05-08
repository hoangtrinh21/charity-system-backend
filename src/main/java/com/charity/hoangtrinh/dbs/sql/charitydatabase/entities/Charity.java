package com.charity.hoangtrinh.dbs.sql.charitydatabase.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "charities")
public class Charity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @Column(name = "CharityMotto", length = 1000)
    private String charityMotto;

    @Column(name = "CharityTarget", length = 1000)
    private String charityTarget;

    @Column(name = "CharityDescription", length = 1000)
    private String charityDescription;

    @Column(name = "CharityFacebook", length = 500)
    private String charityFacebook;

    @Column(name = "CharityInstagram", length = 500)
    private String charityInstagram;

    @Column(name = "CharityTwitter", length = 500)
    private String charityTwitter;

    @Column(name = "CharityLinkedIn", length = 500)
    private String charityLinkedIn;

    @Column(name = "CharityIntroVideo", length = 500)
    private String charityIntroVideo;

    @Column(name = "CharityBank")
    private String charityBank;

    @Column(name = "CharityFile")
    private String charityFile;

    @Column(name = "CharityAccountNumber")
    private String charityAccountNumber;

    @Column(name = "Avatar")
    private String avatar;

    @Column(name = "CharityImages", length = 3000)
    private String charityImages;

    @Column(name = "GoogleMap")
    private String googleMap;

    @Column(name = "IsVerificated")
    private Byte isVerificated;

    @Column(name = "NumFollower")
    private Integer numFollower;

    @Column(name = "NumCampaign")
    private Integer numCampaign;

    @Column(name = "Reach")
    private String reach;

    @Column(name = "Star")
    private Byte star;

}