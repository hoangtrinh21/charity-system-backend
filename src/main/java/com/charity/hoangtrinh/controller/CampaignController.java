package com.charity.hoangtrinh.controller;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.CampaignInfo;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.Province;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.CampaignInfoRepository;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.CampaignInputRepository;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.CampaignOutputRepository;
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

@RestController
@RequestMapping("/charity/campaign")
public class CampaignController {
    @Autowired
    CampaignInfoRepository campaignInfoRepository;
    @Autowired
    CampaignInputRepository campaignInputRepository;
    @Autowired
    CampaignOutputRepository campaignOutputRepository;
    @GetMapping("/get-all")
    public ResponseEntity<ResponseModel> getAllCampaigns() {
        try {
            List<CampaignInfo> campaignInfos = campaignInfoRepository.findAll();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel(HttpStatus.OK.value(),
                            "Have " + campaignInfos.size() + " campaign",
                            campaignInfos));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "INTERNAL_SERVER_ERROR",
                            "{}"));
        }
    }

    @GetMapping("/get-by-organization")
    public ResponseEntity<ResponseModel> getByOrganization(@RequestParam(value = "organization-id") int organizationId) {
        try {
            List<CampaignInfo> campaignInfos = campaignInfoRepository.findByOrganizationIdEquals(organizationId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel(HttpStatus.OK.value(),
                            "Have " + campaignInfos.size() + " campaign",
                            campaignInfos));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "INTERNAL_SERVER_ERROR",
                            "{}"));
        }
    }

    @GetMapping("/get")
    public ResponseEntity<ResponseModel> getById(@RequestParam(value = "id") int id) {
        try {
            Optional<CampaignInfo> campaignInfoOptional = campaignInfoRepository.findById(id);
            return campaignInfoOptional.map(campaignInfo -> ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel(HttpStatus.OK.value(),
                            "Have a campaign",
                            campaignInfo))).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseModel(HttpStatus.NOT_FOUND.value(),
                            "Don't have campaign",
                            "{}")));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "INTERNAL_SERVER_ERROR",
                            "{}"));
        }
    }

//    @GetMapping("/get")
//    public ResponseEntity<ResponseModel> getById(@RequestParam(value = "id") int id) {
//        try {
//            Optional<CampaignInfo> campaignInfoOptional = campaignInfoRepository.findById(id);
//            return campaignInfoOptional.map(campaignInfo -> ResponseEntity.status(HttpStatus.OK)
//                    .body(new ResponseModel(HttpStatus.OK.value(),
//                            "Have a campaign",
//                            campaignInfo))).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(new ResponseModel(HttpStatus.NOT_FOUND.value(),
//                            "Don't have campaign",
//                            "{}")));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                            "INTERNAL_SERVER_ERROR",
//                            "{}"));
//        }
//    }
}
