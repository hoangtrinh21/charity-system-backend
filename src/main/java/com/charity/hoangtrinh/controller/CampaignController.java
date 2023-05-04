package com.charity.hoangtrinh.controller;

import com.charity.hoangtrinh.config.Constants;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.CampaignInfo;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.PostInfo;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.UserAccount;
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
    private UserAccountRepository userAccountRepository;
    @Autowired
    private CampaignInfoRepository campaignInfoRepository;
    @Autowired
    private CampaignInputRepository campaignInputRepository;
    @Autowired
    private CampaignOutputRepository campaignOutputRepository;
    @Autowired
    private PostInfoRepository postInfoRepository;
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
    public ResponseEntity<Object> getAllCampaigns(@RequestHeader Map<String, String> header) {
        try {
            String token = header.getOrDefault("Token", "");

            if (accessService.isAdmin(token))
                return ResponseEntity.status(HttpStatus.OK)
                        .body(campaignInfoRepository.findAll());

            if (accessService.isOrganization(token)) {
                int organizationId = accessService.getUserByToken(token).getId();
                return ResponseEntity.status(HttpStatus.OK)
                        .body(campaignInfoRepository.findByOrganization_Id(organizationId));
            }

            return ResponseEntity.status(HttpStatus.OK)
                    .body(campaignInfoRepository.findByIsActiveEquals(true));
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
    public ResponseEntity<Object> getByCondition(@RequestHeader Map<String, String> header,
                                                        @RequestParam Map<String, String> params) {
        try {
            String token = header.getOrDefault("Token", "");
            boolean isAdminOrOrganization = accessService.isAdmin(token) || accessService.isOrganization(token);

            List<CampaignInfo> campaignInfos = campaignService
                    .getByCondition(params, isAdminOrOrganization);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(campaignInfos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel("INTERNAL_SERVER_ERROR"));
        }
    }


    @PostMapping("/add-campaign")
    public ResponseEntity<Object> addCampaign(@RequestHeader(value = "Token") String token,
                                                     @RequestBody String body) {
        try {
            boolean isOrganization = accessService.isOrganization(token);

            if (!isOrganization)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseModel("You are not organization!"));

            JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();

            Integer organizationId  = accessService.getUserByToken(token).getId();
            Integer lastUpdateTime  = (int) (System.currentTimeMillis() / 1000);
            String  campaignName    = JsonUtil.getString(jsonBody, "campaign_name");
            String  introduction    = JsonUtil.getString(jsonBody, "introduction");
            String  targetObject    = JsonUtil.getString(jsonBody, "target_object");
            String  region          = JsonUtil.getString(jsonBody, "region");
            String  campaignType    = JsonUtil.getString(jsonBody, "campaign_type");
            String  status          = JsonUtil.getString(jsonBody, "status");
            Long    targetAmount    = JsonUtil.getLong(jsonBody, "target_amount");
            Long    receiveAmount   = 0L;
            Long    donorAmount     = 0L;
            Long    spentAmount     = 0L;
            LocalDate startDate         = JsonUtil.getLocalDate(jsonBody, "start_date");
            LocalDate stopReceiveDate   = JsonUtil.getLocalDate(jsonBody, "stop_receive_date");
            LocalDate startActiveDate   = JsonUtil.getLocalDate(jsonBody, "start_active_date");
            LocalDate stopActiveDate    = JsonUtil.getLocalDate(jsonBody, "stop_active_date");
            LocalDate stopDate          = JsonUtil.getLocalDate(jsonBody, "stop_date");

            assert organizationId != null;
            UserAccount organization = userAccountRepository.getReferenceById(organizationId);
            System.out.println(organization);

            CampaignInfo campaignInfo = new CampaignInfo(organization, campaignName, introduction, targetObject,
                    region, campaignType, targetAmount, receiveAmount, donorAmount,
                    spentAmount, lastUpdateTime, startDate, stopReceiveDate,
                    startActiveDate, stopActiveDate, stopDate, status, true);
            campaignInfoRepository.save(campaignInfo);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(campaignInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass()));
        }
    }

    @PutMapping("/update-campaign")
    public ResponseEntity<Object> updateCampaign(@RequestHeader(value = "Token") String token,
                                                        @RequestBody String body) {
        try {
            boolean isOrganization = accessService.isOrganization(token);

            if (!isOrganization)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseModel("You are not organization!"));
            JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();
            int campaignId      = jsonBody.get("campaign-id").getAsInt();
            Integer organizationId  = accessService.getUserByToken(token).getId();

            if (!accessService.getUserByCampaignId(campaignId).getId().equals(organizationId))
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ResponseModel("You do not have permission to this campaign!"));

            Integer lastUpdateTime  = (int) (System.currentTimeMillis() / 1000);
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

            UserAccount organization = userAccountRepository.getReferenceById(organizationId);

            CampaignInfo campaignInfo = new CampaignInfo(campaignId, organization, campaignName, introduction, targetObject,
                    region, campaignType, targetAmount, receiveAmount, donorAmount,
                    spentAmount, lastUpdateTime, startDate, stopReceiveDate,
                    startActiveDate, stopActiveDate, stopDate, status, true);
            campaignInfoRepository.save(campaignInfo);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseModel("Updated campaign"));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass()));
        }
    }

    @DeleteMapping("/delete-campaign")
    public ResponseEntity<Object> deleteCampaign(@RequestHeader(value = "Token") String token,
                                                        @RequestParam(value = "campaign-id") String campaignIdStr) {
        try {
            boolean isOrganization = accessService.isOrganization(token);

            if (!isOrganization)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseModel("You are not organization!"));
            Integer campaignId = Integer.parseInt(campaignIdStr);

            campaignInfoRepository.deleteById(campaignId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel("Deleted campaign with id: " + campaignId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass()));
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
