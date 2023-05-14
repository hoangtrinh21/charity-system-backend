package com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.CampaignInput;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampaignInputRepository extends JpaRepository<CampaignInput, Integer> {
    List<CampaignInput> findByCampaign_IdEquals(Integer id);
}