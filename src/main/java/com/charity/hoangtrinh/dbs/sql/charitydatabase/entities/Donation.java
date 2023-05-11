package com.charity.hoangtrinh.dbs.sql.charitydatabase.entities;

import com.google.gson.JsonArray;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;

import javax.persistence.*;
import java.sql.Blob;

@Getter
@Setter
@Entity
@Table(name = "donations")
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "idDornor")
    private Integer idDornor;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "organizationReceived")
    private String organizationReceived;

    @Column(name = "idOrganization")
    private Integer idOrganization;

    @Column(name = "listRequest", length = 1073741824)
    private Blob listRequest;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "donationAddress")
    private String donationAddress;

    @Column(name = "donationObject")
    private String donationObject;

    @Column(name = "donorName")
    private String donorName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "province")
    private String province;

    @Column(name = "district")
    private String district;

    @Column(name = "ward")
    private String ward;

    @Column(name = "date")
    private String date;

    @Column(name = "description")
    private String description;

    @Column(name = "images")
    private String images;

}