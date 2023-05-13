package com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.CampaignFollower;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.CampaignInfo;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignFollowerRepository extends JpaRepository<CampaignFollower, Integer> {
    long deleteByUserEqualsAndCampaignEquals(UserAccount user, CampaignInfo campaign);
}