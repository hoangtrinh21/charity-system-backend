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

    @Column(name = "UserName")
    private String userName;

    @Column(name = "Password")
    private String password;

    @Column(name = "SaltPassword")
    private String saltPassword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RoleId")
    private Role role;

    @Column(name = "Address")
    private String address;

    @Column(name = "Email", length = 50)
    private String email;

    @Column(name = "PhoneNumber")
    private String phoneNumber;

    @Column(name = "CharityName")
    private String charityName;

    @Column(name = "CharityAddress")
    private String charityAddress;

    @Column(name = "CharityPhone")
    private String charityPhone;

    @Column(name = "CharityEmail")
    private String charityEmail;

    @Column(name = "CharityMotto")
    private String charityMotto;

    @Column(name = "CharityTarget")
    private String charityTarget;

    @Column(name = "CharityDescription")
    private String charityDescription;

    @Column(name = "CharityFile")
    private String charityFile;

    @Column(name = "IsLocked")
    private Byte isLocked;

}