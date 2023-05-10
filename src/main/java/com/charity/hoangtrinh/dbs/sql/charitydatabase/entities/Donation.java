package com.charity.hoangtrinh.dbs.sql.charitydatabase.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "donation")
public class Donation implements Serializable {
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
}