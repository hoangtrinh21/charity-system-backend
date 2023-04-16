package com.charity.hoangtrinh.controller;

import com.charity.hoangtrinh.config.CacheConfig;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.User;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.UserRepository;
import com.charity.hoangtrinh.model.ResponseModel;
import com.charity.hoangtrinh.utils.CustomLogger;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONObject;
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
            JSONObject jsonBody = new JSONObject(body);
            int userId = jsonBody.getInt("user_id");
            String token = jsonBody.getString("token");
            Optional<User> userOptional = userRepository.findById(userId);
            if (!userOptional.isPresent())
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseModel(HttpStatus.NOT_FOUND.value(), "Not found user", "{}"));
            String role = userOptional.get().getRole().getRoleName();
            Map<String, String> roleAndToken = new ConcurrentHashMap<>();
            roleAndToken.put("role", role);
            roleAndToken.put("token", token);
            CacheConfig.accessToken.put(userId, roleAndToken);

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
