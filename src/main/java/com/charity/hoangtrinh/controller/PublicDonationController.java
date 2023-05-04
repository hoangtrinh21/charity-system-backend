package com.charity.hoangtrinh.controller;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.PublicDonation;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.UserAccount;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.PublicDonationRepository;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.UserAccountRepository;
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
import java.util.Optional;

@RequestMapping("/charity/public-donation")
@RestController
public class PublicDonationController {
    @Autowired
    private PublicDonationRepository publicDonationRepository;
    @Autowired
    private AccessService accessService;
    @Autowired
    private UserAccountRepository userAccountRepository;

    /**
     * API lấy toàn bộ vật phẩm ủng hộ
     */
    @GetMapping("/get-all")
    public ResponseEntity<ResponseModel> getAllPublicDonation() {
        try {
            List<PublicDonation> publicDonations = publicDonationRepository.findAll();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel(publicDonations));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel("INTERNAL_SERVER_ERROR"));
        }
    }

    /**
     * API lấy chi tiết vật phẩm ủng hộ
     */
    @GetMapping("/get-by-condition")
    public ResponseEntity<ResponseModel> getPublicDonationByCondition(@RequestParam Map<String, String> conditions) {
        try {
            Integer donationId = conditions.get("donation-id") == null ?
                    null : Integer.parseInt(conditions.get("donation-id"));
            String name = conditions.get("name");
            String status = conditions.get("status");
            String targetAddress = conditions.get("target_address");
            String targetObject = conditions.get("target_object");
            List<PublicDonation> publicDonationList = publicDonationRepository
                    .findByIdEqualsAndNameLikeAndStatusLikeAndTargetAddressLikeAndTargetObjectLike(
                            donationId, name, status, targetAddress, targetObject);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel(publicDonationList));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel("INTERNAL_SERVER_ERROR"));
        }
    }

    /**
     * API thêm vật phẩm
     * @param header header của client, header chưa userid và accesstoken
     * @param body body
     */
    @PostMapping("/insert")
    public ResponseEntity<ResponseModel> insertPublicDonation(@RequestHeader Map<String, String> header,
                                                              @RequestBody String body) {
        try {
            String token = header.get("Token");
            boolean isOrganization = accessService.isOrganization(token);

            if (!isOrganization)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseModel("You are not organization!"));

            JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();
            Integer donorId                 = JsonUtil.getInt(jsonBody, "donor_id");
            assert donorId != null;
            UserAccount donor = userAccountRepository.getReferenceById(donorId);
            Integer introPostId             = JsonUtil.getInt(jsonBody,"intro_post_id");
            Integer receivingOrganizationId = JsonUtil.getInt(jsonBody,"receiving_organization_id");
            String name             = JsonUtil.getString(jsonBody,"name");
            String status           = JsonUtil.getString(jsonBody,"status");
            String targetAddress    = JsonUtil.getString(jsonBody,"target_address");
            String targetObject     = JsonUtil.getString(jsonBody,"target_object");
            String img              = JsonUtil.getString(jsonBody,"img");
            String contentPost      = JsonUtil.getString(jsonBody,"post_content");
            PublicDonation publicDonation =
                    new PublicDonation(donor, introPostId, name, status, receivingOrganizationId, targetAddress, targetObject, img);
            publicDonationRepository.save(publicDonation);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseModel("Inserted public donation"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel("INTERNAL_SERVER_ERROR"));
        }
    }

    /**
     * API sửa thông tin 1 vật phẩm
     * @param header header chứa thông tin userid và accesstoken
     * @param donationId id của vật phẩm ủng hộ
     * @param body body
     */
    @PutMapping("put")
    public ResponseEntity<ResponseModel> updatePublicDonation(@RequestHeader Map<String, String> header,
                                                              @RequestParam(value = "donation-id") int donationId,
                                                              @RequestBody String body) {
        try {
            String token = header.get("Token");
            boolean isOrganization = accessService.isOrganization(token);

            if (!isOrganization)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseModel("You are not organization!"));

            JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();
            Integer donorId                 = JsonUtil.getInt(jsonBody,"donor_id");
            assert donorId != null;
            UserAccount donor = userAccountRepository.getReferenceById(donorId);
            Integer introPostId             = JsonUtil.getInt(jsonBody,"intro_post_id");
            Integer receivingOrganizationId = JsonUtil.getInt(jsonBody,"receiving_organization_id");
            String name             = JsonUtil.getString(jsonBody,"name");
            String status           = JsonUtil.getString(jsonBody,"status");
            String targetAddress    = JsonUtil.getString(jsonBody,"target_address");
            String targetObject     = JsonUtil.getString(jsonBody,"target_object");
            String img              = JsonUtil.getString(jsonBody,"img");
            PublicDonation publicDonation = new PublicDonation(donor, introPostId, name, status, receivingOrganizationId, targetAddress, targetObject, img);
            publicDonationRepository.save(publicDonation);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel("Updated public donation"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel("INTERNAL_SERVER_ERROR"));
        }
    }

    /**
     * API xóa một bài đăng/vật phẩm ủng hộ
     * @param donationId id của vật phẩm
     * @param header header chứa thông tin đăng nhập(*)
     */
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseModel> deletePublicDonation(@RequestParam(value = "donation_id") int donationId,
                                                              @RequestHeader Map<String, String> header) {
        try {
            String token = header.get("Token");
            boolean isOrganization = accessService.isOrganization(token);

            if (!isOrganization)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseModel("You are not organization!"));

            publicDonationRepository.deleteById(donationId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel("Deleted public donation!"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel("INTERNAL_SERVER_ERROR"));
        }
    }
}
