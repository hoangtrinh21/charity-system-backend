package com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.CampaignInfo;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StatementRepository extends JpaRepository<Statement, Integer> {
    boolean existsByCampaignEquals(CampaignInfo campaign);
    @Transactional
    long deleteByCampaignEquals(CampaignInfo campaign);
    long countByCampaignEquals(CampaignInfo campaign);
    List<Statement> findByCampaignEquals(CampaignInfo campaign);
}