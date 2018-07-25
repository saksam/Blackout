package com.example.saksham.blackoutrestuarant.item;

/**
 * Created by saksham_ on 28-May-18.
 */

public class UserAddressItem {

    private String phone;
    private String name;
    private String flatNo;
    private String street;
    private String landmark;
    private String pincode;
    private String city;

    public UserAddressItem()
    {

    }

    public UserAddressItem(String phone, String name, String flatNo, String street, String landmark, String pincode, String city) {
        this.phone = phone;
        this.name = name;
        this.flatNo = flatNo;
        this.street = street;
        this.landmark = landmark;
        this.pincode = pincode;
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
