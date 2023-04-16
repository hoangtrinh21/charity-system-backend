package com.charity.hoangtrinh.controller;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.User;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.RoleRepository;
import com.charity.hoangtrinh.dbs.sql.charitydatabase.repositories.UserRepository;
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

@RequestMapping("/charity/organization")
@RestController
public class OrganizationController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    /**
     * API lấy toàn bộ tổ chức từ thiện
     */
    @GetMapping("/get-all")
    public ResponseEntity<ResponseModel> getAllOrganization() {
        try {
            List<User> organizations = userRepository.findByRole_IdEquals(2);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel(HttpStatus.OK.value(),
                            "Have " + organizations.size() + " public donation!",
                            organizations));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseModel(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "INTERNAL_SERVER_ERROR",
                            "{}"));
        }
    }

    /**
     * API lấy  chi tiết tổ chức từ thiện
     * @param organizationId id của tổ chức
     */
    @GetMapping("/get")
    public ResponseEntity<ResponseModel> getOrganization(@RequestParam(value = "organization-id") int organizationId) {
        try {
            Optional<User> organization = userRepository.findById(organizationId);
            return organization.map(o -> ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseModel(HttpStatus.OK.value(),
                            "Have organization donation!",
                            o))).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseModel(HttpStatus.NOT_FOUND.value(),
                            "Not found organization donation!",
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
