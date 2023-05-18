package com.charity.hoangtrinh.controller;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.Feedback;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.Message;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.UserAccount;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.FeedbackRepository;
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
@RequestMapping("/charity/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private AccessService accessService;
    @Autowired
    private UserAccountRepository userAccountRepository;

    @GetMapping("/get-feedback")
    public ResponseEntity<Object> getFeedback(@RequestHeader(value = "Token") String token) {
        try {
            if (!accessService.isAdmin(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ResponseModel("You are not admin!"));
            }
            List<Feedback> feedbackList = feedbackRepository.findAll();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(feedbackList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass() + ":" + e.getMessage()));
        }
    }

    @PostMapping("/add-feedback")
    public ResponseEntity<Object> addFeedback(@RequestHeader(value = "Token") String token,
                                              @RequestBody String body) {
        try {
            if (!accessService.isDonor(token) && !accessService.isOrganization(token))
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ResponseModel("You are not donor or organization!"));

            UserAccount user = accessService.getUserByToken(token);

            JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();

            String message = jsonBody.get("message").getAsString();

            Feedback feedback = new Feedback();
            feedback.setMessage(message);
            feedback.setUserAccount(user);
            feedbackRepository.save(feedback);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(feedback);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass() + ":" + e.getMessage()));
        }
    }

    @GetMapping("/get-reply")
    public ResponseEntity<Object> getReply(@RequestHeader(value = "Token") String token) {
        try {
            System.out.println(accessService.getUserByToken(token));
            if (!accessService.isDonor(token) && !accessService.isOrganization(token))
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ResponseModel("You are not donor or organization!"));

            UserAccount user = accessService.getUserByToken(token);
            List<Feedback> replyList = feedbackRepository.findByUserAccountEquals(user);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(replyList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass() + ":" + e.getMessage()));
        }
    }

    @PostMapping("/add-reply")
    public ResponseEntity<Object> addReply(@RequestHeader(value = "Token") String token,
                                           @RequestBody String body) {
        try {
            if (!accessService.isAdmin(token))
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ResponseModel("You are not admin!"));

            JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();

            int feedbackId = jsonBody.get("feedback_id").getAsInt();
            String mess = jsonBody.get("message").getAsString();


            Feedback reply = feedbackRepository.getReferenceById(feedbackId);
            reply.setReply(mess);
            feedbackRepository.save(reply);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(reply);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass() + ":" + e.getMessage()));
        }
    }
}
