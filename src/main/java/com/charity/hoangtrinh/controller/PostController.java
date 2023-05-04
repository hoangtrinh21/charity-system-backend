package com.charity.hoangtrinh.controller;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.CampaignInfo;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.PostInfo;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.CampaignInfoRepository;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.PostInfoRepository;
import com.charity.hoangtrinh.model.ResponseModel;
import com.charity.hoangtrinh.services.AccessService;
import com.charity.hoangtrinh.utils.JsonUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/charity/post/")
public class PostController {
    @Autowired
    private PostInfoRepository postInfoRepository;
    @Autowired
    private AccessService accessService;
    @Autowired
    private CampaignInfoRepository campaignInfoRepository;


    @GetMapping("/get-post")
    public ResponseEntity<Object> getAllPostsOnCampaign(@RequestParam(value = "campaign-id") String campaignIdStr) {
        try {
            Integer campaignId = Integer.parseInt(campaignIdStr);

            List<PostInfo> postInfoList = postInfoRepository.findByCampaign_Id(campaignId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(postInfoList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getMessage()));
        }
    }

    @PostMapping("/add-post")
    public ResponseEntity<ResponseModel> addPost(@RequestHeader Map<String, String> header,
                                                 @RequestBody String body) {
        String token = header.getOrDefault("Token", "");
        boolean isOrganization = accessService.isOrganization(token);

        if (!isOrganization)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseModel("You are not organization!"));

        JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();

        try {
            Integer campaignId = JsonUtil.getInt(jsonBody, "campaign-id");
            String content = JsonUtil.getString(jsonBody, "content");
            String type = JsonUtil.getString(jsonBody, "type");
            long submitTime = System.currentTimeMillis();

            assert campaignId != null;
            if (!accessService.getUserByCampaignId(campaignId).equals(accessService.getUserByToken(token)))
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ResponseModel("You do not have permission to this campaign!"));
            CampaignInfo campaign = campaignInfoRepository.getReferenceById(campaignId);
            PostInfo post = new PostInfo(content, type, submitTime, campaign);

            postInfoRepository.save(post);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseModel("Inserted campaign"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getMessage()));
        }
    }

    @PostMapping("/update-post")
    public ResponseEntity<ResponseModel> updatePost(@RequestHeader Map<String, String> header,
                                                    @RequestBody String body) {
        String token = header.getOrDefault("Token", "");
        boolean isOrganization = accessService.isOrganization(token);

        if (!isOrganization)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseModel("You are not organization!"));

        JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();

        try {
            Integer postId = JsonUtil.getInt(jsonBody, "post-id");
            Integer campaignId = JsonUtil.getInt(jsonBody, "campaign-id");
            assert campaignId != null;
            if (!accessService.getUserByCampaignId(campaignId).equals(accessService.getUserByToken(token)))
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ResponseModel("You do not have permission to this campaign!"));

            String content = JsonUtil.getString(jsonBody, "content");
            String type = JsonUtil.getString(jsonBody, "type");
            long submitTime = System.currentTimeMillis();

            CampaignInfo campaign = campaignInfoRepository.getReferenceById(campaignId);
            PostInfo post = new PostInfo(postId, content, type, submitTime, campaign);

            postInfoRepository.save(post);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel("Updated campaign"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getMessage()));
        }
    }


    @DeleteMapping("/delete-post")
    public ResponseEntity<ResponseModel> deletePost(@RequestHeader Map<String, String> header,
                                                    @RequestBody String body) {
        String token = header.getOrDefault("Token", "");
        boolean isOrganization = accessService.isOrganization(token);

        if (!isOrganization)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseModel("You are not organization!"));

        JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();

        try {
            Integer campaignId = JsonUtil.getInt(jsonBody, "campaign-id");
            assert campaignId != null;
            if (!accessService.getUserByCampaignId(campaignId).equals(accessService.getUserByToken(token)))
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ResponseModel("You do not have permission to this campaign!"));

            Integer postId = JsonUtil.getInt(jsonBody, "post-id");

            assert postId != null;
            postInfoRepository.deleteById(postId);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel("Deleted campaign"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getMessage()));
        }
    }
}
