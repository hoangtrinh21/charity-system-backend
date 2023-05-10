package com.charity.hoangtrinh.dbs.sql.charitydatabase.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "user_account")
public class UserAccount implements Serializable {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(userName, that.userName) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(password, that.password) && Objects.equals(saltPassword, that.saltPassword) && Objects.equals(roleId, that.roleId) && Objects.equals(address, that.address) && Objects.equals(email, that.email) && Objects.equals(isLocked, that.isLocked) && Objects.equals(charityId, that.charityId) && Objects.equals(province, that.province) && Objects.equals(district, that.district) && Objects.equals(ward, that.ward) && Objects.equals(provinceId, that.provinceId) && Objects.equals(districtId, that.districtId) && Objects.equals(wardId, that.wardId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, userName, phoneNumber, password, saltPassword, roleId, address, email, isLocked, charityId, province, district, ward, provinceId, districtId, wardId);
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userName='" + userName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", saltPassword='" + saltPassword + '\'' +
                ", roleId=" + roleId +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", isLocked=" + isLocked +
                ", charityId=" + charityId +
                ", province='" + province + '\'' +
                ", district='" + district + '\'' +
                ", ward='" + ward + '\'' +
                ", provinceId='" + provinceId + '\'' +
                ", districtId='" + districtId + '\'' +
                ", wardId='" + wardId + '\'' +
                '}';
    }
}