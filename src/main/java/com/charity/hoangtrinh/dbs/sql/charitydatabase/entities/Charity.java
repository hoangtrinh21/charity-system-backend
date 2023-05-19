package com.charity.hoangtrinh.dbs.sql.charitydatabase.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "charities")
public class Charity {
    @Id
    @Column(name = "Id", nullable = false)
    private Integer id;

    @Column(name = "CharityMotto", length = 1000)
    private String charityMotto;

    private String charityName;

    @Column(name = "CharityWebsite")
    private String charityWebsite;

    @Column(name = "CharityBanner")
    private String charityBanner;

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

    @Column(name = "GoogleMap", length = 1000)
    private String googleMap;

    @Column(name = "IsVerified")
    private Short isVerified;

    @Column(name = "NumFollower")
    private Integer numFollower;

    @Column(name = "NumCampaign")
    private Integer numCampaign;

    @Column(name = "Reach")
    private String reach;

    @Column(name = "Star")
    private Byte star;

    public void setCharityName(UserAccount user) {
        this.charityName = user.getName();
    }
}