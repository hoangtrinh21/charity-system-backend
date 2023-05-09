package com.charity.hoangtrinh.services;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.Donation;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.RequestRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DonationService {
    @Autowired
    private RequestRepository requestRepository;
    public JSONObject buildDonationJsonBody(Donation donation) {
        JSONObject object = new JSONObject();
        object.put("id", String.valueOf(donation.getId()));
        object.put("idDonor", String.valueOf(donation.getIdDonor().getId()));
        object.put("organizationReceived", donation.getOrganizationReceived());
        object.put("name", donation.getName());
        object.put("donationAddress", donation.getDonationAddress());
        object.put("donationObject", donation.getDonationObject());
        object.put("donorName", donation.getIdDonor().getUserName());
        object.put("phone", donation.getIdDonor().getPhoneNumber());
        object.put("address", donation.getIdDonor().getAddress());
        object.put("province", donation.getIdDonor().getAddress());
        object.put("district", donation.getIdDonor().getAddress());
        object.put("ward", donation.getIdDonor().getAddress());
        object.put("date", String.valueOf(donation.getDate()));
        object.put("description", donation.getDescription());
        object.put("images", donation.getImages());
        object.put("request", requestRepository.findByDonation_IdEquals(donation.getId()));

        return object;
    }
}
