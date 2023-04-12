package com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.CampaignInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignInfoRepository extends JpaRepository<CampaignInfo, Integer> {
}