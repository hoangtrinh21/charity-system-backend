package com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.Donation;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {
    List<Request> findByOrganizationIdEquals(Integer organizationId);
    List<Request> findByDonationIdEquals(Integer donationId);
}