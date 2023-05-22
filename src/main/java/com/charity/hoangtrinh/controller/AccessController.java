package com.charity.hoangtrinh.controller;

import com.charity.hoangtrinh.config.CacheConfig;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.UserAccount;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.UserAccountRepository;
import com.charity.hoangtrinh.model.ResponseModel;
import com.charity.hoangtrinh.utils.CustomLogger;
import com.charity.hoangtrinh.utils.JsonUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/charity/access")
public class AccessController {
    private final CustomLogger logger = new CustomLogger(Logger.getLogger(AccessController.class), Level.INFO, true, false);
    @Autowired
    private UserAccountRepository userAccountRepository;
    /**
     * API lấy access token
     * @param body body
     */
    @PostMapping("/token")
    public ResponseEntity<ResponseModel> postAccessToken(@RequestBody String body) {
        try {
            JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();
            Integer userId = JsonUtil.getInt(jsonBody, "user_id");
            String token = JsonUtil.getString(jsonBody,"token");

            if (userId == null || token == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseModel("Userid or token is null!"));

            Optional<UserAccount> userAccountOptional = userAccountRepository.findById(userId);
            if (!userAccountOptional.isPresent())
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseModel("Do not have user: " + userId));
            CacheConfig.accessToken.put(token, userAccountOptional.get());

            logger.info(CacheConfig.accessToken.asMap().toString());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel("Cached access token of user " + userId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel("INTERNAL_SERVER_ERROR"));
        }
    }


    //.../charity/access/get-user-id-by-organization-id
    @PostMapping("/get-user-id-by-organization-id")
    public ResponseEntity<ResponseModel> getUserIdByOrganizationId(@RequestParam(value = "organization-id") String organizationIdStr) {
        try {
            Integer organizationId = Integer.parseInt(organizationIdStr);

            UserAccount userAccount = userAccountRepository.findByCharityIdEquals(organizationId);
            if (userAccount == null)
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseModel("Don't have user by organization id " + organizationId));

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel(userAccount.getId()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel("INTERNAL_SERVER_ERROR"));
        }
    }

    @DeleteMapping("/delete-token")
    public ResponseEntity<Object> deleteToken(@RequestParam(value = "user-id") String userIdStr) {
        try {
            int userId = Integer.parseInt(userIdStr);

            Optional<UserAccount> userAccountOptional = userAccountRepository.findById(userId);
            if (!userAccountOptional.isPresent())
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseModel("Do not have user: " + userId));

            UserAccount user = userAccountOptional.get();

            int count = 0;
            for (String t : CacheConfig.accessToken.asMap().keySet()) {
                if (CacheConfig.accessToken.asMap().get(t).equals(user)) {
                    CacheConfig.accessToken.asMap().remove(t);
                    count++;
                }
            }

            logger.info(CacheConfig.accessToken.asMap().toString());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel("Removed " + count + " token of user " + userId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass() + ":" + e.getMessage()));
        }
    }
}
