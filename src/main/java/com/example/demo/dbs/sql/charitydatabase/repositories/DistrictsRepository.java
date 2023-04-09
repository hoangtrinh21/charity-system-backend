package com.example.demo.dbs.sql.charitydatabase.repositories;

import com.example.demo.dbs.sql.charitydatabase.entities.DistrictsEntity;
import com.example.demo.dbs.sql.charitydatabase.entities.WardsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictsRepository extends JpaRepository<DistrictsEntity, String> {
    List<DistrictsEntity> findByProvinceCode(String provinceCode);
}
