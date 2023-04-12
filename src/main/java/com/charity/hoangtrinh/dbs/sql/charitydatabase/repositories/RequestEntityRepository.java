package com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestEntityRepository extends JpaRepository<RequestEntity, Integer> {
}