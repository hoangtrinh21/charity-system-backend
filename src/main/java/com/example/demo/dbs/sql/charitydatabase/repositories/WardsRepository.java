package com.example.demo.dbs.sql.charitydatabase.repositories;

import com.example.demo.dbs.sql.charitydatabase.entities.WardsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WardsRepository extends JpaRepository<WardsEntity, String> {
    List<WardsEntity> findByDistrictCode(String districtCode);
}
