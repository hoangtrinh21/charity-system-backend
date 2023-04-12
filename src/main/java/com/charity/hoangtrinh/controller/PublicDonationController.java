package com.charity.hoangtrinh.controller;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.PublicDonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/charity/public-donation")
@RestController
public class PublicDonationController {
    @Autowired
    private PublicDonationRepository publicDonationRepository;

}
