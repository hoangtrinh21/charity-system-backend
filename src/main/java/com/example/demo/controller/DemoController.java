package com.example.demo.controller;

import com.example.demo.dbs.sql.charitydatabase.entities.DistrictsEntity;
import com.example.demo.dbs.sql.charitydatabase.entities.ProvincesEntity;
import com.example.demo.dbs.sql.charitydatabase.entities.WardsEntity;
import com.example.demo.dbs.sql.charitydatabase.repositories.DistrictsRepository;
import com.example.demo.dbs.sql.charitydatabase.repositories.ProvincesRepository;
import com.example.demo.dbs.sql.charitydatabase.repositories.WardsRepository;
import com.example.demo.model.ResponseModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/charity")
@RestController
public class DemoController {
    @Autowired
    private ProvincesRepository provincesRepository;
    @Autowired
    private DistrictsRepository districtsRepository;
    @Autowired
    private WardsRepository wardsRepository;
    @GetMapping("/ping")
    public ResponseEntity<ResponseModel> ping() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseModel(HttpStatus.OK.value(), "Hello, i am demo application run on docker. Can i help you?", "{}"));
    }

    @GetMapping("/provinces")
    public ResponseEntity<ResponseModel> getProvinces() {
        try {
            List<ProvincesEntity> provincesList = provincesRepository.findAll();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel(HttpStatus.OK.value(), "Request success", provincesList));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL_SERVER_ERROR", "{}"));
        }
    }

    @GetMapping("/districts-on-province")
    public ResponseEntity<ResponseModel> getDistrictsOnProvince(@RequestParam(name = "province-code") String provinceCode) {
        try {
            List<DistrictsEntity> districts = districtsRepository.findByProvinceCode(provinceCode);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel(HttpStatus.OK.value(), "Request success", districts));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL_SERVER_ERROR", "{}"));
        }
    }

    @GetMapping("/wards-on-districts")
    public ResponseEntity<ResponseModel> getWardsOnDistricts(@RequestParam(name = "district-code") String districtCode) {
        try {
            List<WardsEntity> districts = wardsRepository.findByDistrictCode(districtCode);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel(HttpStatus.OK.value(), "Request success", districts));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL_SERVER_ERROR", "{}"));
        }
    }
}
