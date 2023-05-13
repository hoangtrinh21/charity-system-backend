package com.charity.hoangtrinh.dbs.sql.charitydatabase.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "user_account")
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @Column(name = "Name")
    private String name;

    @Column(name = "UserName")
    private String userName;

    @Column(name = "PhoneNumber", length = 20)
    private String phoneNumber;

    @Column(name = "Password")
    private String password;

    @Column(name = "SaltPassword")
    private String saltPassword;

    @Column(name = "RoleId")
    private Integer roleId;

    @Column(name = "Address")
    private String address;

    @Column(name = "Email", length = 50)
    private String email;

    @Column(name = "IsLocked")
    private Byte isLocked;

    @Column(name = "CharityId")
    private Integer charityId;

    @Column(name = "Province", length = 50)
    private String province;

    @Column(name = "District", length = 50)
    private String district;

    @Column(name = "Ward", length = 50)
    private String ward;

    @Column(name = "ProvinceId", length = 10)
    private String provinceId;

    @Column(name = "DistrictId", length = 10)
    private String districtId;

    @Column(name = "WardId", length = 10)
    private String wardId;

}