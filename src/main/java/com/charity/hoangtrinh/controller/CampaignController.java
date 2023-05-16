package com.charity.hoangtrinh.controller;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.*;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.*;
import com.charity.hoangtrinh.model.ResponseModel;
import com.charity.hoangtrinh.services.AccessService;
import com.charity.hoangtrinh.services.CampaignService;
import com.charity.hoangtrinh.services.PostService;
import com.charity.hoangtrinh.utils.JsonUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

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
    private DonationRepository donationRepository;
    @Autowired
    private PostService postService;
    @Autowired
    private CharityRepository charityRepository;
    @Autowired
    private CampaignFollowerRepository campaignFollowerRepository;
    @Autowired
    private StatementRepository statementRepository;

    /**
     * Lấy toàn bộ chiến dịch
     * @return Toàn bộ các chiến dịch trong database nếu là admin; toàn bộ các chiến dịch của tổ chức nếu là tổ chức từ thiện;
     * toàn bộ các chiến dịch chưa bị khóa nếu là người dùng khách
     */
    @GetMapping("/get-all")
    public ResponseEntity<Object> getAllCampaigns(@RequestHeader(value = "Token") String token) {
        try {
            List<CampaignInfo> campaignInfoList;

            if (accessService.isAdmin(token)) {
                campaignInfoList = campaignInfoRepository.findAll();
                for (CampaignInfo campaignInfo : campaignInfoList) {
                    Charity charity = campaignInfo.getOrganization();
                    UserAccount userAccount = userAccountRepository.findByCharityIdEquals(charity.getId());
                    charity.setCharityName(userAccount);
                }
                return ResponseEntity.status(HttpStatus.OK)
                        .body(campaignInfoList);
            }

            if (accessService.isOrganization(token)) {
                int organizationId = accessService.getUserByToken(token).getCharityId();
                campaignInfoList = campaignInfoRepository.findByOrganization_IdEquals(organizationId);
                for (CampaignInfo campaignInfo : campaignInfoList) {
                    Charity charity = campaignInfo.getOrganization();
                    UserAccount userAccount = userAccountRepository.findByCharityIdEquals(charity.getId());
                    charity.setCharityName(userAccount);
                }
                return ResponseEntity.status(HttpStatus.OK)
                        .body(campaignInfoList);
            }

            campaignInfoList = campaignInfoRepository.findByIsActiveEquals(true);
            for (CampaignInfo campaignInfo : campaignInfoList) {
                Charity charity = campaignInfo.getOrganization();
                UserAccount userAccount = userAccountRepository.findByCharityIdEquals(charity.getId());
                charity.setCharityName(userAccount);
            }

            return ResponseEntity.status(HttpStatus.OK)
                    .body(campaignInfoList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass()));
        }
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<Object> getById(@RequestHeader(value = "Token") String token,
                                          @RequestParam(value = "campaign-id") String campaignIdStr) {
        try {
            boolean isAdminOrOrganization = accessService.isAdmin(token) || accessService.isOrganization(token);
            Integer campaignId = Integer.parseInt(campaignIdStr);

            if (isAdminOrOrganization) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(campaignInfoRepository.findById(campaignId));
            }

            return ResponseEntity.status(HttpStatus.OK)
                    .body(campaignInfoRepository.findByIdEqualsAndIsActiveEquals(campaignId ,true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass()));
        }
    }

    @GetMapping("/get-by-organization")
    public ResponseEntity<Object> getByOrganization(@RequestHeader(value = "Token") String token,
                                          @RequestParam(value = "organization-id") String organizationIdStr) {
        try {
            boolean isAdminOrOrganization = accessService.isAdmin(token) || accessService.isOrganization(token);
            Integer organizationId = Integer.parseInt(organizationIdStr);

            if (isAdminOrOrganization &&
                    Objects.equals(accessService.getUserByToken(token),
                            userAccountRepository.findByCharityIdEquals(organizationId)))
                return ResponseEntity.status(HttpStatus.OK)
                        .body(campaignInfoRepository.findByOrganization_IdEquals(organizationId));

            return ResponseEntity.status(HttpStatus.OK)
                    .body(campaignInfoRepository.findByOrganization_IdEqualsAndIsActiveEquals(organizationId, true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass()));
        }
    }


    /**
     * Lấy các chiến dịch theo các diều kiện (campaign name, region,...)
     * @param params các diều kiện
     * @return Toàn bộ chiến dịch trong database nếu là admin  hoặc tổ chức từ thiện, ngược lại trả về các chiến dịch chưa bị khóa
     */
    @GetMapping("/get-by-condition")
    public ResponseEntity<Object> getByCondition(@RequestHeader(value = "Token") String token,
                                                        @RequestParam Map<String, String> params) {
        try {
            boolean isAdminOrOrganization = accessService.isAdmin(token) || accessService.isOrganization(token);
            String campaignName     = params.get("campaign-name");
            String region           = params.get("region");
            String campaignType     = params.get("campaign-type");
            String targetObject     = params.get("target-object");
            String status           = params.get("status");

            if (isAdminOrOrganization)
                return ResponseEntity.status(HttpStatus.OK)
                        .body(campaignInfoRepository.findByCampaignNameLikeAndRegionLikeAndCampaignTypeLikeAndTargetObjectLikeAndStatusLike(
                                campaignName, region, campaignType, targetObject, status
                        ));
            
            return ResponseEntity.status(HttpStatus.OK)
                    .body(campaignInfoRepository.findByCampaignNameLikeAndRegionLikeAndCampaignTypeLikeAndTargetObjectLikeAndStatusLikeAndIsActiveTrue(
                            campaignName, region, campaignType, targetObject, status
                    ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass() + ":" + e.getMessage()));
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

            Integer organizationId  = accessService.getUserByToken(token).getCharityId();
            Integer lastUpdateTime  = (int) (System.currentTimeMillis() / 1000);
            String  campaignName    = JsonUtil.getString(jsonBody, "campaign_name");
            String  introduction    = JsonUtil.getString(jsonBody, "introduction");
            String  targetObject    = JsonUtil.getString(jsonBody, "target_object");
            String  region          = JsonUtil.getString(jsonBody, "region");
            String  campaignType    = JsonUtil.getString(jsonBody, "campaign_type");
            String  status          = JsonUtil.getString(jsonBody, "status");
            String  images          = JsonUtil.getString(jsonBody, "images");
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
            Charity organization = charityRepository.getReferenceById(organizationId);

            CampaignInfo campaignInfo = new CampaignInfo();
            campaignInfo.setOrganization(organization);
            campaignInfo.setLastUpdateTime(lastUpdateTime);
            campaignInfo.setCampaignName(campaignName);
            campaignInfo.setIntroduction(introduction);
            campaignInfo.setTargetObject(targetObject);
            campaignInfo.setRegion(region);
            campaignInfo.setCampaignType(campaignType);
            campaignInfo.setStatus(status);
            campaignInfo.setTargetAmount(targetAmount);
            campaignInfo.setReceiveAmount(receiveAmount);
            campaignInfo.setDonorAmount(donorAmount);
            campaignInfo.setOrganization(organization);
            campaignInfo.setSpentAmount(spentAmount);
            campaignInfo.setStartDate(startDate);
            campaignInfo.setStopReceiveDate(stopReceiveDate);
            campaignInfo.setStartActiveDate(startActiveDate);
            campaignInfo.setStopActiveDate(stopActiveDate);
            campaignInfo.setStopDate(stopDate);
            campaignInfo.setImages(images);
            campaignInfoRepository.save(campaignInfo);

            campaignInfo.getOrganization().setCharityName(userAccountRepository.findByCharityIdEquals(organizationId));
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(campaignInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass() + ":" + e.getMessage()));
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
            int campaignId      = jsonBody.get("campaign_id").getAsInt();
            Integer organizationId  = accessService.getUserByToken(token).getCharityId();

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
            String  images          = JsonUtil.getString(jsonBody, "images");
            boolean  isStar         = jsonBody.get("isStar").getAsBoolean();
            Long    targetAmount    = JsonUtil.getLong(jsonBody, "target_amount");
            Long    receiveAmount   = JsonUtil.getLong(jsonBody, "receive_amount");
            Long    donorAmount     = JsonUtil.getLong(jsonBody, "donor_amount");
            Long    spentAmount     = JsonUtil.getLong(jsonBody, "spent_amount");
            LocalDate startDate         = JsonUtil.getLocalDate(jsonBody, "start_date");
            LocalDate stopReceiveDate   = JsonUtil.getLocalDate(jsonBody, "stop_receive_date");
            LocalDate startActiveDate   = JsonUtil.getLocalDate(jsonBody, "start_active_date");
            LocalDate stopActiveDate    = JsonUtil.getLocalDate(jsonBody, "stop_active_date");
            LocalDate stopDate          = JsonUtil.getLocalDate(jsonBody, "stop_date");

            Charity organization = charityRepository.getReferenceById(organizationId);

            CampaignInfo campaignInfo = new CampaignInfo();
            campaignInfo.setId(campaignId);
            campaignInfo.setOrganization(organization);
            campaignInfo.setLastUpdateTime(lastUpdateTime);
            campaignInfo.setCampaignName(campaignName);
            campaignInfo.setIntroduction(introduction);
            campaignInfo.setTargetObject(targetObject);
            campaignInfo.setRegion(region);
            campaignInfo.setCampaignType(campaignType);
            campaignInfo.setStatus(status);
            campaignInfo.setTargetAmount(targetAmount);
            campaignInfo.setReceiveAmount(receiveAmount);
            campaignInfo.setDonorAmount(donorAmount);
            campaignInfo.setOrganization(organization);
            campaignInfo.setSpentAmount(spentAmount);
            campaignInfo.setStartDate(startDate);
            campaignInfo.setStopReceiveDate(stopReceiveDate);
            campaignInfo.setStartActiveDate(startActiveDate);
            campaignInfo.setStopActiveDate(stopActiveDate);
            campaignInfo.setStopDate(stopDate);
            campaignInfo.setImages(images);
            campaignInfo.setStar(isStar);
            campaignInfoRepository.save(campaignInfo);

            campaignInfo.getOrganization().setCharityName(userAccountRepository.findByCharityIdEquals(organizationId));
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(campaignInfo);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass() + ":" + e.getMessage()));
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
                    .body(new ResponseModel(e.getClass() + ":" + e.getMessage()));
        }
    }

    @GetMapping("/campaign-get-follower")
    public ResponseEntity<Object> getFollowerCampaign(@RequestParam(value = "campaign-id") String campaignIdStr) {
        try {
            List<CampaignFollower> campaignFollowers =
                    campaignFollowerRepository.findByCampaign_IdEquals(Integer.valueOf(campaignIdStr));
            List<UserAccount> followers = new ArrayList<>();
            for (CampaignFollower c : campaignFollowers) {
                followers.add(c.getUser());
            }

            return ResponseEntity.status(HttpStatus.OK)
                    .body(followers);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass() + ":" + e.getMessage()));
        }
    }

    @GetMapping("/user-get-followed-campaign")
    public ResponseEntity<Object> getCampaignsUserFollow(@RequestHeader(value = "Token") String token) {
        try {
            boolean isDonor = accessService.isDonor(token);

            if (!isDonor)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseModel("You are not donor!"));
            UserAccount donor = accessService.getUserByToken(token);

            List<CampaignFollower> campaignFollowers =
                    campaignFollowerRepository.findByUserEquals(donor);
            List<CampaignInfo> campaignInfos = new ArrayList<>();
            for (CampaignFollower c : campaignFollowers) {
                campaignInfos.add(c.getCampaign());
            }

            return ResponseEntity.status(HttpStatus.OK)
                    .body(campaignInfos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass() + ":" + e.getMessage()));
        }
    }

    @PostMapping("/user-follow-campaign")
    public ResponseEntity<Object> userFollowCampaign(@RequestHeader(value = "Token") String token,
                                                        @RequestParam(value = "campaign-id") String campaignIdStr) {
        try {
            boolean isDonor = accessService.isDonor(token);


            if (!isDonor)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseModel("You are not donor!"));
            UserAccount donor = accessService.getUserByToken(token);

            Optional<CampaignInfo> campaignInfoOptional = campaignInfoRepository.findById(Integer.valueOf(campaignIdStr));

            if (!campaignInfoOptional.isPresent())
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseModel("Don't have this campaign!"));

            if (campaignFollowerRepository.existsByUserEqualsAndCampaignEquals(donor, campaignInfoOptional.get()))
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseModel("You followed this campaign!"));

            CampaignFollower campaignFollower = new CampaignFollower();
            campaignFollower.setUser(donor);
            campaignFollower.setCampaign(campaignInfoOptional.get());
            campaignFollowerRepository.save(campaignFollower);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(campaignFollower);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass() + ":" + e.getMessage()));
        }
    }

    @DeleteMapping("/user-unfollow-campaign")
    public ResponseEntity<Object> userUnfollowCampaign(@RequestHeader(value = "Token") String token,
                                                        @RequestParam(value = "campaign-id") String campaignIdStr) {
        try {
            boolean isDonor = accessService.isDonor(token);

            if (!isDonor)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseModel("You are not donor!"));
            UserAccount donor = accessService.getUserByToken(token);

            CampaignInfo campaignInfo = campaignInfoRepository.getReferenceById(Integer.valueOf(campaignIdStr));

            campaignFollowerRepository.deleteById(campaignFollowerRepository.findByUserEqualsAndCampaignEquals(donor, campaignInfo).getId());

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel("Unfollowed!"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass() + ":" + e.getMessage()));
        }
    }

    @PostMapping("/add-statement-campaign")
    public ResponseEntity<Object> addStatement(@RequestHeader(value = "Token") String token,
                                               @RequestParam(value = "campaign-id") String campaignIdStr,
                                               @RequestBody String body) {
        try {
            int campaignId      = Integer.parseInt(campaignIdStr);
            Optional<CampaignInfo> campaignInfoOptional = campaignInfoRepository.findById(campaignId);

            if (!campaignInfoOptional.isPresent())
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseModel("This campaign do not exists!"));

            JsonArray jsonBody = JsonParser.parseString(body).getAsJsonArray();
            List<Statement> statementSaved = new ArrayList<>();
            for (JsonElement element : jsonBody) {
                JsonObject object = element.getAsJsonObject();

                Long donationAmount = object.get("amount").getAsLong();
                String name = object.get("name").getAsString();
                String note = object.get("note").getAsString();
                Instant time = Instant.now();
                String type = object.get("type").getAsString();

                Statement statement = new Statement();
                statement.setName(name);
                statement.setTimeCreate(time);
                statement.setNote(note);
                statement.setType(type);
                statement.setAmount(donationAmount);
                statement.setCampaign(campaignInfoOptional.get());

                CampaignInfo campaignInfo = campaignInfoOptional.get();
                long oldReceiveAmount = campaignInfo.getReceiveAmount();
                long oldSpentAmount = campaignInfo.getSpentAmount();
                if (type.equals("nhận")) campaignInfo.setReceiveAmount(oldReceiveAmount + donationAmount);
                else campaignInfo.setSpentAmount(oldSpentAmount + donationAmount);
                campaignInfoRepository.save(campaignInfo);

                statementRepository.save(statement);
                statementSaved.add(statement);
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(statementSaved);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass() + ":" + e.getMessage()));
        }
    }

    @GetMapping("/get-statement-campaign")
    public ResponseEntity<Object> getStatement(@RequestParam(value = "campaign-id") String campaignIdStr) {

        try {
            int campaignId = Integer.parseInt(campaignIdStr);
            Optional<CampaignInfo> campaignInfoOptional = campaignInfoRepository.findById(campaignId);
            if (!campaignInfoOptional.isPresent())
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseModel("This campaign do not exists!"));
            List<Statement> statements = statementRepository.findByCampaignEquals(campaignInfoOptional.get());

            return ResponseEntity.status(HttpStatus.OK)
                    .body(statements);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass() + ":" + e.getMessage()));
        }
    }

    @GetMapping("/get-star")
    public ResponseEntity<Object> getStarCampaigns() {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(campaignInfoRepository.findByStarTrue());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass() + ":" + e.getMessage()));
        }
    }
}
