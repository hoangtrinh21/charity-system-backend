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
import com.google.gson.*;
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
import java.util.*;

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
            for (Donation d : donations) {
                if (d.getIdOrganization() == null) continue;
                UserAccount userAccount = userAccountRepository.findByCharityIdEquals(d.getIdOrganization());
                if (userAccount != null)
                    d.setOrganizationReceived(userAccountRepository.findByCharityIdEquals(d.getIdOrganization()).getName());
            }
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
            donation.setOrganizationReceived(userAccountRepository.findByCharityIdEquals(donation.getIdOrganization()).getName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(donation);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass()));
        }
    }

    @GetMapping("/get-by-donor")
    public ResponseEntity<Object> getByDonor(@RequestParam(value = "donor-id") String donorIdStr) {
        try {
            Integer id = Integer.parseInt(donorIdStr);

            UserAccount donor = userAccountRepository.getReferenceById(id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(donationRepository.findByIdDonorEquals(donor.getId()));
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
            System.out.println(jsonBody);

//            Integer idDonor         = Integer.valueOf(jsonBody.get("idDonor").getAsString());
            Integer idDonor         = jsonBody.get("idDonor").getAsInt();
            String status           = jsonBody.get("status").getAsString();
            Integer idOrganization = jsonBody.get("idOrganization") == null ? null : jsonBody.get("idOrganization").getAsInt();
//            try {
//                idOrganization = jsonBody.get("idOrganization").getAsInt();
//            } catch (UnsupportedOperationException e) {
//                // do nothing
//            }
            String name             = jsonBody.get("name").getAsString();
            String donationAddress  = jsonBody.get("donationAddress").getAsString();
            String donationObject   = jsonBody.get("donationObject").getAsString();
            String date             = jsonBody.get("date").getAsString();
            String description      = jsonBody.get("description").getAsString();
            String images           = jsonBody.get("images").getAsString();
            JsonArray listRequest   = jsonBody.get("listRequest").getAsJsonArray();

            UserAccount donor = userAccountRepository.getReferenceById(idDonor);

            Donation donation = new Donation();
            donation.setIdDonor(idDonor);
            donation.setStatus(status);

            if (idOrganization != null) {
                Charity organizationReceived = charityRepository.getReferenceById(idOrganization);
                donation.setOrganizationReceived(userAccountRepository.findByCharityIdEquals(organizationReceived.getId()).getName());
                donation.setIdOrganization(organizationReceived.getId());
            }

            System.out.println(listRequest);
            JSONArray jsonArray = new JSONArray(listRequest.toString());

            donation.setListRequest(jsonArray);

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
            boolean isDonorOrOrganization = accessService.isDonor(token) || accessService.isOrganization(token);
            if (!isDonorOrOrganization)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseModel("You are not donor or organization!"));

            JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();

            Integer id = jsonBody.get("id").getAsInt();
            Integer idDonor = jsonBody.get("idDonor") == null ? null : jsonBody.get("idDonor").getAsInt();
            String status   = jsonBody.get("status").getAsString();
            Integer idOrganization = jsonBody.get("idOrganization") == null ? null : jsonBody.get("idOrganization").getAsInt();
            String name = jsonBody.get("name").getAsString();
            String donationAddress = jsonBody.get("donationAddress").getAsString();
            String donationObject = jsonBody.get("donationObject").getAsString();
            String date = jsonBody.get("date").getAsString();
            String description = jsonBody.get("description").getAsString();
            String images = jsonBody.get("images").getAsString();
            JsonArray listRequest = jsonBody.get("listRequest").getAsJsonArray();


            Optional<Donation> donationOptional = donationRepository.findById(id);
            if (!donationOptional.isPresent())
                return ResponseEntity.status(HttpStatus.OK)
                                .body(new ResponseModel("Not found donation by id " + id));

            Donation donation = donationOptional.get();

            if (idDonor != null) {
                UserAccount donor = userAccountRepository.getReferenceById(idDonor);
                donation.setIdDonor(idDonor);
                donation.setDonorName(donor.getName());
                donation.setPhone(donor.getPhoneNumber());
                donation.setAddress(donor.getAddress());
                donation.setProvince(donor.getProvince());
                donation.setDistrict(donor.getDistrict());
                donation.setWard(donor.getWard());
            }

            donation.setStatus(status);

            if (idOrganization != null) {
                Charity organizationReceived = charityRepository.getReferenceById(idOrganization);
                donation.setOrganizationReceived(organizationReceived.getCharityName());
                donation.setIdOrganization(organizationReceived.getId());
            }

            System.out.println(listRequest);
            JSONArray jsonArray = new JSONArray(listRequest.toString());

            donation.setListRequest(jsonArray);

            donation.setName(name);
            donation.setDonationAddress(donationAddress);
            donation.setDonationObject(donationObject);
            donation.setDate(date);
            donation.setDescription(description);
            donation.setImages(images);

            donationRepository.save(donation);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(donation);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass()));
        }
    }

    @DeleteMapping("/delete-donation")
    public ResponseEntity<Object> deleteCampaign(@RequestHeader(value = "Token") String token,
                                                 @RequestParam(value = "id") String idStr) {
        try {
            boolean isDonor = accessService.isDonor(token);
            if (!isDonor)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseModel("You are not donor!"));

            Integer donationId = Integer.parseInt(idStr);

            donationRepository.deleteById(donationId);

            return ResponseEntity.status(HttpStatus.OK)
                    .body("Deleted donation!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass()));
        }
    }

    @PutMapping("/donor-delete-donation-update-request")
    public ResponseEntity<Object> donorDeleteRequestDonation(@RequestHeader(value = "Token") String token,
                                                             @RequestParam(value = "id") String idStr,
                                                             @RequestBody String body) {
        try {
            boolean isDonor = accessService.isDonor(token);
            if (!isDonor)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseModel("You are not donor!"));

            Integer donationId = Integer.parseInt(idStr);

            Donation donation = donationRepository.getReferenceById(donationId);
            Integer idDonor  = donation.getIdDonor();

            if (!Objects.equals(idDonor, accessService.getUserByToken(token).getId()))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseModel("You are not permission this donation!"));

            JSONArray requestsJson = new JSONArray(body);

            donation.setIdDonor(null);

            donation.setListRequest(requestsJson);
            donationRepository.save(donation);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(donation);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass()));
        }
    }

    @DeleteMapping("/donor-delete-donation")
    public ResponseEntity<Object> donorDeleteDonation(@RequestHeader(value = "Token") String token,
                                                      @RequestParam(value = "id") String idStr) {
        try {
            boolean isDonor = accessService.isDonor(token);
            if (!isDonor)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseModel("You are not donor!"));

            Integer donationId = Integer.parseInt(idStr);

            Donation donation = donationRepository.getReferenceById(donationId);
            Integer idDonor  = donation.getIdDonor();

            if (!Objects.equals(idDonor, accessService.getUserByToken(token).getId()))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseModel("You are not permission this donation!"));

            donation.setIdDonor(null);
            donationRepository.save(donation);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(donation);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass()));
        }
    }

}
