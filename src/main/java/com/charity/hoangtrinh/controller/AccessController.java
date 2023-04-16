package com.charity.hoangtrinh.controller;

import com.charity.hoangtrinh.config.CacheConfig;
import com.charity.hoangtrinh.model.ResponseModel;
import com.charity.hoangtrinh.utils.CustomLogger;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/charity/access")
public class AccessController {
    private CustomLogger logger = new CustomLogger(Logger.getLogger(AccessController.class), Level.INFO, true, false);
    /**
     * API lấy access token
     * @param body body
     */
    @PostMapping("/token")
    public ResponseEntity<ResponseModel> postAccessToken(@RequestBody String body) {
        try {
            JSONObject jsonBody = new JSONObject(body);
            int userId = jsonBody.getInt("user_id");
            String token = jsonBody.getString("token");
            CacheConfig.accessToken.put(userId, token);

            logger.info(CacheConfig.accessToken.asMap().toString());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel(HttpStatus.OK.value(),
                            "Cached access token of user " + userId,
                            "{}"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL_SERVER_ERROR", "{}"));
        }
    }
}
