package com.charity.hoangtrinh.controller;

import com.charity.hoangtrinh.config.Constants;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.PublicDonation;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.PublicDonationRepository;
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

    /**
     * API lấy toàn bộ vật phẩm ủng hộ
     */
    @GetMapping("/get-all")
    public ResponseEntity<ResponseModel> getAllPublicDonation() {
        try {
            List<PublicDonation> publicDonations = publicDonationRepository.findAll();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel(HttpStatus.OK.value(),
                            "Have " + publicDonations.size() + " public donation!",
                            publicDonations));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "INTERNAL_SERVER_ERROR",
                            "{}"));
        }
    }

    /**
     * API lấy chi tiết vật phẩm ủng hộ
     * @param publicDonationId id của vật phẩm
     */
    @GetMapping("/get-by-id")
    public ResponseEntity<ResponseModel> getPublicDonationById(@RequestParam(name = "public-donation-id") int publicDonationId) {
        try {
            Optional<PublicDonation> publicDonationOptional = publicDonationRepository.findById(publicDonationId);
            return publicDonationOptional.map(publicDonation -> ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel(HttpStatus.OK.value(),
                            "Have public donation!",
                            publicDonation))).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseModel(HttpStatus.NOT_FOUND.value(),
                            "Not found public donation!",
                            "{}")));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "INTERNAL_SERVER_ERROR",
                            "{}"));
        }
    }

    @GetMapping("/get-by-name")
    public ResponseEntity<ResponseModel> getPublicDonationByName(@RequestParam(name = "public-donation-name") String publicDonationName) {
        try {
            List<PublicDonation> publicDonationList = publicDonationRepository.findByNameLike(publicDonationName);
            if (publicDonationList.size() == 0) {

            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel(HttpStatus.OK.value(),
                            "Have " + publicDonationList.size() + " public donation!",
                            publicDonationList));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "INTERNAL_SERVER_ERROR",
                            "{}"));
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
                        .body(new ResponseModel(HttpStatus.BAD_REQUEST.value(),
                                "You are not organization!",
                                "{}"));

            JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();
            Integer donorId                 = JsonUtil.getInt(jsonBody, "donor_id");
            Integer introPostId             = JsonUtil.getInt(jsonBody,"intro_post_id");
            Integer receivingOrganizationId = JsonUtil.getInt(jsonBody,"receiving_organization_id");
            String name             = JsonUtil.getString(jsonBody,"name");
            String status           = JsonUtil.getString(jsonBody,"status");
            String targetAddress    = JsonUtil.getString(jsonBody,"target_address");
            String targetObject     = JsonUtil.getString(jsonBody,"target_object");
            String img              = JsonUtil.getString(jsonBody,"img");
            String contentPost      = JsonUtil.getString(jsonBody,"post_content");
            PublicDonation publicDonation =
                    new PublicDonation(donorId, introPostId, name, status, receivingOrganizationId, targetAddress, targetObject, img);
            publicDonationRepository.save(publicDonation);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseModel(HttpStatus.CREATED.value(),
                            "Inserted public donation",
                            ""));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "INTERNAL_SERVER_ERROR",
                            "{}"));
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
                        .body(new ResponseModel(HttpStatus.BAD_REQUEST.value(),
                                "You are not organization!",
                                "{}"));

            JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();
            Integer donorId                 = JsonUtil.getInt(jsonBody,"donor_id");
            Integer introPostId             = JsonUtil.getInt(jsonBody,"intro_post_id");
            Integer receivingOrganizationId = JsonUtil.getInt(jsonBody,"receiving_organization_id");
            String name             = JsonUtil.getString(jsonBody,"name");
            String status           = JsonUtil.getString(jsonBody,"status");
            String targetAddress    = JsonUtil.getString(jsonBody,"target_address");
            String targetObject     = JsonUtil.getString(jsonBody,"target_object");
            String img              = JsonUtil.getString(jsonBody,"img");
            PublicDonation publicDonation = new PublicDonation(donationId, donorId, introPostId, name, status, receivingOrganizationId, targetAddress, targetObject, img);
            publicDonationRepository.save(publicDonation);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel(HttpStatus.OK.value(),
                            "Updated public donation",
                            "{}}"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "INTERNAL_SERVER_ERROR",
                            "{}"));
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
                        .body(new ResponseModel(HttpStatus.BAD_REQUEST.value(),
                                "You are not organization!",
                                "{}"));

            publicDonationRepository.deleteById(donationId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel(HttpStatus.OK.value(),
                            "Deleted public donation!",
                            "{}}"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "INTERNAL_SERVER_ERROR",
                            "{}"));
        }
    }

}
