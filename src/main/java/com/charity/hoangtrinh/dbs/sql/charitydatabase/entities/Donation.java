package com.charity.hoangtrinh.dbs.sql.charitydatabase.entities;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import lombok.*;
import org.camunda.bpm.engine.impl.json.JsonArrayConverter;
import org.camunda.bpm.engine.impl.json.JsonArrayOfObjectsConverter;
import org.json.JSONArray;

import javax.persistence.*;
import java.io.IOException;
import java.sql.Blob;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "donations")
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "idDonor")
    private Integer idDonor;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "organizationReceived")
    private String organizationReceived;

    @Column(name = "idOrganization")
    private Integer idOrganization;

    @Column(name = "listRequest", length = 1073741824)
    @Lob
    private String listRequest;

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

    public void setListRequest(JSONArray listRequest) {
        this.listRequest = listRequest.toString();
    }

    public List<Object> getListRequest() {
        return new JSONArray(listRequest).toList();
    }
}