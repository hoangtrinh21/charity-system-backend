package com.charity.hoangtrinh.controller;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.Message;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.UserAccount;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.MessageRepository;
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

@RestController
@RequestMapping("/charity/message")
public class MessageController {
    @Autowired
    private AccessService accessService;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;

    @GetMapping("/get-receive")
    public ResponseEntity<Object> getMessageUserReceive(@RequestHeader(value = "Token") String token) {
        try {
            UserAccount user = accessService.getUserByToken(token);

            List<Message> receiveMessages = messageRepository.findByUserIdReceiveEquals(user);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(receiveMessages);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass() + ":" + e.getMessage()));
        }
    }

    @GetMapping("/get-send")
    public ResponseEntity<Object> getMessageUserSend(@RequestHeader(value = "Token") String token) {
        try {
            UserAccount user = accessService.getUserByToken(token);

            List<Message> sendMessages = messageRepository.findByUserIdSendEquals(user);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(sendMessages);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass() + ":" + e.getMessage()));
        }
    }

    @PostMapping("/add-message")
    public ResponseEntity<Object> addMessage(@RequestHeader(value = "Token") String token,
                                             @RequestBody String body) {
        try {
            int isLogged = accessService.checkAccessToken(token);
            if (isLogged != 200)
                return ResponseEntity.status(isLogged)
                        .body(new ResponseModel(HttpStatus.valueOf(isLogged).name()));

            JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();
            String content = JsonUtil.getString(jsonBody, "content");
            Integer userIdReceive = JsonUtil.getInt(jsonBody, "user_id_receive");
            Integer userIdSend = JsonUtil.getInt(jsonBody, "user_id_send");

            if (content == null || userIdSend == null || userIdReceive == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseModel("content and user_id_receive and user_id_send must be not null!"));

            Message message = new Message();
            message.setContent(content);
            message.setUserIdReceive(userAccountRepository.getReferenceById(userIdReceive));
            message.setUserIdSend(userAccountRepository.getReferenceById(userIdSend));
            messageRepository.save(message);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(message);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass() + ":" + e.getMessage()));
        }
    }
}
