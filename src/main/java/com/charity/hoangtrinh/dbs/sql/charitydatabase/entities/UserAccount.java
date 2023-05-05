package com.charity.hoangtrinh.dbs.sql.charitydatabase.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return Objects.equals(id, that.id) && Objects.equals(userName, that.userName) && Objects.equals(password, that.password) && Objects.equals(saltPassword, that.saltPassword) && Objects.equals(role, that.role) && Objects.equals(address, that.address) && Objects.equals(email, that.email) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(charityName, that.charityName) && Objects.equals(charityAddress, that.charityAddress) && Objects.equals(charityPhone, that.charityPhone) && Objects.equals(charityEmail, that.charityEmail) && Objects.equals(charityMotto, that.charityMotto) && Objects.equals(charityTarget, that.charityTarget) && Objects.equals(charityDescription, that.charityDescription) && Objects.equals(charityFile, that.charityFile) && Objects.equals(isLocked, that.isLocked);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, password, saltPassword, role, address, email, phoneNumber, charityName, charityAddress, charityPhone, charityEmail, charityMotto, charityTarget, charityDescription, charityFile, isLocked);
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", saltPassword='" + saltPassword + '\'' +
                ", role=" + role +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", charityName='" + charityName + '\'' +
                ", charityAddress='" + charityAddress + '\'' +
                ", charityPhone='" + charityPhone + '\'' +
                ", charityEmail='" + charityEmail + '\'' +
                ", charityMotto='" + charityMotto + '\'' +
                ", charityTarget='" + charityTarget + '\'' +
                ", charityDescription='" + charityDescription + '\'' +
                ", charityFile='" + charityFile + '\'' +
                ", isLocked=" + isLocked +
                '}';
    }
}