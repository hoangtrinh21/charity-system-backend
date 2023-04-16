package com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface WardRepository extends JpaRepository<Ward, String> {
    List<Ward> findByDistrictCodeEquals(String districtCode);
    List<Ward> findByDistrictCode(String districtCode);
}