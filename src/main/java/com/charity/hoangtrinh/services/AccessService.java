package com.charity.hoangtrinh.services;

import com.charity.hoangtrinh.config.CacheConfig;
import com.charity.hoangtrinh.config.Constants;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.CampaignInfo;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.UserAccount;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.CampaignInfoRepository;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.RoleRepository;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class AccessService {
    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CampaignInfoRepository campaignInfoRepository;
    public int checkAccessToken(String token) {
        UserAccount user = CacheConfig.accessToken.getIfPresent(token);
        if (user == null) return HttpStatus.FORBIDDEN.value();

        return HttpStatus.OK.value();
    }

    public boolean isOrganization(String token) {
        return token != null && checkAccessToken(token) == 200 &&
                Objects.requireNonNull(CacheConfig.accessToken.get(token)).getRole().getRoleName()
                        .equals(Constants.ROLE_ORGANIZATION);
    }

    public boolean isAdmin(String token) {
        return token != null && checkAccessToken(token) == 200 &&
                Objects.requireNonNull(CacheConfig.accessToken.get(token)).getRole().getRoleName()
                        .equals(Constants.ROLE_ADMIN);
    }

    public UserAccount getUserByCampaignId(int campaignId) {
        UserAccount organization = campaignInfoRepository.getReferenceById(campaignId).getOrganization();
        System.out.println(organization);
        return organization;
    }

    public UserAccount getUserByToken(String token) {
        return CacheConfig.accessToken.getIfPresent(token);
    }
}
