package com.charity.hoangtrinh.services;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.CampaignInfo;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.UserAccount;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.CampaignInfoRepository;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.StatementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CampaignService {
    @Autowired
    private CampaignInfoRepository campaignInfoRepository;
    @Autowired
    private StatementRepository statementRepository;

    public List<CampaignInfo> getAllCampaigns(boolean isAdmin) {
        if (isAdmin) return campaignInfoRepository.findAll();
        return campaignInfoRepository.findByIsActiveEquals(true);
    }

    public List<CampaignInfo> getOutstanding() {
        List<CampaignInfo> outstanding = new ArrayList<>();

        List<CampaignInfo> allCampaigns = campaignInfoRepository.findAll();
        Map<CampaignInfo, Long> amountDonatedOfCampaign = new HashMap<>();
        allCampaigns.forEach(campaignInfo -> {
            long count = statementRepository.countByCampaignEquals(campaignInfo);
            amountDonatedOfCampaign.put(campaignInfo, count);
        });

        // Sắp xếp các Map.Entry theo giá trị giảm dần
        List<Map.Entry<CampaignInfo, Long>> amountSorted = new ArrayList<>(amountDonatedOfCampaign.entrySet());
        amountSorted.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        for (int i = 0; i < 3 && i < amountSorted.size(); i++) {
            outstanding.add(amountSorted.get(i).getKey());
        }
        return outstanding;
    }

//    public List<CampaignInfo> getByCondition(Map<String, String> conditions, boolean isAdmin) {
//        Integer campaignId      = conditions.get("campaign-id") == null ?
//                null : Integer.parseInt(conditions.get("campaign-id"));
//        Integer organizationId  = conditions.get("organization-id") == null ?
//                null : Integer.parseInt(conditions.get("organization-id"));
//        String organizationName = conditions.get("organization-name");
//        String campaignName     = conditions.get("campaign-name");
//        String region           = conditions.get("region");
//        String campaignType     = conditions.get("campaign-type");
//        String targetObject     = conditions.get("target-object");
//        String status           = conditions.get("status");
//        System.out.println(isAdmin);
//        if (isAdmin)
//            return campaignInfoRepository.
//    }
}
