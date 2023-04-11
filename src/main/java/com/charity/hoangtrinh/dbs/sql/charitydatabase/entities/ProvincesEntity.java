package com.charity.hoangtrinh.dbs.sql.charitydatabase.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "provinces")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "provinces")
public class ProvincesEntity {
    @Id
    private String code;
    @Column(name = "name")
    private String name;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "code_name")
    private String codeName;
    @Column(name = "administrative_unit_id")
    private int administrativeUnitId;
    @Column(name = "administrative_region_id")
    private int administrativeRegionId;
}
