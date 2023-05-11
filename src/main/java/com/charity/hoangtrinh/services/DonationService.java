package com.charity.hoangtrinh.services;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.Donation;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.Request;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.CharityRepository;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.RequestRepository;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.UserAccountRepository;
import com.charity.hoangtrinh.model.DonationResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DonationService {
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private CharityRepository charityRepository;

    public DonationResponse buildDonationJsonBody(Donation donation) {
        DonationResponse donationResponse = new DonationResponse();
        donationResponse.setId(donation.getId());
        donationResponse.setIdDonor(donation.getIdDonor().getId());
        donationResponse.setStatus(donation.getStatus());
        donationResponse.setOrganizationReceived(userAccountRepository.findByCharityIdEquals(donation.getOrganizationReceived().getId()).getName());
        donationResponse.setIdOrganization(donation.getOrganizationReceived().getId());
        donationResponse.setName(donation.getName());
        donationResponse.setDonationAddress(donation.getDonationAddress());
        donationResponse.setDonationObject(donation.getDonationObject());
        donationResponse.setDonorName(donation.getIdDonor().getName());
        donationResponse.setPhone(donation.getIdDonor().getName());
        donationResponse.setAddress(donation.getIdDonor().getAddress());
        donationResponse.setProvince(donation.getIdDonor().getProvince());
        donationResponse.setDistrict(donation.getIdDonor().getDistrict());
        donationResponse.setWard(donation.getIdDonor().getWard());
        donationResponse.setDate(donation.getDate());
        donationResponse.setDescription(donation.getDescription());
        donationResponse.setImages(donation.getImages());

        JSONObject request = new JSONObject();
        List<JSONObject> list = new ArrayList<>();

        for (Request r : requestRepository.findByDonationIdEquals(donation.getId())) {
            request.put("id", r.getOrganizationId());
            request.put("status", r.getStatus());
            request.put("name", userAccountRepository.findByCharityIdEquals(r.getDonationId()).getName());
            list.add(request);
        }
        donationResponse.setListRequest(list);
        return donationResponse;
    }
}
