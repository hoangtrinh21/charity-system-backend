package com.example.demo.dbs.sql.charitydatabase.repositories;

import com.example.demo.dbs.sql.charitydatabase.entities.ProvincesEntity;
import com.example.demo.dbs.sql.charitydatabase.entities.WardsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvincesRepository extends JpaRepository<ProvincesEntity, String> {
}
