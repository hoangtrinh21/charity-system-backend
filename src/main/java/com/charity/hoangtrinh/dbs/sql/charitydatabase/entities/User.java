package com.charity.hoangtrinh.dbs.sql.charitydatabase.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private Integer phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "salt_password", nullable = false)
    private String saltPassword;

    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified = false;

    @Column(name = "image")
    private String image;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Lob
    @Column(name = "social")
    private String social;

    @Column(name = "charity_name")
    private String charityName;

    @Column(name = "charity_motto", length = 3000)
    private String charityMotto;

    @Column(name = "charity_target", length = 3000)
    private String charityTarget;

    @Column(name = "charity_description", length = 3000)
    private String charityDescription;

    @Column(name = "charity_file")
    private String charityFile;

}