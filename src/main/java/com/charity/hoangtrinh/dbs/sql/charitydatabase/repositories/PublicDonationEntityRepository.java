package com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.PublicDonationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicDonationEntityRepository extends JpaRepository<PublicDonationEntity, Integer> {
}