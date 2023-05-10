package com.charity.hoangtrinh.controller;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.District;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.Province;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.Ward;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.AdministrativeRegionRepository;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.DistrictRepository;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.ProvinceRepository;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.WardRepository;
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
    private ProvinceRepository provinceRepository;
    @Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private WardRepository wardRepository;
    @Autowired
    private AdministrativeRegionRepository administrativeRegionRepository;

    @GetMapping("/ping")
    public ResponseEntity<ResponseModel> ping() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseModel("Hello, i am demo application run on docker. Can i help you?"));
    }

    /**
     * API lấy toàn bộ các tỉnh
     * @return danh sách các tỉnh
     */
    @GetMapping("/provinces")
    public ResponseEntity<Object> getProvinces() {
        try {
            List<Province> provincesList = provinceRepository.findAll();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(provincesList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel("INTERNAL_SERVER_ERROR"));
        }
    }

    /**
     * API lấy toàn bộ huyện trong tỉnh
     * @param provinceCode mã tỉnh
     * @return danh sách các huyện trong tỉnh
     */
    @GetMapping("/districts-in-province")
    public ResponseEntity<Object> getDistrictsOnProvince(@RequestParam(name = "province-code") String provinceCode) {
        try {
            List<District> districts = districtRepository.findByProvinceCode_IdEquals(provinceCode);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(districts);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel("INTERNAL_SERVER_ERROR"));
        }
    }

    /**
     * API lấy toàn bộ các xã trong huyện
     * @param districtCode mã huyện
     * @return danh sách các xã
     */
    @GetMapping("/wards-in-districts")
    public ResponseEntity<Object> getWardsOnDistricts(@RequestParam(name = "district-code") String districtCode) {
        try {
            List<Ward> wards = wardRepository.findByDistrictCodeEquals(districtCode);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(wards);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel("INTERNAL_SERVER_ERROR"));
        }
    }

    @GetMapping("/regions")
    public ResponseEntity<Object> getRegions() {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(administrativeRegionRepository.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel("INTERNAL_SERVER_ERROR"));
        }

    }
}
