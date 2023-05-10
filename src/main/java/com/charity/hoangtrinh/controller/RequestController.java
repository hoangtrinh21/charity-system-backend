package com.charity.hoangtrinh.controller;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.Charity;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.Donation;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.Request;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.UserAccount;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.CharityRepository;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.DonationRepository;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.RequestRepository;
import com.charity.hoangtrinh.model.ResponseModel;
import com.charity.hoangtrinh.services.AccessService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/charity/request")
public class RequestController {
    @Autowired
    private AccessService accessService;
    @Autowired
    private CharityRepository charityRepository;
    @Autowired
    DonationRepository donationRepository;
    @Autowired
    private RequestRepository requestRepository;

    @GetMapping("/get-all-of-donation")
    public ResponseEntity<Object> getAllRequestOfDonation(@RequestHeader(value = "Token") String token,
                                                @RequestParam(value = "donation-id") String donationIdStr) {
        try {
            int donationId = Integer.parseInt(donationIdStr);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(requestRepository.findByDonationIdEquals(donationId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass()));
        }
    }

    @GetMapping("/get-all-of-organization")
    public ResponseEntity<Object> getAllRequestOfOrganization(@RequestHeader(value = "Token") String token,
                                                @RequestParam(value = "organization-id") String organizationIdStr) {
        try {
            int organizationId = Integer.parseInt(organizationIdStr);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(requestRepository.findByOrganizationIdEquals(organizationId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass()));
        }
    }

    @PostMapping("/add-request")
    public ResponseEntity<Object> addRequest(@RequestHeader(value = "Token") String token, @RequestBody String body) {
        try {
            boolean isOrganization = accessService.isOrganization(token);
            if (!isOrganization)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseModel("You are not organization!"));
            JSONObject jsonBody = new JSONObject(body);

            Charity organization = charityRepository
                    .getReferenceById(accessService.getUserByToken(token).getCharityId());

            int donationId = jsonBody.getInt("donationId");
            Donation donation = donationRepository.getReferenceById(donationId);

            Request request = new Request();
            request.setDonationId(donationId);
            request.setOrganizationId(organization.getId());
            request.setStatus("Đợi xác nhận");
            requestRepository.save(request);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Added request!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(e.getClass()));
        }
    }
}
