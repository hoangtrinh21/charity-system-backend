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

}