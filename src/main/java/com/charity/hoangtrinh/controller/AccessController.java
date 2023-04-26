package com.charity.hoangtrinh.controller;

import com.charity.hoangtrinh.config.CacheConfig;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.User;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.UserRepository;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/charity/access")
public class AccessController {
    private final CustomLogger logger = new CustomLogger(Logger.getLogger(AccessController.class), Level.INFO, true, false);
    @Autowired
    private UserRepository userRepository;
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
                        .body(new ResponseModel(HttpStatus.BAD_REQUEST.value(),
                                "Userid or token is null!",
                                "{}"));

            Optional<User> userOptional = userRepository.findById(userId);
            if (!userOptional.isPresent())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseModel(HttpStatus.BAD_REQUEST.value(), "Not found user in database", "{}"));
            User user = userOptional.get();
            CacheConfig.accessToken.put(token, user);

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
