package com.charity.hoangtrinh.controller;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.CampaignInfo;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.PostInfo;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.CampaignInfoRepository;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.CharityRepository;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.PostInfoRepository;
import com.charity.hoangtrinh.model.ResponseModel;
import com.charity.hoangtrinh.services.AccessService;
import com.charity.hoangtrinh.utils.JsonUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/charity/post/")
public class PostController {
    @Autowired
    private PostInfoRepository postInfoRepository;
    @Autowired
    private AccessService accessService;
    @Autowired
    private CampaignInfoRepository campaignInfoRepository;
    @Autowired
    private CharityRepository charityRepository;


    @GetMapping("/get-post")
    public ResponseEntity<Object> getAllPostsOnCampaign(@RequestParam(value = "campaign-id") String campaignIdStr) {
        try {
            Integer campaignId = Integer.parseInt(campaignIdStr);

            CampaignInfo campaignInfo = campaignInfoRepository.getReferenceById(campaignId);
            List<PostInfo> postInfoList = postInfoRepository.findByCampaignEquals(campaignInfo);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(postInfoList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getMessage()));
        }
    }

    @GetMapping("/get-post-by-id")
    public ResponseEntity<Object> getAllPostsById(@RequestParam(value = "post-id") String postIdStr) {
        try {
            Integer postId = Integer.parseInt(postIdStr);

            PostInfo postInfo = postInfoRepository.getReferenceById(postId);
            System.out.println(postInfo);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(postInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getMessage()));
        }
    }

    @PostMapping("/add-post")
    public ResponseEntity<Object> addPost(@RequestHeader(value = "Token") String token,
                                                 @RequestBody String body) {
        boolean isOrganization = accessService.isOrganization(token);

        if (!isOrganization)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseModel("You are not organization!"));

        JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();

        try {
            Integer campaignId = JsonUtil.getInt(jsonBody, "campaign_id");

            assert campaignId != null;
            if (!Objects.equals(charityRepository.getReferenceById(accessService.getUserByToken(token).getCharityId()),
                    accessService.getUserByCampaignId(campaignId)))
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ResponseModel("You do not have permission to this campaign!"));

            CampaignInfo    campaign    = campaignInfoRepository.getReferenceById(campaignId);
            String          content     = JsonUtil.getString(jsonBody, "content");
            String          type        = JsonUtil.getString(jsonBody, "type");
            long            submitTime  = System.currentTimeMillis();
            PostInfo        post        = new PostInfo();
            post.setContent(content);
            post.setType(type);
            post.setCampaign(campaign);
            post.setSubmitTime(submitTime);

            postInfoRepository.save(post);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(post);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getMessage()));
        }
    }

    @PutMapping("/update-post")
    public ResponseEntity<Object> updatePost(@RequestHeader(value = "Token") String token,
                                                    @RequestBody String body) {
        boolean isOrganization = accessService.isOrganization(token);

        if (!isOrganization)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseModel("You are not organization!"));

        JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();

        try {
            Integer postId = JsonUtil.getInt(jsonBody, "post_id");
            Integer campaignId = JsonUtil.getInt(jsonBody, "campaign_id");

            assert campaignId != null;
            if (!Objects.equals(charityRepository.getReferenceById(accessService.getUserByToken(token).getCharityId()),
                    accessService.getUserByCampaignId(campaignId)))
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ResponseModel("You do not have permission to this campaign!"));

            String content = JsonUtil.getString(jsonBody, "content");
            String type = JsonUtil.getString(jsonBody, "type");
            long submitTime = System.currentTimeMillis();

            CampaignInfo campaign = campaignInfoRepository.getReferenceById(campaignId);
            PostInfo post = new PostInfo(postId, content, type, submitTime, campaign);

            postInfoRepository.save(post);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(post);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getMessage()));
        }
    }


    @DeleteMapping("/delete-post")
    public ResponseEntity<Object> deletePost(@RequestHeader(value = "Token") String token,
                                             @RequestParam(value = "campaign-id") String campaignId,
                                             @RequestParam(value = "post-id") String postId) {
        boolean isOrganization = accessService.isOrganization(token);

        if (!isOrganization)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseModel("You are not organization!"));


        try {
            assert campaignId != null;
            if (!Objects.equals(charityRepository.getReferenceById(accessService.getUserByToken(token).getCharityId()),
                    accessService.getUserByCampaignId(Integer.parseInt(campaignId))))
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ResponseModel("You do not have permission to this campaign!"));


            assert postId != null;
            postInfoRepository.deleteById(Integer.valueOf(postId));

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel("Deleted campaign"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getMessage()));
        }
    }
}
