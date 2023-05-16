package com.charity.hoangtrinh.dbs.sql.charitydatabase.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "charities")
public class Charity {
    @Id
    @Column(name = "Id", nullable = false)
    private Integer id;

    private String charityName;

    @Column(name = "CharityMotto", length = 1000)
    private String charityMotto;

    @Column(name = "CharityWebsite")
    private String CharityWebsite;

    @Column(name = "CharityBanner")
    private String CharityBanner;

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

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserAccount user;

    public void setCharityName(UserAccount user) {
        this.charityName = user.getName();
    }
}