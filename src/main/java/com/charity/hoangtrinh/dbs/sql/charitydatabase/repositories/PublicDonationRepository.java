package com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.PublicDonation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface PublicDonationRepository extends JpaRepository<PublicDonation, Integer> {
    List<PublicDonation> findByNameLike(String name);
}