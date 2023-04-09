package com.example.demo.dbs.sql.charitydatabase.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "administrative_regions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "administrative_regions")
public class AdministrativeRegionsEntity {
    @Id
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "code_name")
    private String codeName;
}
