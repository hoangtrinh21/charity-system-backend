package com.charity.hoangtrinh.services;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.CampaignInfo;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.PostInfo;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.PostInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostInfoRepository postInfoRepository;
    public CampaignInfo getCampaignByPostId(int postId) {
        Optional<PostInfo> optionalPostInfo  = postInfoRepository.findById(postId);
        return optionalPostInfo.map(PostInfo::getCampaign).orElse(null);
    }
}
