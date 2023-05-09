package com.charity.hoangtrinh.dbs.sql.charitydatabase.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "donation")
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "donation_address", nullable = false)
    private String donationAddress;

    @Column(name = "donation_object", nullable = false)
    private String donationObject;

    @Lob
    @Column(name = "images")
    private String images;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_donor", nullable = false)
    private UserAccount idDonor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_received_id")
    private Charity organizationReceived;

    public Donation() {
    }

    public Donation(LocalDate date, String description, String donationAddress, String donationObject, String images, String name, String status, UserAccount idDonor) {
        this.date = date;
        this.description = description;
        this.donationAddress = donationAddress;
        this.donationObject = donationObject;
        this.images = images;
        this.name = name;
        this.status = status;
        this.idDonor = idDonor;
    }

    public Donation(LocalDate date, String description, String donationAddress, String donationObject, String images, String name, String status, UserAccount idDonor, Charity organizationReceived) {
        this.date = date;
        this.description = description;
        this.donationAddress = donationAddress;
        this.donationObject = donationObject;
        this.images = images;
        this.name = name;
        this.status = status;
        this.idDonor = idDonor;
        this.organizationReceived = organizationReceived;
    }

    public Donation(Integer id, LocalDate date, String description, String donationAddress, String donationObject, String images, String name, String status, UserAccount idDonor, Charity organizationReceived) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.donationAddress = donationAddress;
        this.donationObject = donationObject;
        this.images = images;
        this.name = name;
        this.status = status;
        this.idDonor = idDonor;
        this.organizationReceived = organizationReceived;
    }
}