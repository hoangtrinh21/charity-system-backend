package com.charity.hoangtrinh.controller;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.Charity;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.Donation;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.Request;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.UserAccount;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.CharityRepository;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.DonationRepository;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.RequestRepository;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.UserAccountRepository;
import com.charity.hoangtrinh.model.ResponseModel;
import com.charity.hoangtrinh.services.AccessService;
import com.charity.hoangtrinh.services.DonationService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public Object getAllDonation(@RequestHeader(value = "Token") String token) {
        try {

            List<Donation> donations = donationRepository.findAll();
            List<JSONObject> response = new ArrayList<>();
            JSONObject object;
            for (Donation donation : donations) {
                object = donationService.buildDonationJsonBody(donation);
                response.add(object);
            }
            JSONArray array = new JSONArray(response);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(array.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass()));
        }
    }

    @GetMapping("get-by-id")
    public ResponseEntity<Object> getByCondition(@RequestParam Map<String, String> conditions) {
        try {
            Integer id = conditions.get("donation-id") == null ? null : Integer.valueOf(conditions.get("donation-id"));
            if (id != null) {
                Donation donation = donationRepository.getReferenceById(id);
                JSONObject object = donationService.buildDonationJsonBody(donation);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(object.toString());
            }

            String donorName = conditions.get("donor-name");
            String name = conditions.get("name");
            String status = conditions.get("status");
            String donationAddress = conditions.get("donation-address");
            String donationObject = conditions.get("donation-object");

            List<Donation> donations = donationRepository.findByIdDonor_NameLikeAndNameLikeAndStatusLikeAndDonationAddressLikeAndDonationObjectLike(
                    donorName, name, status, donationAddress, donationObject);

            List<JSONObject> response = new ArrayList<>();
            JSONObject object;
            for (Donation donation : donations) {
                object = donationService.buildDonationJsonBody(donation);
                response.add(object);
            }
            JSONArray array = new JSONArray(response);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(array.toList());
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
            JSONObject jsonBody = new JSONObject(body);

            UserAccount donor = accessService.getUserByToken(token);

            String name = jsonBody.getString("name");
            String donationAddress = jsonBody.getString("donationAddress");
            String donationObject = jsonBody.getString("donationObject");
            String description = jsonBody.getString("description");
            String images = jsonBody.getString("images");

//            Donation donation = new Donation(LocalDate.now(), description, donationAddress, donationObject, images, name, "Chưa nhận", donor);
            Donation donation = new Donation();
            donation.setName(name);
            donation.setDate(LocalDate.now());
            donation.setDescription(description);
            donation.setDonationAddress(donationAddress);
            donation.setDonationObject(donationObject);
            donation.setImages(images);
            donation.setStatus("Chưa nhận");
            donation.setIdDonor(donor);

            donationRepository.save(donation);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel("Inserted donation!"));
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
            System.out.println(donation.getIdDonor());

            if (!Objects.equals(donation.getIdDonor(), donor))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseModel("You are not permission!"));

            donation.setName(jsonBody.get("name").getAsString());
            donation.setStatus(jsonBody.get("status").getAsString());
            donation.setDonationAddress(jsonBody.get("donationAddress").getAsString());
            donation.setDonationObject(jsonBody.get("donationObject").getAsString());
            donation.setDescription(jsonBody.get("description").getAsString());
            donation.setImages(jsonBody.get("images").getAsString());
            JsonElement organizationIdElement = jsonBody.get("organizationId");
            if (organizationIdElement != null)
                donation.setOrganizationReceived(charityRepository.getReferenceById(organizationIdElement.getAsInt()));

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
