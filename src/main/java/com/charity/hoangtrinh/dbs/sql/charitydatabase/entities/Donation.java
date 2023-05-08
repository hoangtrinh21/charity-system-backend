package com.charity.hoangtrinh.dbs.sql.charitydatabase.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "donation")
@NoArgsConstructor
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_donor", nullable = false)
    private UserAccount idDonor;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_received_id")
    private UserAccount organizationReceived;

    @Column(name = "donation_address", nullable = false)
    private String donationAddress;

    @Column(name = "donation_object", nullable = false)
    private String donationObject;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Lob
    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "images")
    private String images;

    public Donation(UserAccount idDonor, String name, String status, String donationAddress, String donationObject, LocalDate date, String description, String images) {
        this.idDonor = idDonor;
        this.name = name;
        this.status = status;
        this.donationAddress = donationAddress;
        this.donationObject = donationObject;
        this.date = date;
        this.description = description;
        this.images = images;
    }

    public Donation(Integer id, UserAccount idDonor, String name, String status, UserAccount organizationReceived, String donationAddress, String donationObject, LocalDate date, String description, String images) {
        this.id = id;
        this.idDonor = idDonor;
        this.name = name;
        this.status = status;
        this.organizationReceived = organizationReceived;
        this.donationAddress = donationAddress;
        this.donationObject = donationObject;
        this.date = date;
        this.description = description;
        this.images = images;
    }

    public Donation(Integer id, UserAccount idDonor, String name, String status, String donationAddress, String donationObject, LocalDate date, String description, String images) {
        this.id = id;
        this.idDonor = idDonor;
        this.name = name;
        this.status = status;
        this.donationAddress = donationAddress;
        this.donationObject = donationObject;
        this.date = date;
        this.description = description;
        this.images = images;
    }
}