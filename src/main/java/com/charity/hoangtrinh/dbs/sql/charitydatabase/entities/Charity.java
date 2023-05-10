package com.charity.hoangtrinh.dbs.sql.charitydatabase.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Charity charity = (Charity) o;
        return Objects.equals(id, charity.id) && Objects.equals(charityMotto, charity.charityMotto) && Objects.equals(charityTarget, charity.charityTarget) && Objects.equals(charityDescription, charity.charityDescription) && Objects.equals(charityFacebook, charity.charityFacebook) && Objects.equals(charityInstagram, charity.charityInstagram) && Objects.equals(charityTwitter, charity.charityTwitter) && Objects.equals(charityLinkedIn, charity.charityLinkedIn) && Objects.equals(charityIntroVideo, charity.charityIntroVideo) && Objects.equals(charityBank, charity.charityBank) && Objects.equals(charityFile, charity.charityFile) && Objects.equals(charityAccountNumber, charity.charityAccountNumber) && Objects.equals(avatar, charity.avatar) && Objects.equals(charityImages, charity.charityImages) && Objects.equals(googleMap, charity.googleMap) && Objects.equals(isVerificated, charity.isVerificated) && Objects.equals(numFollower, charity.numFollower) && Objects.equals(numCampaign, charity.numCampaign) && Objects.equals(reach, charity.reach) && Objects.equals(star, charity.star);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, charityMotto, charityTarget, charityDescription, charityFacebook, charityInstagram, charityTwitter, charityLinkedIn, charityIntroVideo, charityBank, charityFile, charityAccountNumber, avatar, charityImages, googleMap, isVerificated, numFollower, numCampaign, reach, star);
    }
}