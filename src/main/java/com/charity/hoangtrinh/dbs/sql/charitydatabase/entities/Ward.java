package com.charity.hoangtrinh.dbs.sql.charitydatabase.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "wards")
public class Ward {
    @Id
    @Column(name = "code", nullable = false, length = 20)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "code_name")
    private String codeName;

    @Column(name = "district_code", length = 20)
    private String districtCode;

//    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "administrative_unit_id")
    private AdministrativeUnit administrativeUnit;
}