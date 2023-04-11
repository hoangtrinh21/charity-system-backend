package com.charity.hoangtrinh.controller;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.ProvincesEntity;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.WardsEntity;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.DistrictsRepository;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.ProvincesRepository;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.WardsRepository;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.DistrictsEntity;
import com.charity.hoangtrinh.model.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/charity/address")
@RestController
public class AddressController {
    @Autowired
    private ProvincesRepository provincesRepository;
    @Autowired
    private DistrictsRepository districtsRepository;
    @Autowired
    private WardsRepository wardsRepository;
    @GetMapping("/ping")
    public ResponseEntity<ResponseModel> ping() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseModel(HttpStatus.OK.value(),
                        "Hello, i am demo application run on docker. Can i help you?",
                        ""));
    }

    @GetMapping("/provinces")
    public ResponseEntity<ResponseModel> getProvinces() {
        try {
            List<ProvincesEntity> provincesList = provincesRepository.findAll();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel(HttpStatus.OK.value(),
                            "Have " + provincesList.size() + " provinces",
                            provincesList));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "INTERNAL_SERVER_ERROR",
                            "{}"));
        }
    }

    @GetMapping("/districts-in-province")
    public ResponseEntity<ResponseModel> getDistrictsOnProvince(@RequestParam(name = "province-code") String provinceCode) {
        try {
            List<DistrictsEntity> districts = districtsRepository.findByProvinceCode(provinceCode);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel(HttpStatus.OK.value(),
                            "Have " + districts.size() + " districts",
                            districts));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "INTERNAL_SERVER_ERROR",
                            "{}"));
        }
    }

    @GetMapping("/wards-in-districts")
    public ResponseEntity<ResponseModel> getWardsOnDistricts(@RequestParam(name = "district-code") String districtCode) {
        try {
            List<WardsEntity> wards = wardsRepository.findByDistrictCode(districtCode);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel(HttpStatus.OK.value(),
                            "Have " + wards.size(),
                            wards));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL_SERVER_ERROR", "{}"));
        }
    }
}