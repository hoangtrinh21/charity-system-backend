package com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.CampaignInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface CampaignInfoRepository extends JpaRepository<CampaignInfo, Integer> {
    List<CampaignInfo> findByOrganizationIdEquals(Integer organizationId);
}