package com.charity.hoangtrinh.controller;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.Feedback;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.Notification;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.UserAccount;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.NotificationRepository;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.UserAccountRepository;
import com.charity.hoangtrinh.model.ResponseModel;
import com.charity.hoangtrinh.services.AccessService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/charity/notification")
public class NotificationController {
    @Autowired
    private AccessService accessService;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;

    @GetMapping("/get-by-user")
    public ResponseEntity<Object> getByUser(@RequestHeader(value = "Token") String token) {
        try {
            if (accessService.checkAccessToken(token) != 200)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ResponseModel("UNAUTHORIZED"));

            UserAccount user = accessService.getUserByToken(token);
            List<Notification> notifications = notificationRepository.findByUserEquals(user);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(notifications);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass() + ":" + e.getMessage()));
        }
    }

    @PostMapping("/post-to-user")
    public ResponseEntity<Object> postToUser(@RequestBody String body) {
        try {
            JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();
            int userId = jsonBody.get("user_id").getAsInt();
            String message = jsonBody.get("message").getAsString();
            Optional<UserAccount> userAccountOptional = userAccountRepository.findById(Integer.valueOf(userId));
            if (!userAccountOptional.isPresent())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseModel("Don't have user with id " + userId));

            Notification notification = new Notification();
            notification.setUser(userAccountOptional.get());
            notification.setMessage(message);
            notificationRepository.save(notification);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(notification);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass() + ":" + e.getMessage()));
        }
    }
}
