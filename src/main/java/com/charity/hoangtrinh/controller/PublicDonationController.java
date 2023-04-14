package com.charity.hoangtrinh.controller;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.PublicDonation;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.PublicDonationRepository;
import com.charity.hoangtrinh.model.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequestMapping("/charity/public-donation")
@RestController
public class PublicDonationController {
    @Autowired
    private PublicDonationRepository publicDonationRepository;

    @GetMapping("/get-all")
    public ResponseEntity<ResponseModel> getAllPublicDonation() {
        try {
            List<PublicDonation> publicDonations = publicDonationRepository.findAll();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel(HttpStatus.OK.value(),
                            "Have " + publicDonations.size() + " public donation!",
                            publicDonations));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "INTERNAL_SERVER_ERROR",
                            "{}"));
        }
    }

    @GetMapping("/get")
    public ResponseEntity<ResponseModel> getPublicDonation(@RequestParam(name = "public-donation-id") int publicDonationId) {
        try {
            Optional<PublicDonation> publicDonationOptional = publicDonationRepository.findById(publicDonationId);
            return publicDonationOptional.map(publicDonation -> ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel(HttpStatus.OK.value(),
                            "Have public donation!",
                            publicDonation))).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseModel(HttpStatus.NOT_FOUND.value(),
                            "Not found public donation!",
                            "{}")));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "INTERNAL_SERVER_ERROR",
                            "{}"));
        }
    }

}
