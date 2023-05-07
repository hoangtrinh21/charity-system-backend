package com.charity.hoangtrinh.controller;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.Donation;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.DonationRepository;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.RequestRepository;
import com.charity.hoangtrinh.model.ResponseModel;
import com.charity.hoangtrinh.services.AccessService;
import com.charity.hoangtrinh.services.DonationService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/charity/donation")
public class DonationController {
    @Autowired
    DonationRepository donationRepository;
    @Autowired
    private AccessService accessService;
    @Autowired
    private DonationService donationService;

    @GetMapping("/get-all")
    public ResponseEntity<Object> getAllDonation(@RequestHeader(value = "Token") String token) {
        try {
            List<Donation> donations = donationRepository.findAll();
            JSONArray response = new JSONArray();
            JSONObject object;
            for (Donation donation : donations) {
                object = donationService.buildDonationJsonBody(donation);
                response.put(object);
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel(response));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass()));
        }
    }

    @GetMapping("get-by-condition")
    public ResponseEntity<Object> getByCondition(@RequestParam Map<String, String> conditions) {
        try {
            Integer id = conditions.get("donation-id") == null ? null : Integer.valueOf(conditions.get("donation-id"));
            if (id != null) {
                Donation donation = donationRepository.getReferenceById(id);
                JSONObject object = donationService.buildDonationJsonBody(donation);
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseModel(object));
            }

            String donorName = conditions.get("donor_name");
            String name = conditions.get("name");
            String status = conditions.get("status");
            String donationAddress = conditions.get("donation_address");
            String donationObject = conditions.get("donation_object");

            return ResponseEntity.status(HttpStatus.OK)
                    .body(donationRepository.findByNameLikeAndStatusLikeAndDonationAddressLikeAndDonationObjectLikeAndIdDonor_UserNameLike(
                    name, status, donationAddress, donationObject, donorName));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass()));
        }
    }

    @PostMapping("/add-donation")
    public ResponseEntity<Object> addDonation(@RequestHeader(value = "Token") String token, @RequestBody String body) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass()));
    }
}
