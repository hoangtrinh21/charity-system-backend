package com.charity.hoangtrinh.dbs.sql.charitydatabase.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "administrative_units")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "administrative_units")
public class AdministrativeUnitsEntity {
    @Id
    private int id;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "short_name")
    private String shortname;
    @Column(name = "code_name")
    private String codeName;
}
