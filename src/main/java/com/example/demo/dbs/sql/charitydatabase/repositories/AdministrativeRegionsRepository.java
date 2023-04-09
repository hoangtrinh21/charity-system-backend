package com.example.demo.dbs.sql.charitydatabase.repositories;

import com.example.demo.dbs.sql.charitydatabase.entities.AdministrativeRegionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministrativeRegionsRepository extends JpaRepository<AdministrativeRegionsEntity, Integer> {
}
