package com.charity.hoangtrinh.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DonationResponse {
    private Integer id;
    private Integer idDonor;
    private String status;
    private String organizationReceived;
    private Integer idOrganization;
    private List<JSONObject> listRequest;
    private String name;
    private String donationAddress;
    private String donationObject;
    private String donorName;
    private String phone;
    private String address;
    private String province;
    private String district;
    private String ward;
    private LocalDate date;
    private String description;
    private String images;
}
