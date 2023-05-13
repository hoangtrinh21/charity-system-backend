package com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.CampaignFollower;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.CampaignInfo;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampaignFollowerRepository extends JpaRepository<CampaignFollower, Integer> {
    List<CampaignFollower> findByCampaign_IdEquals(Integer id);
    CampaignFollower findByUserEqualsAndCampaignEquals(UserAccount user, CampaignInfo campaign);
    boolean existsByUserEqualsAndCampaignEquals(UserAccount user, CampaignInfo campaign);
    List<CampaignFollower> findByUserEquals(UserAccount user);
    long deleteByUserEqualsAndCampaignEquals(UserAccount user, CampaignInfo campaign);
}