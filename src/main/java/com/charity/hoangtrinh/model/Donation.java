package com.charity.hoangtrinh.model;

import com.charity.hoangtrinh.dbs.sql.charitydatabase.entities.Request;

import java.util.List;
import java.util.Objects;

public class Donation {
    private int id;
    private int idDonor;
    private String status;
    private String organizationReceived;
    private int idOrganization;
    private List<Request> requestList;
    private String name;
    private String donationAddress;
    private String donationObject;
    private String donorName;
    private long phone;
    private String address;
    private String province;
    private String district;
    private String ward;
    private String date;
    private String description;
    private List<String> images;

    public Donation() {
    }

    public Donation(int id, int idDonor, String status, String organizationReceived, int idOrganization, List<Request> requestList, String name, String donationAddress, String donationObject, String donorName, long phone, String address, String province, String district, String ward, String date, String description, List<String> images) {
        this.id = id;
        this.idDonor = idDonor;
        this.status = status;
        this.organizationReceived = organizationReceived;
        this.idOrganization = idOrganization;
        this.requestList = requestList;
        this.name = name;
        this.donationAddress = donationAddress;
        this.donationObject = donationObject;
        this.donorName = donorName;
        this.phone = phone;
        this.address = address;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.date = date;
        this.description = description;
        this.images = images;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdDonor() {
        return idDonor;
    }

    public void setIdDonor(int idDonor) {
        this.idDonor = idDonor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrganizationReceived() {
        return organizationReceived;
    }

    public void setOrganizationReceived(String organizationReceived) {
        this.organizationReceived = organizationReceived;
    }

    public int getIdOrganization() {
        return idOrganization;
    }

    public void setIdOrganization(int idOrganization) {
        this.idOrganization = idOrganization;
    }

    public List<Request> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<Request> requestList) {
        this.requestList = requestList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDonationAddress() {
        return donationAddress;
    }

    public void setDonationAddress(String donationAddress) {
        this.donationAddress = donationAddress;
    }

    public String getDonationObject() {
        return donationObject;
    }

    public void setDonationObject(String donationObject) {
        this.donationObject = donationObject;
    }

    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "Donation{" +
                "id=" + id +
                ", idDonor=" + idDonor +
                ", status='" + status + '\'' +
                ", organizationReceived='" + organizationReceived + '\'' +
                ", idOrganization=" + idOrganization +
                ", requestList=" + requestList +
                ", name='" + name + '\'' +
                ", donationAddress='" + donationAddress + '\'' +
                ", donationObject='" + donationObject + '\'' +
                ", donorName='" + donorName + '\'' +
                ", phone=" + phone +
                ", address='" + address + '\'' +
                ", province='" + province + '\'' +
                ", district='" + district + '\'' +
                ", ward='" + ward + '\'' +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", images=" + images +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Donation donation = (Donation) o;
        return id == donation.id && idDonor == donation.idDonor && idOrganization == donation.idOrganization && phone == donation.phone && Objects.equals(status, donation.status) && Objects.equals(organizationReceived, donation.organizationReceived) && Objects.equals(requestList, donation.requestList) && Objects.equals(name, donation.name) && Objects.equals(donationAddress, donation.donationAddress) && Objects.equals(donationObject, donation.donationObject) && Objects.equals(donorName, donation.donorName) && Objects.equals(address, donation.address) && Objects.equals(province, donation.province) && Objects.equals(district, donation.district) && Objects.equals(ward, donation.ward) && Objects.equals(date, donation.date) && Objects.equals(description, donation.description) && Objects.equals(images, donation.images);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idDonor, status, organizationReceived, idOrganization, requestList, name, donationAddress, donationObject, donorName, phone, address, province, district, ward, date, description, images);
    }
}
