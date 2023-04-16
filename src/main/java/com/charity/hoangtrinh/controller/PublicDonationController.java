package com.charity.hoangtrinh.controller;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.PublicDonation;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.PublicDonationRepository;
import com.charity.hoangtrinh.model.ResponseModel;
import com.charity.hoangtrinh.services.AccessService;
import org.json.JSONObject;
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
    @GetMapping("/get")
    public ResponseEntity<ResponseModel> getPublicDonation(@RequestParam(name = "public-donation-id") int publicDonationId) {
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

    /**
     * API thêm vật phẩm
     * @param header header của client, header chưa userid và accesstoken
     * @param body body
     */
    @PostMapping("/insert")
    public ResponseEntity<ResponseModel> insertPublicDonation(@RequestHeader Map<String, String> header,
                                                              @RequestBody String body) {
        try {
            String userIdStr = header.get("User-Id");
            String token = header.get("Token");
            if (userIdStr == null || token == null)
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ResponseModel(HttpStatus.FORBIDDEN.value(), "Provide userId and token!", "{}"));
            int checked = accessService.checkAccessToken(Integer.parseInt(userIdStr), token);
            if (checked == 403)
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ResponseModel(HttpStatus.FORBIDDEN.value(), "You don't have permission", "{}"));
            if (checked == 401)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ResponseModel(HttpStatus.UNAUTHORIZED.value(), "Wrong author", "{}"));

            JSONObject jsonBody = new JSONObject(body);
            int donorId                 = jsonBody.getInt("donor_id");
            int introPostId             = jsonBody.getInt("intro_post_id");
            int receivingOrganizationId = jsonBody.getInt("receiving_organization_id");
            String name             = jsonBody.getString("name");
            String status           = jsonBody.getString("status");
            String targetAddress    = jsonBody.getString("target_address");
            String targetObject     = jsonBody.getString("target_object");
            String img              = jsonBody.getString("img");
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
            String userIdStr = header.get("User-Id");
            String token = header.get("Token");
            if (userIdStr == null || token == null)
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ResponseModel(HttpStatus.FORBIDDEN.value(), "Provide userId and token!", "{}"));
            int checked = accessService.checkAccessToken(Integer.parseInt(userIdStr), token);
            if (checked == 403)
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ResponseModel(HttpStatus.FORBIDDEN.value(), "You don't have permission", "{}"));
            if (checked == 401)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ResponseModel(HttpStatus.UNAUTHORIZED.value(), "Wrong author", "{}"));

            JSONObject jsonBody = new JSONObject(body);
            int donorId = jsonBody.getInt("donor_id");
            int introPostId = jsonBody.getInt("intro_post_id");
            String name = jsonBody.getString("name");
            String status = jsonBody.getString("status");
            int receivingOrganizationId = jsonBody.getInt("receiving_organization_id");
            String targetAddress = jsonBody.getString("target_address");
            String targetObject = jsonBody.getString("target_object");
            String img = jsonBody.getString("img");
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
            String userIdStr = header.get("User-Id");
            String token = header.get("Token");
            if (userIdStr == null || token == null)
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ResponseModel(HttpStatus.FORBIDDEN.value(), "Provide userId and token!", "{}"));
            int checked = accessService.checkAccessToken(Integer.parseInt(userIdStr), token);
            if (checked == 403)
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ResponseModel(HttpStatus.FORBIDDEN.value(), "You don't have permission", "{}"));
            if (checked == 401)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ResponseModel(HttpStatus.UNAUTHORIZED.value(), "Wrong author", "{}"));


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
