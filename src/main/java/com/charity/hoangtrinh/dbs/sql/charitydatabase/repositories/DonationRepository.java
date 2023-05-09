package com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Integer> {
    List<Donation> findByIdDonor_NameLikeAndNameLikeAndStatusLikeAndDonationAddressLikeAndDonationObjectLike(@Nullable String name, @Nullable String name1, @Nullable String status, @Nullable String donationAddress, @Nullable String donationObject);
}