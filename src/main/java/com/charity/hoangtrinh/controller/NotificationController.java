package com.charity.hoangtrinh.controller;

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

import java.time.LocalDate;
import java.time.LocalDateTime;
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

//    @GetMapping("/get-sent-by-user")
//    public ResponseEntity<Object> getSendByUser(@RequestHeader(value = "Token") String token) {
//        try {
//            if (accessService.checkAccessToken(token) != 200)
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                        .body(new ResponseModel("UNAUTHORIZED"));
//
//            UserAccount userCreate = accessService.getUserByToken(token);
//            List<Notification> notifications = notificationRepository.findByCreatedUserEquals(userCreate);
//            return ResponseEntity.status(HttpStatus.OK)
//                    .body(notifications);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ResponseModel(e.getClass() + ":" + e.getMessage()));
//        }
//    }

    /**
     * lấy thông báo người dùng được nhận
     * @param token token của người dùng dudwcj nhận
     */
    @GetMapping("/get-receive-by-user")
    public ResponseEntity<Object> getReceiveByUser(@RequestHeader(value = "Token") String token) {
        try {
            if (accessService.checkAccessToken(token) != 200)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ResponseModel("UNAUTHORIZED"));

            UserAccount userReceive = accessService.getUserByToken(token);
            List<Notification> notifications = notificationRepository.findByReceiveUserEquals(userReceive);
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
            int receiveUserId = jsonBody.get("receive_user_id").getAsInt();
            int createdUserId = jsonBody.get("created_user_id").getAsInt();
            String message = jsonBody.get("message").getAsString();

            Optional<UserAccount> userCreatedOptional = userAccountRepository.findById(createdUserId);
            if (!userCreatedOptional.isPresent())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseModel("Don't have user with id " + createdUserId));

            Optional<UserAccount> userReceiveOptional = userAccountRepository.findById(receiveUserId);
            if (!userReceiveOptional.isPresent())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseModel("Don't have user with id " + receiveUserId));

            Notification notification = new Notification();
            notification.setCreatedUser(userCreatedOptional.get());
            notification.setReceiveUser(userReceiveOptional.get());
            notification.setMessage(message);
            notification.setTimeCreate(LocalDateTime.now().toString());
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
