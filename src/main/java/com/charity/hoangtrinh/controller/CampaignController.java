package com.charity.hoangtrinh.controller;

import com.charity.hoangtrinh.config.Constants;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.CampaignInfo;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.PostInfo;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.User;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.*;
import com.charity.hoangtrinh.model.ResponseModel;
import com.charity.hoangtrinh.services.AccessService;
import com.charity.hoangtrinh.services.CampaignService;
import com.charity.hoangtrinh.services.PostService;
import com.charity.hoangtrinh.utils.JsonUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.org.apache.bcel.internal.generic.FSUB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/charity/campaign")
public class CampaignController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    CampaignInfoRepository campaignInfoRepository;
    @Autowired
    CampaignInputRepository campaignInputRepository;
    @Autowired
    CampaignOutputRepository campaignOutputRepository;
    @Autowired
    PostInfoRepository postInfoRepository;
    @Autowired
    private AccessService accessService;
    @Autowired
    private CampaignService campaignService;
    @Autowired
    private PublicDonationRepository publicDonationRepository;
    @Autowired
    private PostService postService;

    /**
     * Lấy toàn bộ chiến dịch
     * @return Toàn bộ chiến dịch trong database nếu là admin  hoặc tổ chức từ thiện, ngược lại trả về các chiến dịch chưa bị khóa
     */
    @GetMapping("/get-all")
    public ResponseEntity<ResponseModel> getAllCampaigns(@RequestHeader Map<String, String> header) {
        try {
            String token = header.get("Token");
            boolean isAdminOrOrganization = accessService.isAdmin(token) || accessService.isOrganization(token);

            List<CampaignInfo> campaignInfos = campaignService.getAllCampaigns(isAdminOrOrganization);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel(campaignInfos));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel("INTERNAL_SERVER_ERROR"));
        }
    }

    /**
     * Lấy các chiến dịch theo các diều kiện (campaign name, region,...)
     * @param params các diều kiện
     * @return Toàn bộ chiến dịch trong database nếu là admin  hoặc tổ chức từ thiện, ngược lại trả về các chiến dịch chưa bị khóa
     */
    @GetMapping("/get-by-condition")
    public ResponseEntity<ResponseModel> getByCondition(@RequestHeader Map<String, String> header,
                                                        @RequestParam Map<String, String> params) {
        try {
            String token = header.get("Token");
            boolean isAdminOrOrganization = accessService.isAdmin(token) || accessService.isOrganization(token);

            List<CampaignInfo> campaignInfos = campaignService
                    .getByCondition(params, isAdminOrOrganization);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel(campaignInfos));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel("INTERNAL_SERVER_ERROR"));
        }
    }


    @PostMapping("/add-campaign")
    public ResponseEntity<ResponseModel> addCampaign(@RequestHeader Map<String, String> header,
                                                     @RequestBody String body) {
        try {
            String token = header.get("Token");
            boolean isOrganization = accessService.isOrganization(token);

            if (!isOrganization)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseModel("You are not organization!"));

            JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();

            Integer organizationId  = JsonUtil.getInt(jsonBody, "organization_id");
            Integer lastUpdateTime  = JsonUtil.getInt(jsonBody, "last_update_time");
            String  campaignName    = JsonUtil.getString(jsonBody, "campaign_name");
            String  introduction    = JsonUtil.getString(jsonBody, "introduction");
            String  targetObject    = JsonUtil.getString(jsonBody, "target_object");
            String  region          = JsonUtil.getString(jsonBody, "region");
            String  campaignType    = JsonUtil.getString(jsonBody, "campaign_type");
            String  status          = JsonUtil.getString(jsonBody, "status");
            Long    targetAmount    = JsonUtil.getLong(jsonBody, "target_amount");
            Long    receiveAmount   = JsonUtil.getLong(jsonBody, "receive_amount");
            Long    donorAmount     = JsonUtil.getLong(jsonBody, "donor_amount");
            Long    spentAmount     = JsonUtil.getLong(jsonBody, "spent_amount");
            LocalDate startDate         = JsonUtil.getLocalDate(jsonBody, "start_date");
            LocalDate stopReceiveDate   = JsonUtil.getLocalDate(jsonBody, "stop_receive_date");
            LocalDate startActiveDate   = JsonUtil.getLocalDate(jsonBody, "start_active_date");
            LocalDate stopActiveDate    = JsonUtil.getLocalDate(jsonBody, "stop_active_date");
            LocalDate stopDate          = JsonUtil.getLocalDate(jsonBody, "stop_date");

            assert organizationId != null;
            User organization = userRepository.getReferenceById(organizationId);

            CampaignInfo campaignInfo = new CampaignInfo(organization, campaignName, introduction, targetObject,
                    region, campaignType, targetAmount, receiveAmount, donorAmount,
                    spentAmount, lastUpdateTime, startDate, stopReceiveDate,
                    startActiveDate, stopActiveDate, stopDate, status, true);
            campaignInfoRepository.save(campaignInfo);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseModel("Inserted campaign"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getMessage()));
        }
    }

    @PutMapping("/update-campaign")
    public ResponseEntity<ResponseModel> updateCampaign(@RequestHeader Map<String, String> header,
                                                        @RequestBody String body) {
        try {
            String token = header.get("Token");
            boolean isOrganization = accessService.isOrganization(token);

            if (!isOrganization)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseModel("You are not organization!"));
            JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();
            Integer campaignId      = jsonBody.get("campaign-id").getAsInt();
            Integer organizationId  = JsonUtil.getInt(jsonBody, "organization_id");
            Integer lastUpdateTime  = JsonUtil.getInt(jsonBody, "last_update_time");
            String  campaignName    = JsonUtil.getString(jsonBody, "campaign_name");
            String  introduction    = JsonUtil.getString(jsonBody, "introduction");
            String  targetObject    = JsonUtil.getString(jsonBody, "target_object");
            String  region          = JsonUtil.getString(jsonBody, "region");
            String  campaignType    = JsonUtil.getString(jsonBody, "campaign_type");
            String  status          = JsonUtil.getString(jsonBody, "status");
            Long    targetAmount    = JsonUtil.getLong(jsonBody, "target_amount");
            Long    receiveAmount   = JsonUtil.getLong(jsonBody, "receive_amount");
            Long    donorAmount     = JsonUtil.getLong(jsonBody, "donor_amount");
            Long    spentAmount     = JsonUtil.getLong(jsonBody, "spent_amount");
            LocalDate startDate         = JsonUtil.getLocalDate(jsonBody, "start_date");
            LocalDate stopReceiveDate   = JsonUtil.getLocalDate(jsonBody, "stop_receive_date");
            LocalDate startActiveDate   = JsonUtil.getLocalDate(jsonBody, "start_active_date");
            LocalDate stopActiveDate    = JsonUtil.getLocalDate(jsonBody, "stop_active_date");
            LocalDate stopDate          = JsonUtil.getLocalDate(jsonBody, "stop_date");

            assert organizationId != null;
            User organization = userRepository.getReferenceById(organizationId);

            CampaignInfo campaignInfo = new CampaignInfo(campaignId, organization, campaignName, introduction, targetObject,
                    region, campaignType, targetAmount, receiveAmount, donorAmount,
                    spentAmount, lastUpdateTime, startDate, stopReceiveDate,
                    startActiveDate, stopActiveDate, stopDate, status, true);
            campaignInfoRepository.save(campaignInfo);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseModel("Inserted campaign"));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getMessage()));
        }
    }

    @DeleteMapping("/delete-campaign")
    public ResponseEntity<ResponseModel> deleteCampaign(@RequestHeader Map<String, String> header,
                                                        @RequestBody String body) {
        try {
            String token = header.get("Token");
            boolean isOrganization = accessService.isOrganization(token);

            if (!isOrganization)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseModel("You are not organization!"));
            JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();
            Integer campaignId = jsonBody.get("campaign-id").getAsInt();

            campaignInfoRepository.deleteById(campaignId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel("Deleted campaign with id: " + campaignId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getMessage()));
        }
    }

    @PostMapping("/add-post")
    public ResponseEntity<ResponseModel> addPost(@RequestHeader Map<String, String> header,
                                                 @RequestBody String body) {
        String token = header.get("Token");
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
        String token = header.get("Token");
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
        String token = header.get("Token");
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

    /**
     * Sao kê
     */
//    @PostMapping("/statement")
//    public ResponseEntity<ResponseModel> statement(@RequestParam(value = "campaign-id") String campaignIdStr) {
//        int campaignId = Integer.parseInt(campaignIdStr);
//        CamIn
//    }
}
