package com.example.saksham.blackoutrestuarant.user;

/**
 * Created by saksham_ on 22-Mar-18.
 */

public class Profile {
    private String name;

    private String address;

    Profile()
    {

    }
    Profile(String name,String address)
    {
        this.name=name;
        this.address=address;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;

    }
}

