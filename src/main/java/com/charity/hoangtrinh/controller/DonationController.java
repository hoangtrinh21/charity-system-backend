package com.charity.hoangtrinh.controller;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.Charity;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.Donation;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.Request;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.UserAccount;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.CharityRepository;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.DonationRepository;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.RequestRepository;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.UserAccountRepository;
import com.charity.hoangtrinh.model.DonationResponse;
import com.charity.hoangtrinh.model.ResponseModel;
import com.charity.hoangtrinh.services.AccessService;
import com.charity.hoangtrinh.services.DonationService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.rowset.serial.SerialBlob;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/charity/donation")
public class DonationController {
    @Autowired
    DonationRepository donationRepository;
    @Autowired
    private AccessService accessService;
    @Autowired
    private DonationService donationService;
    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private CharityRepository charityRepository;

    @GetMapping("/get-all")
    public Object getAllDonation() {
        try {
            List<Donation> donations = donationRepository.findAll();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(donations);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass()));
        }
    }

    @GetMapping("get-by-id")
    public ResponseEntity<Object> getById(@RequestParam(value = "donation-id") String donationIdStr) {
        try {
            Integer id = Integer.parseInt(donationIdStr);

            Donation donation = donationRepository.getReferenceById(id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(donation);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass()));
        }
    }

    @PostMapping("/add-donation")
    public ResponseEntity<Object> addDonation(@RequestHeader(value = "Token") String token, @RequestBody String body) {
        try {
            boolean isDonor = accessService.isDonor(token);
            if (!isDonor)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseModel("You are not donor!"));

            JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();

            Integer idDonor  = Integer.valueOf(jsonBody.get("idDonor").getAsString());
            String status   = jsonBody.get("status").getAsString();
            String idOrganization       = jsonBody.get("idOrganization").getAsString();
            String name = jsonBody.get("name").getAsString();
            String donationAddress = jsonBody.get("donationAddress").getAsString();
            String donationObject = jsonBody.get("donationObject").getAsString();
            String date = jsonBody.get("date").getAsString();
            String description = jsonBody.get("description").getAsString();
            String images = jsonBody.get("images").getAsString();
            JsonArray listRequest = jsonBody.get("listRequest").getAsJsonArray();

            UserAccount donor = userAccountRepository.getReferenceById(idDonor);

            Donation donation = new Donation();
            donation.setIdDonor(idDonor);
            donation.setStatus(status);

            if (idOrganization != null) {
                UserAccount organizationReceived = userAccountRepository.getReferenceById(Integer.valueOf(idOrganization));
                donation.setOrganizationReceived(organizationReceived.getName());
                donation.setIdOrganization(organizationReceived.getCharityId());
            }

            donation.setListRequest(new JSONArray(listRequest.toString()));

            donation.setName(name);
            donation.setDonationAddress(donationAddress);
            donation.setDonationObject(donationObject);
            donation.setDonorName(donor.getName());
            donation.setPhone(donor.getPhoneNumber());
            donation.setAddress(donor.getAddress());
            donation.setProvince(donor.getProvince());
            donation.setDistrict(donor.getDistrict());
            donation.setWard(donor.getWard());
            donation.setDate(date);
            donation.setDescription(description);
            donation.setImages(images);

            donationRepository.save(donation);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(donation);
        } catch (Exception e) {
            System.out.println("error");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass()));
        }
    }

    @PutMapping("/update-donation")
    public ResponseEntity<Object> updateDonation(@RequestHeader(value = "Token") String token, @RequestBody String body) {
        try {
            boolean isDonor = accessService.isDonor(token);
            if (!isDonor)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseModel("You are not donor!"));
            JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();

            UserAccount donor = accessService.getUserByToken(token);

            int donationId = jsonBody.get("donationId").getAsInt();
            Donation donation = donationRepository.getReferenceById(donationId);
            System.out.println(donor);
//            System.out.println(donation.getIdDonor());

//            if (!Objects.equals(donation.getIdDonor(), donor))
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                        .body(new ResponseModel("You are not permission!"));

            donation.setName(jsonBody.get("name").getAsString());
            donation.setStatus(jsonBody.get("status").getAsString());
            donation.setDonationAddress(jsonBody.get("donationAddress").getAsString());
            donation.setDonationObject(jsonBody.get("donationObject").getAsString());
            donation.setDescription(jsonBody.get("description").getAsString());
            donation.setImages(jsonBody.get("images").getAsString());
            JsonElement organizationIdElement = jsonBody.get("organizationId");
//            if (organizationIdElement != null)
//                donation.setOrganizationReceived(charityRepository.getReferenceById(organizationIdElement.getAsInt()));

            donationRepository.save(donation);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Updated donation!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass()));
        }
    }

}
